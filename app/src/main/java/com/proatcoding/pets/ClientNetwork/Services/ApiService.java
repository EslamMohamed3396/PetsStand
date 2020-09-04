package com.proatcoding.pets.ClientNetwork.Services;


import com.proatcoding.pets.models.weAcceptPayment.placeOrder.request.BodyPlaceOrder;
import com.proatcoding.pets.models.weAcceptPayment.placeOrder.response.ResponsePlaceOrder;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {


    //payment we accept
    @POST("api/WeAccept/PlaceOrder")
    Observable<ResponsePlaceOrder> RESPONSE_PLACE_ORDER_OBSERVABLE(@Body BodyPlaceOrder bodyPlaceOrder);

}
