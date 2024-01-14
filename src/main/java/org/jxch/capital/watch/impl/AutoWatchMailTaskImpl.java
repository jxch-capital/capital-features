package org.jxch.capital.watch.impl;

import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.mail.config.MailConfig;
import org.jxch.capital.utils.AppContextHolder;
import org.jxch.capital.watch.ScheduledWatchTask;
import org.jxch.capital.watch.WatchMailTask;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoWatchMailTaskImpl implements ScheduledWatchTask {
    @Resource
    private JavaMailSenderImpl javaMailSender;
    private final MailConfig mailConfig;

    @Override
    @SneakyThrows
    @Scheduled(cron = "0 0 16 * * ?")
    public void watchTask() {
        List<WatchMailTask> watchMailTasks = AppContextHolder.getContext().getBeansOfType(WatchMailTask.class).values()
                .stream().sorted(Comparator.comparing(WatchMailTask::getOrder)).toList();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailConfig.getUsername());
        helper.setTo(mailConfig.getUsername());
        helper.setSubject("市场简报");

        helper.setText(String.join("</hr>", watchMailTasks.stream().map(task -> task.htmlBuild("")).toList()), true);
        watchMailTasks.forEach(task -> task.addInline(helper));
        javaMailSender.send(message);
        watchMailTasks.forEach(WatchMailTask::clear);
    }

}
