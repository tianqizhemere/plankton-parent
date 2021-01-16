package top.tianqi.plankton.common.base.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.tianqi.plankton.base.entity.BaseEntity;
import top.tianqi.plankton.common.Result;

import javax.servlet.http.HttpServletRequest;
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
     * 获取分页对象
     * @return Page分页对象
     */
    public <T extends BaseEntity> Page<T> getPage() {
        HttpServletRequest request = getRequest();
        String pageNo = request.getParameter("pageNo");
        if (StringUtils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        return new Page<>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
    }

    /**
     * 获取分页对象
     * @param <T> T
     * @return Page分页对象
     */
    public <T extends  BaseEntity> Page<T> getPage(T t) {
        HttpServletRequest request = getRequest();
        String pageNo = request.getParameter("pageNo");
        if (StringUtils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        return new Page<T>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
    }



    /**
     * 成功信息
     * @param data 返回对象
     * @return
     */
    protected static Result SUCCESS_MESSAGE(Object data) {
        return Result.success("操作成功", data);
    }

    protected static Result SUCCESS_MESSAGE() {
        return SUCCESS_MESSAGE(null);
    }

    /**
     * 错误信息
     * @param msg
     * @return
     */
    protected static Result ERROR_MESSAGE(String msg) {
        return Result.error(msg);
    }

    /**
     * 错误信息
     * @return
     */
    protected static Result ERROR_MESSAGE() {
        return Result.error();
    }

}
