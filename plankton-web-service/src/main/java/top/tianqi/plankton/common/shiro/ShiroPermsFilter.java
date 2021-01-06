package top.tianqi.plankton.common.shiro;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 权限过滤器
 * @author Wukh
 * @create 2021-01-04
 */
public class ShiroPermsFilter  extends PermissionsAuthorizationFilter {

    /**
     * shiro 认证 perms 资源失败后回调方法
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return super.onAccessDenied(request, response);
    }

}