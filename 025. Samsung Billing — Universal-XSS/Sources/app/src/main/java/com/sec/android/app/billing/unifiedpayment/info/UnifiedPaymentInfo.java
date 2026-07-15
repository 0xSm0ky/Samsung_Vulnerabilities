package com.sec.android.app.billing.unifiedpayment.info;

import android.os.Parcel;
import android.os.Parcelable;

public class UnifiedPaymentInfo implements Parcelable {
    public static final Parcelable.Creator<UnifiedPaymentInfo> CREATOR = new Parcelable.Creator<UnifiedPaymentInfo>() {
        @Override
        public UnifiedPaymentInfo createFromParcel(Parcel parcel) {
            return new UnifiedPaymentInfo(parcel);
        }

        @Override
        public UnifiedPaymentInfo[] newArray(int i) {
            return new UnifiedPaymentInfo[i];
        }
    };

    private String appServiceID;
    private UnifiedPaymentServerInfo billingServerInfo;
    private DeviceInfo deviceInfo;
    private String extraData;
    private String libraryVersion;
    private PaymentInfo paymentInfo;
    private String platformCode;
    private ProductInfo productInfo;
    private SandBoxInfo sandBoxData;
    private ServiceStoreInfo serviceStoreInfo;
    private SignatureInfo signatureInfo;
    private String storeRequestID;
    private SubscriptionInfo subscriptionInfo;
    private String timeOffset;
    private UserInfo userInfo;

    public UnifiedPaymentInfo() {
        this.platformCode = "A";
    }

    public UnifiedPaymentInfo(Parcel parcel) {
        this.appServiceID = parcel.readString();
        this.extraData = parcel.readString();
        this.libraryVersion = parcel.readString();
        this.storeRequestID = parcel.readString();
        this.timeOffset = parcel.readString();
        this.billingServerInfo = parcel.readParcelable(UnifiedPaymentServerInfo.class.getClassLoader());
        this.deviceInfo = parcel.readParcelable(DeviceInfo.class.getClassLoader());
        this.paymentInfo = parcel.readParcelable(PaymentInfo.class.getClassLoader());
        this.productInfo = parcel.readParcelable(ProductInfo.class.getClassLoader());
        this.sandBoxData = parcel.readParcelable(SandBoxInfo.class.getClassLoader());
        this.serviceStoreInfo = parcel.readParcelable(ServiceStoreInfo.class.getClassLoader());
        this.signatureInfo = parcel.readParcelable(SignatureInfo.class.getClassLoader());
        this.subscriptionInfo = parcel.readParcelable(SubscriptionInfo.class.getClassLoader());
        this.userInfo = parcel.readParcelable(UserInfo.class.getClassLoader());
        this.platformCode = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAppServiceID() {
        return this.appServiceID;
    }

    public UnifiedPaymentServerInfo getBillingServerInfo() {
        return this.billingServerInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public String getLibraryVersion() {
        return this.libraryVersion;
    }

    public PaymentInfo getPaymentInfo() {
        return this.paymentInfo;
    }

    public ProductInfo getProductInfo() {
        return this.productInfo;
    }

    public SandBoxInfo getSandBoxData() {
        return this.sandBoxData;
    }

    public ServiceStoreInfo getServiceStoreInfo() {
        return this.serviceStoreInfo;
    }

    public SignatureInfo getSignatureInfo() {
        return this.signatureInfo;
    }

    public String getStoreRequestID() {
        return this.storeRequestID;
    }

    public SubscriptionInfo getSubscriptionInfo() {
        return this.subscriptionInfo;
    }

    public String getTimeOffset() {
        return this.timeOffset;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setAppServiceID(String str) {
        this.appServiceID = str;
    }

    public void setBillingServerInfo(UnifiedPaymentServerInfo unifiedPaymentServerInfo) {
        this.billingServerInfo = unifiedPaymentServerInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setExtraData(String str) {
        this.extraData = str;
    }

    public void setLibraryVersion(String str) {
        this.libraryVersion = str;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public void setSandBoxData(SandBoxInfo sandBoxInfo) {
        this.sandBoxData = sandBoxInfo;
    }

    public void setServiceStoreInfo(ServiceStoreInfo serviceStoreInfo) {
        this.serviceStoreInfo = serviceStoreInfo;
    }

    public void setSignatureInfo(SignatureInfo signatureInfo) {
        this.signatureInfo = signatureInfo;
    }

    public void setStoreRequestID(String str) {
        this.storeRequestID = str;
    }

    public void setSubscriptionInfo(SubscriptionInfo subscriptionInfo) {
        this.subscriptionInfo = subscriptionInfo;
    }

    public void setTimeOffset(String str) {
        this.timeOffset = str;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.appServiceID);
        parcel.writeString(this.extraData);
        parcel.writeString(this.libraryVersion);
        parcel.writeString(this.storeRequestID);
        parcel.writeString(this.timeOffset);
        parcel.writeParcelable(this.billingServerInfo, 0);
        parcel.writeParcelable(this.deviceInfo, 0);
        parcel.writeParcelable(this.paymentInfo, 0);
        parcel.writeParcelable(this.productInfo, 0);
        parcel.writeParcelable(this.sandBoxData, 0);
        parcel.writeParcelable(this.serviceStoreInfo, 0);
        parcel.writeParcelable(this.signatureInfo, 0);
        parcel.writeParcelable(this.subscriptionInfo, 0);
        parcel.writeParcelable(this.userInfo, 0);
        parcel.writeString(this.platformCode);
    }
}