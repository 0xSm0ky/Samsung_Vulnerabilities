package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class SandBoxInfo implements Parcelable {
    public static final Parcelable.Creator<SandBoxInfo> CREATOR = new Parcelable.Creator<SandBoxInfo>() {
        @Override
        public SandBoxInfo createFromParcel(Parcel parcel) {
            return new SandBoxInfo(parcel);
        }

        @Override
        public SandBoxInfo[] newArray(int i) {
            return new SandBoxInfo[i];
        }
    };

    private String testMode;
    private String testUserAuthKey;

    public SandBoxInfo() {
    }

    public SandBoxInfo(Parcel parcel) {
        this.testMode = parcel.readString();
        this.testUserAuthKey = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTestMode() {
        return this.testMode;
    }

    public String getTestUserAuthKey() {
        return this.testUserAuthKey;
    }

    public void setTestMode(String str) {
        this.testMode = str;
    }

    public void setTestUserAuthKey(String str) {
        this.testUserAuthKey = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.testMode);
        parcel.writeString(this.testUserAuthKey);
    }
}