package org.example.application.bus;

import org.example.application.repo.GsonEventSerializer;
import org.example.generic.DomainEvent;
import org.example.generic.EventBus;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.example.application.ApplicationConfig.EXCHANGE;


@Service
public class RabbitMQEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;
    private final GsonEventSerializer serializer;
    private final RabbitAdmin rabbitAdmin;


    public RabbitMQEventBus(RabbitTemplate rabbitTemplate, GsonEventSerializer serializer, RabbitAdmin rabbitAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.serializer = serializer;
        this.rabbitAdmin = rabbitAdmin;
    }

    @Override
    public void publish(DomainEvent event) {
        var notification = new Notification(
                event.getClass().getCanonicalName(),
                serializer.serialize(event)
        );
        rabbitTemplate.convertAndSend(EXCHANGE, event.getType(), notification.serialize().getBytes());
    }

    @Override
    public void publishError(Throwable errorEvent) {

    }


}