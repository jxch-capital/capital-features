package org.jxch.capital.watch.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.WatchConfigDto;
import org.jxch.capital.server.BrooksService;
import org.jxch.capital.server.WatchConfigService;
import org.jxch.capital.watch.WatchMailTask;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrooksBlogWatchMailTaskImpl  implements WatchMailTask {
    private final BrooksService brooksService;
    private final WatchConfigService watchConfigService;

    @Override
    public boolean support(Long userId) {
        return watchConfigService.userHasWatch(userId, name());
    }

    @Override
    public String htmlBuild(Long userId, String html) {
        StringBuilder builder = new StringBuilder(html);
        if (support(userId)) {
            for (WatchConfigDto ignored : watchConfigService.findByUseridAndWatchName(userId, name())) {
                builder.append(brooksService.newBlogArticleHtmlAndTransToChinese());
            }
        }
        return builder.toString();
    }

    @Override
    public void addInline(Long userId, MimeMessageHelper helper) {

    }

    @Override
    public void clear(Long userId) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Object getDefaultParam() {
        return null;
    }

    @Override
    public int getOrder() {
        return WatchMailTask.super.getOrder() + 1;
    }

    @Override
    public String name() {
        return "Brooks最新文章";
    }

}
