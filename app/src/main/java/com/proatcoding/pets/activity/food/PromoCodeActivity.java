package com.proatcoding.pets.activity.food;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proatcoding.pets.R;
import com.proatcoding.pets.adapter.PromoCodeAdapter;
import com.proatcoding.pets.databinding.ActivityPromoCodeBinding;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.CheckPromoCodeDTO;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.PromoCodeDTO;
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

public class PromoCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = PromoCodeActivity.class.getSimpleName();
    ActivityPromoCodeBinding promoCodeBinding;
    Context mContext;
    SharedPrefrence preference;
    LoginDTO loginDTO;
    private ArrayList<PromoCodeDTO> promoCodeDTOArrayList;
    PromoCodeAdapter promoCodeAdapter;
    CheckPromoCodeDTO checkPromoCodeDTO;
    String final_price ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promoCodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_promo_code);
        mContext = PromoCodeActivity.this;

        init();
    }

    public void init(){
        preference = SharedPrefrence.getInstance(this);
        loginDTO = preference.getParentUser(Consts.LOGINDTO);

        if(getIntent().hasExtra(Consts.PRICE)){
            final_price = getIntent().getStringExtra(Consts.PRICE);
        }
        promoCodeBinding.tvApply.setOnClickListener(this);
        promoCodeBinding.back.setOnClickListener(this);
        promoCodeBinding.svPromo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    promoCodeAdapter.filter(s);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });



        if (NetworkManager.isConnectToInternet(mContext)) {
            getPromoCode();
        } else {
            ProjectUtils.showLong(mContext, "Please enable your internet connection.");
        }
    }

    public void setAdapter(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        promoCodeBinding.rvPromoCodeList.setLayoutManager(mLayoutManager);
        promoCodeBinding.rvPromoCodeList.setItemAnimator(new DefaultItemAnimator());

        promoCodeAdapter = new PromoCodeAdapter(mContext, promoCodeDTOArrayList);
        promoCodeBinding.rvPromoCodeList.setAdapter(promoCodeAdapter);
    }

    public void getPromoCode() {
        new HttpsRequest(Consts.GET_PROMO_CODE, getParmsPromoCode(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    Type listType = new TypeToken<List<PromoCodeDTO>>() {
                    }.getType();
                    try {
                        promoCodeDTOArrayList = new ArrayList<>();
                        promoCodeDTOArrayList = (ArrayList<PromoCodeDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), listType);

                        setAdapter();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //   ProjectUtils.showLong(mContext, msg);
                }
            }
        });
    }

    public HashMap<String, String> getParmsPromoCode() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.USER_ID, loginDTO.getId());
        Log.e("parms", parms.toString());
        return parms;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tvApply:
                checkPromoCode();
                break;
        }
    }


    public void setadadsf(){
        PromoCodeDTO promoCodeDTO = new PromoCodeDTO();
        promoCodeDTO.setPromo_code(checkPromoCodeDTO.getPromo_code());
        promoCodeDTO.setPromo_code_id(checkPromoCodeDTO.getPromo_code_id());
        promoCodeDTO.setPromo_code_type(checkPromoCodeDTO.getPromo_code_type());
        promoCodeDTO.setDescription(checkPromoCodeDTO.getDescription());

        Intent intent = new Intent();
        intent.putExtra(Consts.DTO, promoCodeDTO);
        setResult(Consts.PROMO_CODE_REQUEST, intent);
        finish();
    }


    public void checkPromoCode() {
        new HttpsRequest(Consts.CHECK_PROMO_CODE, getParmsCheckPromoCode(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        checkPromoCodeDTO = new Gson().fromJson(response.getJSONObject("data").toString(), CheckPromoCodeDTO.class);
                        setadadsf();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //   ProjectUtils.showLong(mContext, msg);
                    if(msg.contains("Promo Code is invalid")){
                        Log.e(TAG, "backResponse: "+msg);
                        dialogShow(msg);
                    }
                }
            }
        });
    }

    public HashMap<String, String> getParmsCheckPromoCode() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.PROMO_CODE, promoCodeBinding.etPromoCode.getText().toString());
        parms.put(Consts.FINAL_PRICE, final_price.split(" ")[1]);
        parms.put(Consts.USER_ID, loginDTO.getId());

        Log.e("parms", parms.toString());
        return parms;
    }


    public void dialogShow(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(""+msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.dismiss();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        PromoCodeDTO promoCodeDTO = new PromoCodeDTO();
        Intent intent = new Intent();
        intent.putExtra(Consts.DTO, promoCodeDTO);
        setResult(Consts.PROMO_CODE_REQUEST, intent);
        finish();
    }
}
