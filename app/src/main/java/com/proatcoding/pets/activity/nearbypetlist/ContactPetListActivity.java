package com.proatcoding.pets.activity.nearbypetlist;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proatcoding.pets.R;
import com.proatcoding.pets.adapter.ContactPetListAdapter;
import com.proatcoding.pets.fragment.NearBy.PetListFrag;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.PetListDTO;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomEditText;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactPetListActivity extends AppCompatActivity {


    private View view;
    private String TAG = PetListFrag.class.getSimpleName();
    private ArrayList<PetListDTO> petListDTOList;
    private RecyclerView.LayoutManager mLayoutManager;
    private ContactPetListAdapter contactPetListAdapter;
    private RecyclerView rvFriendList;
    private HashMap<String, String> paramsPet = new HashMap<>();
    private SharedPrefrence share;
    private LoginDTO loginDTO;
    private LayoutInflater myInflater;
    private CustomEditText etSearch;
    Context mContext;
    CustomTextViewBold tvNo;
    LinearLayout llBackAboutUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(ContactPetListActivity.this);
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contact_pet_list);
        mContext = ContactPetListActivity.this;
        share = SharedPrefrence.getInstance(mContext);
        loginDTO = share.getParentUser(Consts.LOGINDTO);
        paramsPet.put(Consts.USER_ID, loginDTO.getId());

        rvFriendList = findViewById(R.id.rvFriendList);
        tvNo = findViewById(R.id.tvNo);
        llBackAboutUs = findViewById(R.id.llBackAboutUs);

        etSearch = findViewById(R.id.etSearch);

        llBackAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                    contactPetListAdapter.filter(s.toString());

                } catch (Exception e) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getNearByPet();
    }

    public void getNearByPet() {
        ProjectUtils.showProgressDialog(mContext, true, "Please wait....");
        new HttpsRequest(Consts.GET_MY_REQUEST, paramsPet, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    // ProjectUtils.showLong(mContext, msg);
                    Type listType = new TypeToken<List<PetListDTO>>() {
                    }.getType();
                    try {
                        petListDTOList = new ArrayList<>();
                        petListDTOList = (ArrayList<PetListDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), listType);

                        contactPetListAdapter = new ContactPetListAdapter(mContext, petListDTOList, myInflater);

                        mLayoutManager = new LinearLayoutManager(mContext);
                        rvFriendList.setLayoutManager(mLayoutManager);
                        rvFriendList.setItemAnimator(new DefaultItemAnimator());
                        rvFriendList.setAdapter(contactPetListAdapter);
                        tvNo.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvNo.setVisibility(View.VISIBLE);
                    }

                } else {
                    ProjectUtils.showLong(mContext, msg);
                    tvNo.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}