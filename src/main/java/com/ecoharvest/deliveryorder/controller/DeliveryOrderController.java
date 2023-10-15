package com.ecoharvest.deliveryorder.controller;

import com.ecoharvest.deliveryorder.domain.DeliveryOrder;
import com.ecoharvest.deliveryorder.dto.DeliveryOrderRequest;
import com.ecoharvest.deliveryorder.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/delivery-order")
public class DeliveryOrderController {

    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @GetMapping
    public ResponseEntity<List<DeliveryOrder>> findAll() {
        return ResponseEntity.ok(deliveryOrderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DeliveryOrder>> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryOrderService.findById(id));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<DeliveryOrder>> findAllByUserName(@PathVariable Long userId) {
        return ResponseEntity.ok(deliveryOrderService.findAllByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<DeliveryOrder> create(@RequestBody DeliveryOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryOrderService.create(request));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<DeliveryOrder> update(@PathVariable Long id, @RequestBody DeliveryOrder deliveryOrder) {
        DeliveryOrder updatedDeliveryOrder = deliveryOrderService.update(id, deliveryOrder);
        if (updatedDeliveryOrder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDeliveryOrder);
    }

    @PostMapping("/update-status/{id}")
    public ResponseEntity<DeliveryOrder> updateDeliveryOrderStatus(@PathVariable Long id, @RequestBody String deliveryStatus) {
        DeliveryOrder updatedDeliveryOrder = deliveryOrderService.updateDeliveryOrderStatus(id, deliveryStatus);
        return ResponseEntity.ok(updatedDeliveryOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!deliveryOrderService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
