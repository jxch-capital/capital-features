package org.jxch.capital.config;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
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

        EmbeddingStore<TextSegment> embeddingStore = MilvusEmbeddingStore.builder()
                .host("localhost")
                .port(19530)
                .dimension(384)
                .build();


        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        TextSegment segment1 = TextSegment.from("""
                有一个可靠的信号来判断上升趋势中或交易区间内的回撤走势已经结束，那就是当前K线的高点至少高于前一根K线的高点1个最小报价单位。数出此类现象发生的次数，就是“数K线”。在上升行情或交易区间的横向或向下调整中，第一根高点高与前一根K线高点的K线被称为高1. 高1终结了第一波横向或向下的调整走势，不过这一波调整可能只是一波更大规模调整的一部分.。如果市场没有立即转入升势，而是继续盘整或下跌，那么第二次出现的高点高于前一根K线高点的K线就是高2. 高2终结了第二波横向或向下的调整。
                                
                上升趋势中的高2和下跌趋势中的低2相当于ABC调整。第一波是A，高1或低1是方向改变的B，最后一波回调是C。在上升行情的ABC回调中，终结C的是高2入场K线；在下跌行情的ABC回调中，终结C的是低2入场K线。
                                
                如果上升趋势的回调走势出现了第3波，那么终结这一波调整的就是高3，通常类似楔形牛旗；如果下降趋势的回调走势出现第3波，终结调整的就是低3卖出形态，通常是一种楔形熊旗。
                                
                有些上升行情中的调整可以进一步延伸并出现高4. 高4的形成有时候是从高2开始的，只不过这个高2很快就夭折了，而是又出现两波下跌，形成第二个高2. 所以整个价格行为只是更高一个时间级别上的一个高2. 在其他情况下，高4是一轮小规模的“急速与通道”下降趋势.，第一波或第二波向下推动为急速下跌，后面的向下推动走势构成一个下降通道。如果高4仍未能让市场恢复上升趋势，价格跌破其低点，那么市场就很可能不再是上升途中的回调，而是已经进入下跌趋势。我们需要等待进一步的价格行为才能入场交易。
                                
                低1、低2等也是一样的，只不过对应下跌行情或震荡行情中的调整。当下跌趋势或震荡行情发生横向或向上的调整，第一根低点低于前一根K线低点的K线为低1. 低1终结了第一波调整，但这一终结也可能非常短暂，比如只有一根K线。随后再次出现的类似情况分别为低2、低3和低4卖点。如果低4失败（即在触发低4做空信号之后，一根K线涨至低4信号K线的高点之上），那么价格行为所发出的信号是空头已经失去控制权，接下来市场要么进入双向交易模式、多空交替掌权，要么多头夺取控制权。不论哪种情况，空头都可以用强力击穿一根上升趋势线的方式来宣告自己重新夺权。
                                
                                
                
                """);
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        Embedding queryEmbedding = embeddingModel.embed("高1是什么?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 8);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        for (EmbeddingMatch<TextSegment> match : relevant) {
            System.out.println(match.score()); // 0.8144287765026093
            System.out.println(match.embedded().text()); // I like football.
        }

    }
}