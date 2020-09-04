package com.proatcoding.pets.activity.payment.WeAcceptSDK.helper;

import android.content.Context;
import android.content.SharedPreferences;


import com.proatcoding.pets.R;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectBuilder {
    private final static String login = "test";
    private final static String password = "test";
    private final static String requestGatewayCode = "W";
    private final static String requestGatewayType = "W";
    private String app_id;
    public JSONObject returnedJSON;


    public JSONObjectBuilder(Context context) throws JSONException {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.pref_file_name), Context.MODE_PRIVATE);
        app_id = sharedPref.getString(context.getString(R.string.app_id), "");
        returnedJSON = new JSONObject();
        returnedJSON.put("APP_ID", app_id);
        returnedJSON.put("LOGIN", login);
        returnedJSON.put("PASSWORD", password);
        returnedJSON.put("REQUEST_GATEWAY_CODE", requestGatewayCode);
        returnedJSON.put("REQUEST_GATEWAY_TYPE", requestGatewayType);

    }

    public JSONObject buildREFUNDJSONObject(String msisdn, String pin, String language, String msisdn2, String amount, String CID) {

        try {
            returnedJSON.put("TYPE", "PREREQ");
            returnedJSON.put("MSISDN", msisdn);//01274155220
            returnedJSON.put("PIN", pin);//123456
            returnedJSON.put("LANGUAGE1", language);
            returnedJSON.put("MSISDN2", msisdn2);//01141501770
            returnedJSON.put("AMOUNT", amount);
            returnedJSON.put("CID", CID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildPurchaseJSONObject(String msisdn, String pin, String language, String mercode, String amount, String orderno, String CID) {

        try {
            returnedJSON.put("TYPE", "CMPREQ");
            returnedJSON.put("MSISDN", msisdn);
            returnedJSON.put("PIN", pin);
            returnedJSON.put("LANGUAGE1", language);
            returnedJSON.put("MERCODE", mercode);
            returnedJSON.put("AMOUNT", amount);
            returnedJSON.put("ORDERNO", orderno);
            returnedJSON.put("CID", CID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildCashinJSONObject(String msisdn, String pin, String language, String msisdn2, String amount, String CID) {

        try {
            returnedJSON.put("TYPE", "RCIREQ");
            returnedJSON.put("MSISDN", msisdn);
            returnedJSON.put("PIN", pin);
            returnedJSON.put("LANGUAGE1", language);
            returnedJSON.put("AMOUNT", amount);
            returnedJSON.put("MSISDN2", msisdn2);
            returnedJSON.put("CID", CID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildBalanceInquiryJSONObject(String msisdn, String pin, String language) {

        try {
            returnedJSON.put("TYPE", "CBEREQ");
            returnedJSON.put("MSISDN", msisdn);
            returnedJSON.put("PIN", pin);
            returnedJSON.put("LANGUAGE1", language);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildUserJSONObject(String username, String password) {

        try {
            returnedJSON.put("TYPE", "MPOSINQREQ");
            returnedJSON.put("MSISDN", username);
            returnedJSON.put("PIN", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }


    public JSONObject buildRSAKeyJSONObject(String androidID, String key) {

        try {
            returnedJSON.put("TYPE", "MPOSKEYREQ");
            returnedJSON.put("LOGIN", "test");
            returnedJSON.put("PASSWORD", "test");
            returnedJSON.put("REQUEST_GATEWAY_CODE", "W");
            returnedJSON.put("REQUEST_GATEWAY_TYPE", "W");
            returnedJSON.put("LANGUAGE1", "2");
            returnedJSON.put("SNO", androidID);
            returnedJSON.put("PUB", key);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildAESKeyJSONObject(String androidID, String key) {

        try {
            returnedJSON.put("TYPE", "MPOSKEYREQ");
            returnedJSON.put("LOGIN", "test");
            returnedJSON.put("PASSWORD", "test");
            returnedJSON.put("REQUEST_GATEWAY_CODE", "W");
            returnedJSON.put("REQUEST_GATEWAY_TYPE", "W");
            returnedJSON.put("LANGUAGE1", "2");
            returnedJSON.put("SNO", androidID);
            returnedJSON.put("KEY", key);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildHistoryJSONObject(String username) {

        try {
            returnedJSON.put("TYPE", "CLTREQ");
            returnedJSON.put("MSISDN", username);
            returnedJSON.put("PIN", "");
            returnedJSON.put("LOGIN", "test");
            returnedJSON.put("PASSWORD", "test");
            returnedJSON.put("REQUEST_GATEWAY_CODE", "W");
            returnedJSON.put("REQUEST_GATEWAY_TYPE", "W");
            returnedJSON.put("LANGUAGE1", "2");
            returnedJSON.put("CID", "");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildShiftJSONObject(String username, String password, String Date) {

        try {
            returnedJSON.put("TYPE", "MPOSINQREQ");
            returnedJSON.put("MSISDN", username);
            returnedJSON.put("PIN", password);
            returnedJSON.put("TIME", Date);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildTxnJSONObject(String msi, String trx_id) {

        try {
            returnedJSON.put("LOGIN", "test");
            returnedJSON.put("PASSWORD", "test");
            returnedJSON.put("REQUEST_GATEWAY_CODE", "W");
            returnedJSON.put("REQUEST_GATEWAY_TYPE", "W");
            returnedJSON.put("TYPE", "CLTXNRREQ");
            returnedJSON.put("MSISDN", msi);
            returnedJSON.put("CID", "");
            returnedJSON.put("TXNREFID", trx_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }

    public JSONObject buildreverseJSONObject(String msi, String msi2, String cid, String pin, String trx_id) {

        try {
            returnedJSON.put("LOGIN", "test");
            returnedJSON.put("PASSWORD", "test");
            returnedJSON.put("REQUEST_GATEWAY_CODE", "W");
            returnedJSON.put("REQUEST_GATEWAY_TYPE", "W");
            returnedJSON.put("TYPE", "PREVREQ");
            returnedJSON.put("MSISDN", msi);
            returnedJSON.put("MSISDN2", msi2);
            returnedJSON.put("PIN", pin);
            returnedJSON.put("CID", cid);
            returnedJSON.put("PurchID", trx_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedJSON;
    }


}