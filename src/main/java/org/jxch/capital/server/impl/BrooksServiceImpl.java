package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jxch.capital.http.ai.gemini.GeminiApi;
import org.jxch.capital.http.ai.TextAiParam;
import org.jxch.capital.http.brooks.BrooksBlogApi;
import org.jxch.capital.server.BrooksService;
import org.jxch.capital.utils.CollU;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrooksServiceImpl implements BrooksService {
    private final BrooksBlogApi brooksBlogApi;
    private final GeminiApi geminiApi;


    @Override
    public String newBlogArticleHtmlAndTransToChinese() {
        String html = brooksBlogApi.newArticleHtml();
        Document doc = Jsoup.parse(html);

        doc.select("div.addtoany_share_save_container").forEach(Node::remove);
        doc.select("footer.entry-footer").forEach(Node::remove);
        doc.select("p.caption").forEach(Node::remove);
        doc.select("span.entry-comments-link").forEach(Node::remove);
        doc.select(".wp-block-heading").stream()
                .filter(element -> Objects.equals(element.text(), "End of day video review")
                        || Objects.equals(element.text(), "Trading Room")
                        || Objects.equals(element.text(), "Charts use Pacific Time"))
                .findFirst().ifPresent(element -> {
                    element.nextElementSiblings().remove();
                    element.remove();
                });
        doc.getAllElements().forEach(element -> element.removeAttr("class"));

        Elements lis = doc.select("li,p");
        for (int i = 0; i < lis.size(); i++) {
            lis.get(i).text(transAi(lis.get(i).text()));
            log.debug("翻译进度：{}/{}", i + 1, lis.size());
        }

        return doc.body().children().toString();
    }

    private String transAi(String text) {
        String res = CollU.last(geminiApi.questionTextChain(transAiParam().addText(text)).chainText());
        return Objects.equals(res, "NULL") ? text : res;
    }

    private TextAiParam transAiParam() {
        return geminiApi.getDefaultParam()
                .addText("英译中,注意炒股及金融领域的专用术语的翻译准确性（比如把Bar翻译为K线），以及常见缩写的翻译（金融术语及标的名称除外），不要翻译人名。");
    }

}
