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
import top.tianqi.plankton.common.util.AddressUtils;
import top.tianqi.plankton.common.utils.JsonUtil;
import top.tianqi.plankton.system.entity.ExceptionLog;
import top.tianqi.plankton.system.entity.OperationLog;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.ExceptionLogService;
import top.tianqi.plankton.system.service.OperationLogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
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
                operlog.setModel(opLog.model()); // 操作模块
                operlog.setType(opLog.type()); // 操作类型
                operlog.setOperationDesc(opLog.desc()); // 操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            operlog.setMethod(methodName);

            // 获取请求参数
            Object[] args = joinPoint.getArgs();
            String params = "";
            String queryString = request.getQueryString();
            //获取请求参数集合并进行遍历拼接
            if(args.length>0){
                if("POST".equals(request.getMethod())){
                    Object object = args[0];
                    Map map = getKeyAndValue(object);
                    params = JsonUtil.toJsonString(map);
                }else if("GET".equals(request.getMethod())){
                    params = queryString;
                }
            }
            operlog.setRequestParam(params);
            operlog.setResponseParam(JsonUtil.toJsonString(keys)); // 返回结果
            User currentUser = sysLogService.getCurrentUser();
            if (currentUser == null) {
                currentUser = new User();
            }
            operlog.setUserId(currentUser.getId());
            operlog.setCode(currentUser.getCode());
            operlog.setIp(AddressUtils.getRemoteIp(request)); // 请求IP
            operlog.setUri(request.getRequestURI()); // 请求URI
            operlog.setCreateTime(new Date());
            operlog.setModifyTime(new Date());
            sysLogService.save(operlog);
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
            // 获取请求参数
            Object[] args = joinPoint.getArgs();
            String params = "";
            String queryString = request.getQueryString();
            //获取请求参数集合并进行遍历拼接
            if(args.length>0){
                if("POST".equals(request.getMethod())){
                    Object object = args[0];
                    Map map = getKeyAndValue(object);
                    params = JsonUtil.toJsonString(map);
                }else if("GET".equals(request.getMethod())){
                    params = queryString;
                }
            }
            excepLog.setRequestParam(params); // 请求参数
            excepLog.setMethod(methodName); // 请求方法名
            excepLog.setName(e.getClass().getName()); // 异常名称
            excepLog.setMethod(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
            User currentUser = sysLogService.getCurrentUser();
            if (currentUser == null) {
                currentUser = new User();
            }
            excepLog.setUserId(currentUser.getId()); // 操作员ID
            excepLog.setCode(currentUser.getCode()); // 操作员名称
            excepLog.setUri(request.getRequestURI()); // 操作URI
            excepLog.setIp(SystemUtil.getHostInfo().getAddress()); // 操作员IP
            excepLog.setCreateTime(new Date()); // 发生异常时间
            excepLog.setModifyTime(new Date());
            exceptionLogService.save(excepLog);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

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

    /**
     * 获取POST请求参数
     * @param obj
     * @return
     */
    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (Field field : fs) {
            field.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = field.get(obj);
                // 得到此属性的值
                map.put(field.getName(), val);// 设置键值
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
