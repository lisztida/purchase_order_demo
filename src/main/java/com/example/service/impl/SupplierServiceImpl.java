package com.example.service.impl;

import com.example.bean.Supplier;
import com.example.mapper.SupplierMapper;
import com.example.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Resource
    private SupplierMapper supplierMapper;

    @Override
    public List<Supplier> getAllSupplier(){
        List<Supplier> suppliers = supplierMapper.getAllSupplier();
        return suppliers;
    }
}
