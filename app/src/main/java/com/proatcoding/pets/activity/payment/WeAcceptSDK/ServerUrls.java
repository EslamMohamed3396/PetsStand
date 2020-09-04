package com.proatcoding.pets.activity.payment.WeAcceptSDK;

class ServerUrls {
    private static final String PAYMOB_SERVER_IP = "https://accept.paymobsolutions.com/api/acceptance/";
    static final String API_URL_USER_PAYMENT=PAYMOB_SERVER_IP + "payments/pay";
    static final String API_URL_TOKENIZE_CARD=PAYMOB_SERVER_IP + "tokenization?payment_token=";
    static final String API_NAME_USER_PAYMENT = "USER_PAYMENT";
    static final String API_NAME_TOKENIZE_CARD = "CARD_TOKENIZER";
    static final String PAYMOB_SUCCESS_URL = PAYMOB_SERVER_IP + "post_pay";
}
