package com.delivery.storeadmin.domain.sse.controller;

import com.delivery.storeadmin.domain.authorization.model.UserSession;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {

    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) {
        log.info("login user {}", userSession);

        var emitter = new SseEmitter();
        // 클라이언트와 타임아웃이 일어났을 때
        emitter.onTimeout(emitter::complete);

        emitter.onCompletion(() -> {
            // 클라이언트와 연결이 종료됐을 때 하는 작업
        });

        // 최초 연결 시 응답 전송
        var event = SseEmitter.event()
                .name("onopen");

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @GetMapping("/push-event")
    public void pushEvent(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) {

    }
}
