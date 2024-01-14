package org.jxch.capital.watch;

import org.springframework.core.Ordered;
import org.springframework.mail.javamail.MimeMessageHelper;

public interface WatchMailTask extends Ordered {

    String htmlBuild(String html);

    void addInline(MimeMessageHelper helper);

    void clear();

    @Override
    default int getOrder() {
        return Integer.MAX_VALUE;
    }

}
