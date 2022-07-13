package com.epam.microservice.processor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@EnableRabbit
@Configuration
class RabbitMQConfig implements RabbitListenerConfigurer {
    @Value("${spring.rabbitmq.queue}")
    private String queue;
    private String topic = "microservices.topic";
    public static final String MICROSERVICES_QUEUE_DEAD = "microservices.queue.dead";

    @Bean
    Queue resourceQueue() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", MICROSERVICES_QUEUE_DEAD)
                .withArgument("x-message-ttl", 15000) //if message is not consumed in 15 seconds send to DLQ
                .build();
    }

    @Bean
    TopicExchange resourceCreateExchange() {
        return ExchangeBuilder.topicExchange(topic).build();
    }

    @Bean
    Binding binding(Queue resourceQueue, TopicExchange resourceCreateExchange) {
        return BindingBuilder.bind(resourceQueue).to(resourceCreateExchange).with(queue);
    }
    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(MICROSERVICES_QUEUE_DEAD).build();
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
