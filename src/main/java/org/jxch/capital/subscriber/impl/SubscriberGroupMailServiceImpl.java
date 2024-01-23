package org.jxch.capital.subscriber.impl;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.domain.dto.SubscriberConfigGroupDto;
import org.jxch.capital.domain.dto.UserConfigDto;
import org.jxch.capital.domain.dto.UserSubscriberDto;
import org.jxch.capital.mail.config.MailConfig;
import org.jxch.capital.server.UserConfigService;
import org.jxch.capital.subscriber.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberGroupMailServiceImpl implements SubscriberGroupService {
    @Resource
    private JavaMailSenderImpl javaMailSender;
    private final MailConfig mailConfig;
    private final SubscriberConfigGroupService subscriberConfigGroupService;
    private final SubscriberConfigService subscriberConfigService;
    private final UserConfigService userConfigService;
    private final UserSubscriberService userSubscriberService;

    @Override
    public void subscribe(@NonNull UserSubscriberDto userSubscriberDto) {
        try {
            UserConfigDto user = userConfigService.findById(userSubscriberDto.getUserId());
            SubscriberConfigGroupDto groupDto = subscriberConfigGroupService.findById(userSubscriberDto.getSubscriberConfigGroupId());
            List<SubscriberConfigDto> subscriberConfigs = subscriberConfigService.findById(groupDto.getSubscriberConfigIds());
            Map<Long, SubscriberConfigDto> configMap = subscriberConfigs.stream().collect(Collectors.toMap(SubscriberConfigDto::getId, Function.identity()));

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailConfig.getUsername());
            helper.setTo(user.getEmail());
            helper.setSubject(groupDto.getName());

            helper.setText(groupDto.getSubscriberConfigIds().stream().map(configMap::get)
                    .map(config -> Subscribers.getSubscriber(MailSubscriber.class, config.getService()).mailHtml(config))
                    .collect(Collectors.joining("</hr>")), true);
            subscriberConfigs.forEach(config -> Subscribers.getSubscriber(MailSubscriber.class, config.getService()).addInline(config, helper));
            javaMailSender.send(message);
            subscriberConfigs.forEach(config -> Subscribers.getSubscriber(MailSubscriber.class, config.getService()).clear(config));

            userSubscriberDto.setLastSubscribeTime(Calendar.getInstance().getTime());
            userSubscriberService.save(Collections.singletonList(userSubscriberDto));
        } catch (MessagingException e) {
            log.error("邮件发送失败：{}", JSONObject.toJSONString(userSubscriberDto));
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Subscriber> supportSubscribers() {
        return Subscribers.allSubscribers(MailSubscriber.class).stream()
                .map(mailSubscriber -> (Subscriber) mailSubscriber).toList();
    }

    @Override
    public String name() {
        return "邮件订阅组";
    }

}
