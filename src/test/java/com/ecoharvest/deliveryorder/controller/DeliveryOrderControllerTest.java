package com.ecoharvest.deliveryorder.controller;

import com.ecoharvest.deliveryorder.domain.DeliveryOrder;
import com.ecoharvest.deliveryorder.domain.Item;
import com.ecoharvest.deliveryorder.service.DeliveryOrderService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Tests for DeliveryOrderController")
@ExtendWith(MockitoExtension.class)
public class DeliveryOrderControllerTest {

    @Mock
    private DeliveryOrderService service;

    @InjectMocks
    private DeliveryOrderController controller;

    private static final Long INPUT_ID = 1L;

    @BeforeAll
    static void setup() {}

    @AfterAll
    static void tearDown() {}

    private List<Item> createMockItems() {
        Item item = new Item();
        item.setId(1L);
        item.setProductId("650ec16b2c691006bbca819d");
        item.setName("Orange");
        item.setDescription("Orange fruit");
        item.setQuantity(1);
        item.setPrice(BigDecimal.ONE);
        return Arrays.asList(item);
    }

    private DeliveryOrder createMockDeliveryOrder() {
        DeliveryOrder mockOrder = new DeliveryOrder();
        mockOrder.setId(1L);
        mockOrder.setItems(createMockItems());
        mockOrder.setTotalPrice(BigDecimal.TEN);
        mockOrder.setUserId(1L);
        mockOrder.setAddress("ABC Street");
        mockOrder.setCreatedTimestamp(ZonedDateTime.now());
        mockOrder.setDate(LocalDate.now());
        mockOrder.setTimeslot("11:00-12:00");
        mockOrder.setDeliveryStatus("SCHEDULED");
        return mockOrder;
    }

    @Test
    @DisplayName("Basic test for GET request: Get an order")
    public void testGetOrderRequest() {
        List<DeliveryOrder> mockExpectedValue = Arrays.asList(createMockDeliveryOrder());
        when(service.findAll()).thenReturn(mockExpectedValue);

        ResponseEntity responseEntity = controller.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockExpectedValue, responseEntity.getBody());
    }
}
