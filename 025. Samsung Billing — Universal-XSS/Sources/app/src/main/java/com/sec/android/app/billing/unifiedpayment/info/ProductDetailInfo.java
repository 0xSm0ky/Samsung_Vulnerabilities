package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDetailInfo implements Parcelable {
    public static final Parcelable.Creator<ProductDetailInfo> CREATOR = new Parcelable.Creator<ProductDetailInfo>() {
        @Override
        public ProductDetailInfo createFromParcel(Parcel parcel) {
            return new ProductDetailInfo(parcel);
        }

        @Override
        public ProductDetailInfo[] newArray(int i) {
            return new ProductDetailInfo[i];
        }
    };

    private String amount;
    private String optional1;
    private String pricingTypeCode;
    private String productID;
    private String productImageURL;
    private String productName;
    private String subscriptionDate;
    private String tax;
    private String taxCategory;
    private String tieredAmount;
    private String upOrDowngradeAmount;
    private String validityPeriod;

    public ProductDetailInfo() {
    }

    public ProductDetailInfo(Parcel parcel) {
        this.amount = parcel.readString();
        this.optional1 = parcel.readString();
        this.pricingTypeCode = parcel.readString();
        this.productID = parcel.readString();
        this.productImageURL = parcel.readString();
        this.productName = parcel.readString();
        this.subscriptionDate = parcel.readString();
        this.tax = parcel.readString();
        this.tieredAmount = parcel.readString();
        this.validityPeriod = parcel.readString();
        this.taxCategory = parcel.readString();
        this.upOrDowngradeAmount = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getOptional1() {
        return this.optional1;
    }

    public String getPricingTypeCode() {
        return this.pricingTypeCode;
    }

    public String getProductID() {
        return this.productID;
    }

    public String getProductImageURL() {
        return this.productImageURL;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getSubscriptionDate() {
        return this.subscriptionDate;
    }

    public String getTax() {
        return this.tax;
    }

    public String getTaxCategory() {
        return this.taxCategory;
    }

    public String getTieredAmount() {
        return this.tieredAmount;
    }

    public String getUpOrDowngradeAmount() {
        return this.upOrDowngradeAmount;
    }

    public String getValidityPeriod() {
        return this.validityPeriod;
    }

    public void setAmount(String str) {
        this.amount = str;
    }

    public void setOptional1(String str) {
        this.optional1 = str;
    }

    public void setPricingTypeCode(String str) {
        this.pricingTypeCode = str;
    }

    public void setProductID(String str) {
        this.productID = str;
    }

    public void setProductImageURL(String str) {
        this.productImageURL = str;
    }

    public void setProductName(String str) {
        this.productName = str;
    }

    public void setSubscriptionDate(String str) {
        this.subscriptionDate = str;
    }

    public void setTax(String str) {
        this.tax = str;
    }

    public void setTaxCategory(String str) {
        this.taxCategory = str;
    }

    public void setTieredAmount(String str) {
        this.tieredAmount = str;
    }

    public void setUpOrDowngradeAmount(String str) {
        this.upOrDowngradeAmount = str;
    }

    public void setValidityPeriod(String str) {
        this.validityPeriod = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.amount);
        parcel.writeString(this.optional1);
        parcel.writeString(this.pricingTypeCode);
        parcel.writeString(this.productID);
        parcel.writeString(this.productImageURL);
        parcel.writeString(this.productName);
        parcel.writeString(this.subscriptionDate);
        parcel.writeString(this.tax);
        parcel.writeString(this.tieredAmount);
        parcel.writeString(this.validityPeriod);
        parcel.writeString(this.taxCategory);
        parcel.writeString(this.upOrDowngradeAmount);
    }
}