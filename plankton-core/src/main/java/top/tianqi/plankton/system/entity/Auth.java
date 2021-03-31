package top.tianqi.plankton.system.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 用户权限
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("auth")
public class Auth extends BaseEntity  {

    private static final long serialVersionUID = 7556578432170650779L;

    /** 权限名称 */
    private String name;

    /** 权限值 */
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
