package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class SubscriptionInfo implements Parcelable {
    public static final Parcelable.Creator<SubscriptionInfo> CREATOR = new Parcelable.Creator<SubscriptionInfo>() {
        @Override
        public SubscriptionInfo createFromParcel(Parcel parcel) {
            return new SubscriptionInfo(parcel);
        }

        @Override
        public SubscriptionInfo[] newArray(int i) {
            return new SubscriptionInfo[i];
        }
    };

    private String dayPeriodPayment;
    private String freeTrialPeriod;
    private String notificationURL;
    private String[] periodStartPaymentInfoList;
    private String repeatCount;
    private String repeatNumber;
    private String retryCount;
    private String retryPeriod;
    private String statusNotificationURL;
    private String subscriptionID;
    private String subscriptionOrderURL;
    private String subscriptionTypeCode;
    private String timeZone;

    public SubscriptionInfo() {
    }

    public SubscriptionInfo(Parcel parcel) {
        this.subscriptionTypeCode = parcel.readString();
        this.freeTrialPeriod = parcel.readString();
        this.subscriptionOrderURL = parcel.readString();
        this.timeZone = parcel.readString();
        this.retryCount = parcel.readString();
        this.retryPeriod = parcel.readString();
        this.repeatCount = parcel.readString();
        this.dayPeriodPayment = parcel.readString();
        int readInt = parcel.readInt();
        if (readInt != 0) {
            String[] strArr = new String[readInt];
            this.periodStartPaymentInfoList = strArr;
            parcel.readStringArray(strArr);
        }
        this.notificationURL = parcel.readString();
        this.subscriptionID = parcel.readString();
        this.repeatNumber = parcel.readString();
        this.statusNotificationURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDayPeriodPayment() {
        return this.dayPeriodPayment;
    }

    public String getFreeTrialPeriod() {
        return this.freeTrialPeriod;
    }

    public String getNotificationURL() {
        return this.notificationURL;
    }

    public String[] getPeriodStartPaymentInfoList() {
        return this.periodStartPaymentInfoList;
    }

    public String getRepeatCount() {
        return this.repeatCount;
    }

    public String getRepeatNumber() {
        return this.repeatNumber;
    }

    public String getRetryCount() {
        return this.retryCount;
    }

    public String getRetryPeriod() {
        return this.retryPeriod;
    }

    public String getStatusNotificationURL() {
        return this.statusNotificationURL;
    }

    public String getSubscriptionID() {
        return this.subscriptionID;
    }

    public String getSubscriptionOrderURL() {
        return this.subscriptionOrderURL;
    }

    public String getSubscriptionTypeCode() {
        return this.subscriptionTypeCode;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setDayPeriodPayment(String str) {
        this.dayPeriodPayment = str;
    }

    public void setFreeTrialPeriod(String str) {
        this.freeTrialPeriod = str;
    }

    public void setNotificationURL(String str) {
        this.notificationURL = str;
    }

    public void setPeriodStartPaymentInfoList(String[] strArr) {
        this.periodStartPaymentInfoList = strArr;
    }

    public void setRepeatCount(String str) {
        this.repeatCount = str;
    }

    public void setRepeatNumber(String str) {
        this.repeatNumber = str;
    }

    public void setRetryCount(String str) {
        this.retryCount = str;
    }

    public void setRetryPeriod(String str) {
        this.retryPeriod = str;
    }

    public void setStatusNotificationURL(String str) {
        this.statusNotificationURL = str;
    }

    public void setSubscriptionID(String str) {
        this.subscriptionID = str;
    }

    public void setSubscriptionOrderURL(String str) {
        this.subscriptionOrderURL = str;
    }

    public void setSubscriptionTypeCode(String str) {
        this.subscriptionTypeCode = str;
    }

    public void setTimeZone(String str) {
        this.timeZone = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.subscriptionTypeCode);
        parcel.writeString(this.freeTrialPeriod);
        parcel.writeString(this.subscriptionOrderURL);
        parcel.writeString(this.timeZone);
        parcel.writeString(this.retryCount);
        parcel.writeString(this.retryPeriod);
        parcel.writeString(this.repeatCount);
        parcel.writeString(this.dayPeriodPayment);
        if (this.periodStartPaymentInfoList == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(this.periodStartPaymentInfoList.length);
            parcel.writeStringArray(this.periodStartPaymentInfoList);
        }
        parcel.writeString(this.notificationURL);
        parcel.writeString(this.subscriptionID);
        parcel.writeString(this.repeatNumber);
        parcel.writeString(this.statusNotificationURL);
    }
}