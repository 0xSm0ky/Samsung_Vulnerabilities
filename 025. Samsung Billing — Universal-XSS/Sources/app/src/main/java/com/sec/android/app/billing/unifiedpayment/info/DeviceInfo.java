package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceInfo> CREATOR = new Parcelable.Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel parcel) {
            return new DeviceInfo(parcel);
        }

        @Override
        public DeviceInfo[] newArray(int i) {
            return new DeviceInfo[i];
        }
    };

    private String channelID;
    private String csc;
    private String dateFormat;
    private String deviceID;
    private String deviceUID;
    private String displayType;
    private String language;
    private String mcc;
    private String mnc;
    private String msisdn;
    private String osVersion;
    private String shopID;
    private String upc_darkMode;
    private String upc_deviceType;
    private String userAgent;
    private String usimType;

    public DeviceInfo() {
    }

    public DeviceInfo(Parcel parcel) {
        this.upc_deviceType = parcel.readString();
        this.upc_darkMode = parcel.readString();
        this.channelID = parcel.readString();
        this.csc = parcel.readString();
        this.dateFormat = parcel.readString();
        this.deviceID = parcel.readString();
        this.deviceUID = parcel.readString();
        this.displayType = parcel.readString();
        this.language = parcel.readString();
        this.mcc = parcel.readString();
        this.mnc = parcel.readString();
        this.msisdn = parcel.readString();
        this.osVersion = parcel.readString();
        this.shopID = parcel.readString();
        this.userAgent = parcel.readString();
        this.usimType = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getChannelID() {
        return this.channelID;
    }

    public String getCsc() {
        return this.csc;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public String getDeviceID() {
        return this.deviceID;
    }

    public String getDeviceUID() {
        return this.deviceUID;
    }

    public String getDisplayType() {
        return this.displayType;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getMcc() {
        return this.mcc;
    }

    public String getMnc() {
        return this.mnc;
    }

    public String getMsisdn() {
        return this.msisdn;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public String getShopID() {
        return this.shopID;
    }

    public String getUpc_darkMode() {
        return this.upc_darkMode;
    }

    public String getUpc_deviceType() {
        return this.upc_deviceType;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String getUsimType() {
        return this.usimType;
    }

    public void setChannelID(String str) {
        this.channelID = str;
    }

    public void setCsc(String str) {
        this.csc = str;
    }

    public void setDateFormat(String str) {
        this.dateFormat = str;
    }

    public void setDeviceID(String str) {
        this.deviceID = str;
    }

    public void setDeviceUID(String str) {
        this.deviceUID = str;
    }

    public void setDisplayType(String str) {
        this.displayType = str;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setMcc(String str) {
        this.mcc = str;
    }

    public void setMnc(String str) {
        this.mnc = str;
    }

    public void setMsisdn(String str) {
        this.msisdn = str;
    }

    public void setOsVersion(String str) {
        this.osVersion = str;
    }

    public void setShopID(String str) {
        this.shopID = str;
    }

    public void setUpc_darkMode(String str) {
        this.upc_darkMode = str;
    }

    public void setUpc_deviceType(String str) {
        this.upc_deviceType = str;
    }

    public void setUserAgent(String str) {
        this.userAgent = str;
    }

    public void setUsimType(String str) {
        this.usimType = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.upc_deviceType);
        parcel.writeString(this.upc_darkMode);
        parcel.writeString(this.channelID);
        parcel.writeString(this.csc);
        parcel.writeString(this.dateFormat);
        parcel.writeString(this.deviceID);
        parcel.writeString(this.deviceUID);
        parcel.writeString(this.displayType);
        parcel.writeString(this.language);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeString(this.msisdn);
        parcel.writeString(this.osVersion);
        parcel.writeString(this.shopID);
        parcel.writeString(this.userAgent);
        parcel.writeString(this.usimType);
    }
}