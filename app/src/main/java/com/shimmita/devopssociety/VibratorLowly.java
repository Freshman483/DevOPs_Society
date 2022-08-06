package com.shimmita.devopssociety;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

public class VibratorLowly {
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public VibratorLowly(Context context) {
        this.context = context;
        functionVibrateNow();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void functionVibrateNow() {
        Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator())
        {

            vibrator.vibrate(VibrationEffect.createOneShot(50,VibrationEffect.DEFAULT_AMPLITUDE));

        }
        else
            vibrator.vibrate(50);
    }
}
