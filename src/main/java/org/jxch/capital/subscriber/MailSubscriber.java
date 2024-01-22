package org.jxch.capital.subscriber;

import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.springframework.mail.javamail.MimeMessageHelper;

public interface MailSubscriber extends Subscriber {

    String mailHtml(SubscriberConfigDto configDto);

    void addInline(MimeMessageHelper helper);

    void clear();

}
