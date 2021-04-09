package top.tianqi.plankton.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.core.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.core.system.entity.Role;
import top.tianqi.plankton.core.system.mapper.RoleMapper;
import top.tianqi.plankton.core.system.service.RoleService;

/**
 * 角色服务层实现
 *
 * @author Wukh
 * @create 2021-01-24
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page<Role> findPage(String name, Page<Role> page) {
        LambdaQueryWrapper<Role> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.like(StringUtils.isNotBlank(name), Role::getName, name);
        roleWrapper.orderByDesc(Role::getCreateTime);
        return roleMapper.selectPage(page, roleWrapper);
    }
}
