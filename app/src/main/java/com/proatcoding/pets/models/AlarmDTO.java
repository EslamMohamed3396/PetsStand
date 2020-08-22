package com.proatcoding.pets.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AlarmDTO implements Parcelable {

    private long id;
    private long datetime;
    private String label="";
    private boolean isEnabled;
    private String day="";
    private static final long NO_ID = -1;

    public AlarmDTO() {
        this(NO_ID);
    }

    public AlarmDTO(long id){
        this(id, System.currentTimeMillis());
    }

    public AlarmDTO(long id, long datetime){
        this(id, datetime, null);
    }

    public AlarmDTO(long id, long datetime, String label) {
        this(id, datetime, label, null);
    }

    public AlarmDTO(long id, long datetime, String label, String day) {
        this.id = id;
        this.datetime = datetime;
        this.label = label;
        this.day = day;
    }

    private AlarmDTO(Parcel in) {
        id = in.readLong();
        datetime = in.readLong();
        label = in.readString();
        isEnabled = in.readByte() != 0;
    }

    public int notificationId() {
        final long id = getId();
        return (int) (id^(id>>>32));
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", label='" + label + '\'' +
                ", isEnabled=" + isEnabled +
                ", day=" + day +
                '}';
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (id^(id>>>32));
        result = 31 * result + (int) (datetime^(datetime>>>32));
        result = 31 * result + label.hashCode();
        result = 31 * result + day.hashCode();
        return result;
    }



    public static final Creator<AlarmDTO> CREATOR = new Creator<AlarmDTO>() {
        @Override
        public AlarmDTO createFromParcel(Parcel in) {
            return new AlarmDTO(in);
        }

        @Override
        public AlarmDTO[] newArray(int size) {
            return new AlarmDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(datetime);
        parcel.writeString(label);
        parcel.writeString(day);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
    }

    public long getId() {
        return id;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
