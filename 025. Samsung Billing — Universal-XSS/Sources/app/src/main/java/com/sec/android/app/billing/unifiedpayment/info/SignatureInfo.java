package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class SignatureInfo implements Parcelable {
    public static final Parcelable.Creator<SignatureInfo> CREATOR = new Parcelable.Creator<SignatureInfo>() {
        @Override
        public SignatureInfo createFromParcel(Parcel parcel) {
            return new SignatureInfo(parcel);
        }

        @Override
        public SignatureInfo[] newArray(int i) {
            return new SignatureInfo[i];
        }
    };

    private String baseString;
    private String signature;
    private String timeStamp;

    public SignatureInfo() {
    }

    public SignatureInfo(Parcel parcel) {
        this.baseString = parcel.readString();
        this.signature = parcel.readString();
        this.timeStamp = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBaseString() {
        return this.baseString;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setBaseString(String str) {
        this.baseString = str;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public void setTimeStamp(String str) {
        this.timeStamp = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.baseString);
        parcel.writeString(this.signature);
        parcel.writeString(this.timeStamp);
    }
}