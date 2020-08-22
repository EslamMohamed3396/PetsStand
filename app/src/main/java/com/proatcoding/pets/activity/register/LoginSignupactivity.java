package com.proatcoding.pets.activity.register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.BaseActivity;
import com.proatcoding.pets.fragment.userauth.LoginFragment;
import com.proatcoding.pets.fragment.userauth.SignUpFragment;
import com.proatcoding.pets.jsonparser.JSONParser;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginSignupactivity extends AppCompatActivity implements View.OnClickListener {

    public static CustomTextViewBold CTVBsignup;
    public static CustomTextViewBold CTVBlogin, CTVBskip;
    public static ImageView IVsignup;
    public static ImageView IVlog;
    private FrameLayout logisignframe;
    Context mContext;
    public String LOGIN_FRAGMENT = "login";
    public String SIGNUP_FRAGMENT = "signup";
    private static int position_tab = 0;
    LoginFragment loginFragment = new LoginFragment();
    SignUpFragment signUpFragment = new SignUpFragment();
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SharedPrefrence prefrence;
    private SharedPreferences userDetails;
    LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signupactivity);
        ProjectUtils.changeStatusBarColorNew(this, R.color.white);
        prefrence = SharedPrefrence.getInstance(mContext);

        userDetails = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.e("tokensss", userDetails.getString(Consts.TOKAN, ""));

        mContext = LoginSignupactivity.this;
        init();
        selectFirstItemAsDefault();

    }

    public void init() {

        CTVBsignup = (CustomTextViewBold) findViewById(R.id.CTVBsignup);
        CTVBlogin = (CustomTextViewBold) findViewById(R.id.CTVBlogin);
        CTVBskip = findViewById(R.id.CTVBskip);
        IVsignup = (ImageView) findViewById(R.id.IVsignup);
        IVlog = (ImageView) findViewById(R.id.IVlog);
        logisignframe = (FrameLayout) findViewById(R.id.logisignframe);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        CTVBsignup.setOnClickListener(this);
        CTVBlogin.setOnClickListener(this);
        CTVBskip.setOnClickListener(this);

    }

    private void selectFirstItemAsDefault() {

        Fragment fragment;
        fragment = loginFragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.logisignframe, fragment, LOGIN_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
        position_tab = 0;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.CTVBsignup:

                FragmentTransaction signupTransaction = fragmentManager.beginTransaction();
                signupTransaction.setCustomAnimations(R.anim.pop_enter, R.anim.push_out_left);
                signupTransaction.replace(R.id.logisignframe, signUpFragment, SIGNUP_FRAGMENT);
                signupTransaction.commitAllowingStateLoss();
                position_tab = 1;

                IVsignup.setVisibility(View.VISIBLE);
                IVlog.setVisibility(View.GONE);
                CTVBsignup.setTextColor(getResources().getColor(R.color.black));
                CTVBlogin.setTextColor(getResources().getColor(R.color.gray));
                View view1 = getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }

                break;
            case R.id.CTVBlogin:

                FragmentTransaction loginTransaction = fragmentManager.beginTransaction();
                loginTransaction.setCustomAnimations(R.anim.pop_enter, R.anim.push_out_left);
                loginTransaction.replace(R.id.logisignframe, loginFragment, LOGIN_FRAGMENT);
                loginTransaction.commitAllowingStateLoss();
                position_tab = 0;

                IVsignup.setVisibility(View.GONE);
                IVlog.setVisibility(View.VISIBLE);
                CTVBsignup.setTextColor(getResources().getColor(R.color.gray));
                CTVBlogin.setTextColor(getResources().getColor(R.color.black));
                View view2 = getCurrentFocus();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }
                break;
            case R.id.CTVBskip:
                guestlogin();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        clickDone();
    }

    public void clickDone() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.walk_icon)
                .setTitle(R.string.app_name)
                .setMessage(getResources().getString(R.string.close_msg))
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


    public void guestLogin(){
        prefrence.setBooleanValue(SharedPrefrence.IS_LOGIN, true);
        Intent intent = new Intent(LoginSignupactivity.this, BaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public Map<String, String> getParmsGuestLogin() {

        HashMap<String, String> params = new HashMap<>();
        params.put(Consts.EMAIL, Consts.GUEST_EMAIL);
        params.put(Consts.PASSWORD, Consts.GUEST_PASSWORD);
        params.put(Consts.DEVICE_TOKAN, userDetails.getString(Consts.TOKAN, ""));
        params.put(Consts.DEVICE_ID, "123456");
        params.put(Consts.OS_TYPE, "android");

        Log.e("Login", params.toString());
        return params;
    }

    public void guestlogin() {
        ProjectUtils.showProgressDialog(this, true, getResources().getString(R.string.please_wait));
        AndroidNetworking.post(Consts.BASE_URL + Consts.LOGIN)
                .addBodyParameter(getParmsGuestLogin())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProjectUtils.pauseProgressDialog();
                        JSONParser jsonParser = new JSONParser(LoginSignupactivity.this, response);

                        if (jsonParser.RESULT) {
                            try {
                                Log.e("Login", "Guest Login response:--- "+response.toString());
                                loginDTO = new Gson().fromJson(response.getJSONObject("data").toString(), LoginDTO.class);
                                prefrence.setParentUser(loginDTO, Consts.LOGINDTO);

                                guestLogin();

//                                ProjectUtils.showToast(LoginSignupactivity.this, jsonParser.MESSAGE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            ProjectUtils.showToast(LoginSignupactivity.this, jsonParser.MESSAGE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Log", anError.getErrorBody());
                        ProjectUtils.pauseProgressDialog();
                    }
                });
    }

}
