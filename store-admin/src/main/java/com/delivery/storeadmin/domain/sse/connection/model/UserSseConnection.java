package com.delivery.storeadmin.domain.sse.connection.model;

import com.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ConnectionPoolInterface<String, UserSseConnection> connectionPoolInterface;
    private final ObjectMapper objectMapper;

    private UserSseConnection(String uniqueKey,
                              ConnectionPoolInterface<String, UserSseConnection> connectionPoolInterface,
                              ObjectMapper objectMapper) {
        this.uniqueKey = uniqueKey;
        this.sseEmitter = new SseEmitter(1000L * 60);
        // callback 초기화
        this.connectionPoolInterface = connectionPoolInterface;
        this.objectMapper = objectMapper;
        // on completion
        this.sseEmitter.onCompletion(() -> {
            // connection pool remove
            connectionPoolInterface.onCompletionCallback(this);
        });
        // on timeout
        this.sseEmitter.onTimeout(this.sseEmitter::complete);
        // onopen 메시지
        sendMessage("onopen", "connect");
    }

    public static UserSseConnection connect(String uniqueKey,
                                            ConnectionPoolInterface<String, UserSseConnection> connectionPoolInterface,
                                            ObjectMapper objectMapper) {
        return new UserSseConnection(uniqueKey, connectionPoolInterface, objectMapper);
    }

    public void sendMessage(String eventName, Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .name(eventName)
                    .data(json);
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .data(json);
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
