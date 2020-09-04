package com.proatcoding.pets.activity.payment.WeAcceptSDK.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ResponseParser {

    HashMap<String, String> returnedMap;
    JSONObject responseObject;

    public ResponseParser(String response) {
        try {
            responseObject = new JSONObject(response);
        } catch (JSONException e) {
            // TODO: Parsing Error
            e.printStackTrace();
        }
        returnedMap = new HashMap<String, String>();
    }

    public boolean isStatusOK() {
        String status = "";

        try {
            status = responseObject.getString("status");
        } catch (JSONException e) {
            // TODO: Parsing Error
            e.printStackTrace();
        }

        return (status.compareTo("OK")) == 0;
    }

    public HashMap<String, String> getDetails() {

        JSONObject detailsObject = null;
        JSONArray detailsArray = null;

        try {
            detailsArray = responseObject.getJSONArray("details");
            detailsObject = detailsArray.getJSONObject(0);
            Iterator<?> objectIterator = detailsObject.keys();

            while (objectIterator.hasNext()) {
                String nextKey = (String)objectIterator.next();
                returnedMap.put(nextKey, detailsObject.getString(nextKey));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return returnedMap;
    }

    public String getFailureMessage() {
        HashMap<String, String> responseMap = getDetails();
        if (responseMap.containsKey("failure_message")) {
            return responseMap.get("failure_message");
        }
        else {
            return null;
        }
    }
}