package com.example.service.impl;

import com.example.bean.PurchaseOrder;
import com.example.mapper.PurchaseMapper;
import com.example.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;

    @Override
    public List<PurchaseOrder> getPurchaseOrdersByCreate(String createName){
        List<PurchaseOrder> purchaseOrders = purchaseMapper.getPurchaseOrdersByCreate(createName);
        return purchaseOrders;
    }
}
