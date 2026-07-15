package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceStoreInfo implements Parcelable {
    public static final Parcelable.Creator<ServiceStoreInfo> CREATOR = new Parcelable.Creator<ServiceStoreInfo>() {
        @Override
        public ServiceStoreInfo createFromParcel(Parcel parcel) {
            return new ServiceStoreInfo(parcel);
        }

        @Override
        public ServiceStoreInfo[] newArray(int i) {
            return new ServiceStoreInfo[i];
        }
    };

    private ServiceStoreInterfaceInfo billingInterfaceURL;
    private String country;
    private String serviceURL;
    private String telNoForCS;
    private String upc_storeName;

    public ServiceStoreInfo() {
    }

    public ServiceStoreInfo(Parcel parcel) {
        this.upc_storeName = parcel.readString();
        this.country = parcel.readString();
        this.serviceURL = parcel.readString();
        this.telNoForCS = parcel.readString();
        this.billingInterfaceURL = parcel.readParcelable(ServiceStoreInterfaceInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ServiceStoreInterfaceInfo getBillingInterfaceURL() {
        return this.billingInterfaceURL;
    }

    public String getCountry() {
        return this.country;
    }

    public String getServiceURL() {
        return this.serviceURL;
    }

    public String getTelNoForCS() {
        return this.telNoForCS;
    }

    public String getUpc_storeName() {
        return this.upc_storeName;
    }

    public void setBillingInterfaceURL(ServiceStoreInterfaceInfo serviceStoreInterfaceInfo) {
        this.billingInterfaceURL = serviceStoreInterfaceInfo;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public void setServiceURL(String str) {
        this.serviceURL = str;
    }

    public void setTelNoForCS(String str) {
        this.telNoForCS = str;
    }

    public void setUpc_storeName(String str) {
        this.upc_storeName = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.upc_storeName);
        parcel.writeString(this.country);
        parcel.writeString(this.serviceURL);
        parcel.writeString(this.telNoForCS);
        parcel.writeParcelable(this.billingInterfaceURL, 0);
    }
}