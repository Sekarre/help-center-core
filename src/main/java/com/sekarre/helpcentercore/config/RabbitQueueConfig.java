package com.sekarre.helpcentercore.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class RabbitQueueConfig {

    @Value("${notification.queue.name}")
    private String queueName;

    @Bean
    public Queue notificationQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public MappingJackson2HttpMessageConverter notificationJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
