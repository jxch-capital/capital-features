package org.jxch.capital.mail;

import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.mail.config.MailConfig;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyMailServiceImpl implements MailService {
    @Resource
    private JavaMailSenderImpl mailSender;
    @Resource
    private MailConfig mailConfig;

    @Override
    @SneakyThrows
    public void sendHtmlMail(String title, String html) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(mailConfig.getUsername());
        helper.setSubject(title);
        helper.setText(html, true);
        mailSender.send(message);
    }

}
