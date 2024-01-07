package org.jxch.capital.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.ws.TextAiWSHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final TextAiWSHandler textAiWSHandler;

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(textAiWSHandler, "/textAiWSHandler");
    }

}
