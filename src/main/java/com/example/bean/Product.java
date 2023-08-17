package com.example.bean;

import lombok.Data;

@Data
public class Product {
    private String productCode;
    private String productName;
    private Supplier supplier;
    private String productCategory;
    private String specType;
    private String unit;
    private String price;
}
