package top.tianqi.plankton.system.service.impl;

import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Role;
import top.tianqi.plankton.system.mapper.RoleMapper;
import top.tianqi.plankton.system.service.RoleService;

/**
 * 角色服务层实现
 * @author Wukh
 * @create 2021-01-24
 */
@Service(value = "roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {
}
