package com.example.mapper;

import com.example.bean.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getProductBySup(String supplierCode);
}
