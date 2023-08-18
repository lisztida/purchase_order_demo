package com.example.mapper;

import com.example.bean.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    PurchaseOrder getPurchaseOrderById(String createName);
    Integer getTotalNum(String createName);
    void insertPurchaseOrder(PurchaseOrder purchaseOrder);
    void deletePurchaseById(String purchaseCode);
    void updatePurchaseOrder(PurchaseOrder purchaseOrder);
    List<PurchaseOrder> getPurchaseOrdersByCreate(String createName,int pageNum,int pageSize);
}
