package com.proatcoding.pets.activity.food;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;
import com.google.android.gms.location.places.Place;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.proatcoding.pets.ClientNetwork.Cient.ClientWeAccept;
import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.payment.WeAcceptSDK.IntentConstants;
import com.proatcoding.pets.activity.payment.WeAcceptSDK.PayActivity;
import com.proatcoding.pets.activity.payment.WeAcceptSDK.PayActivityIntentKeys;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.AddressDTO;
import com.proatcoding.pets.models.CartDTO;
import com.proatcoding.pets.models.CheckPromoCodeDTO;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.MakeOrderDTO;
import com.proatcoding.pets.models.weAcceptPayment.placeOrder.request.BodyPlaceOrder;
import com.proatcoding.pets.models.weAcceptPayment.placeOrder.request.Items;
import com.proatcoding.pets.models.weAcceptPayment.placeOrder.response.ResponsePlaceOrder;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomEditText;
import com.proatcoding.pets.utils.ProjectUtils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.schibstedspain.leku.LocationPickerActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;

public class OrdershipedActivity extends AppCompatActivity implements OnClickListener, PaymentResultListener {

    Context mContext;
    CustomEditText cetZipCode, cetName, cetMobileNo, cetAddressMap, cetAddress, cetSpecialNote, cetEmail, cetCity, cetCountry;
    RadioGroup RGpayment;
    RadioButton RBcaseon, RBonline;

    CardView cvOrder;
    LinearLayout back;
    private static final String TAG = "OrdershipedActivity";
    private LoginDTO loginDTO;
    private Place place;
    private MakeOrderDTO makeOrderDTO;
    HashMap<String, String> parmsOrder = new HashMap<>();
    private CountryCodePicker countryCodePicker;
    String orderID = "";
    private String totalPay = "", shoppingpay = "";
    Double latitude, longitude;
    AddressDTO addressDTO;
    int flag = 0;
    String orderId;
    String checksumString = "";
    float finalPrice;
    String selectedRadioButton = "";
    CheckPromoCodeDTO checkPromoCodeDTO;
    String figure = "0", promo_code = "", promo_code_id = "0", promo_code_type = "0", final_price = "0", discount = "0";
    private ArrayList<CartDTO> cartDTOList;

    String txRef;
    String country = "US", currency = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(OrdershipedActivity.this);
        setContentView(R.layout.activity_ordershiped);
        mContext = OrdershipedActivity.this;
        SharedPrefrence prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getParentUser(Consts.LOGINDTO);

        totalPay = getIntent().getStringExtra(Consts.PAYMENT_STATUS);
        shoppingpay = getIntent().getStringExtra(Consts.SHIPPING_COST);


        finalPrice = (Float.valueOf(totalPay) + Float.valueOf(shoppingpay));
        if (getIntent().hasExtra(Consts.DTO)) {
            cartDTOList = (ArrayList<CartDTO>) getIntent().getSerializableExtra(Consts.CART);
            checkPromoCodeDTO = (CheckPromoCodeDTO) getIntent().getSerializableExtra(Consts.DTO);
            if (checkPromoCodeDTO != null) {
                figure = checkPromoCodeDTO.getFigure();
                promo_code = checkPromoCodeDTO.getPromo_code();
                promo_code_id = checkPromoCodeDTO.getPromo_code_id();
                promo_code_type = checkPromoCodeDTO.getPromo_code_type();
                final_price = checkPromoCodeDTO.getFinal_price();
                discount = checkPromoCodeDTO.getDiscount();

            } else {
                figure = "0";
                promo_code = "";
                promo_code_id = "0";
                promo_code_type = "0";
                final_price = String.valueOf((Float.valueOf(totalPay) + Float.valueOf(shoppingpay)));
                discount = "0";
            }
        } else {
            finalPrice = (Float.valueOf(totalPay) + Float.valueOf(shoppingpay));
        }

