package top.tianqi.plankton.core.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.tianqi.plankton.core.system.entity.Nonmember;

import java.util.List;
import java.util.Map;

/**
 * 非会员持久层接口
 * @author Wukh
 * @create 2021-01-19
 */
public interface NonmemberMapper extends BaseMapper<Nonmember> {

    /**
     * 统计未注册用户的总数量
     * @return 总数量
     */
    Integer findTotalVisitCount();

    List<Map<String, Object>> findModelVisitCount();
}
