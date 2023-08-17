package com.example.mapper;

import com.example.bean.Supplier;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierMapper {
    List<Supplier> getAllSupplier();
}
