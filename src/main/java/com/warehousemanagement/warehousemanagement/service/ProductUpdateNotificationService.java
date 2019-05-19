package com.warehousemanagement.warehousemanagement.service;

import com.warehousemanagement.warehousemanagement.entity.Product;

public interface ProductUpdateNotificationService {

    void sendUpdateNotification(Product product, boolean isNewProduct);
}
