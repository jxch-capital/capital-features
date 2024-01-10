package org.jxch.capital.ws;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.fastjson2.JSONObject;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.event.ServerJobProgressEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class WSMsgBusHandler extends TextWebSocketHandler {
    private static final String SUBSCRIBE = "subscribe";
    private static final String SERVER_JOB_PROGRESS_CHANNEL = "ServerJobProgressEvent";
    private static final Map<String, Set<WebSocketSession>> MQ = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        BusEventParam param = JSONObject.parseObject(message.getPayload(), BusEventParam.class);
        if (Objects.equals(param.getType(), SUBSCRIBE)) {
            MQ.putIfAbsent(param.getChannel(), new ConcurrentHashSet<>());
            MQ.get(param.getChannel()).add(session);
        }
    }

    public void sendMsg(String channel, String msg) {
        Set<WebSocketSession> sessions = MQ.get(channel);
        if (Objects.nonNull(sessions) && !sessions.isEmpty()) {
            for (WebSocketSession session : sessions) {
                try {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(msg));
                    }
                } catch (IOException | IllegalStateException e) {
                    try {
                        session.close();
                    } catch (IOException ignored) {
                    }
                    sessions.remove(session);
                }
            }
        }
    }

    @EventListener
    public void listenServerJobProgressEvent(@NonNull ServerJobProgressEvent event) {
        sendMsg(SERVER_JOB_PROGRESS_CHANNEL, JSONObject.toJSONString(event.getSource()));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusEventParam {
        private String type;
        private String channel;
    }

}
