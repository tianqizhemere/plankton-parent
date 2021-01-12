package top.tianqi.plankton.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * aop配置
 * @author Wukh
 * @create 2021-01-12
 */
@Aspect
@Component
public class AOPConfig {

    /** 创建时间 */
    private static final String CREATETIME = "createTime";

    /** 修改时间 */
    private static final String MODIFYTIME = "modifyTime";


    @Pointcut("execution(* top.tianqi.plankton.*.controller..*.save(..))")
    public void executeAddService() {};

    @Pointcut("execution(* top.tianqi.plankton.*.controller..*.update(..))")
    public void executeUpdateService() {};


    @Before("executeAddService()")
    public void beforeInsert(JoinPoint jp) {
        Object[] args = jp.getArgs();

        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            if (beanWrapper.isWritableProperty(CREATETIME)) {
                beanWrapper.setPropertyValue(CREATETIME, new Date());
            }
            if (beanWrapper.isWritableProperty(MODIFYTIME)) {
                beanWrapper.setPropertyValue(MODIFYTIME, new Date());
            }
        }
    }

    @Before("executeUpdateService()")
    public void beforeUpdate(JoinPoint jp) {
        Object[] args = jp.getArgs();

        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            if (beanWrapper.isWritableProperty(MODIFYTIME)) {
                beanWrapper.setPropertyValue(MODIFYTIME, new Date());
            }
        }
    }

}
