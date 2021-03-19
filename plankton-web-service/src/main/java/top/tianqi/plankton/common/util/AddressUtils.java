package top.tianqi.plankton.common.util;

import cn.hutool.core.io.IoUtil;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * IP地址工具类
 * @author Wukh
 * @create 2021-01-19
 */
public class AddressUtils {

    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public final static String ERROR_IP = "127.0.0.1";

    public final static Pattern pattern = Pattern.
            compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

    /**
     * 取外网IP
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        //过滤反向代理的ip
        String[] stemps = ip.split(",");
        if (stemps != null && stemps.length >= 1) {
            //得到第一个IP，即客户端真实IP
            ip = stemps[0];
        }
        ip = ip.trim();
        if (ip.length() > 23) {
            ip = ip.substring(0, 23);
        }
        return ip;
    }

    /**
     * 获取用户的真实ip
     *
     * @param request
     * @return
     */
    public static String getUserIP(HttpServletRequest request) {
        String ip = null;
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            // Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }
        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 根据ip获取地址
     * @param ip
     * @return
     */
    public static String getIpCity(String ip) throws IOException {
        if (!Util.isIpAddress(ip)) {
            log.error("Error: Invalid ip address");
            return null;
        }
        //db，读取resources目录下的ip2region.db
        ClassPathResource resource = new ClassPathResource("/ip2region.db");
        InputStream inputStream = resource.getInputStream();
        try {
            byte[] dbBinStr = IoUtil.readBytes(inputStream);
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbBinStr);
            // 查询算法memory，采用二进制方式初始化DBSearcher需要使用MEMORY_ALGORITYM，
            //否则会抛出null。
            Method method = searcher.getClass().getMethod("memorySearch", String.class);
            //（格式：国家|大区|省份|城市|运营商)
            String cityIpString = ((DataBlock) method.invoke(searcher, ip)).getRegion();
            StringBuilder addressBuild = new StringBuilder("");
            String[] splitIpArr = cityIpString.split("\\|");
            if (splitIpArr.length > 0) {
                addressBuild.append(splitIpArr[2]).append("-");
                addressBuild.append(splitIpArr[3]).append("-");
                addressBuild.append(splitIpArr[4]);
            }
            return addressBuild.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
