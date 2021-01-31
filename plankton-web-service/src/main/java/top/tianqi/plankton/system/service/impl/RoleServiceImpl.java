package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.Role;
import top.tianqi.plankton.system.mapper.RoleMapper;
import top.tianqi.plankton.system.service.RoleService;

import java.util.List;

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
    public PageResult findPage(String name, Page<Role> page) {
        List<Role> list = roleMapper.findPage(name, page);
        return new PageResult(page.getCurrent(), page.getSize(),  page.getTotal() , page.getPages(), list);
    }
}
