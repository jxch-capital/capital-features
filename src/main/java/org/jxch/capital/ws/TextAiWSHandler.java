package org.jxch.capital.ws;

import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.TextAiServiceParam;
import org.jxch.capital.server.TextAiService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextAiWSHandler extends TextWebSocketHandler {
    private final TextAiService textAiService;

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws IOException {
        TextAiServiceParam param = JSONObject.parseObject(message.getPayload(), TextAiServiceParam.class);
        log.debug(JSONObject.toJSONString(param));

        TextMessage textMessage = new TextMessage(
                "ALL-" + textAiService.questionLastResStream(param, (str) -> {
                    try {
                        session.sendMessage(new TextMessage("SUB-" + str));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));

        session.sendMessage(textMessage);
        log.debug(textMessage.toString());
    }

}
