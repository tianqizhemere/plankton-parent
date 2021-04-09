package top.tianqi.plankton.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.constant.Constant;
import top.tianqi.plankton.util.JedisUtil;
import top.tianqi.plankton.shiro.token.JwtToken;
import top.tianqi.plankton.util.AddressUtils;
import top.tianqi.plankton.core.common.utils.JsonUtil;
import top.tianqi.plankton.core.common.utils.PropertiesUtil;
import top.tianqi.plankton.util.JwtUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT过滤器
 * @author tianQi
 * @create 2021-01-12
 */
public class JwtFilter extends BasicHttpAuthenticationFilter implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    /** app应用名称 */
    private static final String APPLICATION_NAME = "TFingerprint";

    /** 排除拦截的请求 */
    @Value("${jwt.anonymous.urls}")
    private String anonymousStr;

    /**
     * 执行登录认证(判断用户是否想要登入)
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = this.getAuthzHeader(request);
        log.info("用户是否想要登录：{}", token);
        return token != null;
    }

    /**
     * 执行登录
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        JwtToken token = new JwtToken(this.getAuthzHeader(request));
        this.getSubject(request, response).login(token);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return this.sendChallenge(request, response);
    }

    /**
     * 验证是否登录
     * 验证是否为放行路径
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // 获取当前请求类型
        String httpMethod = httpServletRequest.getMethod();
        // 请求IP
        String remoteIp = AddressUtils.getRemoteIp(httpServletRequest);
        // 获取当前请求URI
        String requestURI = httpServletRequest.getRequestURI();
        // 检查请求头是否包含有 appName
        String appName = httpServletRequest.getHeader("appName");
        if (JwtFilter.APPLICATION_NAME.equals(appName)) {
            log.info("外置应用请求头 {} 请求路径 {} 请求ip {}", appName, requestURI, remoteIp);
            return true;
        }
        for (String anonymous : anonymousStr.split(",")) {
            if (requestURI.contains(anonymous)) {
                return true;
            }
        }
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
                Throwable throwable = e.getCause();
                if (throwable instanceof SignatureVerificationException) {
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable instanceof TokenExpiredException) {
                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
                    }
                } else {
                    if (throwable != null) {
                        // 获取应用异常msg
                        msg = throwable.getMessage();
                    }
                }
                // Token认证失败直接返回Response信息
                this.response401(response, msg);
                return false;
            }
        } else {
            // 没有携带Token
            String userIP = AddressUtils.getUserIP(httpServletRequest);
            log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {} 外网请求IP {} 实际IP {} ", requestURI, httpMethod, remoteIp, userIP);
            this.response401(response, "请先登录");
            return false;
        }
        return true;
    }

    /**
     * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        String token = this.getAuthzHeader(request);
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        // 判断Redis中RefreshToken是否存在
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // Redis中RefreshToken还存在，获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                // 获取当前最新时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                // 读取配置文件，获取refreshTokenExpireTime属性
                PropertiesUtil.readProperties("config.properties");
                String refreshTokenExpireTime = PropertiesUtil.getProperty("refreshTokenExpireTime");
                // 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
                JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
                // 刷新AccessToken，设置时间戳为当前最新时间戳
                token = JwtUtil.sign(account, currentTimeMillis);
                // 将新刷新的AccessToken再次进行Shiro的登录
                JwtToken jwtToken = new JwtToken(token);
                // 提交给realm进行认证，如果没有抛出异常则代表登入成功
                this.getSubject(request, response).login(jwtToken);
                // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setHeader("Authorization", token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                return true;
            }
        }
        return false;
    }

    /**
     * 返回Response信息
     */
    private void response401(ServletResponse resp, String msg) {
        try {
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json;charset=utf-8");
            Result result = Result.error(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + msg);
            httpResponse.getWriter().write(JsonUtil.toJsonString(result));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
