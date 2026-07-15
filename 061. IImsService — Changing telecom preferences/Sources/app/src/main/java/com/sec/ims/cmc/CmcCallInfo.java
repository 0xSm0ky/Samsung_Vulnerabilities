package com.sec.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;

public class CmcCallInfo implements Parcelable {
    public static final Parcelable.Creator<CmcCallInfo> CREATOR = new Parcelable.Creator<CmcCallInfo>() {
        @Override
        public CmcCallInfo createFromParcel(Parcel in) {
            return new CmcCallInfo(in);
        }

        @Override
        public CmcCallInfo[] newArray(int size) {
            return new CmcCallInfo[size];
        }
    };

    private int mCmcCallState;
    private int mCmcType;
    private int mLineSlotId;
    private String mPdDeviceId;

    private CmcCallInfo(Parcel in) {
        this.mLineSlotId = 0;
        this.mCmcType = 0;
        this.mCmcCallState = 0;
        this.mPdDeviceId = "";
        this.mLineSlotId = in.readInt();
        this.mCmcType = in.readInt();
        this.mCmcCallState = in.readInt();
        this.mPdDeviceId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mLineSlotId);
        out.writeInt(this.mCmcType);
        out.writeInt(this.mCmcCallState);
        out.writeString(this.mPdDeviceId);
    }

    public String toString() {
        return "CmcCallInfo(" + this.mLineSlotId + ") [mCmcType=" + this.mCmcType + ", mCmcCallState=" + this.mCmcCallState + ", mPdDeviceId=" + this.mPdDeviceId + "]";
    }
}
