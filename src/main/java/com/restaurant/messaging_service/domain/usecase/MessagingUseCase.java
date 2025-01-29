package com.restaurant.messaging_service.domain.usecase;

import com.restaurant.messaging_service.domain.api.IMessagingServicePort;
import com.restaurant.messaging_service.domain.model.NotifyClient;
import com.restaurant.messaging_service.domain.spi.ICodePersistencePort;
import com.restaurant.messaging_service.domain.utils.CodeGenerator;

public class MessagingUseCase implements IMessagingServicePort {

    private final ICodePersistencePort codeServicePort;

    public MessagingUseCase(ICodePersistencePort codeServicePort) {
        this.codeServicePort = codeServicePort;
    }

    @Override
    public void notifyClient(NotifyClient notifyClient) {

        String securityCode = CodeGenerator.generateCode();
        codeServicePort.saveCode(notifyClient.getOrderId(),securityCode);

    }
}
