package com.samsung.android.app.livefocusviewercommonlib.utils;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataUtils {
    public static float readFloat(ByteArrayInputStream byteArrayInputStream) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{(byte) byteArrayInputStream.read(), (byte) byteArrayInputStream.read(), (byte) byteArrayInputStream.read(), (byte) byteArrayInputStream.read()});
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap.getFloat();
    }

    public static void writeFloat(ByteArrayOutputStream out, float value) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putFloat(value);
        byte[] arr = byteBuffer.array();
        if (arr.length != 4) {
            throw new RuntimeException("not 4");
        }
        out.write(arr);
    }

    public static int readInt(ByteArrayInputStream byteArrayInputStream) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{(byte) byteArrayInputStream.read(), (byte) byteArrayInputStream.read(), (byte) byteArrayInputStream.read(), (byte) byteArrayInputStream.read()});
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap.getInt();
    }

    public static void writeInt(ByteArrayOutputStream byteArrayInputStream, int value) throws IOException {
        ByteBuffer wrap = ByteBuffer.allocate(4);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        wrap.putInt(value);
        byte[] arr = wrap.array();
        if (arr.length != 4) {
            throw new RuntimeException("not 4");
        }
        byteArrayInputStream.write(arr);
    }

    public static short readShort(ByteArrayInputStream byteArrayInputStream) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{(byte) byteArrayInputStream.read(), (byte) byteArrayInputStream.read()});
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap.getShort();
    }

    public static void writeShort(ByteArrayOutputStream out, short value) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort(value);
        byte[] arr = byteBuffer.array();
        if (arr.length != 2) {
            throw new RuntimeException("not 2");
        }
        out.write(arr);
    }

    public static byte readByte(ByteArrayInputStream byteArrayInputStream) {
        return (byte) byteArrayInputStream.read();
    }

    public static void writeByte(ByteArrayOutputStream out, byte value) throws IOException {
        out.write(new byte[]{value});
    }

    public static void writeByteArray(ByteArrayOutputStream out, byte[] value) throws IOException {
        out.write(value);
    }

    public static String readString(ByteArrayInputStream byteArrayInputStream, int i) {
        byte[] bArr = new byte[i];
        int read = byteArrayInputStream.read(bArr, 0, i);
        long skip = byteArrayInputStream.skip(128 - i);
        if (read == -1) {
            Log.d("DOFV_DATAUTILS", " End of file reached ");
        }
        if (skip == 0) {
            Log.d("DOFV_DATAUTILS", "  Byte count is negative ");
        }
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void writeString(ByteArrayOutputStream out, int size, String str) throws IOException {
        byte[] bytes = str.getBytes();
        out.write(bytes);
        for (int i = 0; i < size - bytes.length; i++) {
            out.write((byte) 'a');
        }
    }

    public static String readStringNoskip(ByteArrayInputStream byteArrayInputStream, int i) {
        byte[] bArr = new byte[i];
        if (byteArrayInputStream.read(bArr, 0, i) == -1) {
            Log.d("DOFV_DATAUTILS", " End of file reached ");
        }
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}