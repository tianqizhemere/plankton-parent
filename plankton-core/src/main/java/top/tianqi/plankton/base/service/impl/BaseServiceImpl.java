package top.tianqi.plankton.base.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import top.tianqi.plankton.base.service.BaseService;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.status.ErrorStateEnum;
import top.tianqi.plankton.system.entity.User;

/**
 * service层超类
 * @author Wukh
 * @create 2021-01-04
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T>{

    @Override
    public User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new BusinessException(ErrorStateEnum.REQUEST_UNAUTHC_EXCEPTION);
        }
        return user;
    }
}
