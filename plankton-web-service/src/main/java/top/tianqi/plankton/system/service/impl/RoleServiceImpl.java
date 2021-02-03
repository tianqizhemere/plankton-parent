package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page<Role> findPage(String name, Page<Role> page) {
        LambdaQueryWrapper<Role> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.like(StringUtils.isNotBlank(name),Role::getName, name);
        return roleMapper.selectPage(page, roleWrapper);
    }
}
