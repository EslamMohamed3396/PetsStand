
package com.proatcoding.pets.models.weAcceptPayment.placeOrder.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePlaceOrder implements Parcelable
{

    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("PaymentURL")
    @Expose
    private String paymentURL;
    @SerializedName("PaymentKey")
    @Expose
    private String paymentKey;
    public final static Creator<ResponsePlaceOrder> CREATOR = new Creator<ResponsePlaceOrder>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponsePlaceOrder createFromParcel(Parcel in) {
            return new ResponsePlaceOrder(in);
        }

        public ResponsePlaceOrder[] newArray(int size) {
            return (new ResponsePlaceOrder[size]);
        }

    }
    ;

    protected ResponsePlaceOrder(Parcel in) {
        this.status = ((int) in.readValue((int.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentURL = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentKey = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ResponsePlaceOrder() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentURL() {
        return paymentURL;
    }

    public void setPaymentURL(String paymentURL) {
        this.paymentURL = paymentURL;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(paymentURL);
        dest.writeValue(paymentKey);
    }

    public int describeContents() {
        return  0;
    }

}
