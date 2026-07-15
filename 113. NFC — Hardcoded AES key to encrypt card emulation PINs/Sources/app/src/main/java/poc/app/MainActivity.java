package poc.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String pin = "1234";
        String encryptedPin = encryptAES(pin, generateCipherKey());
        Log.d("PinCache", String.format("PIN{plain/encrypted} = {%s/%s}", pin, encryptedPin));

        String decryptedPin = decryptAES(encryptedPin, generateCipherKey());
        Log.d("PinCache", String.format("PIN{encrypted/plain} = {%s/%s}", encryptedPin, decryptedPin));
    }

    private String generateCipherKey() {
        return toMD5Hash("PIN");
    }

    private static String toMD5Hash(String value) {
        return toHash("MD5", value);
    }

    private static String toHash(String algorithm, String value) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
        }
        if (md == null) {
            return null;
        }
        md.update(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : md.digest()) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    public static String encryptAES(String plainText, String key) {
        return encrypt(plainText, key, "AES");
    }

    public static String decryptAES(String cipherText, String key) {
        return decrypt(cipherText, key, "AES");
    }

    private static String encrypt(String plainText, String key, String algorithm) {
        byte[] ciphered = runCipher(1, plainText.getBytes(StandardCharsets.UTF_8), key, algorithm);
        return encode(ciphered);
    }

    private static String decrypt(String cipherText, String key, String algorithm) {
        byte[] cipherBytes = decode(cipherText);
        byte[] ciphered = runCipher(2, cipherBytes, key, algorithm);
        if (ciphered != null) {
            return new String(ciphered, StandardCharsets.UTF_8);
        }
        return null;
    }

    private static byte[] runCipher(int opmode, byte[] targetBytes, String key, String algorithm) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(opmode, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
            return cipher.doFinal(targetBytes);
        } catch (Exception e) {
            return null;
        }
    }

    private static String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    private static byte[] decode(String text) {
        if (text == null) {
            return null;
        }
        byte[] decoded = new byte[text.length() / 2];
        for (int i = 0; i < decoded.length; i++) {
            int beginIndex = i * 2;
            int endIndex = beginIndex + 2;
            decoded[i] = (byte) Integer.parseInt(text.substring(beginIndex, endIndex), 16);
        }
        return decoded;
    }
}