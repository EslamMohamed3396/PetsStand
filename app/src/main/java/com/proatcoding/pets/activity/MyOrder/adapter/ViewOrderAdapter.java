package com.proatcoding.pets.activity.MyOrder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.MyOrder.vieworder.ViewOrderActivity;
import com.proatcoding.pets.models.MyOrderDetails;
import com.proatcoding.pets.utils.CustomTextView;

import java.util.ArrayList;


/**
 * Created by varun on 21/2/18.
 */

public class ViewOrderAdapter extends BaseAdapter {
    private ViewOrderActivity viewOrderActivity;
    private ArrayList<MyOrderDetails> myOrderDtoItemList;
    private LayoutInflater inflater;

    public ViewOrderAdapter(ViewOrderActivity viewOrderActivity, ArrayList<MyOrderDetails> myOrderDtoItemList, LayoutInflater inflater) {
        this.viewOrderActivity = viewOrderActivity;
        this.myOrderDtoItemList = myOrderDtoItemList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return myOrderDtoItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return myOrderDtoItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();
        View itemView = inflater.inflate(R.layout.adapter_order_view, null, false);

        holder.tvProductName = itemView.findViewById(R.id.tvProductName);
        holder.tvTotal = itemView.findViewById(R.id.tvTotal);
        holder.tvQuntity = itemView.findViewById(R.id.tvQuntity);
        holder.tvPrice = itemView.findViewById(R.id.tvPrice);


        holder.tvProductName.setText(myOrderDtoItemList.get(position).getP_name());
        holder.tvTotal.setText(myOrderDtoItemList.get(position).getCurrency_type() + " " + myOrderDtoItemList.get(position).getTotal_price());
        holder.tvPrice.setText(myOrderDtoItemList.get(position).getCurrency_type() + " " + myOrderDtoItemList.get(position).getPrice_discount());
        holder.tvQuntity.setText(" x " + myOrderDtoItemList.get(position).getQuantity());
        return itemView;
    }

    static class ViewHolder {
        public CustomTextView tvProductName, tvTotal, tvQuntity, tvPrice;
    }

}
