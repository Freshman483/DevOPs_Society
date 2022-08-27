package com.shimmita.devopssociety.mains;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shimmita.devopssociety.R;

public class AlertWarning {
    public int remainder=0;
    Context context;
    MaterialAlertDialogBuilder warningAlert;
    public AlertWarning(Context ct,int value_passed)
    {
        this.context=ct;
        this.remainder=value_passed;
        warningAlert=new MaterialAlertDialogBuilder(context);
        warningAlert.setIcon(R.drawable.ic_baseline_warning);
        warningAlert.setTitle("DevOPS Society Alert\n("+remainder +" Attempt(s) Remaining)");
        warningAlert.setMessage("\nNo Item Was Selected From The Menu, DevOPs Society Cares More About Your Phone " +
                "Resources And Won't Make Them Go Wasted.\n" +
                "\nFor Instance Saving Your Phone Power Consumption And Memory Usage\n" +
                "\nIf No Choice Is Selected 3 times The App Will Be Exited.");

        warningAlert.setPositiveButton("OK, Understood",null);
        warningAlert.setCancelable(false);
        warningAlert.create();
        warningAlert.show();

    }
}
