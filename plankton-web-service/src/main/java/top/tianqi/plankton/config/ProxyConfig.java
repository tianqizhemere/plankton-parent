package top.tianqi.plankton.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网络代理设置
 * @author Wukh
 * @create 2021-04-22
 */
@Configuration
@ConfigurationProperties(prefix = "proxy")
public class ProxyConfig {

    /** 是否启用代理 */
    private Boolean enabled;

    /** 代理主机地址 */
    private String host;

    /** 代理端口 */
    private Integer port;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
