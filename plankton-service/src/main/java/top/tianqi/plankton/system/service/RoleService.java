package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.base.service.BaseService;
import top.tianqi.plankton.system.entity.Role;

/**
 * 角色服务层接口
 *
 * @author Wukh
 * @create 2021-01-24
 */
public interface RoleService extends BaseService<Role> {

    Page<Role> findPage(String name, Page<Role> page);
}
