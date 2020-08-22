package com.proatcoding.pets.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.proatcoding.pets.BuildConfig;
import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.UserProfile.UserProfileActivity;
import com.proatcoding.pets.fragment.AddPet.PetList;
import com.proatcoding.pets.fragment.Home.HomeFragment;
import com.proatcoding.pets.fragment.MoreFragment;
import com.proatcoding.pets.fragment.NearBy.NearByFrag;
import com.proatcoding.pets.fragment.duties.DutiesFragment;
import com.proatcoding.pets.fragment.foodDelivery.Catagory;
import com.proatcoding.pets.fragment.wall.WallFragment;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.AppVersion;
import com.proatcoding.pets.models.DeviceInfo;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.User;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.BuilderManager;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.GPSTracker;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONObject;

import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getCanonicalName();
    public BoomMenuButton bmb1;
    private Context mContext;
    SharedPrefrence preference;
    public User user;
    public Catagory catagory = new Catagory();
    public MoreFragment moreFragment = new MoreFragment();
    public NearByFrag nearByFrag = new NearByFrag();
    public DutiesFragment dutiesFragment = new DutiesFragment();
    public PetList petList = new PetList();
    public HomeFragment homeFragment = new HomeFragment();
    public WallFragment wallFragment = new WallFragment();
    public FragmentManager fm;
    private DeviceInfo deviceInfo;
    public Fragment deviceList;
    public static String TAG_HOME = "home";
    public static String TAG_WALL = "wall";
    public static String TAG_NEAR_BY = "near_by";
    public static String TAG_REMINDER = "reminder";
    public static String TAG_FOOD = "food";
    public static String TAG_MORE = "more";
    public GPSTracker gps;
    double latitude, longitude;
    int ind = 0;
    LoginDTO loginDTO;

    int versionCode;
    String versionName = "";
    AppVersion appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(BaseActivity.this);
        setContentView(R.layout.activity_base);
        preference = SharedPrefrence.getInstance(this);
        loginDTO = preference.getParentUser(Consts.LOGINDTO);
        mContext = BaseActivity.this;
        fm = getSupportFragmentManager();
        gps = new GPSTracker(mContext);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Log.e("Loction", "Lat" + latitude + "long" + longitude);
        } else {

            gps.showSettingsAlert();
        }

        versionCode = BuildConfig.VERSION_CODE;
        versionName = BuildConfig.VERSION_NAME;

        Fragment fragment = homeFragment;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, TAG_HOME);
        fragmentTransaction.commitAllowingStateLoss();

        bmb1 = (BoomMenuButton) findViewById(R.id.bmb1);
        bmb1.setButtonEnum(ButtonEnum.TextInsideCircle);
        bmb1.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_1);
        bmb1.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_1);
        for (int i = 0; i < bmb1.getPiecePlaceEnum().pieceNumber(); i++) {
            bmb1.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor());

        }

        bmb1.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                super.onClicked(index, boomButton);
                ind = index;
                changeBoomButton();
            }
        });

        //when user Login as a guest
        if (loginDTO.getEmail().contains(Consts.GUEST_EMAIL)) {
            bmb1.setVisibility(View.GONE);
        } else {
            bmb1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loginDTO.getEmail().contains(Consts.GUEST_EMAIL)) {
            if (!preference.getStringValue(Consts.PROFILE_STATUS).equalsIgnoreCase("")) {

            } else {
                Intent intent = new Intent(mContext, UserProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("FlagLogin", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }

        }

        try {
            checkCurrentVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeBoomButton() {
        if (ind == 0) {
            Fragment fragment = homeFragment;
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, TAG_HOME);
            fragmentTransaction.commitAllowingStateLoss();
        } else if (ind == 1) {
            Fragment fragment = catagory;
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, TAG_FOOD);
            fragmentTransaction.commitAllowingStateLoss();
        } else if (ind == 2) {
            Fragment fragment = nearByFrag;
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, TAG_NEAR_BY);
            fragmentTransaction.commitAllowingStateLoss();
        } else if (ind == 3) {
            Fragment fragment = dutiesFragment;
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, TAG_REMINDER);
            fragmentTransaction.commitAllowingStateLoss();
        } else if (ind == 4) {
            Fragment fragment = wallFragment;
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, TAG_WALL);
            fragmentTransaction.commitAllowingStateLoss();

        } else if (ind == 5) {
            Fragment fragment = moreFragment;
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, TAG_MORE);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        if (ind == 0) {
            clickDone();
        } else {
            Fragment fragment = homeFragment;
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, TAG_HOME);
            fragmentTransaction.commitAllowingStateLoss();
            ind = 0;
        }
    }

    public void clickDone() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.walk_icon)
                .setTitle(R.string.app_name)
                .setMessage("Are you sure want to close PetStand?")
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

    public void checkCurrentVersion() {

        new HttpsRequest(Consts.GET_CURRENT_VERSION, getParamsVersion(), this).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {

                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    try {
                        appVersion = new AppVersion();
                        appVersion = new Gson().fromJson(response.getJSONObject("data").toString(), AppVersion.class);
                        if (!appVersion.getVersion_name().equalsIgnoreCase(String.valueOf(versionName))) {
                            versionAlert();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    protected HashMap<String, String> getParamsVersion() {
        HashMap<String, String> paramsversion = new HashMap<>();
        paramsversion.put(Consts.DEVICE_TYPE, Consts.DEVICE_TYPE_VALUE);

        return paramsversion;
    }

    public void versionAlert() {
        new android.app.AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.version_msg))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//Copy App URL from Google Play Store.
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                        startActivity(intent);

                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        closeApp();
                    }
                })
                .show();
    }

    public void closeApp() {
        Intent i = new Intent();
        i.putExtra("type", getString(R.string.app_name));
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
