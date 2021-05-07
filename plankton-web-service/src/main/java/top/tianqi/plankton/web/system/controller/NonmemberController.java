package top.tianqi.plankton.web.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.web.common.controller.BaseController;
import top.tianqi.plankton.web.system.service.NonmemberService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 非会员controller
 * @author Wukh
 * @create 2021-01-19
 */
@RestController
@RequestMapping("/system/nonmember")
public class NonmemberController extends BaseController {

    @Resource(name = "nonmemberServiceImpl")
    private NonmemberService nonmemberService;

    /**
     * 加载数据列表
     * @return Result 前端提示信息
     */
    @GetMapping("page")
    public Result findPage(){
        return SUCCESS_MESSAGE();
    }

    /**
     * 报表统计，统计未注册用户数量型号分类
     * @return Result 前端提示信息
     */
    @GetMapping("visitCount")
    public Result visitCount(){
        Map<String, Object> data = new HashMap<>();
        // 获取未注册用户总数量
        Integer totalVisitCount = nonmemberService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        List<Map<String, Object>> modelVisitCount = nonmemberService.findModelVisitCount();
        data.put("modelVisitCount", modelVisitCount);
        return SUCCESS_MESSAGE(data);
    }
}
