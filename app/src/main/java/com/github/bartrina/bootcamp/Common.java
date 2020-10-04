package com.github.bartrina.bootcamp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public final class Common {

    public static boolean checkAndGetPermissions(Activity activity, String[] permissions)
    {
        Context context = activity.getApplicationContext();
        boolean ok = true;
        for(String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[] { permission }, 0);
            }
            ok = ok && (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
        }
        return ok;
    }
}
