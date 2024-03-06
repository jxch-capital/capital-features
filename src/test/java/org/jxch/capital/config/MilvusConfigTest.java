package org.jxch.capital.config;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.OnnxEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class MilvusConfigTest {
//    @Autowired
    private MilvusEmbeddingStore embeddingStore2;

    @Test
    void milvusEmbeddingStore() {
//        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        EmbeddingModel embeddingModel = new OnnxEmbeddingModel("D:\\huggingface\\shibing624\\text2vec-base-chinese\\onnx\\model.onnx");


        EmbeddingStore<TextSegment> embeddingStore = MilvusEmbeddingStore.builder()
                .host("localhost")
                .port(19530)
                .dimension(768)
                .build();


        TextSegment segment1 = TextSegment.from("突破K线通常具有高成交量,有时候其成交量将是普通K线的10至20倍。成交量越高,以及急速拉升的K线越多,出现重大后续行情的几率就越高。在突破之前,多头和空头均在分批建仓,争夺市场的控制权,双方均试图在各自方向成功突破。一旦出现明确的突破,输的一方会很快斩仓止损,而赢的一方甚至会更为激进地加仓。结果是一根或多根趋势K线,通常伴有高成交量。成交量并非总是特别高,但是当其为近期K线平均水平的10倍或更高时,成功突破的概率就更高。成功突破指的是拥有多根后续K线。此外,在几根K线之内失败的突破也可能伴有非比寻常的高成交量,但是这种情况较不常见。成交量的可靠性不足以指导决策,而构成急速拉升的大型趋势K线已经告诉你突破是否很可能会成功。试图将成交量纳人考虑,更多时候会让你分心,妨碍你发挥最佳水平。");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        Embedding queryEmbedding = embeddingModel.embed("如K线重卺,影线越来越长,反方向的趋势K线和回调K线。尽管趋势可能持续很长时间,但是这一段趋势通常会被回撤,成为交易区间的一部分。举例而言,在个急速与通道的上涨趋势形态中,急速拉升是突破,通道通常成为交易区间的第一腿,因此经常被回撤。").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 8);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        for (EmbeddingMatch<TextSegment> match : relevant) {
            System.out.println(match.score()); // 0.8144287765026093
            System.out.println(match.embedded().text()); // I like football.
        }

    }
}