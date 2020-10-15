package com.github.bartrina.bootcamp.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

public class SimpleHTTPRequester implements HTTPRequester {

    @Inject
    public SimpleHTTPRequester() {

    }

    public String get(URL url) throws IOException {
        HttpsURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder acc = new StringBuilder();
            String tmp = null;
            while((tmp = in.readLine()) != null) {
                acc.append(tmp);
            }
            in.close();
            result = acc.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    public Bitmap getBitmap(URL url) throws IOException {
        return BitmapFactory.decodeStream(url.openConnection().getInputStream());
    }
}
