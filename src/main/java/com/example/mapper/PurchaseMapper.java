package com.example.mapper;

import com.example.bean.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    List<PurchaseOrder> getPurchaseOrdersByCreate(String createName);
}
