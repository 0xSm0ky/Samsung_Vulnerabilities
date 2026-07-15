package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceStoreInterfaceInfo implements Parcelable {
    public static final Parcelable.Creator<ServiceStoreInterfaceInfo> CREATOR = new Parcelable.Creator<ServiceStoreInterfaceInfo>() {
        @Override
        public ServiceStoreInterfaceInfo createFromParcel(Parcel parcel) {
            return new ServiceStoreInterfaceInfo(parcel);
        }

        @Override
        public ServiceStoreInterfaceInfo[] newArray(int i) {
            return new ServiceStoreInterfaceInfo[i];
        }
    };

    private String addGiftCardnCouponURL;
    private String getGiftCardnCouponURL;
    private String getTaxInfoURL;
    private String notiPaymentResultURL;
    private String requestOrderURL;

    public ServiceStoreInterfaceInfo() {
    }

    public ServiceStoreInterfaceInfo(Parcel parcel) {
        this.addGiftCardnCouponURL = parcel.readString();
        this.getGiftCardnCouponURL = parcel.readString();
        this.getTaxInfoURL = parcel.readString();
        this.notiPaymentResultURL = parcel.readString();
        this.requestOrderURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAddGiftCardnCouponURL() {
        return this.addGiftCardnCouponURL;
    }

    public String getGetGiftCardnCouponURL() {
        return this.getGiftCardnCouponURL;
    }

    public String getGetTaxInfoURL() {
        return this.getTaxInfoURL;
    }

    public String getNotiPaymentResultURL() {
        return this.notiPaymentResultURL;
    }

    public String getRequestOrderURL() {
        return this.requestOrderURL;
    }

    public void setAddGiftCardnCouponURL(String str) {
        this.addGiftCardnCouponURL = str;
    }

    public void setGetGiftCardnCouponURL(String str) {
        this.getGiftCardnCouponURL = str;
    }

    public void setGetTaxInfoURL(String str) {
        this.getTaxInfoURL = str;
    }

    public void setNotiPaymentResultURL(String str) {
        this.notiPaymentResultURL = str;
    }

    public void setRequestOrderURL(String str) {
        this.requestOrderURL = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.addGiftCardnCouponURL);
        parcel.writeString(this.getGiftCardnCouponURL);
        parcel.writeString(this.getTaxInfoURL);
        parcel.writeString(this.notiPaymentResultURL);
        parcel.writeString(this.requestOrderURL);
    }
}