package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.base.service.BaseService;
import top.tianqi.plankton.system.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 用户服务层接口
 *
 * @author Wukh
 * @create 2021-01-04
 */
public interface UserService extends BaseService<User> {

    /**
     * 根据code获取user
     *
     * @param code 用户唯一标识
     * @return
     */
    User getUser(String code);

    /**
     * 分页查询
     *
     * @param code   用户唯一标识
     * @param phone  手机号码
     * @param qq     QQ
     * @param models 设备型号
     * @param page   分页对象
     * @return Page<User> 用户分页对象
     */
    Page<User> getPage(String code, String phone, String qq, List<String> modelList, Page<User> page);

    /**
     * 通过用户名获取用户角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    Set<String> getUserRoles(Long userId);

    /**
     * 通过用户名获取用户权限集合
     *
     * @param userId 用户id
     * @return 权限集合
     */
    Set<String> getUserPermissions(Long userId);

    /**
     * 根据code列表查询用户
     *
     * @param codes code列表
     * @param page  分页对象
     * @return IPage<User>
     */
    IPage<User> selectByCodes(List<String> codes, Page<User> page);
}
