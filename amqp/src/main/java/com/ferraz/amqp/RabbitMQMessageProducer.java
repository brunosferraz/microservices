package com.ferraz.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publish(Object payload, String exchange, String routingKey) {
        log.info("Publishing to {} using rooting key {}. Payload {}",
                exchange, routingKey, payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Published to {} using rooting key {}. Payload {}",
                exchange, routingKey, payload);
    }
}
