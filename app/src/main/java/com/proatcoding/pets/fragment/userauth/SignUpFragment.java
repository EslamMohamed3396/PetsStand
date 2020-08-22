package com.proatcoding.pets.fragment.userauth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hbb20.CountryCodePicker;
import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.register.OTPActivity;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.network.NetworkManager;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomButton;
import com.proatcoding.pets.utils.CustomEditText;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by pushpraj on 19/2/18.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener {

    View view;
    private CustomButton CBsignup;

    private CustomEditText CETYname, CETYLname, CETCpassword, CETemail, CETpassword;
    private CustomEditText mobileET;

    LoginDTO loginDTO = new LoginDTO();
    LinearLayout LLvisible,LLCvisible;
    SharedPrefrence sharedPrefrence;
    ImageView text_visible3,text_Cvisible3;
    private boolean isHide = false;
    private boolean isCHide = false;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    LoginFragment loginFragment = new LoginFragment();

    private String otp;
    private String TAG = SignUpFragment.class.getSimpleName();
    private CountryCodePicker countryCodePicker;
    HashMap<String, String> parms = new HashMap<>();

    private SharedPreferences userDetails;
 //   private TelephonyManager telephonyManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        sharedPrefrence = SharedPrefrence.getInstance(getActivity());
        init();
        userDetails = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.e("tokensss", userDetails.getString(Consts.TOKAN, ""));
      /*  telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        Log.e("LOGIN_ID", "my id: " + telephonyManager.getDeviceId() + "  co" + telephonyManager.getSimCountryIso());*/
        return view;
    }

    private void init() {
        mobileET = (CustomEditText) view.findViewById(R.id.mobileET);

        CBsignup = (CustomButton) view.findViewById(R.id.CBsignup);
        CETYname = (CustomEditText) view.findViewById(R.id.CETYname);
        CETYLname = (CustomEditText) view.findViewById(R.id.CETYLname);
        CETCpassword = (CustomEditText) view.findViewById(R.id.CETCpassword);
        CETemail = (CustomEditText) view.findViewById(R.id.CETemail);
        CETpassword = (CustomEditText) view.findViewById(R.id.CETpassword);
        LLvisible = (LinearLayout) view.findViewById(R.id.LLvisible);
        LLCvisible = (LinearLayout) view.findViewById(R.id.LLCvisible);

        text_visible3 = (ImageView) view.findViewById(R.id.text_visible3);
        text_Cvisible3 = (ImageView) view.findViewById(R.id.text_Cvisible3);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        CBsignup.setOnClickListener(this);
        LLvisible.setOnClickListener(this);
        LLCvisible.setOnClickListener(this);

        countryCodePicker = view.findViewById(R.id.ccp);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CBsignup:
                submit();
                break;
            case R.id.LLvisible:
                eyePassword();
                break;

                case R.id.LLCvisible:
                eyeCPassword();
                break;

        }
    }


    private void eyePassword() {
        if (isHide) {
            text_visible3.setImageResource(R.drawable.visible_black);
            CETpassword.setTransformationMethod(null);
            isHide = false;
        } else {
            text_visible3.setImageResource(R.drawable.invisible_black);
            CETpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isHide = true;
        }
    }
    private void eyeCPassword() {
        if (isCHide) {
            text_Cvisible3.setImageResource(R.drawable.visible_black);
            CETCpassword.setTransformationMethod(null);
            isCHide = false;
        } else {
            text_Cvisible3.setImageResource(R.drawable.invisible_black);
            CETCpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isCHide = true;
        }
    }

    private void submit() {

        if (!validateFirstName()) {
            return;
        } else if (!validateLastName()) {
            return;
        } else if (!validateEmail()) {
            return;
        } else if (!validateNumber()) {
            return;
        } /*else if (!validatePassword(CETpassword)) {
            return;
        } else if (!checkpass()) {
            return;
        }*/ else {
            if (NetworkManager.isConnectToInternet(getActivity())) {
                Random otp1 = new Random();
                StringBuilder builder = new StringBuilder();
                for (int count = 0; count <= 3; count++) {
                    builder.append(otp1.nextInt(10));
                }
                otp = builder.toString();
                sendOTP();
            } else {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_concation));
            }
        }
    }

    public boolean validateEmail() {
        if (!ProjectUtils.isEmailValid(CETemail.getText().toString().trim())) {
            CETemail.setError("Please enter correct email.");
            CETemail.requestFocus();
            return false;
        } else {
            CETemail.setError(null);
            CETemail.clearFocus();
            return true;
        }
    }

    public boolean validateNumber() {
        if (!ProjectUtils.isEditTextFilled(mobileET)) {
            mobileET.setError("Please Enter mobile number");
            mobileET.requestFocus();
            return false;
        } else {
            mobileET.setError(null);
            mobileET.clearFocus();
            return true;
        }
    }

    public boolean validateFirstName() {
        if (!ProjectUtils.isEditTextFilled(CETYname)) {
            CETYname.setError("Please Enter first name");
            CETYname.requestFocus();
            return false;
        } else {
            CETYname.setError(null);
            CETYname.clearFocus();
            return true;
        }
    }

    public boolean validateLastName() {
        if (!ProjectUtils.isEditTextFilled(CETYLname)) {
            CETYLname.setError("Please Enter last name");
            CETYLname.requestFocus();
            return false;
        } else {
            CETYLname.setError(null);
            CETYLname.clearFocus();
            return true;
        }
    }

    /*public boolean validatePassword(EditText editText) {
        if (editText.getText().toString().trim().equalsIgnoreCase("")) {
            editText.setError(getResources().getString(R.string.password_val));
            editText.requestFocus();
            return false;
        } else {
            if (!ProjectUtils.isPasswordValid(editText.getText().toString().trim())) {
                editText.setError(getResources().getString(R.string.password_val1));
                editText.requestFocus();
                return false;
            } else {
                editText.setError(null);
                editText.clearFocus();
                return true;
            }
        }

    }

    private boolean checkpass() {
        if (CETCpassword.getText().toString().trim().equals("")) {
            CETCpassword.setError(getResources().getString(R.string.password_val1));
            return false;
        } else if (!CETpassword.getText().toString().trim().equals(CETCpassword.getText().toString().trim())) {
            CETCpassword.setError(getResources().getString(R.string.password_val2));
            return false;
        }
        return true;
    }*/

    public void sendOTP() {
        parms.put(Consts.MOBILE_NO, mobileET.getText().toString().trim());
        parms.put(Consts.DEVICE_ID, "12345");
        parms.put(Consts.OTP, otp);
        parms.put(Consts.COUNTRY_CODE, countryCodePicker.getSelectedCountryCode() + "");
        parms.put(Consts.OS_TYPE, "android");
        parms.put(Consts.DEVICE_TOKAN, userDetails.getString(Consts.DEVICE_TOKAN, ""));
        sharedPrefrence.setValue(Consts.OTP, otp);

        ProjectUtils.showProgressDialog(getActivity(), true, "Please Wait!!");
        new HttpsRequest(Consts.SEND_OTP, parms, getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showLong(getActivity(), msg);

                    try {
                        Intent in = new Intent(getActivity(), OTPActivity.class);
                        in.putExtra("number", "+" + countryCodePicker.getSelectedCountryCode() + "-" + mobileET.getText().toString());
                        in.putExtra("mobile_number", mobileET.getText().toString());
                        in.putExtra("country_code", countryCodePicker.getSelectedCountryCode());
                        in.putExtra("otp", otp);
                        in.putExtra("email", CETemail.getText().toString());
                        in.putExtra("first_name", CETYname.getText().toString());
                        in.putExtra("last_name", CETYLname.getText().toString());
                        in.putExtra("user_status", "signUp");
                        startActivity(in);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    ProjectUtils.showLong(getActivity(), msg);
                }
            }
        });
    }

    /*public void signUp() {

        ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));
        AndroidNetworking.post(Consts.BASE_URL + Consts.SIGN_UP)
                .addBodyParameter(getParms())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProjectUtils.pauseProgressDialog();
                        JSONParser jsonParser = new JSONParser(getActivity(), response);

                        if (jsonParser.RESULT) {
                            try {
                                FragmentTransaction signupTransaction = fragmentManager.beginTransaction();
                                signupTransaction.setCustomAnimations(R.anim.pop_enter, R.anim.push_out_left);
                                signupTransaction.replace(R.id.logisignframe, loginFragment, "login");
                                signupTransaction.commitAllowingStateLoss();

                                CETYname.setText("");
                                CETCpassword.setText("");
                                CETemail.setText("");
                                CETpassword.setText("");

                                LoginSignupactivity.IVsignup.setVisibility(View.GONE);
                                LoginSignupactivity.IVlog.setVisibility(View.VISIBLE);
                                LoginSignupactivity.CTVBsignup.setTextColor(getResources().getColor(R.color.gray));
                                LoginSignupactivity.CTVBlogin.setTextColor(getResources().getColor(R.color.black));

                                ProjectUtils.showToast(getActivity(), jsonParser.MESSAGE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            ProjectUtils.showToast(getActivity(), jsonParser.MESSAGE);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Log", anError.getErrorBody());
                        ProjectUtils.pauseProgressDialog();

                    }
                });
    }

    public Map<String, String> getParms() {

        HashMap<String, String> params = new HashMap<>();

        params.put(Consts.EMAIL, CETemail.getText().toString().trim());
        params.put(Consts.FIRST_NAME, CETYname.getText().toString().trim());
        params.put(Consts.PASSWORD, CETpassword.getText().toString().trim());

        Log.e("Login", params.toString());
        return params;
    }*/
}
