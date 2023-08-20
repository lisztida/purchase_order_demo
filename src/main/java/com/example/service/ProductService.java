package com.example.service;

import com.example.bean.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsBySup(String supplierCode);
}
