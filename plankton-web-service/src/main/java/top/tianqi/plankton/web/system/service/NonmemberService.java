package top.tianqi.plankton.web.system.service;

import top.tianqi.plankton.web.common.service.BaseService;
import top.tianqi.plankton.core.system.entity.Nonmember;

import java.util.List;
import java.util.Map;

/**
 * 非会员服务层接口
 *
 * @author Wukh
 * @create 2021-01-19
 */
public interface NonmemberService extends BaseService<Nonmember> {

    /**
     * 获取未注册用户数量
     * @return
     */
    Integer findTotalVisitCount();

    /**
     * 统计各型号未注册用户数量
     * @return
     */
    List<Map<String, Object>> findModelVisitCount();

}
