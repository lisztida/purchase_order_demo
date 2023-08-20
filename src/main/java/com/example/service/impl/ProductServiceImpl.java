package com.example.service.impl;

import com.example.bean.Product;
import com.example.bean.Supplier;
import com.example.mapper.ProductMapper;
import com.example.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductsBySup(String supplierCode){
        List<Product> products = productMapper.getProductBySup(supplierCode);
        return products;
    }
}
