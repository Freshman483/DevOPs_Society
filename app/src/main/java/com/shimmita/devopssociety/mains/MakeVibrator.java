package com.shimmita.devopssociety.mains;

import android.content.Context;
import android.os.*;
import android.widget.Toast;

import com.shimmita.devopssociety.R;

import es.dmoral.toasty.Toasty;

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
            Toasty.custom(context, "Device Vibration Is For Notification(s)", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

            if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O)
            {
                vibrator.vibrate(VibrationEffect.createOneShot(100,VibrationEffect.DEFAULT_AMPLITUDE));
            }
            vibrator.vibrate(100);
        }
        else
        {
            Toast.makeText(context, "No Vibration Object for Notification!", Toast.LENGTH_LONG).show();
        }
    }
}
