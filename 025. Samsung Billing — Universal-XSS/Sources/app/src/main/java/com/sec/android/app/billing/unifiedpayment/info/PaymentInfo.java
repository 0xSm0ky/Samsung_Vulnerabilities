package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentInfo implements Parcelable {
    public static final Parcelable.Creator<PaymentInfo> CREATOR = new Parcelable.Creator<PaymentInfo>() {
        @Override
        public PaymentInfo createFromParcel(Parcel parcel) {
            return new PaymentInfo(parcel);
        }

        @Override
        public PaymentInfo[] newArray(int i) {
            return new PaymentInfo[i];
        }
    };

    private String __exceptionPaymentMethods;
    private String confirmPasswordYN;
    private String exceptionPaymentMethods;
    private String freeTrialPeriod;
    private String freeTrialPeriodType;
    private String giftCardnCouponYN;
    private String paymentType;
    private String recentPayment;
    private String recentPaymentInfo;
    private String subscriptionPeriod;
    private String subscriptionPeriodType;
    private String subscriptionStartDate;
    private String tieredCount;
    private String tieredPeriod;
    private String tieredPeriodType;
    private String tieredStartDate;
    private String upOrDowngradeStartDate;

    public PaymentInfo() {
    }

    public PaymentInfo(Parcel parcel) {
        this.confirmPasswordYN = parcel.readString();
        this.__exceptionPaymentMethods = parcel.readString();
        this.exceptionPaymentMethods = parcel.readString();
        this.freeTrialPeriod = parcel.readString();
        this.freeTrialPeriodType = parcel.readString();
        this.giftCardnCouponYN = parcel.readString();
        this.paymentType = parcel.readString();
        this.recentPayment = parcel.readString();
        this.recentPaymentInfo = parcel.readString();
        this.subscriptionPeriod = parcel.readString();
        this.subscriptionPeriodType = parcel.readString();
        this.subscriptionStartDate = parcel.readString();
        this.tieredCount = parcel.readString();
        this.tieredPeriod = parcel.readString();
        this.tieredPeriodType = parcel.readString();
        this.tieredStartDate = parcel.readString();
        this.upOrDowngradeStartDate = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getConfirmPasswordYN() {
        return this.confirmPasswordYN;
    }

    public String getExceptionPaymentMethods() {
        return this.exceptionPaymentMethods;
    }

    public String getExceptionPaymentMethodsInternal() {
        return this.__exceptionPaymentMethods;
    }

    public String getFreeTrialPeriod() {
        return this.freeTrialPeriod;
    }

    public String getFreeTrialPeriodType() {
        return this.freeTrialPeriodType;
    }

    public String getGiftCardnCouponYN() {
        return this.giftCardnCouponYN;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public String getRecentPayment() {
        return this.recentPayment;
    }

    public String getRecentPaymentInfo() {
        return this.recentPaymentInfo;
    }

    public String getSubscriptionPeriod() {
        return this.subscriptionPeriod;
    }

    public String getSubscriptionPeriodType() {
        return this.subscriptionPeriodType;
    }

    public String getSubscriptionStartDate() {
        return this.subscriptionStartDate;
    }

    public String getTieredCount() {
        return this.tieredCount;
    }

    public String getTieredPeriod() {
        return this.tieredPeriod;
    }

    public String getTieredPeriodType() {
        return this.tieredPeriodType;
    }

    public String getTieredStartDate() {
        return this.tieredStartDate;
    }

    public String getUpOrDowngradeStartDate() {
        return this.upOrDowngradeStartDate;
    }

    public void setConfirmPasswordYN(String str) {
        this.confirmPasswordYN = str;
    }

    public void setExceptionPaymentMethods(String str) {
        this.exceptionPaymentMethods = str;
    }

    public void setExceptionPaymentMethodsInternal(String str) {
        this.__exceptionPaymentMethods = str;
    }

    public void setFreeTrialPeriod(String str) {
        this.freeTrialPeriod = str;
    }

    public void setFreeTrialPeriodType(String str) {
        this.freeTrialPeriodType = str;
    }

    public void setGiftCardnCouponYN(String str) {
        this.giftCardnCouponYN = str;
    }

    public void setPaymentType(String str) {
        this.paymentType = str;
    }

    public void setRecentPayment(String str) {
        this.recentPayment = str;
    }

    public void setRecentPaymentInfo(String str) {
        this.recentPaymentInfo = str;
    }

    public void setSubscriptionPeriod(String str) {
        this.subscriptionPeriod = str;
    }

    public void setSubscriptionPeriodType(String str) {
        this.subscriptionPeriodType = str;
    }

    public void setSubscriptionStartDate(String str) {
        this.subscriptionStartDate = str;
    }

    public void setTieredCount(String str) {
        this.tieredCount = str;
    }

    public void setTieredPeriod(String str) {
        this.tieredPeriod = str;
    }

    public void setTieredPeriodType(String str) {
        this.tieredPeriodType = str;
    }

    public void setTieredStartDate(String str) {
        this.tieredStartDate = str;
    }

    public void setUpOrDowngradeStartDate(String str) {
        this.upOrDowngradeStartDate = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.confirmPasswordYN);
        parcel.writeString(this.__exceptionPaymentMethods);
        parcel.writeString(this.exceptionPaymentMethods);
        parcel.writeString(this.freeTrialPeriod);
        parcel.writeString(this.freeTrialPeriodType);
        parcel.writeString(this.giftCardnCouponYN);
        parcel.writeString(this.paymentType);
        parcel.writeString(this.recentPayment);
        parcel.writeString(this.recentPaymentInfo);
        parcel.writeString(this.subscriptionPeriod);
        parcel.writeString(this.subscriptionPeriodType);
        parcel.writeString(this.subscriptionStartDate);
        parcel.writeString(this.tieredCount);
        parcel.writeString(this.tieredPeriod);
        parcel.writeString(this.tieredPeriodType);
        parcel.writeString(this.tieredStartDate);
        parcel.writeString(this.upOrDowngradeStartDate);
    }
}