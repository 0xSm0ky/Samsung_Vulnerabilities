package com.sec.android.app.samsungapps.api.aidl;

interface IInstallAgentResultCallback {
    void onInstallStart(String str);

    void onInstallSuccess(String str);

    void onInstallFailed(String str, String str2);
}