package com.proatcoding.pets.activity.register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.BaseActivity;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.network.NetworkManager;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomEditText;
import com.proatcoding.pets.utils.CustomTextView;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;
import com.proatcoding.pets.utils.SmsListener;
import com.proatcoding.pets.utils.SmsReceiver;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.proatcoding.pets.MyFirebaseMessagingService.MyPREFERENCES;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener, SmsListener {
    private String TAG = OTPActivity.class.getSimpleName();
    private SharedPrefrence prefrence;
    private Context mContext;
    private LinearLayout back;
    private CardView CBapprove;
    private CustomEditText etOne;
    private CustomEditText etTwo;
    private CustomEditText etThree;
    private CustomEditText etFour;
    private CustomTextViewBold tvResend;
    private CustomTextView tvMobile;
    private LoginDTO loginDTO;
    private String value_otp, email, countryCode, number;
    public static final String OTP_REGEX = "[0-9]{1,6}";
    private String user_status = "";
    HashMap<String, String> parms = new HashMap<>();
    SharedPreferences sharedpreferences;
    String f_name, l_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(OTPActivity.this);
        setContentView(R.layout.activity_otp);
        mContext = OTPActivity.this;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getParentUser(Consts.LOGINDTO);
        init();
        checkOTP();
    }

    private void init() {
        tvMobile = findViewById(R.id.tvMobile);
        tvMobile.setText(getIntent().getStringExtra("number"));
        value_otp = getIntent().getStringExtra("otp");
        user_status = getIntent().getStringExtra("user_status");

        CBapprove = findViewById(R.id.CBapprove);
        CBapprove.setOnClickListener(this);
        etOne = findViewById(R.id.etOne);
        etTwo = findViewById(R.id.etTwo);
        etThree = findViewById(R.id.etThree);
        etFour = findViewById(R.id.etFour);
        tvResend = findViewById(R.id.tvResend);
        tvResend.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        etOne.addTextChangedListener(new GenericTextWatcher(etOne));
        etTwo.addTextChangedListener(new GenericTextWatcher(etTwo));
        etThree.addTextChangedListener(new GenericTextWatcher(etThree));
        etFour.addTextChangedListener(new GenericTextWatcher(etFour));

    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click_event));
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvResend:
                ProjectUtils.showLong(mContext, "In progress.");
                break;
            case R.id.CBapprove:
                checkOTP();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void clickDone() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.walk_icon)
                .setTitle(R.string.app_name)
                .setMessage("Are you sure want to close PetStand")
                .setPositiveButton("Yes, Ok!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * * function  checkOTP(), check otp entered is true or not
     */

    public void checkOTP() {
        String one = etOne.getText().toString().trim();
        String two = etTwo.getText().toString().trim();
        String three = etThree.getText().toString().trim();
        String four = etFour.getText().toString().trim();
        String otp = one + "" + two + "" + three + "" + four;
        Log.e("OTP", otp);

            SmsReceiver.bindListener(null, "Varun");

            if (NetworkManager.isConnectToInternet(mContext)) {

                /*Here put code of login or signup api*/
                if (user_status.equalsIgnoreCase("login")) {
                    number = getIntent().getStringExtra("mobile_number");
                    countryCode = getIntent().getStringExtra("country_code");

                    getLoginByMobileNo();
                }else if(user_status.equalsIgnoreCase("signUp")){

                    number = getIntent().getStringExtra("mobile_number");
                    countryCode = getIntent().getStringExtra("country_code");
                    email = getIntent().getStringExtra("email");
                    f_name = getIntent().getStringExtra("first_name");
                    l_name = getIntent().getStringExtra("last_name");
                    getSignByMobileNo();
                }

                /*if (!loginDTO.getAddress().equalsIgnoreCase("")) {
                    ProjectUtils.showLong(mContext, "Login Successful");
                    prefrence.setBooleanValue(SharedPrefrence.IS_LOGIN, true);
                    Intent intent = new Intent(mContext, BaseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    prefrence.setBooleanValue(SharedPrefrence.IS_LOGIN, true);
                    ProjectUtils.showLong(mContext, "Please Update your profile.");
                    Intent intent = new Intent(mContext, UserProfileActivity.class);
                    intent.putExtra("FlagLogin", 1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }*/
            } else {
                ProjectUtils.showLong(mContext, "Please enable your internet connection.");
            }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SmsReceiver.bindListener(this, "PetStand");
    }


    @Override
    public void messageReceived(String messageText) {
        try {


            Log.d("Text", messageText);
            //ProjectUtils.showToast(mContext,messageText);
            Pattern pattern = Pattern.compile(OTP_REGEX);
            Matcher matcher = pattern.matcher(messageText);
            String otp_one = "";
            while (matcher.find()) {
                otp_one = matcher.group();
                Log.e("While", otp_one);
            }
            Log.e("ONE", otp_one.charAt(0) + "");
            etOne.setText("" + otp_one.charAt(0));
            etTwo.setText("" + otp_one.charAt(1));
            etThree.setText("" + otp_one.charAt(2));
            etFour.setText("" + otp_one.charAt(3));
            etFour.setSelection(etFour.getText().length());

            // btnSubmit.performClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * automatic moving text on verification
     */

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.etOne:
                    if (text.length() == 1)
                        etTwo.requestFocus();
                    break;
                case R.id.etTwo:
                    if (text.length() == 1)
                        etThree.requestFocus();
                    else
                        etOne.requestFocus();
                    break;
                case R.id.etThree:
                    if (text.length() == 1)
                        etFour.requestFocus();
                    else
                        etTwo.requestFocus();
                    break;
                case R.id.etFour:
                    if (text.length() == 0)
                        etThree.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SmsReceiver.bindListener(null, "Varun");
    }


    public void getLoginByMobileNo() {
        parms.put(Consts.MOBILE_NO, number);
        parms.put(Consts.DEVICE_ID, "12345");
        parms.put(Consts.OS_TYPE, "android");
        parms.put(Consts.DEVICE_TOKAN, ""+sharedpreferences.getString(Consts.TOKAN, ""));
        parms.put(Consts.COUNTRY_CODE, countryCode);

        ProjectUtils.showProgressDialog(mContext, true, "Please Wait!!");
        new HttpsRequest(Consts.LOGIN_MOBILE_NO, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showLong(mContext, msg);

                    try {
                        LoginDTO loginDTO = new Gson().fromJson(response.getJSONObject("data").toString(), LoginDTO.class);
                        prefrence.setParentUser(loginDTO, Consts.LOGINDTO);

                        prefrence.setBooleanValue(SharedPrefrence.IS_LOGIN, true);
                        Intent intent = new Intent(mContext, BaseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    ProjectUtils.showLong(mContext, msg);
                }
            }
        });
    }

    public void getSignByMobileNo() {
        parms.put(Consts.FIRSTNAME, f_name);
        parms.put(Consts.LAST_NAME, l_name);
        parms.put(Consts.MOBILE_NO, number);
        parms.put(Consts.COUNTRY_CODE, ""+countryCode);
        parms.put(Consts.EMAIL, ""+email);
        parms.put(Consts.DEVICE_ID, "12345");
        parms.put(Consts.OS_TYPE, "android");
        parms.put(Consts.DEVICE_TOKAN, ""+sharedpreferences.getString(Consts.TOKAN, ""));

        ProjectUtils.showProgressDialog(mContext, true, "Please Wait!!");
        new HttpsRequest(Consts.SIGNUP_BY_MOBILE_NO, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showLong(mContext, msg);

                    try {
                        LoginDTO loginDTO = new Gson().fromJson(response.getJSONObject("data").toString(), LoginDTO.class);
                        prefrence.setParentUser(loginDTO, Consts.LOGINDTO);

                        prefrence.setBooleanValue(SharedPrefrence.IS_LOGIN, true);
                        Intent intent = new Intent(mContext, BaseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    ProjectUtils.showLong(mContext, msg);
                }
            }
        });
    }


}
