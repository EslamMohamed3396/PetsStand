package com.proatcoding.pets.activity.MyOrder.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.cardview.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.MyOrder.MyOrderActivity;
import com.proatcoding.pets.activity.MyOrder.vieworder.ViewOrderActivity;
import com.proatcoding.pets.activity.PaymentViewActivity;
import com.proatcoding.pets.models.MyOrderDto;
import com.proatcoding.pets.utils.Consts;
import com.proatcoding.pets.utils.CustomTextView;
import com.proatcoding.pets.utils.CustomTextViewBold;
import com.proatcoding.pets.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by PC on 21/2/18.
 */
public class OrderAdapter extends BaseAdapter {
    private MyOrderActivity myOrderActivity;
    private ArrayList<MyOrderDto> myOrderDtoList;
    private ArrayList<MyOrderDto> newmyOrderDtoList;
    private LayoutInflater inflater;

    public OrderAdapter(MyOrderActivity myOrderActivity, ArrayList<MyOrderDto> myOrderDtoList, LayoutInflater inflater) {
        this.myOrderActivity = myOrderActivity;
        this.myOrderDtoList = myOrderDtoList;
        this.inflater = inflater;
        this.newmyOrderDtoList = new ArrayList<MyOrderDto>();
        this.newmyOrderDtoList.addAll(myOrderDtoList);
    }

    @Override
    public int getCount() {
        return myOrderDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return myOrderDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();
        View itemView = inflater.inflate(R.layout.adapter_airtime_history, null, false);

        holder.tvOrderID = itemView.findViewById(R.id.tvOrderID);
        holder.tvDetails = itemView.findViewById(R.id.tvDetails);
        holder.tvDate = itemView.findViewById(R.id.tvDate);
        holder.tvAmount = itemView.findViewById(R.id.tvAmount);
        holder.tvStatus = itemView.findViewById(R.id.tvStatus);
        holder.tvDatePlace = itemView.findViewById(R.id.tvDatePlace);
        holder.cardview = itemView.findViewById(R.id.cardview);
        holder.tvpay = itemView.findViewById(R.id.tvpay);
        holder.tvPaymentModeValue = itemView.findViewById(R.id.tvPaymentModeValue);

     /*   holder.tvOrderID.setText("Order ID : " + myOrderDtoList.get(position).getOrder_id());
        holder.tvDate.setText("Order on : " + myOrderDtoList.get(position).getOrder_date());
        holder.tvDatePlace.setText("Placed on : " + myOrderDtoList.get(position).getPlace_date());
        holder.tvStatus.setText(myOrderDtoList.get(position).getStatus_txt().toUpperCase());
        holder.tvAmount.setText("Amount : " + myOrderActivity.getResources().getString(R.string.Rs) + " " + myOrderDtoList.get(position).getFinal_price()*//* + "/" + myOrderDtoList.get(position).getLine_items().size() + " items"*//*);*/


        holder.tvOrderID.setText(myOrderDtoList.get(position).getOrder_id());
        holder.tvDate.setText(ProjectUtils.parseToddMMMyyyy(myOrderDtoList.get(position).getOrder_date()));

        if (myOrderDtoList.get(position).getStatus().equals("6")) {
            holder.tvStatus.setText(myOrderDtoList.get(position).getStatus_txt().toUpperCase());
            holder.tvStatus.setTextColor(myOrderActivity.getResources().getColor(R.color.red));
            holder.tvDatePlace.setText("Not yet confirmed");
            holder.tvDatePlace.setTextColor(myOrderActivity.getResources().getColor(R.color.red));
        } else if (myOrderDtoList.get(position).getStatus().equals("5")) {
            holder.tvStatus.setText(myOrderDtoList.get(position).getStatus_txt().toUpperCase());
            holder.tvStatus.setTextColor(myOrderActivity.getResources().getColor(R.color.red));
            holder.tvDatePlace.setText("Not yet confirmed");
            holder.tvDatePlace.setTextColor(myOrderActivity.getResources().getColor(R.color.red));
        } else if (myOrderDtoList.get(position).getStatus().equals("1")) {
            holder.tvStatus.setText(myOrderDtoList.get(position).getStatus_txt().toUpperCase());
            holder.tvStatus.setTextColor(myOrderActivity.getResources().getColor(R.color.green2));
            holder.tvDatePlace.setText(ProjectUtils.parseToddMMMyyyy(myOrderDtoList.get(position).getPlace_date()));
            holder.tvDatePlace.setTextColor(myOrderActivity.getResources().getColor(R.color.dark_gray));
        } else if (myOrderDtoList.get(position).getStatus().equals("2")) {
            holder.tvStatus.setText(myOrderDtoList.get(position).getStatus_txt().toUpperCase());
            holder.tvStatus.setTextColor(myOrderActivity.getResources().getColor(R.color.green2));
            holder.tvDatePlace.setText(ProjectUtils.parseToddMMMyyyy(myOrderDtoList.get(position).getPlace_date()));
            holder.tvDatePlace.setTextColor(myOrderActivity.getResources().getColor(R.color.dark_gray));
        } else if (myOrderDtoList.get(position).getStatus().equals("3")) {
            holder.tvStatus.setText(myOrderDtoList.get(position).getStatus_txt().toUpperCase());
            holder.tvStatus.setTextColor(myOrderActivity.getResources().getColor(R.color.green2));
            holder.tvDatePlace.setText(ProjectUtils.parseToddMMMyyyy(myOrderDtoList.get(position).getPlace_date()));
            holder.tvDatePlace.setTextColor(myOrderActivity.getResources().getColor(R.color.dark_gray));
        } else if (myOrderDtoList.get(position).getStatus().equals("4")) {
            holder.tvStatus.setText(myOrderDtoList.get(position).getStatus_txt().toUpperCase());
            holder.tvStatus.setTextColor(myOrderActivity.getResources().getColor(R.color.green2));
            holder.tvDatePlace.setText(ProjectUtils.parseToddMMMyyyy(myOrderDtoList.get(position).getPlace_date()));
            holder.tvDatePlace.setTextColor(myOrderActivity.getResources().getColor(R.color.dark_gray));
        }

        if (myOrderDtoList.get(position).getPayment_status().equals("0")) {
            if(myOrderDtoList.get(position).getPayment_mode().equalsIgnoreCase("1")){
                holder.tvpay.setVisibility(View.GONE);
                holder.tvPaymentModeValue.setText("COD");
            }else{
                holder.tvpay.setVisibility(View.VISIBLE);
                holder.tvPaymentModeValue.setText("Online");
            }
        } else if (myOrderDtoList.get(position).getPayment_status().equals("1")) {
            holder.tvpay.setVisibility(View.GONE);
            holder.tvPaymentModeValue.setText("Online");
        }


        holder.tvAmount.setText(myOrderDtoList.get(position).getCurrency_type() + " " + myOrderDtoList.get(position).getFinal_price());
        holder.cardview.setOnClickListener(v -> {
            Intent in = new Intent(myOrderActivity, ViewOrderActivity.class);
            in.putExtra(Consts.ORDER, myOrderDtoList.get(position));
            in.putExtra(Consts.ORDER_ID, myOrderDtoList.get(position).getOrder_id());
            in.putExtra(Consts.FINAL_PRICE, myOrderDtoList.get(position).getFinal_price());
            in.putExtra(Consts.CONFIRM_STATUS, myOrderDtoList.get(position).getPayment_status());
            in.putExtra(Consts.CURRENCYTYPE, myOrderDtoList.get(position).getCurrency_type());
            myOrderActivity.startActivity(in);
        });

        holder.tvpay.setOnClickListener(v -> myOrderActivity.genrateCheckSum(position));
        return itemView;
    }

