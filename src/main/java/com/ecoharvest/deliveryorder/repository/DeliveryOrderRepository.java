package com.ecoharvest.deliveryorder.repository;

import com.ecoharvest.deliveryorder.domain.DeliveryOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryOrderRepository extends CrudRepository<DeliveryOrder, Long> {

    List<DeliveryOrder> findAll();

    Optional<DeliveryOrder> findById(Long id);

    List<DeliveryOrder> findAllByUserId(Long userId);

}
