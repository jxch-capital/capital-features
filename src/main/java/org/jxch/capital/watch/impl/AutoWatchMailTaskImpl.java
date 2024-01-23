package org.jxch.capital.watch.impl;

import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.UserConfigDto;
import org.jxch.capital.mail.config.MailConfig;
import org.jxch.capital.server.UserConfigService;
import org.jxch.capital.utils.AppContextHolder;
import org.jxch.capital.watch.ScheduledWatchTask;
import org.jxch.capital.watch.WatchMailTask;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Deprecated
@RequiredArgsConstructor
public class AutoWatchMailTaskImpl implements ScheduledWatchTask {
    @Resource
    private JavaMailSenderImpl javaMailSender;
    private final MailConfig mailConfig;
    private final UserConfigService userConfigService;

    @Override
    @SneakyThrows
    @Scheduled(cron = "0 0 8 * * ?")
    public void watchTask() {
        List<WatchMailTask> watchMailTasks = AppContextHolder.getContext().getBeansOfType(WatchMailTask.class).values()
                .stream().sorted(Comparator.comparing(WatchMailTask::getOrder)).toList();

        for (UserConfigDto userConfigDto : userConfigService.findAll()) {
            Long userId = userConfigDto.getId();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailConfig.getUsername());
            helper.setTo(userConfigDto.getEmail());
            helper.setSubject("市场简报");

            List<String> htmlList = watchMailTasks.stream().map(task -> task.htmlBuild(userId, "")).toList();
            if (hasContent(htmlList)) {
                helper.setText(String.join("</hr>", htmlList), true);
                watchMailTasks.forEach(task -> task.addInline(userId, helper));
                javaMailSender.send(message);
                watchMailTasks.forEach(watchMailTask -> watchMailTask.clear(userId));
            }
        }

        watchMailTasks.forEach(WatchMailTask::clear);
    }

    private boolean hasContent(List<String> htmlList) {
        return Objects.nonNull(htmlList) && !htmlList.isEmpty() &&
                htmlList.stream().anyMatch(html -> Objects.nonNull(html) && !html.isBlank());
    }

}
