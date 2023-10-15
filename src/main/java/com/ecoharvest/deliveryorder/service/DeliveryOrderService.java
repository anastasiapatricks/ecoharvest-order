package com.ecoharvest.deliveryorder.service;

import com.ecoharvest.deliveryorder.client.ProductClient;
import com.ecoharvest.deliveryorder.domain.DeliveryOrder;
import com.ecoharvest.deliveryorder.domain.Item;
import com.ecoharvest.deliveryorder.domain.Product;
import com.ecoharvest.deliveryorder.dto.DeliveryOrderRequest;
import com.ecoharvest.deliveryorder.dto.ItemRequest;
import com.ecoharvest.deliveryorder.repository.DeliveryOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryOrderService {

    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;
    @Autowired
    private ProductClient productClient;

    public DeliveryOrder create(DeliveryOrderRequest request) {
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setUserId(request.getUserId());
        deliveryOrder.setDeliveryStatus("SCHEDULED");
        deliveryOrder.setCreatedTimestamp(ZonedDateTime.now());
        deliveryOrder.setDate(request.getDate());
        deliveryOrder.setTimeslot(request.getTimeslot());
        deliveryOrder.setAddress(request.getAddress());

        ArrayList<Item> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (ItemRequest itemRequest: request.getItems()) {
            Product p = productClient.getProductDetails(itemRequest.getProductId());
            Item item = new Item();
            item.setProductId(p.getId());
            item.setName(p.getProductName());
            item.setDescription(p.getDescription());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(p.getPrice());
            item.setDeliveryOrder(deliveryOrder);
            items.add(item);

            totalPrice = totalPrice.add(p.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        deliveryOrder.setItems(items);
        deliveryOrder.setTotalPrice(totalPrice);

        return deliveryOrderRepository.save(deliveryOrder);
    }

    public List<DeliveryOrder> findAll() {
        return deliveryOrderRepository.findAll();
    }

    public Optional<DeliveryOrder> findById(Long id) {
        return deliveryOrderRepository.findById(id);
    }

    public List<DeliveryOrder> findAllByUserId(Long userId) {
        return deliveryOrderRepository.findAllByUserId(userId);
    }

    public DeliveryOrder update(Long id, DeliveryOrder updatedDeliveryOrder) {
        Optional<DeliveryOrder> deliveryOrderOptional = findById(id);
        if (deliveryOrderOptional.isEmpty()) {
            return null;
        }
        DeliveryOrder deliveryOrder = deliveryOrderOptional.get();
        deliveryOrder.getItems().clear();
        for (Item item : updatedDeliveryOrder.getItems()) {
            item.setDeliveryOrder(deliveryOrder);
            deliveryOrder.getItems().add(item);
        }
        deliveryOrder.setUserId(updatedDeliveryOrder.getUserId());
        deliveryOrder.setAddress(updatedDeliveryOrder.getAddress());
        deliveryOrder.setCreatedTimestamp(updatedDeliveryOrder.getCreatedTimestamp());
        deliveryOrder.setTimeslot(updatedDeliveryOrder.getTimeslot());
        deliveryOrder.setDeliveryStatus(updatedDeliveryOrder.getDeliveryStatus());
        DeliveryOrder savedDeliveryOrder = save(deliveryOrder);
        return savedDeliveryOrder;
    }

    public DeliveryOrder updateDeliveryOrderStatus(Long id, String deliveryStatus) {
        Optional<DeliveryOrder> deliveryOrderOptional = findById(id);
        if (deliveryOrderOptional.isEmpty()) {
            return null;
        }
        DeliveryOrder deliveryOrder = deliveryOrderOptional.get();
        deliveryOrder.setDeliveryStatus(deliveryStatus);
        DeliveryOrder savedDeliveryOrder = save(deliveryOrder);
        return savedDeliveryOrder;
    }

    public DeliveryOrder save(DeliveryOrder deliveryOrder) {
        return deliveryOrderRepository.save(deliveryOrder);
    }

    public boolean deleteById(Long id) {
        Optional<DeliveryOrder> deliveryOrderOptional = findById(id);
        if (deliveryOrderOptional.isEmpty()) {
            return false;
        }
        deliveryOrderRepository.deleteById(id);
        return true;
    }

}
