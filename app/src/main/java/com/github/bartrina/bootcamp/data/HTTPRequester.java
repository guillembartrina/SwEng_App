package com.github.bartrina.bootcamp.data;

import android.graphics.Bitmap;

import java.io.IOException;
import java.net.URL;

public interface HTTPRequester {
    String get(URL url) throws IOException;
    Bitmap getBitmap(URL url) throws IOException;
}
