package org.jxch.capital.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Slf4j
@SpringBootTest
class MyMailServiceImplTest {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Test
    public void test() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("测试邮件标题"); // 邮件标题
        message.setText("正文内容"); // 邮件内容
        message.setTo("xicheng_jiang@outlook.com"); // 收件人邮箱地址
        message.setFrom("xicheng_jiang@outlook.com"); // 你的Outlook邮箱地址

        mailSender.send(message);
    }

}