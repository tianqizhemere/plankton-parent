package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.system.entity.Attach;
import top.tianqi.plankton.system.service.AttachService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 文件controller
 * @author Wukh
 * @create 2021-01-12
 */
@RestController
@RequestMapping("/system/attach")
public class AttachController extends BaseController {

    @Resource(name = "attachServiceImpl")
    private AttachService attachService;

    @PostMapping(value = "/uploadFile")
    public Result uploadFile(HttpServletRequest request){
        Integer dataType = Integer.valueOf(request.getParameter("dataType"));
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        List<Attach> list = attachService.uploadFile(files.values(), dataType);
        return Result.success(list);
    }

    /**
     * 获取文件列表
     * @param recordId 记录ID
     * @param dataType 数据标识来源
     * @return List<AttachVO> 附件列表
     */
    @GetMapping(value = "/getFileList")
    public Result getFileList(Long recordId, Integer dataType) {
        List<Attach> list = attachService.getFileList(recordId, dataType);
        return Result.success(list);
    }

    /**
     * 删除文件
     * @param recordId 记录ID
     * @param dataType 数据标识来源
     * @return Message 前端提示信息
     */
    @PostMapping(value = "/delete")
    public Result delete(BigInteger recordId, Integer dataType) {
        attachService.delete(recordId, dataType);
        return SUCCESS_MESSAGE();
    }

}
