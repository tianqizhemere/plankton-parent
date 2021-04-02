package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.controller.BaseController;
import top.tianqi.plankton.system.service.BlacklistService;

import javax.annotation.Resource;

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
     * @return Result 前端提信息
     */
    @GetMapping("list")
    public Result list(String ip, String uri, String method){
        blacklistService.findPage(ip, uri, method, getPage());
        return SUCCESS_MESSAGE(getPage());
    }
}
