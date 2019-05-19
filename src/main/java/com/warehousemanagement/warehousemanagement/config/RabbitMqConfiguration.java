package com.warehousemanagement.warehousemanagement.config;

import java.util.Map;
import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.dsl.AmqpOutboundEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Slf4j
@Configuration
public class RabbitMqConfiguration {

    public static final String HEADER_ROUTING_KEY = "routingKey";
    private static final String ROUTING_KEY_EXPRRESSION = String.format("headers['%s']", HEADER_ROUTING_KEY);
    public static final String HEADER_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String HEADER_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    private static final String TYPE_ID_HEADER_KEY = "__TypeId__";
    private static final String EXCHANGE_CREATED_LOG = "Exchange with name {} was created, {}";
    private static final String QUEUE_CREATED_LOG = "Queue with name {} was created, {}";
    // Message channel names
    public static final String PRODUCT_UPDATE_CHANNEL = "productUpdateChannel";
    public static final String DISTRIBUTOR_UPDATE_CHANNEL = "distributorUpdateChannel";

    private String productUpdateExchangeName;
    private String distributorUpdateExchangeName;
    private String deadLetterQueueName;

    public RabbitMqConfiguration(@Value("${product.update.exchange}") String productUpdateExchangeName,
            @Value("${distributor.update.exchange}") String distributorUpdateExchangeName,
            @Value("${dead.letter.queue}") String deadLetterQueueName) {
        this.productUpdateExchangeName = productUpdateExchangeName;
        this.distributorUpdateExchangeName = distributorUpdateExchangeName;
        this.deadLetterQueueName = deadLetterQueueName;
    }

    @PostConstruct
    public void logCreatedExchangesAndQueues() {
        log.info(EXCHANGE_CREATED_LOG, productUpdateExchangeName);
        log.info(EXCHANGE_CREATED_LOG, distributorUpdateExchangeName);
        log.info(QUEUE_CREATED_LOG, deadLetterQueueName);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(amqpJsonConverter(objectMapper));
        rabbitTemplate.setBeforePublishPostProcessors(messagingPostProcessor());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter amqpJsonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    private static MessagePostProcessor messagingPostProcessor() {
        return message -> {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            headers.remove(TYPE_ID_HEADER_KEY);
            return message;
        };
    }

    @Bean
    public Exchange productUpdateExchange() {
        return ExchangeBuilder.topicExchange(productUpdateExchangeName).durable(true).build();
    }

    @Bean
    public Exchange distributorUpdateExchange() {
        return ExchangeBuilder.topicExchange(distributorUpdateExchangeName).durable(true).build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(deadLetterQueueName);
    }

    @Bean(name = PRODUCT_UPDATE_CHANNEL)
    public MessageChannel productUpdateChannel() {
        return MessageChannels.direct(PRODUCT_UPDATE_CHANNEL).get();
    }

    @Bean(name = DISTRIBUTOR_UPDATE_CHANNEL)
    public MessageChannel distributorUpdateChannel() {
        return MessageChannels.direct(DISTRIBUTOR_UPDATE_CHANNEL).get();
    }

    @Bean
    public IntegrationFlow sendProductUpdate(RabbitTemplate rabbitTemplate) {
        return IntegrationFlows.from(PRODUCT_UPDATE_CHANNEL)
                .log()
                .handle(sendToRabbit(rabbitTemplate, productUpdateExchangeName))
                .get();
    }

    @Bean
    public IntegrationFlow sendDistributorUpdate(RabbitTemplate rabbitTemplate) {
        return IntegrationFlows.from(DISTRIBUTOR_UPDATE_CHANNEL)
                .log()
                .handle(sendToRabbit(rabbitTemplate, distributorUpdateExchangeName))
                .get();
    }

    private AmqpOutboundEndpointSpec sendToRabbit(RabbitTemplate rabbitTemplate, String exchangeName) {
        return Amqp.outboundAdapter(rabbitTemplate)
                .exchangeName(exchangeName)
                .routingKeyExpression(ROUTING_KEY_EXPRRESSION);
    }

}
