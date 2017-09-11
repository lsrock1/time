package com.example.sangroklee.myapplication;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by sangroklee on 2017. 9. 7..
 */

public class BackPressHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                "Please click BACK again to exit.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
