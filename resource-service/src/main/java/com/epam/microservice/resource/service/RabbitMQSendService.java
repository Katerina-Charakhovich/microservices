package com.epam.microservice.resource.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSendService {
    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.topic}")
    private String exchange;
    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    @Autowired
    public RabbitMQSendService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Long message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