        Checkout.preload(getApplicationContext());
        findUI();
        if (getIntent().hasExtra(Consts.FLAG)) {
            flag = getIntent().getIntExtra(Consts.FLAG, 0);
            if (flag == 1) {
                addressDTO = (AddressDTO) getIntent().getSerializableExtra(Consts.ADDRESS);
                showData();
            }
        }

    }

    private void showData() {
        cetAddressMap.setText(addressDTO.getLandMark());
        cetCountry.setText(addressDTO.getCountry());
        cetCity.setText(addressDTO.getCity());
        cetAddress.setText(addressDTO.getAddress());
        cetZipCode.setText(addressDTO.getZip());

    }

    private void findUI() {
        countryCodePicker = findViewById(R.id.ccp);
        cetName = findViewById(R.id.cetName);
        cetMobileNo = findViewById(R.id.cetMobileNo);
        cetAddressMap = findViewById(R.id.cetAddressMap);
        cetAddress = findViewById(R.id.cetAddress);
        RGpayment = findViewById(R.id.RGpayment);
        RBonline = findViewById(R.id.RBonline);
        RBcaseon = findViewById(R.id.RBcaseon);
        cvOrder = findViewById(R.id.cvOrder);
        RBcaseon = findViewById(R.id.RBcaseon);
        back = findViewById(R.id.back);
        cetSpecialNote = findViewById(R.id.cetNote);
        cetEmail = findViewById(R.id.cetEmail);
        cetCity = findViewById(R.id.cetCity);
        cetCountry = findViewById(R.id.cetCountry);
        cetZipCode = findViewById(R.id.cetZipCode);

        back.setOnClickListener(this);
        cvOrder.setOnClickListener(this);
        cetAddressMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cvOrder:
                int selectedId = RGpayment.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = findViewById(selectedId);
                selectedRadioButton = radioButton.getText().toString();
                order();
                break;
            case R.id.cetAddressMap:
                ProjectUtils.showProgressDialog(mContext, true, "Please Wait!!");
                findPlace();
                break;
        }
    }

    private void order() {
        if (!validateName()) {
            return;
        } else if (!validateMobile()) {
            return;
        } else if (!validateEmail()) {
            return;
        } else if (!validateAddress(cetAddressMap)) {
            return;
        } else if (!validateAddress(cetAddress)) {
            return;
        } else if (!validateCity()) {
            return;
        } else if (!validateCountry()) {
            return;
        } else if (!validateZip()) {
            return;
        } else {
            if (selectedRadioButton.equalsIgnoreCase(getResources().getString(R.string.online_payment))) {
                //payment();
//                genrateCheckSum();
                //paymentFlutterWave();
                setPlaceOrder((int) finalPrice);
            } else if (selectedRadioButton.equalsIgnoreCase(getResources().getString(R.string.cod))) {
                Random otp1 = new Random();
                StringBuilder builder = new StringBuilder();
                for (int count = 0; count <= 10; count++) {
                    builder.append(otp1.nextInt(10));
                }
                orderID = builder.toString();
                makeOrder("", "0");
            }
        }
    }


    private void startPayActivityNoToken(String key) {
        Intent pay_intent = new Intent(this, PayActivity.class);
        putNormalExtras(pay_intent, key);
        startActivityForResult(pay_intent, 10);
    }


    private void putNormalExtras(Intent intent, String key) {
        intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false);
        intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, false);
        intent.putExtra(PayActivityIntentKeys.SHOW_ALERTS, true);
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, key);
    }


    private void setPlaceOrder(int total) {

        List<Items> itemsList = new ArrayList<>();

        Items items;

        for (int i = 0; i < cartDTOList.size(); i++) {
            double price = cartDTOList.get(i).getProduct_total_price() * 100;
            items = new Items(cartDTOList.get(i).getP_name(), cartDTOList.get(i).getP_description(), (int) price, Integer.parseInt(cartDTOList.get(i).getQuantity()));
            itemsList.add(items);
        }


        double totalPrice = total * 100;
        BodyPlaceOrder bodyPlaceOrder = new BodyPlaceOrder((int) totalPrice, "EGP",
                itemsList,
                1 + cetMobileNo.getText().toString() + "@yahoo.com",
                loginDTO.getId(),
                cetName.getText().toString(),
                cetName.getText().toString(),
                cetMobileNo.getText().toString());

        ClientWeAccept.getInstance().responsePlaceOrderObservable(bodyPlaceOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponsePlaceOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponsePlaceOrder responsePlaceOrder) {
                        if (responsePlaceOrder.getStatus() == 1) {
                            startPayActivityNoToken(responsePlaceOrder.getPaymentKey());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void paymentFlutterWave() {
        txRef = " " + UUID.randomUUID().toString();
        RavePayManager ravePayManager = new RavePayManager(this);
        // enableSubAccount(ravePayManager);

        ravePayManager.setAmount(finalPrice)
                .setCountry(country)
                .setCurrency(currency)
                .setEmail(cetEmail.getText().toString().trim())
                .setfName(loginDTO.getFirst_name())
                .setlName(loginDTO.getFirst_name())
                .setNarration(Consts.PAYMENT_FOR_PET)
                .setPublicKey(Consts.FLUTTERWAVE_PUBLIC_key)
                .setEncryptionKey(Consts.FLUTTERWAVE_ENCRYPTION_KEY)
                .setTxRef(txRef)
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(false)
                .acceptGHMobileMoneyPayments(false)
                .onStagingEnv(true)
                .allowSaveCardFeature(true)
                .withTheme(R.style.DefaultPayTheme)
                .initialize();
    }

//    public void genrateCheckSum() {
//        Random otp1 = new Random();
//        StringBuilder builder = new StringBuilder();
//        for (int count = 0; count <= 10; count++) {
//            builder.append(otp1.nextInt(10));
//        }
//        orderID = builder.toString();
//        finalPrice = (Float.valueOf(totalPay) + Float.valueOf(shoppingpay));
//
//        HashMap<String, String> checksumParams = new HashMap<>();
//
//        checksumParams.put(Consts.MID_PAYTM, Consts.PAYTM_MID);
//        checksumParams.put(Consts.ORDER_ID_PAYTM, orderID);
//        checksumParams.put(Consts.CUST_ID_PAYTM, loginDTO.getId());
//        checksumParams.put(Consts.MOBILE_NO_PAYTM, cetMobileNo.getText().toString());
//        checksumParams.put(Consts.EMAIL_PAYTM, cetEmail.getText().toString().trim());
//        checksumParams.put(Consts.CHANNEL_ID_PAYTM, "WAP");
//        checksumParams.put(Consts.WEBSITE_PAYTM, "DEFAULT");//WEBSTAGING
//        checksumParams.put(Consts.TXN_AMOUNT_PAYTM, finalPrice + "");
//        checksumParams.put(Consts.INDUSTRY_TYPE_ID_PAYTM, "Retail105");
//        checksumParams.put(Consts.CALLBACK_URL_PAYTM, Consts.PAYTM_CALLBACK_URL + orderID);
//
//        Log.e(TAG, " param --->" + checksumParams.toString());
//
//
//        ProjectUtils.showProgressDialog(mContext, true, "Please Wait!!");
//        AndroidNetworking.post(Consts.PAYTM_CHECKSUM)
//                .addBodyParameter(checksumParams)
//                .setTag("test")
//                .setPriority(Priority.HIGH)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        ProjectUtils.pauseProgressDialog();
//                        Log.e(TAG, " response body --->" + response.toString());
//
//                        JSONParser jsonParser = new JSONParser(mContext, response);
//                        if (jsonParser.RESULT) {
//                            try {
//                                checksumString = response.getJSONObject("data").getString("CHECKSUMHASH");
//                                makeOrder("", "0");
//                                initializePaytmPayment();
//                            } catch (Exception e) {
//
//                            }
//                        } else {
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        ProjectUtils.pauseProgressDialog();
//                        Log.e(TAG, " error body --->" + anError.getErrorBody() + " error msg --->" + anError.getMessage());
//                    }
//                });
//    }
//
//
//    private void initializePaytmPayment() {
//        PaytmPGService paytmPGService = PaytmPGService.getProductionService();
//
//        HashMap<String, String> Params = new HashMap<>();
//
//        Params.put(Consts.MID_PAYTM, Consts.PAYTM_MID);
//        Params.put(Consts.ORDER_ID_PAYTM, orderID);
//        Params.put(Consts.CUST_ID_PAYTM, loginDTO.getId());
//        Params.put(Consts.MOBILE_NO_PAYTM, cetMobileNo.getText().toString());
//        Params.put(Consts.EMAIL_PAYTM, cetEmail.getText().toString().trim());
//        Params.put(Consts.CHANNEL_ID_PAYTM, "WAP");
//        Params.put(Consts.WEBSITE_PAYTM, "DEFAULT"); // WEBSTAGING
//        Params.put(Consts.TXN_AMOUNT_PAYTM, finalPrice + "");
//        Params.put(Consts.INDUSTRY_TYPE_ID_PAYTM, "Retail105");
//        Params.put(Consts.CALLBACK_URL_PAYTM, Consts.PAYTM_CALLBACK_URL + orderID);
//        Params.put(Consts.CHECKSUMHASH_PAYTM, checksumString);
//
//        Log.e(TAG, "Paytm param --->" + Params.toString());
//
//        //creating a paytmOrder instance using the hashmap
//        PaytmOrder order = new PaytmOrder(Params);
//
//        //initializing a PaytmPGService
//        paytmPGService.initialize(order, null);
//
//        //finally starting the paytm payment transaction using PaytmPGService
//        paytmPGService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
//            @Override
//            public void onTransactionResponse(Bundle inResponse) {
//                Log.e("TXNID", "onTransactionResponse: " + inResponse.getString("TXNID"));
//                Log.e(TAG, "onTransactionResponse: " + inResponse.getString("RESPMSG"));
//
//                if (inResponse.getString("STATUS").equals("TXN_SUCCESS")) {
//
//                    try {
//                        transactionStatus(orderID);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    String TXNID = inResponse.getString("TXNID");
//                    makeOrder(TXNID, "1");
//                }
//
//            }
//
//            @Override
//            public void networkNotAvailable() {
//                Log.d(TAG, "networkNotAvailable: ");
//            }
//
//            @Override
//            public void clientAuthenticationFailed(String inErrorMessage) {
//            }
//
//            @Override
//            public void someUIErrorOccurred(String inErrorMessage) {
//
//            }
//
//            @Override
//            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//                Log.d(TAG, "onErrorLoadingWebPage: " + String.valueOf(iniErrorCode));
//                Log.d(TAG, "onErrorLoadingWebPage: " + inErrorMessage);
//                Log.d(TAG, "onErrorLoadingWebPage: " + inFailingUrl);
//            }
//
//            @Override
//            public void onBackPressedCancelTransaction() {
//
//            }
//
//            @Override
//            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                Log.d(TAG, "onTransactionCancel: " + inErrorMessage);
//                Log.d(TAG, "onTransactionCancel: " + inResponse);
//            }
//        });
//    }


//    public void payment() {
//
//
//        String url = "http://phpstack-225750-688566.cloudwaysapps.com/Paypal/paypal?user_id=" + loginDTO.getId() +
//                "&order_id=" + orderID + "&user_name=" + cetName.getText().toString() + "&amount=1"/* +totalPay*/;
//        Intent intent = new Intent(OrdershipedActivity.this, PaymentViewActivity.class);
//        intent.putExtra(Consts.PAYAMENT_URL, url);
//        intent.putExtra(Consts.ADDRESS, cetAddress.getText().toString());
//        intent.putExtra(Consts.LANDMARK, cetAddressMap.getText().toString());
//        intent.putExtra(Consts.NAME, cetName.getText().toString());
//        intent.putExtra(Consts.COUNTRY_CODE, countryCodePicker.getSelectedCountryCode() + "");
//        intent.putExtra(Consts.MOBILE_NO, cetMobileNo.getText().toString());
//        intent.putExtra(Consts.EMAIL, cetEmail.getText().toString());
//        intent.putExtra(Consts.CITY, cetCity.getText().toString());
//        intent.putExtra(Consts.COUNTRY, cetCountry.getText().toString());
//        intent.putExtra(Consts.ZIP, cetZipCode.getText().toString().trim());
//        intent.putExtra(Consts.ORDER_ID, orderID);
//        startActivity(intent);
//        finish();
//    }


    public boolean validateName() {
        if (!ProjectUtils.isEditTextFilled(cetName)) {
            cetName.setError("Please enter your name");
            cetName.requestFocus();
            return false;
        } else {
            cetName.setError(null);
            cetName.clearFocus();
            return true;
        }
    }

    public boolean validateZip() {
        if (!ProjectUtils.isEditTextFilled(cetZipCode)) {
            cetZipCode.setError("Please enter zip code");
            cetZipCode.requestFocus();
            return false;
        } else {
            cetZipCode.setError(null);
            cetZipCode.clearFocus();
            return true;
        }
    }

    public boolean validateCountry() {
        if (!ProjectUtils.isEditTextFilled(cetCountry)) {
            cetCountry.setError("Please enter your country");
            cetCountry.requestFocus();
            return false;
        } else {
            cetCountry.setError(null);
            cetCountry.clearFocus();
            return true;
        }
    }

    public boolean validateCity() {
        if (!ProjectUtils.isEditTextFilled(cetCity)) {
            cetCity.setError("Please enter your city");
            cetCity.requestFocus();
            return false;
        } else {
            cetCity.setError(null);
            cetCity.clearFocus();
            return true;
        }
    }

    public boolean validateMobile() {
        countryCodePicker.registerCarrierNumberEditText(cetMobileNo);
        if (!countryCodePicker.isValidFullNumber()) {
            cetMobileNo.setError("Please enter your mobile.");
            cetMobileNo.requestFocus();
            return false;
        } else {
            cetMobileNo.setError(null);
            cetMobileNo.clearFocus();
            return true;
        }
    }

    public boolean validateEmail() {
        if (!ProjectUtils.isEmailValid(cetEmail.getText().toString().trim())) {
            cetEmail.setError("Please enter correct email.");
            cetEmail.requestFocus();
            return false;
        } else {
            cetEmail.setError(null);
            cetEmail.clearFocus();
            return true;
        }

    }

    public boolean validateAddress(CustomEditText editText) {
        if (!ProjectUtils.isEditTextFilled(editText)) {
            editText.setError("Please enter your Address.");
            editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            editText.clearFocus();
            return true;
        }
    }


    public void findPlace() {
        try {
            Intent locationPickerIntent = new LocationPickerActivity.Builder()
                    .withGooglePlacesEnabled()
                    .build(mContext);

            startActivityForResult(locationPickerIntent, 101);
        } catch (Exception e) {
            // TODO: Handle the error.
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                try {
                    ProjectUtils.pauseProgressDialog();

                    latitude = data.getDoubleExtra(LATITUDE, 0.0);
                    longitude = data.getDoubleExtra(LONGITUDE, 0.0);
                    Log.d("LONGITUDE****", longitude.toString());

                    String postalcode = data.getStringExtra(ZIPCODE);

                    getAddress(latitude, longitude);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            Log.e(TAG, "onActivityResult: " + data);
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Log.e("RESULT_SUCCESS", data.getStringExtra("response"));

                makeOrder(txRef, "1");
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Log.e("RESULT_ERROR", data.toString());
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Log.e("RESULT_CANCELLED", data.toString());
            }
        } else if (requestCode == 10) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (resultCode == 0) {
                } else if (resultCode == IntentConstants.USER_CANCELED) {
                    // User canceled and did no payment request was fired
                    Log.d(TAG, "Subscribed- USER_CANCELED");
                } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
                    // You forgot to pass an important key-value pair in the intent's extras
                    Log.d(TAG, "Subscribed- MISSING_ARGUMENT" + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE));
                } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
                    // An error occurred while handling an API's response
                    Log.d(TAG, "Subscribed- TRANSACTION_ERROR  " + extras.getString(IntentConstants.TRANSACTION_ERROR_REASON));

                } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
                    // User attempted to pay but their transaction was rejected
                    Log.d(TAG, "Subscribed- TRANSACTION_REJECTED");

                    // Use the static keys declared in PayResponseKeys to extract the fields you want
                } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
                    // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
                    Log.d(TAG, "Subscribed- TRANSACTION_REJECTED_PARSING_ISSUE");

                } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
                    Log.d(TAG, "Subscribed- TRANSACTION_SUCCESSFUL");
                    // User finished their payment successfully

                    // Use the static keys declared in PayResponseKeys to extract the fields you want
                } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
                    // User finished their payment successfully. An error occured while reading the returned JSON.

                    Log.d(TAG, "Subscribed- TRANSACTION_SUCCESSFUL_PARSING_ISSUE");
                } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
                    Log.d(TAG, "Subscribed- TRANSACTION_SUCCESSFUL_PARSING_ISSUE");
                }
            }
        }
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            Log.e("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);

            cetAddressMap.setText(obj.getAddressLine(0));

            String areacode = obj.getPostalCode();
            String city = obj.getSubAdminArea();
            String state = obj.getAdminArea();
            String country = obj.getCountryName();

         /*   if (city != null) {
                cetCity.setText(city);
            }
            if (country != null) {
                cetCountry.setText("india");
            }*/
            if (areacode != null) {
                cetZipCode.setText(areacode);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void makeOrder(String paymentId, final String status) {
        orderID = String.valueOf((long) Math.floor(Math.random() * 9000000000L));
        String payment_mode = "";
        parmsOrder.put(Consts.USER_ID, loginDTO.getId());
        parmsOrder.put(Consts.ADDRESS, cetAddressMap.getText().toString());
        parmsOrder.put(Consts.PAYMENT_ID, paymentId);
        parmsOrder.put(Consts.LANDMARK, cetAddress.getText().toString());
        parmsOrder.put(Consts.NAME, cetName.getText().toString());
        parmsOrder.put(Consts.COUNTRY_CODE, countryCodePicker.getSelectedCountryCode() + "");
        parmsOrder.put(Consts.MOBILE_NO, cetMobileNo.getText().toString());
        parmsOrder.put(Consts.EMAIL, cetEmail.getText().toString());
        parmsOrder.put(Consts.SPECIAL_NOTE, cetSpecialNote.getText().toString());
        parmsOrder.put(Consts.CITY, cetCity.getText().toString());
        parmsOrder.put(Consts.COUNTRY, cetCountry.getText().toString());
        parmsOrder.put(Consts.ZIP, cetZipCode.getText().toString().trim());
        parmsOrder.put(Consts.SHIPPING_COST, shoppingpay);
        parmsOrder.put(Consts.PAYMENT_STATUS, status);
        parmsOrder.put(Consts.ORDER_ID, orderID);

        if (selectedRadioButton.equalsIgnoreCase(getResources().getString(R.string.online_payment))) {
            payment_mode = "0";
            parmsOrder.put(Consts.PAYMENT_MODE, payment_mode);
        } else if (selectedRadioButton.equalsIgnoreCase(getResources().getString(R.string.cod))) {
            payment_mode = "1";
            parmsOrder.put(Consts.PAYMENT_MODE, payment_mode);
        }

        parmsOrder.put(Consts.PROMO_CODE_ID, promo_code_id);
        parmsOrder.put(Consts.PROMO_CODE_TYPE, promo_code_type);
        parmsOrder.put(Consts.FIGURE, figure);
        parmsOrder.put(Consts.PROMO_CODE, promo_code);
        parmsOrder.put(Consts.FINAL_PRICE, final_price);
        parmsOrder.put(Consts.DISCOUNT, discount);

        String finalPayment_mode = payment_mode;

        Log.e(TAG, "makeOrder: " + parmsOrder.toString());

        ProjectUtils.showProgressDialog(mContext, true, "Please Wait!!");
        new HttpsRequest(Consts.ORDER, parmsOrder, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        try {
                            makeOrderDTO = new Gson().fromJson(response.getJSONObject("data").toString(), MakeOrderDTO.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (status.equals("1")) {
                            Intent intent = new Intent(OrdershipedActivity.this, ViewOrderDetails.class);
                            intent.putExtra(Consts.MAKE_ORDER, makeOrderDTO);
                            startActivity(intent);
                            finish();
                        } else if (status.equalsIgnoreCase("0") && finalPayment_mode.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(OrdershipedActivity.this, ViewOrderDetails.class);
                            intent.putExtra(Consts.MAKE_ORDER, makeOrderDTO);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    ProjectUtils.showLong(mContext, msg);

                }
            }
        });
    }

//    public void transactionStatus(String orderId) {
//        new HttpsRequest(Consts.PAYTM_TRANS_STATUS_URL + orderId, mContext).stringGet(TAG, new Helper() {
//            @Override
//            public void backResponse(boolean flag, String msg, JSONObject response) {
//                if (flag) {
//                    Log.e(TAG, "Response :-------" + response.toString());
//                }
//            }
//        });
//    }

//    public void startPayment() {
//
//        Random otp1 = new Random();
//        StringBuilder builder = new StringBuilder();
//        for (int count = 0; count <= 10; count++) {
//            builder.append(otp1.nextInt(10));
//        }
//        orderID = builder.toString();
//
//        makeOrder("", "0");
//
//        float finalPrice = (Float.valueOf(totalPay) + Float.valueOf(shoppingpay)) * 100f;
//
//        final Activity activity = this;
//
//        final Checkout co = new Checkout();
//
//        try {
//            JSONObject options = new JSONObject();
//            options.put("name", cetName.getText().toString());
//            options.put("description", "");
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("currency", "INR");
//            options.put("amount", finalPrice + "");
//
//            JSONObject preFill = new JSONObject();
//            preFill.put("email", cetEmail.getText().toString());
//            preFill.put("contact", cetMobileNo.getText().toString());
//
//            options.put("prefill", preFill);
//
//            co.open(activity, options);
//        } catch (Exception e) {
//            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
//                    .show();
//            e.printStackTrace();
//        }
//    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();

            makeOrder(razorpayPaymentID, "1");

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed" /*+ code + " " + response*/, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


}
