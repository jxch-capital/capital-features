package org.jxch.capital.http.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RetrySuccessInterceptor implements Interceptor {
    @Builder.Default
    private int maxRetry = 5;
    @Builder.Default
    private Duration retryWait = null;

    private boolean needRetryWait(int retryNum) {
        return Objects.nonNull(retryWait) && retryNum < maxRetry;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) {
        Request request = chain.request();
        Throwable exception = null;
        int retryNum = 0;

        while (retryNum < maxRetry) {
            try {
                Response response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
            } catch (Throwable e) {
                exception = e;
            }

            if (needRetryWait(retryNum)) {
                try {
                    Thread.sleep(retryWait.toMillis() * (retryNum + 1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    // 中断后，仍需决定是否继续重试或退出
                    throw new RuntimeException("重试过程被中断", e);
                }
            }

            retryNum++;
            log.debug("重试 [{}/{}]: {}", retryNum, maxRetry, request);
        }

        log.error("重试失败：[{}/{}]: {}", retryNum, maxRetry, request);
        throw new RuntimeException("达到重试次数上限 (" + maxRetry + " 次) 仍然失败: " + request, exception);
    }

}
