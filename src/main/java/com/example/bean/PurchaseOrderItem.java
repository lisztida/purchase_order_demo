package com.example.bean;

import lombok.Data;

@Data
public class PurchaseOrderItem {
    private String purchaseItemId;
    private String purchaseCode;
    private String supplierCode;
    private String productCode;
    private int nums;
    private Float totalPrice;
    private Product product;
}
