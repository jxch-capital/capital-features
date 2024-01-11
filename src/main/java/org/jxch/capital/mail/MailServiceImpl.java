package org.jxch.capital.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class MailServiceImpl  implements MailService{
    private final MyMailServiceImpl myMailService;

    @Override
    public void sendHtmlMail(String title, String html) {
        // todo 暂时先给自己发，以后制作管理页面，手动添加邮箱管理，包括订阅的频道（WatchMailTask的子接口）
        myMailService.sendHtmlMail(title, html);
    }

}
