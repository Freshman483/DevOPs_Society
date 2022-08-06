package com.shimmita.devopssociety;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SuccessfulRegistration extends AppCompatActivity {

    MaterialAlertDialogBuilder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_registration);
        builder=new MaterialAlertDialogBuilder(this);

    }
}