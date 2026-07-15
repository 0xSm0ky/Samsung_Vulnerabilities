package com.samsung.android.knox.custom;

parcelable StatusbarIconItem;

interface IKnoxCustomManagerSystemUiCallback {
    void setLockScreenHiddenItems(int i);

    void setLockScreenOverrideMode(int i);

    void setQuickPanelButtons(int i);

    void setQuickPanelEditMode(int i);

    void setQuickPanelItems(String str);

    void setQuickPanelUnavailableButtons(String str);

    void setScreenOffOnStatusBarDoubleTapState(boolean z);

    void setStatusBarTextInfo(String str, int i, int i2, int i3);

    void setStatusBarIconsState(boolean z);

    void setBatteryLevelColourItem(in StatusbarIconItem statusbarIconItem);

    void setHideNotificationMessages(int i);

    void setStatusBarNotificationsState(boolean z);

    void setUnlockSimOnBootState(boolean z);

    void setUnlockSimPin(String str);

    void setChargerConnectionSoundEnabledState(boolean z);

    void setStatusBarHidden(boolean z);

    void setVolumePanelEnabledState(boolean z);

    void setQuickPanelButtonUsers(boolean z);

    void setHardKeyIntentState(boolean z);
}