    static class ViewHolder {
        public CustomTextView tvOrderID, tvDetails, tvDate, tvAmount, tvStatus, tvDatePlace, tvpay, tvPaymentModeValue;
        public CardView cardview;
    }


    public void initPayment(final int position) {
        final Dialog dialog = new Dialog(myOrderActivity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.paymentdailog);
        final CustomTextViewBold ctvPayPal = dialog.findViewById(R.id.ctvPayPal);
        String payPal = "Pay by <font color='#303F9F'>Pay</font><font color='#00BCD4'>Pal.</font>";
        ctvPayPal.setText(Html.fromHtml(payPal), CustomTextViewBold.BufferType.NORMAL);

        final CustomTextViewBold ctvstripe = dialog.findViewById(R.id.ctvstripe);
        String strip = "Pay by <font color='#00BCD4'>Stripe</font>";
        ctvstripe.setText(Html.fromHtml(strip), CustomTextViewBold.BufferType.NORMAL);

        final LinearLayout llPayPal = dialog.findViewById(R.id.llPayPal);
        final LinearLayout llstripe = dialog.findViewById(R.id.llstripe);
        dialog.show();
        llPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://phpstack-132936-652468.cloudwaysapps.com/Paypal/paypal?user_id=" + myOrderActivity.loginDTO.getId() +
                        "&order_id=" + myOrderDtoList.get(position).getOrder_id() + "&user_name=" + myOrderActivity.loginDTO.getFirst_name() + "&amount=" + myOrderDtoList.get(position).getFinal_price();
                Intent intent = new Intent(myOrderActivity, PaymentViewActivity.class);
                intent.putExtra(Consts.PAYAMENT_URL, url);
                myOrderActivity.startActivity(intent);
                dialog.dismiss();
            }
        });
        llstripe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://phpstack-132936-652468.cloudwaysapps.com/Stripe/BookingPayement/make_payment?user_id=" + myOrderActivity.loginDTO.getId() +
                        "&order_id=" + myOrderDtoList.get(position).getOrder_id() + "&user_name=" + myOrderActivity.loginDTO.getFirst_name() + "&amount=" + myOrderDtoList.get(position).getFinal_price();
                Intent intent = new Intent(myOrderActivity, PaymentViewActivity.class);
                intent.putExtra(Consts.PAYAMENT_URL, url);
                myOrderActivity.startActivity(intent);

                dialog.dismiss();
            }
        });
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myOrderDtoList.clear();
        if (charText.length() == 0) {
            myOrderDtoList.addAll(newmyOrderDtoList);
        } else {
            for (MyOrderDto foodListDTO : newmyOrderDtoList) {
                if (foodListDTO.getOrder_id().toLowerCase(Locale.getDefault()).contains(charText)) {
                    myOrderDtoList.add(foodListDTO);
                }
            }
        }
        notifyDataSetChanged();
    }
}
