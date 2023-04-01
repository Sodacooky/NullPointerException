package soda.npe.servicemiscellaneous.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.utils.MailUtil;

@RestController
public class Test {

    @Resource
    private MailUtil mailUtil;

    @GetMapping("/send")
    public Boolean send(String to, String title,String content){
        return mailUtil.sendEmail(to,title,content);
    }

}
