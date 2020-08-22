package com.proatcoding.pets.activity.notification;

import android.content.Context;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proatcoding.pets.R;
import com.proatcoding.pets.adapter.NotificationAdapter;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.NotificationDTO;
import com.proatcoding.pets.network.NetworkManager;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    SharedPrefrence sharedPrefrence;
    LoginDTO loginDTO;

    ImageView ivBack;
    RecyclerView RVitemlistt;
    ArrayList<NotificationDTO> notificationDTOlist;
    private LinearLayoutManager mLayoutManager;
    CustomTextViewBold tvNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(NotificationActivity.this);
        setContentView(R.layout.activity_notification);
        mContext = NotificationActivity.this;
        sharedPrefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = sharedPrefrence.getParentUser(Consts.LOGINDTO);
        init();
    }

    private void init() {
        RVitemlistt = findViewById(R.id.RVitemlistt);
        tvNo = findViewById(R.id.tvNo);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkManager.isConnectToInternet(mContext)) {
            notification();
        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }


    public void notification() {
        new HttpsRequest(Consts.GETNOTIFICATTION, getParms(), mContext).stringPost("Notification", new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {

                if (flag) {
                    try {
                        notificationDTOlist = new ArrayList<>();
                        Type listType = new TypeToken<List<NotificationDTO>>() {
                        }.getType();

                        notificationDTOlist = (ArrayList<NotificationDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), listType);
                        showData();
                        tvNo.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    // ProjectUtils.showToast(mContext, msg);
                    ProjectUtils.pauseProgressDialog();
                    tvNo.setVisibility(View.VISIBLE);


                }
            }
        });
    }

    public void showData() {
        mLayoutManager = new LinearLayoutManager(mContext);
        RVitemlistt.setLayoutManager(mLayoutManager);

        NotificationAdapter notificationAdapter = new NotificationAdapter(mContext, notificationDTOlist);
        RVitemlistt.setAdapter(notificationAdapter);


    }

    public Map<String, String> getParms() {

        HashMap<String, String> params = new HashMap<>();
        params.put(Consts.USER_ID, loginDTO.getId());

        Log.e("notification", params.toString());
        return params;
    }
}
