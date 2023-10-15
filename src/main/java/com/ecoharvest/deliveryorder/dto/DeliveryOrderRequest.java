package com.ecoharvest.deliveryorder.dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class DeliveryOrderRequest {
    private Long userId;
    private ArrayList<ItemRequest> items;
    private LocalDate date;
    private String timeslot;
    private String address;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ArrayList<ItemRequest> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemRequest> items) {
        this.items = items;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
