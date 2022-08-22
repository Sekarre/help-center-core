package com.sekarre.helpcentercore.services.notification;

import com.sekarre.helpcentercore.DTO.notification.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationSender {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void sendNotification(NotificationDTO notificationDTO) {
        rabbitTemplate.convertAndSend(queue.getName(), notificationDTO);
    }
}
