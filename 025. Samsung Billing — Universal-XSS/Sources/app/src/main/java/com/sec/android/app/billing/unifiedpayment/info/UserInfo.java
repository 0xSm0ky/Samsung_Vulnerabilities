package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel parcel) {
            return new UserInfo(parcel);
        }

        @Override
        public UserInfo[] newArray(int i) {
            return new UserInfo[i];
        }
    };

    private String accessToken;
    private String authAppID;
    private String userEmail;
    private String userID;
    private String userName;

    public UserInfo() {
    }

    public UserInfo(Parcel parcel) {
        this.accessToken = parcel.readString();
        this.authAppID = parcel.readString();
        this.userEmail = parcel.readString();
        this.userID = parcel.readString();
        this.userName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getAuthAppID() {
        return this.authAppID;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setAccessToken(String str) {
        this.accessToken = str;
    }

    public void setAuthAppID(String str) {
        this.authAppID = str;
    }

    public void setUserEmail(String str) {
        this.userEmail = str;
    }

    public void setUserID(String str) {
        this.userID = str;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.accessToken);
        parcel.writeString(this.authAppID);
        parcel.writeString(this.userEmail);
        parcel.writeString(this.userID);
        parcel.writeString(this.userName);
    }
}