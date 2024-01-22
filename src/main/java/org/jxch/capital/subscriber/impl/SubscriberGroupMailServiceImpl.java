package org.jxch.capital.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.subscriber.MailSubscriber;
import org.jxch.capital.subscriber.Subscriber;
import org.jxch.capital.subscriber.SubscriberGroupService;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberGroupMailServiceImpl implements SubscriberGroupService {



    @Override
    public List<Subscriber> supportSubscribers() {
        return AppContextHolder.getContext().getBeansOfType(MailSubscriber.class).values().stream()
                .map(mailSubscriber -> (Subscriber) mailSubscriber).toList();
    }

    @Override
    public String name() {
        return "邮件订阅组";
    }

}
