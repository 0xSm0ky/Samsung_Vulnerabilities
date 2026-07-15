package com.samsung.android.sdk.simplesharing;

import android.os.Bundle;
import com.samsung.android.sdk.simplesharing.ISimpleSharingCallback;

parcelable ExchangeData;

interface ISimpleSharingSdk {
    ExchangeData exchangeData(in ExchangeData exchangeData);

    Bundle checkServiceRegistered(in Bundle bundle, ISimpleSharingCallback iSimpleSharingCallback);

    Bundle requestShareLink(in Bundle bundle, ISimpleSharingCallback iSimpleSharingCallback);

    Bundle cancelShareLink(in Bundle bundle, ISimpleSharingCallback iSimpleSharingCallback);

    Bundle getQuota(in Bundle bundle, ISimpleSharingCallback iSimpleSharingCallback);

    Bundle getPolicy(in Bundle bundle, ISimpleSharingCallback iSimpleSharingCallback);
}