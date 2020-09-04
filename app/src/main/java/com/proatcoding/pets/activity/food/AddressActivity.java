package com.proatcoding.pets.activity.food;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proatcoding.pets.R;
import com.proatcoding.pets.adapter.AdapterAddress;
import com.proatcoding.pets.databinding.ActivityAddressBinding;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.AddressDTO;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.network.NetworkManager;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    private String TAG = AddressActivity.class.getSimpleName();
    SharedPrefrence sharedPrefrence;
    LoginDTO loginDTO;
    ArrayList<AddressDTO> addressDTOList = new ArrayList<>();
    AdapterAddress adapterAddress;
    ActivityAddressBinding binding;
    public String totalPay = "", shoppingpay = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(AddressActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        mContext = AddressActivity.this;
        sharedPrefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = sharedPrefrence.getParentUser(Consts.LOGINDTO);

        totalPay = getIntent().getStringExtra(Consts.PAYMENT_STATUS);
        shoppingpay = getIntent().getStringExtra(Consts.SHIPPING_COST);
        findUI();

    }

    private void findUI() {

        Log.e(TAG, "findUI: " + totalPay);
        binding.llBack.setOnClickListener(this);
        binding.tvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                finish();
                break;
            case R.id.tvSkip:
                Intent inn = new Intent(mContext, OrdershipedActivity.class);
                inn.putExtra(Consts.PAYMENT_STATUS, totalPay);
                inn.putExtra(Consts.SHIPPING_COST, shoppingpay);
                inn.putExtra(Consts.DTO, getIntent().getSerializableExtra(Consts.DTO));
                inn.putExtra(Consts.CART, getIntent().getSerializableExtra(Consts.CART));
                startActivity(inn);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkManager.isConnectToInternet(mContext)) {
            getAddress();
        } else {
            ProjectUtils.showToast(mContext, getString(R.string.internet_concation));
        }
    }

    public void getAddress() {
        ProjectUtils.showProgressDialog(mContext, true, "Please wait....");
        new HttpsRequest(Consts.ADDRESS_FILL1, getparmsCart(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                addressDTOList = new ArrayList<>();
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    Type listType = new TypeToken<List<AddressDTO>>() {
                    }.getType();
                    try {
                        addressDTOList = new Gson().fromJson(response.getJSONArray("data").toString(), listType);

                       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        binding.rvAddress.setLayoutManager(gridLayoutManager);*/

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        binding.rvAddress.setLayoutManager(mLayoutManager);
                        binding.rvAddress.setItemAnimator(new DefaultItemAnimator());
                        adapterAddress = new AdapterAddress(AddressActivity.this, addressDTOList);
                        binding.rvAddress.setAdapter(adapterAddress);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //  ProjectUtils.showLong(mContext, msg);
                    Intent inn = new Intent(mContext, OrdershipedActivity.class);
                    inn.putExtra(Consts.PAYMENT_STATUS, totalPay);
                    inn.putExtra(Consts.SHIPPING_COST, shoppingpay);
                    inn.putExtra(Consts.DTO, getIntent().getSerializableExtra(Consts.DTO));
                    inn.putExtra(Consts.CART, getIntent().getSerializableExtra(Consts.CART));
                    startActivity(inn);
                    finish();

                }
            }
        });

    }

    public HashMap<String, String> getparmsCart() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.USER_ID, loginDTO.getId());
        Log.e("parms", parms.toString());
        return parms;
    }

}
