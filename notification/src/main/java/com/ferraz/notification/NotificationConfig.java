package com.ferraz.notification;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    private final RabbitAdmin rabbitAdmin;

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKeys;

    public NotificationConfig(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    @Bean
    Queue queue() {
        return new Queue(this.notificationQueue, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(this.internalExchange);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        Binding binding = BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(this.internalNotificationRoutingKeys);
        rabbitAdmin.declareBinding(binding);

        return binding;
    }

    public String getInternalExchange() {
        return internalExchange;
    }

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public String getInternalNotificationRoutingKeys() {
        return internalNotificationRoutingKeys;
    }

}
