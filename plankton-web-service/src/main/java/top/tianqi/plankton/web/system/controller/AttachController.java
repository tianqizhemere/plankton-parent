package top.tianqi.plankton.web.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.tianqi.plankton.config.ServerConfig;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.system.entity.Attach;
import top.tianqi.plankton.web.common.controller.BaseController;
import top.tianqi.plankton.web.system.service.AttachService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.net.ConnectException;
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

    /**
     * 文件上传
     * @param request HttpServletRequest
     * @return Result 前端提示信息
     */
    @PostMapping("uploadFile")
    public Result uploadFile(HttpServletRequest request){
        Integer dataType = Integer.valueOf(request.getParameter("dataType"));
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        List<Attach> list = attachService.uploadFile(files.values(), dataType);
        return Result.success(list);
    }

    /**
     * 获取文件列表
     * @param recordId 关联记录ID
     * @param dataType 数据标识来源
     * @return Result 前端提示信息
     */
    @GetMapping("getFileList")
    public Result getFileList(Long recordId, Integer dataType) {
        List<Attach> list = attachService.getFileList(recordId, dataType);
        return Result.success(list);
    }

    /**
     * 删除文件
     * @param recordId 记录ID
     * @param dataType 数据标识来源
     * @return Result 前端提示信息
     */
    @PostMapping("delete")
    public Result delete(BigInteger recordId, Integer dataType) {
        attachService.delete(recordId, dataType);
        return SUCCESS_MESSAGE();
    }

    /**
     * 获取服务器完整路径
     * @return Result 前端提示信息
     */
    @GetMapping("getServerUrl")
    public Result getServerUrl(){
        return SUCCESS_MESSAGE(ServerConfig.getUrl());
    }

    /**
     * 文件转换
     * @param srcPath 文件路径
     * @return Message 前端提示信息
     * @throws ConnectException openOffice连接异常
     */
    @GetMapping("transitionFile")
    public Result transitionFile(String srcPath) throws ConnectException {
        logger.info("openOffice转换开始>>>>>>>>>>>>>");
        srcPath = attachService.transitionFile(srcPath);
        logger.info("openOffice转换结束>>>>>>>>>>>>>");
        return SUCCESS_MESSAGE(srcPath);
    }
}
