package com.proatcoding.pets.fragment.duties;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.proatcoding.pets.R;
import com.proatcoding.pets.SysApplication;
import com.proatcoding.pets.activity.BaseActivity;
import com.proatcoding.pets.activity.duties.AddReminder;
import com.proatcoding.pets.activity.duties.ConfirmReminder;
import com.proatcoding.pets.activity.duties.HistoryReminder;
import com.proatcoding.pets.adapter.DutiesAdapter;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.Reminder;
import com.proatcoding.pets.network.NetworkManager;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomTextView;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by welcome pc on 22-10-2016.
 */
public class DutiesFragment extends Fragment implements View.OnClickListener {
    public BaseActivity baseActivity;
    private ListView duties;
    private DutiesAdapter adapter;
    private List<Reminder> listItems = new ArrayList<>();
    public LoginDTO loginDTO;
    public SysApplication sysApplication;
    private CustomTextView show_history;
    private SharedPrefrence share;
    private ImageView addDuties;
    View rootView;
    private String TAG = DutiesFragment.class.getSimpleName();
    private CustomTextViewBold tvNotFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_duties, container, false);
        sysApplication = SysApplication.getInstance();
        share = SharedPrefrence.getInstance(getActivity());
        loginDTO = share.getParentUser(Consts.LOGINDTO);
        init(rootView);
        addDuties.setOnClickListener(this);

        return rootView;
    }

    public void init(View view) {
        duties = (ListView) view.findViewById(R.id.duties);
        addDuties = (ImageView) view.findViewById(R.id.addDuties);
        show_history = (CustomTextView) view.findViewById(R.id.show_history);
        tvNotFound = (CustomTextViewBold) view.findViewById(R.id.tvNotFound);
        show_history.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkManager.isConnectToInternet(getActivity())) {
            getReminders();
        } else {
            ProjectUtils.showLong(getActivity(), getResources().getString(R.string.internet_concation));
        }
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.click_event));
        switch (v.getId()) {
            case R.id.addDuties: {
                startActivity(new Intent(getActivity(), AddReminder.class));
                break;
            }
            case R.id.show_history: {
                clickOnViewHistory();
                break;
            }
        }
    }

    public void clickOnViewHistory() {
        Intent intent = new Intent(getActivity(), HistoryReminder.class);
        startActivity(intent);
    }


    public void getReminders() {
        ProjectUtils.showProgressDialog(getActivity(), true, "Please Wait!!");
        new HttpsRequest(Consts.GET_REMINDERS, getparm(), getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    duties.setVisibility(View.VISIBLE);
                    tvNotFound.setVisibility(View.GONE);
                    handleResponse(response);

                } else {
                    ProjectUtils.showLong(getActivity(), msg);
                    duties.setVisibility(View.GONE);
                    tvNotFound.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.USER_ID, loginDTO.getId());
        Log.e("parms", parms.toString());
        return parms;
    }

    public void handleResponse(JSONObject response) {
        try {
            Reminder[] list = new Gson().fromJson(response.getJSONArray("data").toString(), Reminder[].class);
            listItems = new LinkedList<>(Arrays.asList(list));
            setListInAscendingOrder();
            adapter = new DutiesAdapter(this, R.layout.duties_item, listItems);
            duties.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setListInAscendingOrder() {
        Collections.sort(listItems, new Comparator<Reminder>() {
            public int compare(Reminder obj1, Reminder obj2) {
                return Long.valueOf(obj1.getAppointment_timestamp()).compareTo(Long.valueOf(obj2.getAppointment_timestamp())); // To compare integer values
            }
        });
    }

    public void clickOnItems(int position) {
        Intent intent = new Intent(getActivity(), ConfirmReminder.class);
        intent.putExtra("confirmObj", listItems.get(position));
        startActivity(intent);
    }
}
