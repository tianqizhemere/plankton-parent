package top.tianqi.plankton.core.notice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.annotation.aop.OperateLog;
import top.tianqi.plankton.core.common.controller.BaseController;
import top.tianqi.plankton.core.common.constant.OperationConst;
import top.tianqi.plankton.core.notice.entity.Notice;
import top.tianqi.plankton.core.notice.service.NoticeService;

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
@RequestMapping("/notice/notice")
public class NoticeController extends BaseController {

    @Resource(name = "noticeServiceImpl")
    private NoticeService noticeService;

    /**
     * 加载数据列表
     * @param title 通知标题
     * @return Result 前端提示信息
     */
    @GetMapping("page")
    public Result getPage(String title) {
        IPage<Notice> page = noticeService.getPage(title, getPage());
        return SUCCESS_MESSAGE(page);
    }

    /**
     * 发布通知
     * @param notice 通知对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.NOTICE_MODEL, desc = "新增通知", type = OperationConst.INSERT)
    @RequiresPermissions(value = "notice:notice:save")
    @PostMapping("save")
    public Result save(@Valid @RequestBody Notice notice, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        noticeService.save(notice);
        return SUCCESS_MESSAGE();
    }

    /**
     * 修改通知
     * @param notice 通知对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.NOTICE_MODEL, desc = "修改通知", type = OperationConst.UPDATE)
    @RequiresPermissions(value = "notice:notice:update")
    @PostMapping("update")
    public Result update(@Valid @RequestBody Notice notice, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        noticeService.updateById(notice);
        return SUCCESS_MESSAGE();
    }

    /**
     * 删除通知
     * @param notices 通知对象集合
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.NOTICE_MODEL, desc = "删除通知", type = OperationConst.DELETE)
    @RequiresPermissions(value = "notice:notice:delete")
    @PostMapping("delete")
    public Result delete(@RequestBody List<Notice> notices){
        List<Long> ids = notices.stream().map(Notice::getId).collect(Collectors.toList());
        noticeService.removeByIds(ids);
        return SUCCESS_MESSAGE();
    }

    /**
     * 获取当前用户未读的通知
     * @param model 机型
     * @param version 版本号
     * @return Result 前端提示信息
     */
    @OperateLog(model = "通知管理", desc = "获取用户通知", type = OperationConst.SELECT)
    @GetMapping("get")
    public Result getUserNotice(String model, String version){
        Notice notice = noticeService.getUserNotice(model, version);
        return SUCCESS_MESSAGE(notice);
    }
}
