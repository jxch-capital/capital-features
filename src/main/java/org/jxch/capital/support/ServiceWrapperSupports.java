package org.jxch.capital.support;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceWrapperSupports {
    private final AppContextHolder appContextHolder;

    @SneakyThrows
    @PostConstruct
    public void init() {
        List<String> allServiceNameHold = appContextHolder.allServiceNameHold(ServiceWrapperSupport.class);
        long count = allServiceNameHold.stream().distinct().count();
        if (!Objects.equals((int) count, allServiceNameHold.size())) {
            String duplicateServiceNames = allServiceNameHold.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(","));
            throw new Exception("存在重名服务: " + duplicateServiceNames);
        }
    }

    public static List<ServiceWrapper> allServiceWrapper(Class<? extends ServiceWrapperSupport> clazz) {
        return AppContextHolder.allService(clazz).stream().map(ServiceWrapperSupport::getDefaultServiceWrapper).toList();
    }


}
