package com.sec.android.app.dexonpc.discovery;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceData implements Parcelable {
    public static final Parcelable.Creator<DeviceData> CREATOR = new Parcelable.Creator<DeviceData>() {
        @Override
        public DeviceData createFromParcel(Parcel parcel) {
            return new DeviceData(parcel);
        }

        @Override
        public DeviceData[] newArray(int i) {
            return new DeviceData[i];
        }
    };

    private String id;
    private String ip;
    private String name;
    private int state;

    @Override
    public int describeContents() {
        return 0;
    }

    public DeviceData(String id, String name, String ip, int state) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.state = state;
    }

    public DeviceData(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.ip = parcel.readString();
        this.state = parcel.readInt();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.ip);
        parcel.writeInt(this.state);
    }

    public String toString() {
        return "DeviceData{id='" + this.id + "', ip='" + this.ip + "', name='" + this.name + "', state=" + this.state + '}';
    }
}