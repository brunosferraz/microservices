package com.ferraz.notification;

import com.ferraz.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification =
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("com.ferraz")
                        .message(notificationRequest.message())
                        .sendAt(LocalDateTime.now()).build();

        notificationRepository.save(notification);
    }
}
