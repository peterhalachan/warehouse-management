package com.warehousemanagement.warehousemanagement.service.impl;

import com.warehousemanagement.warehousemanagement.assembler.ProductMessageAssembler;
import com.warehousemanagement.warehousemanagement.config.RabbitMqConfiguration;
import com.warehousemanagement.warehousemanagement.dto.message.ProductMessageDto;
import com.warehousemanagement.warehousemanagement.entity.Product;
import com.warehousemanagement.warehousemanagement.service.ProductUpdateNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductUpdateNotificationServiceImpl implements ProductUpdateNotificationService {

    private static final String CREATE_ROUTING_KEY = "create";
    private static final String UPDATE_ROUTING_KEY = "update";

    private ProductMessageAssembler productMessageAssembler;
    private MessageChannel messageChannel;

    @Autowired
    public ProductUpdateNotificationServiceImpl(ProductMessageAssembler productMessageAssembler,
            @Qualifier(RabbitMqConfiguration.PRODUCT_UPDATE_CHANNEL) MessageChannel messageChannel) {
        this.productMessageAssembler = productMessageAssembler;
        this.messageChannel = messageChannel;
    }

    @Override
    public void sendUpdateNotification(Product product, boolean isNewProduct) {
        Message<ProductMessageDto> message = MessageBuilder.withPayload(productMessageAssembler.toDto(product))
                .setHeader(RabbitMqConfiguration.HEADER_ROUTING_KEY, resolveRoutingKey(isNewProduct)).build();
        messageChannel.send(message);
        if (isNewProduct) {
            log.debug("A message for a new product was sent. Product id {}", product.getId());
        } else {
            log.debug("A message for an updated product was sent. Product id {}", product.getId());
        }

    }

    private String resolveRoutingKey(boolean isNewProduct) {
        return isNewProduct ? CREATE_ROUTING_KEY : UPDATE_ROUTING_KEY;
    }
}
