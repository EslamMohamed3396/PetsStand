package com.proatcoding.pets.activity.payment.WeAcceptSDK.helper;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;

public class StringPOSTRequest extends StringRequest {

    private String postContents;

    public StringPOSTRequest(String url, String postContents, Response.Listener<String> listener, ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.setShouldCache(false);
        this.postContents = postContents;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return postContents.getBytes();
    }

    @Override
    public String getBodyContentType()
    {
        return "application/json";
    }

}