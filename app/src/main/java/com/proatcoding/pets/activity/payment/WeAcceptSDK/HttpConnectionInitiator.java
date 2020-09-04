package com.proatcoding.pets.activity.payment.WeAcceptSDK;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;

class HttpConnectionInitiator extends AsyncTask<String, Void, String> {
    HttpURLConnection http = null;
    AsyncResponse delegate = null;
    String apiName = null, status_code = null;
    Context context;

    HttpConnectionInitiator(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        apiName = params[0];

        String link = null;
        String apiCallResult = null;
        switch (apiName) {
            case ServerUrls.API_NAME_USER_PAYMENT: {
                link = ServerUrls.API_URL_USER_PAYMENT;
                break;
            }
            case ServerUrls.API_NAME_TOKENIZE_CARD: {
                link = ServerUrls.API_URL_TOKENIZE_CARD + params[2];
                break;
            }
        }


        try {
            HttpPost httpPost = new HttpPost(link);
            httpPost.setEntity(new StringEntity(params[1]));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);

            Log.d("OkHttp", "the link is " + link);

            //TODO
            status_code = httpResponse.getStatusLine().getStatusCode() + "";

            apiCallResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            Log.e("OkHttp", e.getMessage());
        }

        return apiCallResult;
    }

    @Override
    protected void onPostExecute(String apiCallResult) {
        super.onPostExecute(apiCallResult);
        //TODO
        if (delegate != null) {
            delegate.processFinish(apiName, apiCallResult, status_code);
        }
    }
}
