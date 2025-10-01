package com.ms.notificacao.infraestrutura.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "agendamento-exchange";
    public static final String ROUTING_KEY = "agendamento-routing-key";
    public static final String NOTIFICACAO_QUEUE_NAME = "notificacao-queue";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue notificacaoQueue() {
        return new Queue(NOTIFICACAO_QUEUE_NAME, true);
    }

    @Bean
    public Binding notificacaoBinding(Queue notificacaoQueue, DirectExchange exchange) {
        return BindingBuilder.bind(notificacaoQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
