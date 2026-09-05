# Details

<table>
    <tr>
        <td>Name</td>
        <td>NFC</td>
    </tr>
    <tr>
        <td>Package name</td>
        <td><code>com.android.nfc</code></td>
    </tr>
    <tr>
        <td>Reported date</td>
        <td>2022.09.15</td>
    </tr>
    <tr>
        <td>Fixed date</td>
        <td>2023.01.04</td>
    </tr>
    <tr>
        <td>Severity</td>
        <td>Moderate</td>
    </tr>
    <tr>
        <td>Handle</td>
        <td><a href="https://nvd.nist.gov/vuln/detail/CVE-2023-21426">CVE-2023-21426</a> (SVE-2022-2278)</td>
    </tr>
    <tr>
        <td>Reward</td>
        <td>$660</td>
    </tr>
</table>

# Description

Oversecured found in the NFC app, patched by Samsung, in the file `com/samsung/android/nfc/gpfelica/cardemulation/PinCache.java` a hardcoded AES key:
![](04%20رئيسية/Samsung%20Vulnerabilities/113.%20NFC%20—%20Hardcoded%20AES%20key%20to%20encrypt%20card%20emulation%20PINs/Oversecured_report.png)

This AES key corresponded to the value of `TO_HEX_STRING(MD5("PIN"))`, i.e. the constant `"PIN"` is hashed using MD5 and then the array of bytes is translated into a string. This AES key was used to encrypt and decrypt card emulation PINs.

This and similar hardcoded keys create a false sense of security that the data is stored in a protected form. Many developers think that this adds another layer of security. In reality, it only reduces the level of security.

**Proof of Concept**

We reused the code from the NFC app:
```java
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
```

The result of tests:
```
09-15 15:40:36.187 27649 27649 D PinCache: PIN{plain/encrypted} = {1234/b19c0e85ea1e0ad6ba7e458dedc93ade}
09-15 15:40:36.188 27649 27649 D PinCache: PIN{encrypted/plain} = {b19c0e85ea1e0ad6ba7e458dedc93ade/1234}
```

## References

- [Oversecured Blog. Use cryptography in mobile apps the right way](https://blog.oversecured.com/Use-cryptography-in-mobile-apps-the-right-way/)
- [Oversecured Blog. Discovering vendor-specific vulnerabilities in Android](https://blog.oversecured.com/Discovering-vendor-specific-vulnerabilities-in-Android/)
