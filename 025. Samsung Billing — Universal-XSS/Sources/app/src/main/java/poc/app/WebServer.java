package poc.app;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class WebServer extends Thread {
    private static ServerSocket serverSocket;

    protected abstract void printHeaders(PrintWriter printWriter, String path, Map<String, String> queryParams);

    protected abstract void printBody(PrintWriter printWriter, String path, Map<String, String> queryParams);

    public void run() {
        while (true) {
            try {
                new WebServer.EchoThread(serverSocket.accept()).start();
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
    }

    private void process(OutputStream outputStream, String path, Map<String, String> queryParams) {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        printWriter.append("HTTP/1.1 200").append("\r\n");
        printHeaders(printWriter, path, queryParams);
        printWriter.append("\r\n");
        printBody(printWriter, path, queryParams);
        printWriter.flush();
        printWriter.close();
    }

    private class EchoThread extends Thread {
        protected Socket socket;

        public EchoThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                OutputStream outputStream = new PrintStream(new BufferedOutputStream(this.socket.getOutputStream()));
                byte[] bArr = new byte[6400];
                while (this.socket.isConnected() && dataInputStream.read(bArr) != -1) {
                    processLocation(outputStream, new String(bArr).trim().split("\\r?\\n")[0].split(" ")[1]);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void processLocation(OutputStream outputStream, String url) {
        Log.d("evil", "Request: " + url);
        process(outputStream, url, splitQuery(url));
    }

    private static Map<String, String> splitQuery(String url) {
        int queryPos = url.indexOf("?");
        if (queryPos < 0) {
            return Collections.emptyMap();
        }
        String substring = url.substring(queryPos + 1);
        try {
            Map<String, String> hashMap = new HashMap<>();
            for (String query : substring.split("&")) {
                int keyValuePos = query.indexOf("=");
                String key = keyValuePos > 0 ? URLDecoder.decode(query.substring(0, keyValuePos)) : query;
                String value = keyValuePos > 0 ? URLDecoder.decode(query.substring(keyValuePos + 1)) : null;
                hashMap.put(key, value);
            }
            return hashMap;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    final protected void printHeader(PrintWriter printWriter, String key, String value) {
        printWriter.append(key).append(": ").append(value).append("\r\n");
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            start();
            Log.d("evil", "Server started, port: " + port);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}