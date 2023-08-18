package com.example.service.impl;

import com.example.bean.PurchaseOrder;
import com.example.bean.PurchaseOrderItem;
import com.example.mapper.PurchaseMapper;
import com.example.mapper.PurchaseOrderItemMapper;
import com.example.service.PurchaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;
    @Resource
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    @Override
    public PurchaseOrder getPurchaseOrderById(String purchaseId){
        PurchaseOrder purchaseOrder = purchaseMapper.getPurchaseOrderById(purchaseId);
        return purchaseOrder;
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrdersByCreate(String createName,int pageNum,int pageSize){
        pageNum = (pageNum - 1) * pageSize;
        List<PurchaseOrder> purchaseOrders = purchaseMapper.getPurchaseOrdersByCreate(createName,pageNum,pageSize);
        return purchaseOrders;
    }

    @Override
    public Integer getTotalNum(String createName){
        Integer totalNum = purchaseMapper.getTotalNum(createName);
        return totalNum;
    }

    @Override
    public void insertPurchase(PurchaseOrder purchaseOrder) {
        purchaseMapper.insertPurchaseOrder(purchaseOrder);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        for(PurchaseOrderItem item:purchaseOrderItems){
            purchaseOrderItemMapper.insertItem(item);
        }
    }

    @Override
    public void deletePurchaseById(String purchaseCode){
        purchaseOrderItemMapper.deleteItemsById(purchaseCode);
        purchaseMapper.deletePurchaseById(purchaseCode);
    }

    @Override
    public void updatePurchase(PurchaseOrder purchaseOrder) {
        purchaseMapper.updatePurchaseOrder(purchaseOrder);
        purchaseOrderItemMapper.deleteItemsById(purchaseOrder.getPurchaseCode());
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        for(PurchaseOrderItem item:purchaseOrderItems){
            purchaseOrderItemMapper.insertItem(item);
        }
    }


}
