package soda.npe.common.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailException;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    private final MailAccount mailAccount;

    //construct
    public MailUtil() {
        mailAccount = new MailAccount();
        mailAccount.setHost("smtp.qq.com");
        mailAccount.setPort(465);
        mailAccount.setSslEnable(true);
        mailAccount.setAuth(true);
        mailAccount.setFrom("hibin_org@qq.com");
        mailAccount.setUser("hibin_org@qq.com");
        mailAccount.setPass("");
    }

    public boolean sendEmail(String goalAddress, String title, String content) {
        try {
            cn.hutool.extra.mail.MailUtil.send(mailAccount, goalAddress, title, content, false);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }

}
