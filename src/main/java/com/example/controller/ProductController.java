package com.example.controller;

import com.example.bean.Product;
import com.example.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @GetMapping("/getBySup")
    List<Product> getProductsBySup(@RequestParam("supplierCode") String supplierCode){
        List<Product> products =  productService.getProductsBySup(supplierCode);
        return products;
    }
}
