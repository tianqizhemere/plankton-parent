package top.tianqi.plankton.web.system.service;

import top.tianqi.plankton.web.common.service.BaseService;
import top.tianqi.plankton.core.system.entity.Auth;

import java.util.Set;

/**
 * 用户权限服务层接口
 *
 * @author Wukh
 * @create 2021-01-04
 */
public interface AuthService extends BaseService<Auth> {

    /**
     * 根据用户 id 获取权限
     *
     * @param userId 用户id
     * @return Set<String> 权限列表
     */
    Set<String> getUserAuthListById(Long userId);
}
