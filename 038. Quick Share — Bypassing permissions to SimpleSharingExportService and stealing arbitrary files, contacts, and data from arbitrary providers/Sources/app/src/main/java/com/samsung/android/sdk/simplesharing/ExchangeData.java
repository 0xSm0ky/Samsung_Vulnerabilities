package com.samsung.android.sdk.simplesharing;

import android.os.Parcel;
import android.os.Parcelable;

public class ExchangeData implements Parcelable {
    public static final Parcelable.Creator<ExchangeData> CREATOR = new Parcelable.Creator<ExchangeData>() {
        @Override
        public ExchangeData createFromParcel(Parcel parcel) {
            return new ExchangeData(parcel);
        }

        @Override
        public ExchangeData[] newArray(int i) {
            return new ExchangeData[i];
        }
    };

    private long aidlRevision;
    private long exchangeDataRevision;
    private String packageName;
    private long versionCode;

    @Override
    public int describeContents() {
        return 0;
    }

    public ExchangeData(long exchangeDataRevision, long aidlRevision, String packageName, long versionCode) {
        this.exchangeDataRevision = exchangeDataRevision;
        this.aidlRevision = aidlRevision;
        this.packageName = packageName;
        this.versionCode = versionCode;
    }

    public ExchangeData(Parcel parcel) {
        this.exchangeDataRevision = parcel.readLong();
        this.aidlRevision = parcel.readLong();
        this.packageName = parcel.readString();
        this.versionCode = parcel.readLong();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.exchangeDataRevision);
        parcel.writeLong(this.aidlRevision);
        parcel.writeString(this.packageName);
        parcel.writeLong(this.versionCode);
    }
}