package top.tianqi.plankton.web.remote.service;

/**
 * 邮件服务层接口
 * @author Wukh
 * @create 2021-05-07
 */
public interface MailService {

    /**
     * 发送邮件
     * @param title 标题
     * @param content 内容
     * @param toUsers 收件人
     */
    void send(String title, String content, String[] toUsers) throws Exception;
}
