package com.proatcoding.pets.models.weAcceptPayment.placeOrder.request;


import java.util.List;

public class BodyPlaceOrder {

    private int Amount_cents;
    private String Currency;
    private List<Items>Items;
    private String UserEmail;
    private String UserID;
    private String UserFirstName;
    private String UserLastName;
    private String UserPhoneNumber;

    public BodyPlaceOrder(int amount_cents, String currency, List<Items> itemsList, String userEmail, String userID, String userFirstName, String userLastName, String userPhoneNumber) {
        Amount_cents = amount_cents;
        Currency = currency;
        this.Items = itemsList;
        UserEmail = userEmail;
        UserID = userID;
        UserFirstName = userFirstName;
        UserLastName = userLastName;
        UserPhoneNumber = userPhoneNumber;
    }


}
