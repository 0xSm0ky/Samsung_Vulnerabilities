package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductInfo implements Parcelable {
    public static final Parcelable.Creator<ProductInfo> CREATOR = new Parcelable.Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel parcel) {
            return new ProductInfo(parcel);
        }

        @Override
        public ProductInfo[] newArray(int i) {
            return new ProductInfo[i];
        }
    };

    private String couponCount;
    private String currency;
    private ProductDetailInfo[] detailProductInfos;
    private String tax;
    private String taxIncluded;
    private String totalAmount;
    private String vatIncluded;

    public ProductInfo() {
    }

    public ProductInfo(Parcel parcel) {
        this.couponCount = parcel.readString();
        this.currency = parcel.readString();
        this.tax = parcel.readString();
        this.taxIncluded = parcel.readString();
        this.vatIncluded = parcel.readString();
        this.totalAmount = parcel.readString();
        Parcelable[] readParcelableArray = parcel.readParcelableArray(ProductDetailInfo.class.getClassLoader());
        if (readParcelableArray != null) {
            this.detailProductInfos = new ProductDetailInfo[readParcelableArray.length];
            for (int i = 0; i < readParcelableArray.length; i++) {
                this.detailProductInfos[i] = (ProductDetailInfo) readParcelableArray[i];
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCouponCount() {
        return this.couponCount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public ProductDetailInfo[] getDetailProductInfos() {
        return this.detailProductInfos;
    }

    public String getTax() {
        return this.tax;
    }

    public String getTaxIncluded() {
        return this.taxIncluded;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public String getVatIncluded() {
        return this.vatIncluded;
    }

    public void setCouponCount(String str) {
        this.couponCount = str;
    }

    public void setCurrency(String str) {
        this.currency = str;
    }

    public void setDetailProductInfos(ProductDetailInfo[] productDetailInfoArr) {
        this.detailProductInfos = productDetailInfoArr;
    }

    public void setTax(String str) {
        this.tax = str;
    }

    public void setTaxIncluded(String str) {
        this.taxIncluded = str;
    }

    public void setTotalAmount(String str) {
        this.totalAmount = str;
    }

    public void setVatIncluded(String str) {
        this.vatIncluded = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.couponCount);
        parcel.writeString(this.currency);
        parcel.writeString(this.tax);
        parcel.writeString(this.taxIncluded);
        parcel.writeString(this.vatIncluded);
        parcel.writeString(this.totalAmount);
        parcel.writeParcelableArray(this.detailProductInfos, 0);
    }
}