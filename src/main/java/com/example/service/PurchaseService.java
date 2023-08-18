package com.example.service;

import com.example.bean.PurchaseOrder;

import java.util.List;

public interface PurchaseService {
    PurchaseOrder getPurchaseOrderById(String createName);
    Integer getTotalNum(String createName);
    void insertPurchase(PurchaseOrder purchaseOrder);
    void deletePurchaseById(String purchaseCode);
    void updatePurchase(PurchaseOrder purchaseOrder);
    List<PurchaseOrder> getPurchaseOrdersByCreate(String name,int pageNum,int pageSize);
}
