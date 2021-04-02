package top.tianqi.plankton.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Nonmember;
import top.tianqi.plankton.system.mapper.NonmemberMapper;
import top.tianqi.plankton.system.service.NonmemberService;

/**
 * 非会员服务层实现
 *
 * @author Wukh
 * @create 2021-01-19
 */
@Service
public class NonmemberServiceImpl extends BaseServiceImpl<NonmemberMapper, Nonmember> implements NonmemberService {

    @Autowired
    private NonmemberMapper nonmemberMapper;
}
