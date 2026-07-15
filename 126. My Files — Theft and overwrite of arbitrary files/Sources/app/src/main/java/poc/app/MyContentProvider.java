package poc.app;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileNotFoundException;
import java.io.IOException;

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

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_display_name"});
        matrixCursor.addRow(new Object[]{"../../../../data/user/0/com.sec.android.app.myfiles/test.txt"});
        return matrixCursor;
    }

    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        try {
            return getContext().getAssets().openFd("test.txt").getParcelFileDescriptor();
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}