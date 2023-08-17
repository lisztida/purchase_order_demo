package com.example.bean;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrder {
    private String purchaseCode;
    private String createDate;
    private String createBy;
    private Double totalPrice;
    private List<PurchaseOrderItem> purchaseOrderItems;
}
