package top.tianqi.plankton.base.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.status.StatusInfoEnum;
import top.tianqi.plankton.system.entity.User;

/**
 * service层超类
 * @author Wukh
 * @create 2021-01-04
 */
public class SuperService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 获取当前用户
     *
     * @return
     */
    public User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new BusinessException(StatusInfoEnum.REQUEST_UNAUTHC_EXCEPTION);
        }
        return user;
    }
}
