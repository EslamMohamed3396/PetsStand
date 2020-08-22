package com.proatcoding.pets.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.BaseActivity;
import com.proatcoding.pets.models.PromoCodeDTO;
import com.proatcoding.pets.sharedprefrence.SharedPrefrence;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomTextView;
import com.proatcoding.pets.utils.CustomTextViewBold;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hemant on 13/1/20.
 */

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.MyViewHolder> {

    private ArrayList<PromoCodeDTO> promoCodeList;
    private ArrayList<PromoCodeDTO> newPromoCodeList;
    private SharedPrefrence prefrence;
    public BaseActivity baseActivity;
    private Context context;

    public PromoCodeAdapter(Context context, ArrayList<PromoCodeDTO> promoCodeList) {
        this.promoCodeList = promoCodeList;
        this.context = context;
        prefrence = SharedPrefrence.getInstance(context);
        newPromoCodeList = new ArrayList<>();
        newPromoCodeList.addAll(promoCodeList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_code_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.ctvbPromoValue.setText(promoCodeList.get(position).getTitle());
        holder.ctvPromoDescription.setText(promoCodeList.get(position).getDescription());
        holder.ctvPromoName.setText(promoCodeList.get(position).getPromo_code());

        Glide.with(context)
                .load(promoCodeList.get(position).getImage())
                .placeholder(R.drawable.about_us_back)
                .into(holder.ivType);

        holder.ctvbApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    PromoCodeDTO promoCodeDTO = promoCodeList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra(Consts.DTO, promoCodeDTO);
                    ((Activity)context).setResult(Consts.PROMO_CODE_REQUEST, intent);
                    ((Activity)context).finish();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return promoCodeList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextViewBold ctvbPromoValue, ctvbApply;
        CustomTextView ctvPromoDescription, ctvPromoName;
        ImageView ivType;

        public MyViewHolder(View item) {
            super(item);
            ctvbPromoValue = (CustomTextViewBold) item.findViewById(R.id.ctvbPromoValue);
            ctvPromoDescription = (CustomTextView) item.findViewById(R.id.ctvPromoDescription);
            ctvPromoName = (CustomTextView) item.findViewById(R.id.ctvPromoName);
            ctvbApply = (CustomTextViewBold) item.findViewById(R.id.ctvbApply);
            ivType = (ImageView) item.findViewById(R.id.ivType);

        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        promoCodeList.clear();
        if (charText.length() == 0) {
            promoCodeList.addAll(newPromoCodeList);
        } else {
            for (PromoCodeDTO petCate : newPromoCodeList) {
                if (petCate.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    promoCodeList.add(petCate);
                }
            }
        }
        notifyDataSetChanged();
    }

}