package com.epam.microservice.processor.service;

import com.epam.microservice.processor.processor.ResourceProcessor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@EnableRetry
//@Retryable(value = AmqpException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
public class RabbitMQConsumerService {
    private final ResourceProcessor resourceProcessor;

    private static final Logger LOGGER = LogManager.getLogger(RabbitMQConsumerService.class);
    @Autowired
    public RabbitMQConsumerService(ResourceProcessor resourceProcessor) {
        this.resourceProcessor = resourceProcessor;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(Long resourceId) {
        LOGGER.log(Level.INFO,"Receive id:"+resourceId);
        resourceProcessor.process(resourceId);
    }
}
