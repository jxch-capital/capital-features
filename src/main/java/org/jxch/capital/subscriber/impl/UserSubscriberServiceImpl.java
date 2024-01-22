package org.jxch.capital.subscriber.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.UserSubscriberRepository;
import org.jxch.capital.domain.convert.UserSubscriberMapper;
import org.jxch.capital.domain.dto.UserSubscriberDto;
import org.jxch.capital.subscriber.SubscriberConfigGroupService;
import org.jxch.capital.subscriber.SubscriberGroupService;
import org.jxch.capital.subscriber.UserSubscriberService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserSubscriberServiceImpl implements UserSubscriberService, ApplicationRunner {
    private final UserSubscriberMapper userSubscriberMapper;
    private final UserSubscriberRepository userSubscriberRepository;
    private final SubscriberConfigGroupService subscriberConfigGroupService;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final Map<Long, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    @Override
    public List<UserSubscriberDto> findAll() {
        return userSubscriberMapper.toUserSubscriberDto(userSubscriberRepository.findAll());
    }

    @Override
    public UserSubscriberDto findById(Long id) {
        return userSubscriberMapper.toUserSubscriberDto(userSubscriberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("没有这个ID: " + id)));
    }

    @Override
    public void delById(List<Long> ids) {
        userSubscriberRepository.deleteAllById(ids);
        ids.forEach(id -> {
            if (taskMap.containsKey(id)) {
                taskMap.get(id).cancel(true);
                taskMap.remove(id);
            }
        });
    }

    @Override
    public Integer save(List<UserSubscriberDto> dtoList) {
        List<UserSubscriberDto> userSubscribers = userSubscriberMapper.toUserSubscriberDto(userSubscriberRepository.saveAllAndFlush(userSubscriberMapper.toUserSubscriber(dtoList)));
        refreshAndPutTask(userSubscribers);
        return userSubscribers.size();
    }

    @Override
    public List<UserSubscriberDto> findByUserId(Long userId) {
        return userSubscriberMapper.toUserSubscriberDto(userSubscriberRepository.findAllByUserId(userId));
    }

    @Override
    public void subscriber(Long id) {
        UserSubscriberDto dto = findById(id);
        SubscriberGroupService groupService = subscriberConfigGroupService.getGroupServiceById(dto.getSubscriberConfigGroupId());
        groupService.subscribe(dto);
    }

    private void refreshAndPutTask(@NonNull List<UserSubscriberDto> dtoList) {
        dtoList.forEach(dto -> {
            if (taskMap.containsKey(dto.getId())) {
                taskMap.get(dto.getId()).cancel(true);
            }
            SubscriberGroupService groupService = subscriberConfigGroupService.getGroupServiceById(dto.getSubscriberConfigGroupId());
            ScheduledFuture<?> task = taskScheduler.schedule(() -> groupService.subscribe(dto), new CronTrigger(dto.getCron()));
            taskMap.put(dto.getId(), task);
        });
    }

    @Override
    public void refreshSubscriberTasks() {
        refreshAndPutTask(findAll());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        refreshSubscriberTasks();
        log.info("订阅器定时任务启动");
    }

}
