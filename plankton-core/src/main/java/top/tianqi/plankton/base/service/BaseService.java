package top.tianqi.plankton.base.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.IService;
import top.tianqi.plankton.system.entity.User;

/**
 * service基类接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface BaseService<M extends BaseMapper<T>, T> extends IService<T> {

    /**
     * 获取当前用户
     * @return currentUser
     */
    User getCurrentUser();
}
