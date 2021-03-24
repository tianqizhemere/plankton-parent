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

    private static int serverPort;

    private static String serverUrl;

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
