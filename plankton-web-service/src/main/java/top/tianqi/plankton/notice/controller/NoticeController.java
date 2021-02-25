package top.tianqi.plankton.notice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.notice.entity.Notice;
import top.tianqi.plankton.notice.service.NoticeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知Controller
 * @author Wukh
 * @create 2021-02-21
 */
@RestController
@RequestMapping(value = "/notice/notice")
public class NoticeController extends BaseController {

    @Resource(name = "noticeServiceImpl")
    private NoticeService noticeService;

    @GetMapping(value = "/page")
    public Result getPage(String title) {
        IPage<Notice> page = noticeService.getPage(title, getPage());
        return SUCCESS_MESSAGE(page);
    }

    @OperLog(model = "通知管理", desc = "新增通知", type = OperationConst.INSERT)
    @RequiresPermissions(value = "notice:notice:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody Notice notice, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        noticeService.save(notice);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "通知管理", desc = "修改通知", type = OperationConst.UPDATE)
    @RequiresPermissions(value = "notice:notice:update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody Notice notice, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        noticeService.updateById(notice);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "通知管理", desc = "删除通知", type = OperationConst.DELETE)
    @RequiresPermissions(value = "notice:notice:delete")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody List<Notice> notices){
        List<Long> ids = notices.stream().map(Notice::getId).collect(Collectors.toList());
        noticeService.removeByIds(ids);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "通知管理", desc = "获取用户通知", type = OperationConst.SELECT)
    @GetMapping(value = "/get")
    public Result getUserNotice(String model, String version){
        Notice notice = noticeService.getUserNotice(model, version);
        return SUCCESS_MESSAGE(notice);
    }
}
