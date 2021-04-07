package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 黑名单
 * @author Wukh
 * @create 2021-03-11
 */
@TableName("blacklist")
public class Blacklist extends BaseEntity {

    private static final long serialVersionUID = 2476438859930288464L;

    /** 开启状态，关闭 */
    public static final int CLOSE = 0;

    /** 开启状态，开启 */
    public static final int OPEN = 1;

    /** 黑名单ip */
    @NotBlank(message = "IP不能为空")
    private String ip;

    /** 请求URI */
    @NotBlank(message = "请求URI不能为空")
    private String requestUri;

    /** 请求方法，如果为ALL则表示对所有方法生效 */
    private String requestMethod;

    /** 限制时间起 */
    private String limitFrom;

    /** 限制时间止 */
    private String limitTo;

    /** ip对应地址 */
    private String location;

    /** 状态，0关闭，1开启 */
    private Integer isEnable;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getLimitFrom() {
        return limitFrom;
    }

    public void setLimitFrom(String limitFrom) {
        this.limitFrom = limitFrom;
    }

    public String getLimitTo() {
        return limitTo;
    }

    public void setLimitTo(String limitTo) {
        this.limitTo = limitTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}
