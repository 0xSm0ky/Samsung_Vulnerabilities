package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class UnifiedPaymentServerInfo implements Parcelable {
    public static final Parcelable.Creator<UnifiedPaymentServerInfo> CREATOR = new Parcelable.Creator<UnifiedPaymentServerInfo>() {
        @Override
        public UnifiedPaymentServerInfo createFromParcel(Parcel parcel) {
            return new UnifiedPaymentServerInfo(parcel);
        }

        @Override
        public UnifiedPaymentServerInfo[] newArray(int i) {
            return new UnifiedPaymentServerInfo[i];
        }
    };

    private String upServerURL;

    public UnifiedPaymentServerInfo() {
    }

    public UnifiedPaymentServerInfo(Parcel parcel) {
        this.upServerURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUpServerURL() {
        return this.upServerURL;
    }

    public void setUpServerURL(String str) {
        this.upServerURL = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.upServerURL);
    }
}