package com.proatcoding.pets.activity.petprofile;

import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proatcoding.pets.R;
import com.proatcoding.pets.adapter.AdapterShowPetFollowers;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.FollowerDTO;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.network.NetworkManager;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PetFolowersActivity extends AppCompatActivity implements View.OnClickListener {


    JSONObject jsonObject = new JSONObject();
    Context mContext;
    private String TAG = PetFolowersActivity.class.getSimpleName();
    String pettId = "";
    ArrayList<FollowerDTO> getFollowerList;
    AdapterShowPetFollowers adapterShowPetFollowers;
    ImageView ivBack;
    RecyclerView RVview;
    CustomTextViewBold tvNo, CTVheader;
    SwipeRefreshLayout swipeRefreshLayout;
    private HashMap<String, String> params = new HashMap<>();
    SharedPrefrence sharedPrefrence;
    LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(PetFolowersActivity.this);
        setContentView(R.layout.activity_show_pet_view);
        mContext = PetFolowersActivity.this;
        sharedPrefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = sharedPrefrence.getParentUser(Consts.LOGINDTO);
        pettId = getIntent().getStringExtra(Consts.PET_ID);
        findUI();
    }

    private void findUI() {

        ivBack = findViewById(R.id.ivBack);
        RVview = findViewById(R.id.RVview);
        tvNo = findViewById(R.id.tvNo);
        CTVheader = findViewById(R.id.CTVheader);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        CTVheader.setText("Follower");
        tvNo.setText("No Follower Found");


        ivBack.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkManager.isConnectToInternet(mContext)) {
                    getFollower();
                } else {
                    ProjectUtils.showToast(mContext, getString(R.string.internet_concation));
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finishBack();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkManager.isConnectToInternet(mContext)) {
            getFollower();
        } else {
            ProjectUtils.showToast(mContext, getString(R.string.internet_concation));
        }
    }

    private void finishBack() {
        finish();
    }


    public void getFollower() {

        params.put(Consts.PET_ID, pettId);
        params.put(Consts.USER_ID, loginDTO.getId());
        //  ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_MY_PET_FOLLOWERS, params, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                // ProjectUtils.pauseProgressDialog();
                swipeRefreshLayout.setRefreshing(false);
                if (flag) {

                    try {
                        getFollowerList = new ArrayList<>();
                        Type getDTO = new TypeToken<List<FollowerDTO>>() {
                        }.getType();
                        getFollowerList = (ArrayList<FollowerDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getDTO);


                        adapterShowPetFollowers = new AdapterShowPetFollowers(mContext, getFollowerList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        RVview.setLayoutManager(mLayoutManager);
                        RVview.setItemAnimator(new DefaultItemAnimator());
                        RVview.setAdapter(adapterShowPetFollowers);

                        tvNo.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                } else {
                  //  ProjectUtils.showToast(mContext, msg);
                    tvNo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
