package top.tianqi.plankton.web.common.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.tianqi.plankton.core.BaseEntity;
import top.tianqi.plankton.core.common.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 基类controller
 * @author Wukh
 * @create 2021-01-04
 */
public class BaseController {

    Map<String,String> orderTypeMap = new HashMap<>(2);

    public BaseController(){
        orderTypeMap.put("asc","asc");
        orderTypeMap.put("desc","desc");
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * 文件下载
     * @param trueName 文件名称
     * @param downLoadPath 文件下载路径
     * @return ResponseEntity
     * @throws Exception
     */
    public ResponseEntity<byte[]> down(String trueName, String downLoadPath) throws Exception {
        HttpServletRequest request = getRequest();

        File file = new File(downLoadPath);
        HttpHeaders headers = new HttpHeaders();
        String fileName;

        try {
            String userAgent = request.getHeader("User-Agent");
            // 判断是否为IE
            if (userAgent.toUpperCase().indexOf("MSIE") > 0 || userAgent.toUpperCase().indexOf("TRIDENT") > 0) {
                fileName = URLEncoder.encode(trueName, "UTF-8");
            } else {
                fileName = new String(trueName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
        } catch (UnsupportedEncodingException e) {
            throw new Exception("文件名转码失败", e);
        }
        // 解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        byte[] bytes;
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new Exception("读取文件失败", e);
        }
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    /**
     * 获取分页对象
     * @return Page分页对象
     */
    public <T extends BaseEntity> Page<T> getPage() {
        HttpServletRequest request = getRequest();
        String pageNum = request.getParameter("pageNum");
        if (StringUtils.isEmpty(pageNum)) {
            pageNum = "1";
        }
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        return new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }

    /**
     * 获取分页对象
     * @param <T> T
     * @return Page分页对象
     */
    public <T extends  BaseEntity> Page<T> getPage(T t) {
        HttpServletRequest request = getRequest();
        String pageNum = request.getParameter("pageNum");
        if (StringUtils.isEmpty(pageNum)) {
            pageNum = "1";
        }
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        return new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }

    /**
     * 成功信息
     * @param data 返回对象
     * @return Result 前端提示信息
     */
    protected static Result SUCCESS_MESSAGE(Object data) {
        return Result.success("操作成功", data);
    }

    /**
     * 默认成功信息
     * @return Result 前端提示信息
     */
    protected static Result SUCCESS_MESSAGE() {
        return Result.success();
    }

    /**
     * 错误信息
     * @param msg 错误提示信息
     * @return Result 前端提示信息
     */
    protected static Result ERROR_MESSAGE(String msg) {
        return Result.error(msg);
    }

    /**
     * 错误信息
     * @return Result 前端提示信息
     */
    protected static Result ERROR_MESSAGE() {
        return Result.error();
    }

}
