package soda.npe.servicequestion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;


/**
 * 对问题和答案发布、修改、点赞订阅等操作
 */
@RestController
@RequestMapping("/operation")
public class OperationController {

    @GetMapping("/subscription")
    public RestResponse subscription(Long questionId, @RequestHeader("Authorization") String token) {
        //校验token并从中获取当前用户ID
    }

}
