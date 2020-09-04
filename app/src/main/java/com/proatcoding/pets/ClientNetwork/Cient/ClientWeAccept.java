package com.proatcoding.pets.ClientNetwork.Cient;


import com.proatcoding.pets.ClientNetwork.Services.ApiService;
import com.proatcoding.pets.models.weAcceptPayment.placeOrder.request.BodyPlaceOrder;
import com.proatcoding.pets.models.weAcceptPayment.placeOrder.response.ResponsePlaceOrder;
import com.proatcoding.pets.utils.Consts;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientWeAccept {

    private ApiService apiService;
    private static ClientWeAccept SINSTANCE;
    private static OkHttpClient.Builder sBuilder;

    private ClientWeAccept() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_PAYMENT)
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            if (sBuilder == null) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                sBuilder = new OkHttpClient.Builder()
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS)
                        .addInterceptor(httpLoggingInterceptor);
            }
            return sBuilder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static ClientWeAccept getInstance() {
        if (SINSTANCE == null) {
            SINSTANCE = new ClientWeAccept();
        }
        return SINSTANCE;
    }


    public Observable<ResponsePlaceOrder> responsePlaceOrderObservable(BodyPlaceOrder bodyPlaceOrder) {
        return apiService.RESPONSE_PLACE_ORDER_OBSERVABLE(bodyPlaceOrder);
    }

}
