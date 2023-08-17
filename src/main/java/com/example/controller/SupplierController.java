package com.example.controller;

import com.example.bean.Supplier;
import com.example.service.impl.SupplierServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Resource
    SupplierServiceImpl supplierService;

    @RequestMapping("/getAll")
    public List<Supplier> getAllSupplier(){
        List<Supplier> suppliers = supplierService.getAllSupplier();
        return suppliers;
    }
}
