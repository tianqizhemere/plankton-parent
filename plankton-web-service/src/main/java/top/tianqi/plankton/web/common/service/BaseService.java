package top.tianqi.plankton.web.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.tianqi.plankton.core.system.entity.User;

/**
 * service基类接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 获取当前登录用户
     * @return currentUser
     */
    User getCurrentUser();
}
