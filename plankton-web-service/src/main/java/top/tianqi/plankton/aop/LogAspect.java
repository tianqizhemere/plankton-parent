package top.tianqi.plankton.aop;

import cn.hutool.system.SystemUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.utils.JsonUtil;
import top.tianqi.plankton.system.entity.ExceptionLog;
import top.tianqi.plankton.system.entity.OperationLog;
import top.tianqi.plankton.system.service.ExceptionLogService;
import top.tianqi.plankton.system.service.OperationLogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志切面类
 * @author tianQi
 * @create 2021-01-07
 */
@Aspect
@Component
public class LogAspect {

    @Resource(name = "operationLogServiceImpl")
    private OperationLogService sysLogService;

    @Resource(name = "exceptionLogServiceImpl")
    private ExceptionLogService exceptionLogService;


    /** 设置操作日志切入点 记录操作日志 */
    @Pointcut("@annotation(top.tianqi.plankton.common.annotation.aop.OperLog)")
    public void logPointCut(){
    }

     /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
     @Pointcut("execution(* top.tianqi.plankton.*.controller..*.*(..))")
     public void operaExceptionLogPoinCut() {
     }

    /**
    * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
    *
    * @param joinPoint 切入点
    * @param keys      返回结果
    */
    @AfterReturning(value = "logPointCut()", returning = "keys")
    public void saveSysLog(JoinPoint joinPoint, Object keys) {

        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperationLog operlog = new OperationLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                operlog.setModel(opLog.operationModel()); // 操作模块
                operlog.setType(opLog.operationType()); // 操作类型
                operlog.setDesc(opLog.operationDesc()); // 操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            operlog.setMethod(methodName);

            // 请求的参数
            assert request != null;
            Map<String, String> rtnMap = convertMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JsonUtil.toJsonString(rtnMap);

            operlog.setRequestParam(params);
            operlog.setResponseParam(JsonUtil.toJsonString(keys)); // 返回结果
            operlog.setUserId(1L);
            operlog.setUsername("test");
            operlog.setIp(SystemUtil.getHostInfo().getAddress()); // 请求IP
            operlog.setUri(request.getRequestURI()); // 请求URI
            operlog.setCreateTime(new Date());
            operlog.setModifyTime(new Date());
            sysLogService.insert(operlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operaExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExceptionLog excepLog = new ExceptionLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数
            assert request != null;
            Map<String, String> rtnMap = convertMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JsonUtil.toJsonString(rtnMap);
            excepLog.setRequestParam(params); // 请求参数
            excepLog.setMethod(methodName); // 请求方法名
            excepLog.setName(e.getClass().getName()); // 异常名称
            excepLog.setMethod(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
            excepLog.setUserId(1L); // 操作员ID
            excepLog.setUsername("数据测试"); // 操作员名称
            excepLog.setUri(request.getRequestURI()); // 操作URI
            excepLog.setIp(SystemUtil.getHostInfo().getAddress()); // 操作员IP
            excepLog.setCreateTime(new Date()); // 发生异常时间
            excepLog.setModifyTime(new Date());
            exceptionLogService.insert(excepLog);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }


    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    private Map<String, String> convertMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    private String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
    }
}
