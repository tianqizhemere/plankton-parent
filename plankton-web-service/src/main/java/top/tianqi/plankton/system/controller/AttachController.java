package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.system.entity.Attach;
import top.tianqi.plankton.system.service.AttachService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Result uploadImage(HttpServletRequest request, HttpServletResponse response){
        Integer dataType = Integer.valueOf(request.getParameter("dataType"));
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        List<Attach> list = attachService.uploadImage(files.values(), dataType);
        return Result.success(list);
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result uploadFile(HttpServletRequest request,HttpServletResponse response){
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
    @RequestMapping(value = "/getFileList", method = RequestMethod.GET)
    public Result getFileList(Long recordId, Integer dataType) {
        List<Attach> list = attachService.getFileList(recordId, dataType);
        return Result.success(null);
    }


    /**
     * 删除文件
     * @param recordId 记录ID
     * @param dataType 数据标识来源
     * @return Message 前端提示信息
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(BigInteger recordId, Integer dataType) {
        attachService.delete(recordId, dataType);
        return SUCCESS_MESSAGE();
    }

}
