package com.example.controller;

import com.example.bean.PurchaseOrder;
import com.example.service.PurchaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Resource
    private PurchaseService purchaseService;

    @GetMapping("/getAll")
    List<PurchaseOrder> getPurchaseOrderByCreate(@RequestParam("createName") String createName){
        List<PurchaseOrder> purchaseOrders = purchaseService.getPurchaseOrdersByCreate(createName);
        return purchaseOrders;
    }

}
