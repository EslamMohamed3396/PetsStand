package com.proatcoding.pets.models.weAcceptPayment.placeOrder.request;

public class Items {

    private String name;
    private String description;
    private int amount_cents;
    private int quantity;

    public Items(String name, String description, int amount_cents, int quantity) {
        this.name = name;
        this.description = description;
        this.amount_cents = amount_cents;
        this.quantity = quantity;
    }
}
