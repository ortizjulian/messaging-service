package com.restaurant.messaging_service.domain.spi;

public interface ICodePersistencePort {
    void saveCode(Long orderId, String code);
}
