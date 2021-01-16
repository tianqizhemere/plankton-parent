package top.tianqi.plankton.config.shiro.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.utils.Base64ConvertUtil;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * jwt工具类
 * @author tianQi
 * @create 2021-01-12
 */
@Component
public class JwtUtil {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 过期时间改为从配置文件获取
     */
    private static String accessTokenExpireTime;

    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey;

    @Value("${accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Value("${encryptJWTKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtUtil.encryptJWTKey = encryptJWTKey;
    }


    /**
     * 校验token是否正确
     * @param token 秘钥
     * @return boolean 是否正确
     */
    public static boolean verify(String token) {
        try {
            // ieml加JWT私钥解密
            String secret = getClaim(token, Constant.ACCOUNT) + Base64ConvertUtil.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            logger.error("JWTToken认证解密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new BusinessException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     * 获取imel(设备识别码)
     * 获取token中的信息无需secret解密也能获得
     * @param token 秘钥
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            logger.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw new BusinessException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 生成签名
     * @param ieml 设备识别号
     * @return 返回加密的Token
     */
    public static String sign(String ieml, String currentTimeMillis)  {
        try {
            // ieml加JWT私钥加密
            String secret = ieml + Base64ConvertUtil.decode(encryptJWTKey);
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带ieml信息
            return JWT.create()
                    .withClaim(Constant.ACCOUNT, ieml)
                    .withClaim("currentTimeMillis", currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            logger.error("JWTToken加密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new BusinessException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String sign = sign("9527", System.currentTimeMillis() + "");
        System.out.println("verify(sign) = " + verify(sign));
        String claim = getClaim(sign, Constant.ACCOUNT);
        System.out.println(claim);
    }

}
