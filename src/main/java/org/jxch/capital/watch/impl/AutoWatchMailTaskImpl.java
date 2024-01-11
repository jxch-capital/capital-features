package org.jxch.capital.watch.impl;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.mail.MyMailServiceImpl;
import org.jxch.capital.utils.AppContextHolder;
import org.jxch.capital.watch.ScheduledWatchTask;
import org.jxch.capital.watch.WatchMailTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoWatchMailTaskImpl implements ScheduledWatchTask {
    private final MyMailServiceImpl myMailService;

    @Override
    @Scheduled(cron = "0 0 16 * * ?")
    public void watchTask() {
        String htmlString = String.join("\n\n", AppContextHolder.getContext().getBeansOfType(WatchMailTask.class).values()
                .stream().map(WatchMailTask::watchTask).toList());
        myMailService.sendHtmlMail("市场简报 " + DateUtil.now(), htmlString);
    }

}
