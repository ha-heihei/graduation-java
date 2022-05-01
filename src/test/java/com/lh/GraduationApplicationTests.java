package com.lh;

import com.lh.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class GraduationApplicationTests {

    @Resource
    private MailUtils mailUtils;

    @Resource
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Test
    void contextLoads() {
        System.out.println(cryptPasswordEncoder.encode("admin"));
    }

    @Test
    void mailTest(){
        mailUtils.sendHtmlMail("857269545@qq.com","图片分享","<h3>我的图片</h3><img src='https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-09/2f48d5da-3edd-431d-89c2-248d76ac85b5.jpg'/>");
    }

}
