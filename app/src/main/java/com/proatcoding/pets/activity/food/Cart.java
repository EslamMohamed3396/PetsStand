package com.proatcoding.pets.activity.food;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.BaseActivity;
import com.proatcoding.pets.activity.register.LoginSignupactivity;
import com.proatcoding.pets.adapter.CartAdapter;
import com.proatcoding.pets.https.HttpsRequest;
import com.proatcoding.pets.interfaces.Helper;
import com.proatcoding.pets.models.CartDTO;
import com.proatcoding.pets.models.CheckPromoCodeDTO;
import com.proatcoding.pets.models.LoginDTO;
import com.proatcoding.pets.models.MakeOrderDTO;
import com.proatcoding.pets.models.PromoCodeDTO;
import com.proatcoding.pets.models.SendShopifyProductDTO;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomButton;
import com.proatcoding.pets.utils.CustomTextView;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mayank on 17/2/18.
 */

public class Cart extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back;
    ListView lvCart;
    private CustomTextView ctvNumber;
    private Context mContext;
    private static final String TAG = "CartCart";
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private ArrayList<CartDTO> cartDTOList;
    ArrayList<SendShopifyProductDTO> list = new ArrayList<>();
    private ArrayList<SendShopifyProductDTO> sendShopifyProductDTOList;
    private CartAdapter cartAdapter;
    private CustomButton btNext, btcontinueshop;
    private CustomTextViewBold tvgrandtotel, tvShippingtotel, tvMainAmount;
    private LinearLayout llNoFound;
    private MakeOrderDTO makeOrderDTO;
    HashMap<String, String> parmsOrder = new HashMap<>();
    private CustomTextView tvApply, tvRemove, tvPromoDetails, tvPromoCode;
    private RelativeLayout rlApplyPromo, rlPromoDetails;
    PromoCodeDTO promoCodeDTO;
    CheckPromoCodeDTO checkPromoCodeDTO = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.setStatusBarGradiant(Cart.this);
        setContentView(R.layout.cart_frag);
        mContext = Cart.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getParentUser(Consts.LOGINDTO);

        parmsOrder.put(Consts.USER_ID, loginDTO.getId());
        parmsOrder.put(Consts.ADDRESS, loginDTO.getAddress());
        init();
    }

    public void init() {
        llNoFound = findViewById(R.id.llNoFound);
        ctvNumber = findViewById(R.id.ctvNumber);
        lvCart = findViewById(R.id.lvCart);
        back = findViewById(R.id.back);
        tvShippingtotel = findViewById(R.id.tvShippingtotel);
        back.setOnClickListener(this);

        rlApplyPromo = findViewById(R.id.rlApplyPromo);
        rlPromoDetails = findViewById(R.id.rl_Promo_details);

        tvApply = findViewById(R.id.tvApply);
        tvApply.setOnClickListener(this);

        tvRemove = findViewById(R.id.tvRemove);
        tvRemove.setOnClickListener(this);

        tvPromoDetails = findViewById(R.id.tvPromoDetails);
        tvPromoCode = findViewById(R.id.tvPromoCode);

        tvMainAmount = findViewById(R.id.tvMainAmount);
        tvgrandtotel = findViewById(R.id.tvgrandtotel);
        btNext = findViewById(R.id.btNext);
        btcontinueshop = findViewById(R.id.btcontinueshop);
        btNext.setOnClickListener(this);
        btcontinueshop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.click_event));
        switch (view.getId()) {
            case R.id.btNext:
                if (loginDTO.getAddress().equals("")) {
                    ProjectUtils.showLong(mContext, "Please fill your address");
                } else {
                    cartnext();
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.btcontinueshop:
                Intent intent = new Intent(Cart.this, BaseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.tvApply:
                Intent intent1 = new Intent(Cart.this, PromoCodeActivity.class);
                intent1.putExtra(Consts.PRICE, tvgrandtotel.getText().toString());
                startActivityForResult(intent1, Consts.PROMO_CODE_REQUEST);
                break;
            case R.id.tvRemove:
                rlApplyPromo.setVisibility(View.VISIBLE);
                rlPromoDetails.setVisibility(View.GONE);

                tvgrandtotel.setPaintFlags(0);
                tvMainAmount.setText("");
                break;
        }
    }

    private void cartnext() {
        try {
            if (cartDTOList.size() > 0) {
                ProjectUtils.showAlertDialogWithCancel(mContext, "Confirm", "Look like you have confirmed your order. Customer team will contact you. Thank you", "Confirm", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    //makeOrder();
                    Intent inn = new Intent(mContext, AddressActivity.class);

                    if (rlPromoDetails.getVisibility() == View.VISIBLE) {
                        inn.putExtra(Consts.PAYMENT_STATUS, tvMainAmount.getText().toString().trim().substring(2));
                    } else {
                        inn.putExtra(Consts.PAYMENT_STATUS, tvgrandtotel.getText().toString().trim().substring(2));
                    }

                    if (!tvShippingtotel.getText().toString().trim().equals("Free")) {
                        inn.putExtra(Consts.SHIPPING_COST, tvShippingtotel.getText().toString().trim().substring(2));
                    } else {
                        inn.putExtra(Consts.SHIPPING_COST, "0");
                    }
                    inn.putExtra(Consts.DTO,  checkPromoCodeDTO);
                    inn.putExtra(Consts.CART,  cartDTOList);
                    startActivity(inn);
                }, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            } else {
                ProjectUtils.showLong(mContext, "No items in cart.");
            }
        } catch (Exception e) {
            ProjectUtils.showLong(mContext, "No items in cart.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            promoCodeDTO = new PromoCodeDTO();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginDTO.getEmail().contains(Consts.GUEST_EMAIL)) {
            clickDone();
        } else {
            getMyCart();
        }
    }

    public void getMyCart() {
        ProjectUtils.showProgressDialog(mContext, true, "Please Wait!!");
        new HttpsRequest(Consts.GET_MY_CART, getparms(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    Type listType = new TypeToken<List<CartDTO>>() {
                    }.getType();
                    try {
                        cartDTOList = new ArrayList<>();
                        cartDTOList = new Gson().fromJson(response.getJSONArray("data").toString(), listType);
                        Log.v(TAG, "CART CART : " + response.toString());
                        showcartlistdata();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    // ProjectUtils.showLong(mContext, msg);
                    llNoFound.setVisibility(View.VISIBLE);
                    lvCart.setVisibility(View.GONE);
                    tvShippingtotel.setText(" " + 0);
                    tvgrandtotel.setText(" " + 0);
                    ctvNumber.setText(" " + 0);

                    rlApplyPromo.setVisibility(View.GONE);
                    rlPromoDetails.setVisibility(View.GONE);
                    tvgrandtotel.setPaintFlags(0);
                    tvMainAmount.setText("");
                }
            }
        });

    }

    public HashMap<String, String> getparms() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.USER_ID, loginDTO.getId());
        Log.e("parms", parms.toString());
        return parms;
    }


    public void showcartlistdata() {

        float valuesum = 0f;
        for (int i = 0; i < cartDTOList.size(); i++) {
            valuesum = valuesum + (cartDTOList.get(i).getPrice_dicount() * (Float.parseFloat(String.valueOf(cartDTOList.get(i).getQuantity()))));
        }


        if (valuesum > 500f) {
            Float shipping = 0f;

            for (int i = 0; i < cartDTOList.size(); i++) {
                if (cartDTOList.get(i).getMandatory().equals("1")) {
                    if (Float.parseFloat(cartDTOList.get(i).getShipping_cost()) > shipping) {
                        shipping = Float.parseFloat(cartDTOList.get(i).getShipping_cost());
                    }
                }
            }
            if (shipping == 0f) {
                tvShippingtotel.setText("Free");
            } else {
                tvShippingtotel.setText(cartDTOList.get(0).getCurrency_type() + " " + shipping + "");
            }

        } else {
            Float shipping = 0f;
            for (int i = 0; i < cartDTOList.size(); i++) {
                if (Float.parseFloat(cartDTOList.get(i).getShipping_cost()) > shipping) {
                    shipping = Float.parseFloat(cartDTOList.get(i).getShipping_cost());
                }
            }

            if (shipping == 0f) {
                tvShippingtotel.setText("Free");
            } else {
                tvShippingtotel.setText(cartDTOList.get(0).getCurrency_type() + " " + shipping + "");
            }
        }

        tvgrandtotel.setText(cartDTOList.get(0).getCurrency_type() + " " + ProjectUtils.round(valuesum, 2) + "");
        //   tvShippingtotel.setText(cartDTOList.get(0).getCurrency_type() + " " + ProjectUtils.round(valueshipping, 1) + "");

        if (cartDTOList.size() > 0) {
            llNoFound.setVisibility(View.GONE);
            lvCart.setVisibility(View.VISIBLE);
            cartAdapter = new CartAdapter(Cart.this, cartDTOList);
            lvCart.setAdapter(cartAdapter);
            ctvNumber.setText(String.valueOf(cartDTOList.size()));

            if (checkPromoCodeDTO != null) {
//                tvgrandtotel.setPaintFlags(tvgrandtotel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                tvMainAmount.setText(cartDTOList.get(0).getCurrency_type()+" "+checkPromoCodeDTO.getFinal_price());
                checkPromoCode();
            } else {
                tvgrandtotel.setPaintFlags(0);
                tvMainAmount.setText("");
            }
        } else {
            rlApplyPromo.setVisibility(View.GONE);

            tvgrandtotel.setPaintFlags(0);
            tvMainAmount.setText("");

            llNoFound.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.GONE);
            tvgrandtotel.setText(" " + 0);
            ctvNumber.setText(" " + 0);
        }

    }


    public void cardpricemethodPlus(float pr) {

        String strvalue = tvgrandtotel.getText().toString().trim().substring(2);
        float val = Float.parseFloat(strvalue);
        tvgrandtotel.setText(cartDTOList.get(0).getCurrency_type() + " " + ProjectUtils.round((val + pr), 2) + "");
    }

    public void cardpricemethodMinus(float pr) {
        String strvalue = tvgrandtotel.getText().toString().trim().substring(2);
        float val = Float.parseFloat(strvalue);
        tvgrandtotel.setText(cartDTOList.get(0).getCurrency_type() + " " + ProjectUtils.round((val - pr), 2) + "");

    }


    public void makeOrder() {
        ProjectUtils.showProgressDialog(mContext, true, "Please Wait!!");
        new HttpsRequest(Consts.ORDER, parmsOrder, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        makeOrderDTO = new Gson().fromJson(response.getJSONObject("data").toString(), MakeOrderDTO.class);

                        Intent intent = new Intent(Cart.this, ViewOrderDetails.class);
                        intent.putExtra(Consts.MAKE_ORDER, makeOrderDTO);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    ProjectUtils.showLong(mContext, msg);
                }
            }
        });

    }

    public void clickDone() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.walk_icon)
                .setTitle(R.string.app_name)
                .setCancelable(false)
                .setMessage(R.string.guest_login_alert)
                .setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }


    public void logout() {
        prefrence.clearPreferences(SharedPrefrence.IS_LOGIN);
        Intent intent = new Intent(Cart.this, LoginSignupactivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "requestCode:  " + requestCode + "  :resultCode:  " + resultCode);

        if (requestCode == Consts.PROMO_CODE_REQUEST && resultCode == Consts.PROMO_CODE_REQUEST) {
            Log.e("TAG", "onActivityResult: " + Consts.PROMO_CODE_REQUEST);
            if (data != null) {
                promoCodeDTO = (PromoCodeDTO) data.getSerializableExtra(Consts.DTO);
                if (!promoCodeDTO.getPromo_code().equalsIgnoreCase("")) {
                    rlApplyPromo.setVisibility(View.GONE);
                    rlPromoDetails.setVisibility(View.VISIBLE);

                    tvPromoCode.setText(promoCodeDTO.getPromo_code() + " - Applied");
                    tvPromoDetails.setText(promoCodeDTO.getDescription());
                } else {
                    rlApplyPromo.setVisibility(View.VISIBLE);
                    rlPromoDetails.setVisibility(View.GONE);

                    tvgrandtotel.setPaintFlags(0);
                    tvMainAmount.setText("");
                }
                try {
                    checkPromoCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                rlApplyPromo.setVisibility(View.VISIBLE);
                rlPromoDetails.setVisibility(View.GONE);
            }
        } else {

        }
    }

    public void checkPromoCode() {
        new HttpsRequest(Consts.CHECK_PROMO_CODE, getParmsCheckPromoCode(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        if (msg.contains("Promo Code is invalid")) {
                            tvgrandtotel.setPaintFlags(0);
                            tvMainAmount.setText("");
                        } else {
                            checkPromoCodeDTO = new Gson().fromJson(response.getJSONObject("data").toString(), CheckPromoCodeDTO.class);
                            Log.d(TAG, "backResponse: " + response.toString());

                            if (checkPromoCodeDTO != null) {
                                tvgrandtotel.setPaintFlags(tvgrandtotel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                tvMainAmount.setText(cartDTOList.get(0).getCurrency_type() + " " + checkPromoCodeDTO.getFinal_price());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //   ProjectUtils.showLong(mContext, msg);
                }
            }
        });
    }

    public HashMap<String, String> getParmsCheckPromoCode() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.PROMO_CODE, promoCodeDTO.getPromo_code());
        parms.put(Consts.FINAL_PRICE, tvgrandtotel.getText().toString().split(" ")[1]);
        parms.put(Consts.USER_ID, loginDTO.getId());

        Log.e("parms", parms.toString());
        return parms;
    }

}