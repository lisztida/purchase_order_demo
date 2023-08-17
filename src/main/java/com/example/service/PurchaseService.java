package com.example.service;

import com.example.bean.PurchaseOrder;

import java.util.List;

public interface PurchaseService {
    List<PurchaseOrder> getPurchaseOrdersByCreate(String createName);
}
