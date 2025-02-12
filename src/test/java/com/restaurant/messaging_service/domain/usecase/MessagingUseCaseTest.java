package com.restaurant.messaging_service.domain.usecase;

import com.restaurant.messaging_service.domain.exceptions.InvalidCodeException;
import com.restaurant.messaging_service.domain.exceptions.OrderIsNotReadyException;
import com.restaurant.messaging_service.domain.model.NotifyClient;
import com.restaurant.messaging_service.domain.spi.ICodePersistencePort;
import com.restaurant.messaging_service.domain.spi.IMessagingPersistencePort;
import com.restaurant.messaging_service.domain.utils.CodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;


class MessagingUseCaseTest {

    @Mock
    private IMessagingPersistencePort messagingPersistencePort;

    @Mock
    private ICodePersistencePort codePersistencePort;

    @InjectMocks
    private MessagingUseCase messagingUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void notifyClient_ShouldSendNotificationAndSaveCode() {

        NotifyClient notifyClient = new NotifyClient("+573045721800",1L);


        String generatedCode = "1234";

        Mockito.mockStatic(CodeGenerator.class);
        Mockito.when(CodeGenerator.generateCode()).thenReturn(generatedCode);

        messagingUseCase.notifyClient(notifyClient);

        Mockito.verify(messagingPersistencePort, Mockito.times(1)).notifyClient(notifyClient.getPhoneNumber(), generatedCode);
        Mockito.verify(codePersistencePort, Mockito.times(1)).saveCode(notifyClient.getOrderId(), generatedCode);

    }

    @Test
    void MessagingUseCase_VerifyCode_WhenOrderIsNotReady_ShouldThrowOrderIsNotReadyException() {

        Long orderId = 1L;
        String code = "123456";

        Mockito.when(codePersistencePort.orderIsReady(orderId)).thenReturn(false);

        assertThrows(OrderIsNotReadyException.class, () -> messagingUseCase.verifyCode(orderId, code));
    }

    @Test
    void MessagingUseCase_VerifyCode_WhenCodeIsInvalid_ShouldThrowInvalidCodeException() {
        Long orderId = 1L;
        String code = "123456";

        Mockito.when(codePersistencePort.orderIsReady(orderId)).thenReturn(true);
        Mockito.when(codePersistencePort.isCodeValid(orderId, code)).thenReturn(false);

        assertThrows(InvalidCodeException.class, () -> messagingUseCase.verifyCode(orderId, code));
    }

    @Test
    void MessagingUseCase_VerifyCode_WhenValidCode_ShouldNotThrowException() {
        Long orderId = 1L;
        String code = "123456";

        Mockito.when(codePersistencePort.orderIsReady(orderId)).thenReturn(true);
        Mockito.when(codePersistencePort.isCodeValid(orderId, code)).thenReturn(true);

        messagingUseCase.verifyCode(orderId, code);
    }
}