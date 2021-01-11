package top.tianqi.plankton.common.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 禁用 session
 * @author Wukh
 * @create 2021-01-10
 */
public class CustomWebSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 禁用session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
