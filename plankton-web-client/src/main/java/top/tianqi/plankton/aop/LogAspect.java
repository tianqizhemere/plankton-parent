package top.tianqi.plankton.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.tianqi.plankton.common.annotation.aop.OperateLog;
import top.tianqi.plankton.common.utils.AddressUtils;
import top.tianqi.plankton.common.utils.JsonUtil;
import top.tianqi.plankton.system.entity.ExceptionLog;
import top.tianqi.plankton.system.entity.OperationLog;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.ExceptionLogService;
import top.tianqi.plankton.system.service.OperationLogService;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 操作日志切面类
 * @author tianQi
 * @create 2021-01-07
 */
@Aspect
@Component
public class LogAspect {

    @Resource(name = "operationLogServiceImpl")
    private OperationLogService operationLogService;

    @Resource(name = "exceptionLogServiceImpl")
    private ExceptionLogService exceptionLogService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /** 设置操作日志切入点 记录操作日志 */
    @Pointcut("@annotation(top.tianqi.plankton.common.annotation.aop.OperateLog)")
    public void logPointCut(){
    }

     /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
     @Pointcut("execution(* top.tianqi.plankton.*.controller..*.*(..))")
     public void operaExceptionLogPointCut() {
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

        OperationLog operationLog = new OperationLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperateLog opLog = method.getAnnotation(OperateLog.class);
            if (opLog != null) {
                operationLog.setModel(opLog.model()); // 操作模块
                operationLog.setType(opLog.type()); // 操作类型
                operationLog.setOperationDesc(opLog.desc()); // 操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            operationLog.setMethod(methodName);
            // 获取请求参数
            Object[] args = joinPoint.getArgs();
            String params = "";
            String queryString = request.getQueryString();
            //获取请求参数集合并进行遍历拼接
            if(args.length>0){
                if("POST".equals(request.getMethod())){
                    Object object = args[0];
                    Map<String, Object> map = getKeyAndValue(object);
                    params = JsonUtil.toJsonString(map);
                }else if("GET".equals(request.getMethod())){
                    params = queryString;
                }
            }
            operationLog.setRequestParam(params);
            operationLog.setResponseParam(JsonUtil.toJsonString(keys)); // 返回结果
            // 登录请求，解析参数获取请求对象， 因为登录没有调用shiro的登录，shiro的Principal对象为null
            if (Objects.equals("/login", request.getRequestURI())) {
                Map<String, Object> map = getKeyAndValue(args[0]);
                User user = userService.getUser(map.get("code").toString());
                operationLog.setCode(user.getCode());
                operationLog.setUserId(user.getId());
            } else {
                User currentUser = operationLogService.getCurrentUser();
                if (currentUser != null) {
                    operationLog.setUserId(currentUser.getId());
                    operationLog.setCode(currentUser.getCode());
                }
            }
            operationLog.setIp(AddressUtils.getRemoteIp(request)); // 请求IP
            operationLog.setUri(request.getRequestURI()); // 请求URI
            operationLogService.save(operationLog);
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
    @AfterThrowing(pointcut = "operaExceptionLogPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExceptionLog exceptionLog = new ExceptionLog();
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
            // 获取请求参数
            Object[] args = joinPoint.getArgs();
            String params = "";
            String queryString = request.getQueryString();
            // 获取请求参数集合并进行遍历拼接
            if(args.length>0){
                if("POST".equals(request.getMethod())){
                    Object object = args[0];
                    Map<String, Object> map = getKeyAndValue(object);
                    params = JsonUtil.toJsonString(map);
                }else if("GET".equals(request.getMethod())){
                    params = queryString;
                }
            }
            exceptionLog.setRequestParam(params); // 请求参数
            exceptionLog.setMethod(methodName); // 请求方法名
            // 异常名称
            exceptionLog.setName(e.getClass().getName());
            // 异常信息
            exceptionLog.setMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            // 异常提示信息
            exceptionLog.setErrorMessage(e.getMessage());
            if (Objects.equals("/login", request.getRequestURI())) {
                Map<String, Object> map = getKeyAndValue(args[0]);
                exceptionLog.setCode(map.get("code").toString());
            } else {
                User currentUser = operationLogService.getCurrentUser();
                if (currentUser != null) {
                    exceptionLog.setUserId(currentUser.getId());
                    exceptionLog.setCode(currentUser.getCode());
                }
            }
            exceptionLog.setUri(request.getRequestURI()); // 操作URI
            exceptionLog.setIp(AddressUtils.getRemoteIp(request)); // 请求IP
            exceptionLogService.save(exceptionLog);
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
    protected String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
    }

    /**
     * 获取POST请求参数
     * @param obj 请求参数对象
     * @return
     */
    protected static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (Field field : fs) {
            field.setAccessible(true); // 设置些属性是可以访问的
            Object val;
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
