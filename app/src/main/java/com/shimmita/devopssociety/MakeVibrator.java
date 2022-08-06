package com.shimmita.devopssociety;

import android.content.Context;
import android.os.*;
import android.widget.Toast;

public class MakeVibrator {
    Context context;

    public MakeVibrator(Context context) {
        this.context = context;
        startVibration();
    }

    private void startVibration() {
        Vibrator vibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator.hasVibrator())
        {
            Toast.makeText(context, "Device Vibration Is Ready for Notifications", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O)
            {
                vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
            }
            vibrator.vibrate(200);
        }
        else
        {
            Toast.makeText(context, "No Vibration Object for Notification!", Toast.LENGTH_LONG).show();
        }
    }
}
