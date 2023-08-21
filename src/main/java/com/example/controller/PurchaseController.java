package com.example.controller;

import com.example.bean.PurchaseOrder;
import com.example.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Resource
    private PurchaseService purchaseService;

    @GetMapping("/getPurchase")
    public PurchaseOrder getPurchaseOrderById(@RequestParam("purchaseId") String purchaseId){
        PurchaseOrder purchaseOrder = purchaseService.getPurchaseOrderById(purchaseId);
        return purchaseOrder;
    }

    @GetMapping("/getAll")
    public Map<String, Object> getAllByCreate(@RequestParam("pageNum") int pageNum,
                                              @RequestParam("pageSize") int pageSize,
                                              @RequestParam("createBy") String name){
        Map<String,Object> res = new HashMap<>();
        List<PurchaseOrder> purchaseOrders = purchaseService.getPurchaseOrdersByCreate(name,pageNum,pageSize);
        int totalNum = purchaseService.getTotalNum(name);
        res.put("data",purchaseOrders);
        res.put("totalNum",totalNum);
        return res;
    }

    @PostMapping("/insert")
    public Map<String, Object> insertPurchase(@RequestBody PurchaseOrder purchaseOrder){
        purchaseService.insertPurchase(purchaseOrder);
        Map<String, Object> res = new HashMap<>();
        res.put("insertedPurchaseCode",purchaseOrder.getPurchaseCode());
//        System.out.println(purchaseOrder);
        return res;
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deletePurchaseById(@RequestParam("purchaseCode") String purchaseCode){
        purchaseService.deletePurchaseById(purchaseCode);
        Map<String, Object> res = new HashMap<>();
        res.put("deletedPurchaseCode",purchaseCode);
        return res;
    }

    @PostMapping("/update")
    public Map<String, Object> updatePurchase(@RequestBody PurchaseOrder purchaseOrder){
        purchaseService.updatePurchase(purchaseOrder);
        Map<String, Object> res = new HashMap<>();
        res.put("updatedPurchaseCode",purchaseOrder.getPurchaseCode());
        return res;
    }

}
