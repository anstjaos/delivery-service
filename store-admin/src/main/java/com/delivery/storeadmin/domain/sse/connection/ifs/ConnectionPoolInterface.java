package com.delivery.storeadmin.domain.sse.connection.ifs;

public interface ConnectionPoolInterface<T, R> {

    void addSession(T uniqueKey, R session);

    R getSession(T uniqueKey);

    void onCompletionCallback(R session);
}
