package com.ms.notificacao.infraestrutura.config.rabbitmq;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RabbitMQConfigTest {

    private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void exchange_shouldExposeConfiguredName() {
        DirectExchange exchange = config.exchange();

        assertThat(exchange.getName()).isEqualTo(RabbitMQConfig.EXCHANGE_NAME);
        assertThat(exchange.isDurable()).isTrue();
    }

    @Test
    void queues_shouldBeDurableAndNamed() {
        Queue notificacao = config.notificacaoQueue();
        Queue historico = config.notificacaoQueue();

        assertThat(notificacao.getName()).isEqualTo(RabbitMQConfig.NOTIFICACAO_QUEUE_NAME);
        assertThat(notificacao.isDurable()).isTrue();
        assertThat(historico.getName()).isEqualTo(RabbitMQConfig.NOTIFICACAO_QUEUE_NAME);
        assertThat(historico.isDurable()).isTrue();
    }

    @Test
    void bindings_shouldLinkQueuesToExchangeWithRoutingKey() {
        Queue notificacao = config.notificacaoQueue();
        Queue historico = config.notificacaoQueue();
        DirectExchange exchange = config.exchange();

        Binding notificacaoBinding = config.notificacaoBinding(notificacao, exchange);
        Binding historicoBinding = config.notificacaoBinding(historico, exchange);

        assertThat(notificacaoBinding.getRoutingKey()).isEqualTo(RabbitMQConfig.ROUTING_KEY);
        assertThat(historicoBinding.getRoutingKey()).isEqualTo(RabbitMQConfig.ROUTING_KEY);
        assertThat(notificacaoBinding.getExchange()).isEqualTo(exchange.getName());
        assertThat(historicoBinding.getExchange()).isEqualTo(exchange.getName());
    }

    @Test
    void messageConverter_shouldReturnJacksonConverter() {
        assertThat(config.messageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }
}