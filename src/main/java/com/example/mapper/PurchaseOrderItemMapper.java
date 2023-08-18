package com.example.mapper;

import com.example.bean.PurchaseOrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseOrderItemMapper {
    void insertItem(PurchaseOrderItem purchaseOrderItem);
    void deleteItemsById(String purchaseCode);
}
