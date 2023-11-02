package com.delivery.storeadmin.domain.userorder.business;

import com.delivery.common.message.model.UserOrderMessage;
import com.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import com.delivery.storeadmin.domain.userorder.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final SseConnectionPool sseConnectionPool;

    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(() -> new RuntimeException("사용자 주문 내역 없음"));

        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 사용자에게 push
        userConnection.sendMessage();
    }
}
