package top.tianqi.plankton.web.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.enumeration.ErrorStateEnum;
import top.tianqi.plankton.core.system.entity.Blacklist;
import top.tianqi.plankton.web.common.controller.BaseController;
import top.tianqi.plankton.web.system.service.BlacklistService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * 黑名单controller
 * @author Wukh
 * @create 2021-04-02
 */
@RestController
@RequestMapping("/system/blacklist")
public class BlacklistController extends BaseController {

    @Resource(name = "blacklistServiceImpl")
    private BlacklistService blacklistService;

    /**
     * 加载数据列表
     * @param ip ip
     * @param uri 访问URI
     * @param method 请求方式
     * @return Result 前端提信息
     */
    @GetMapping("list")
    public Result list(String ip, String uri, String method, Page<Blacklist> page){
        blacklistService.findPage(ip, uri, method, page);
        return SUCCESS_MESSAGE(page);
    }

    /**
     * 新增黑名单
     * @param blacklist 黑名单对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @PostMapping("save")
    public Result save(@Valid Blacklist blacklist, BindingResult result){
        if (result.hasErrors()){
            return Result.error(ErrorStateEnum.MISSING_PARAMETER.getCode(), result.getFieldError().getDefaultMessage());
        }
        blacklistService.save(blacklist);
        return SUCCESS_MESSAGE();
    }

    /**
     * 修改黑名单
     * @param blacklist 黑名单对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @PostMapping("update")
    public Result update(@Valid Blacklist blacklist, BindingResult result){
        if (result.hasErrors()){
            return Result.error(ErrorStateEnum.MISSING_PARAMETER.getCode(), result.getFieldError().getDefaultMessage());
        }
        blacklistService.updateById(blacklist);
        return SUCCESS_MESSAGE();
    }

    /**
     * 删除黑名单
     * @param ids 黑名单id数组
     * @return Result 前端提示信息
     */
    @PostMapping("delete")
    public Result delete(Long[] ids){
        blacklistService.removeByIds(Arrays.asList(ids));
        return SUCCESS_MESSAGE();
    }

}
