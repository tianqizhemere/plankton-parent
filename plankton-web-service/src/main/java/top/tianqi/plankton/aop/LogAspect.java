package top.tianqi.plankton.aop;

import cn.hutool.system.SystemUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import top.tianqi.plankton.common.base.annotation.aop.Operation;
import top.tianqi.plankton.system.entity.SysLog;
import top.tianqi.plankton.system.mapper.SysLogDao;

import java.lang.reflect.Method;

/**
 * 操作日志切面类
 * @author tianQi
 * @create 2021-01-07
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogDao sysLogDao;

    /** 在注解的位置切入代码 */
    @Pointcut("@annotation(top.tianqi.plankton.common.base.annotation.aop.Operation)")
    public void logPointCut(){
    }

    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();


        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            String value = operation.value();
            //保存获取的操作
            sysLog.setRequestDesc(value);
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();

        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setParams(params);
        }

        //注入Syslog对象
        //username应从session里取出
        sysLog.setUsername("Sinya");

        //IP地址获取工具为HUtooljar包,自行添加依赖
        sysLog.setUserIp(SystemUtil.getHostInfo().getAddress());
        sysLog.setRequestMethod(className + "." + methodName);

        sysLogDao.insert(sysLog);
    }

}
