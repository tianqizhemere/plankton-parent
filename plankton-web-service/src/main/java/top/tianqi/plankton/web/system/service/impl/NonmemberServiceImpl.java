package top.tianqi.plankton.web.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.web.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.core.system.entity.Nonmember;
import top.tianqi.plankton.core.system.mapper.NonmemberMapper;
import top.tianqi.plankton.web.system.service.NonmemberService;

import java.util.List;
import java.util.Map;

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

    @Override
    public Integer findTotalVisitCount() {
        return nonmemberMapper.findTotalVisitCount();
    }

    @Override
    public List<Map<String, Object>> findModelVisitCount() {
        return nonmemberMapper.findModelVisitCount();
    }
}
