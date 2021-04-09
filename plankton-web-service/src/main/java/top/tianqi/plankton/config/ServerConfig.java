package top.tianqi.plankton.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取当前服务的IP端口
 * @author Wukh
 * @create 2021-03-23
 */
@Component
public class ServerConfig {

    /** 服务端口 */
    private static int serverPort;

    /** 访问地址 */
    private static String serverUrl;

    /** 静态注入 */
    @SuppressWarnings("unused")
    private static ServerConfig serverConfig;

    @Autowired
    public void setServerConfig(ServerConfig serverConfig){
        ServerConfig.serverConfig = serverConfig;
    }

    @Value("${server.port}")
    public void setServerPort(Integer serverPort) {
        ServerConfig.serverPort = serverPort;
    }

    @Value("${server.url}")
    public void setServerUrl(String serverUrl) {
        ServerConfig.serverUrl = serverUrl;
    }

    public static String getUrl() {
        return serverUrl + ":" + serverPort;
    }

}
