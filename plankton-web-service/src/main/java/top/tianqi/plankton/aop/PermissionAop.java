package top.tianqi.plankton.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import top.tianqi.plankton.common.annotation.aop.shiro.PermissionModule;
import top.tianqi.plankton.common.annotation.aop.shiro.PermissionOperate;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;

/**
 * shiro权限校验
 * @author Wukh
 * @create 2021-01-10
 */
@Aspect
@Component
public class PermissionAop {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Before(value="@annotation(operate)")
    public void permission(JoinPoint jp, PermissionOperate operate) throws Throwable{
        Class aspectClz = jp.getTarget().getClass();
        if(aspectClz.isAnnotationPresent(PermissionModule.class)){
            PermissionModule module = (PermissionModule) aspectClz.getAnnotation(PermissionModule.class);
            String[] operates = operate.value();
            String[] checks = new String[operates.length];
            for(int i = 0 ; i < operates.length ; i++){
                checks[i] = module.value()+":"+operates[i];
            }
            //if(!UserUtil.isPermitted(operate.relation(),checks))
            //    throw new UnauthorizedException("用户:["+UserUtil.getName()+"]正在越权访问资源:"+ ArraysUtil.joinStringArray(Arrays.asList(operates),","));
        }
    }
}
