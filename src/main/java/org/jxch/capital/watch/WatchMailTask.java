package org.jxch.capital.watch;

import org.springframework.core.Ordered;
import org.springframework.mail.javamail.MimeMessageHelper;

public interface WatchMailTask extends Ordered {

    boolean support(Long userId);

    String htmlBuild(Long userId, String html);

    void addInline(Long userId, MimeMessageHelper helper);

    void clear(Long userId);

    void clear();

    @Override
    default int getOrder() {
        return 0;
    }

    default String name() {
        return getClass().getSimpleName();
    }

    Object getDefaultParam();

}
