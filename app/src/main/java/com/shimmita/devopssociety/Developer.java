package com.shimmita.devopssociety;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Developer extends AppCompatActivity {

    androidx.appcompat.widget.PopupMenu popupMenu;
    MaterialAlertDialogBuilder builder;
    TextView textView;
    ConstraintLayout parent;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        this.setTitle("Developer Information");
        parent = findViewById(R.id.parentDeveloperConstraint);
        textView = findViewById(R.id.textviewDeveloper);
        builder = new MaterialAlertDialogBuilder(this);
        popupMenu = new androidx.appcompat.widget.PopupMenu(Developer.this, findViewById(R.id.cardviewParent));

        functionBuilderCall();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void functionBuilderCall() {

        builder.setTitle("Select Your Action");
        builder.setMessage("What Interest Are You Having Towards The developer Of devOPs Society?");
        builder.setCancelable(false);
        builder.setPositiveButton("Contact Developer", (dialogInterface, i) -> {
            functionPoupSelection();
            dialogInterface.dismiss();
        });

        builder.setNegativeButton("View Profile", (dialogInterface, i) -> {
            textView.setVisibility(View.VISIBLE);
            dialogInterface.dismiss();
        });
        builder.create();
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void functionPoupSelection() {
        textView.setVisibility(View.VISIBLE);
        popupMenu.inflate(R.menu.developer_options);
        popupMenu.setForceShowIcon(true);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.callDeveloper:

                    try {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+254757450727")));
                    } catch (Exception e) {
                        Toast.makeText(Developer.this, "Error Phone App Unavailable In This Device !", Toast.LENGTH_LONG).show();

                    }

                    return true;

                case R.id.Message:

                    try {
                        String text = "Hey developer @DevOps,....";
                        Intent intentMessage = new Intent();
                        intentMessage.setAction(Intent.ACTION_SEND);
                        intentMessage.putExtra(Intent.EXTRA_TEXT, text);
                        intentMessage.setType("text/plain");
                        Intent sendNowIntent = Intent.createChooser(intentMessage, null);
                        startActivity(sendNowIntent);
                    } catch (Exception e) {
                        Toast.makeText(Developer.this, "Error Messaging App Unavailable In This Device !", Toast.LENGTH_LONG).show();
                    }
                    return true;

                default:
                    return false;
            }
        });
        popupMenu.show();
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        functionPoupSelection();
    }*/
}