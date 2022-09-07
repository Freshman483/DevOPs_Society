package com.shimmita.devopssociety.alert_dialog_devops_official_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.DrawerMainStarter;
import com.shimmita.devopssociety.mains.MakeVibrator;
import com.shimmita.devopssociety.mains.VibratorLowly;

import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class AlertUSerOfEnteringLoginCredentials extends BottomSheetDialogFragment {
    //declaration of the variables
    AppCompatButton appCompatButton;
    EditText email, password;
    //
    //firebase auth variable is required to login particular use
    FirebaseAuth auth;
    //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.user_officials_alert_login_layout, null);
        //code here

        //init variables
        appCompatButton = view.findViewById(R.id.loginButtonAlert);
        email = view.findViewById(R.id.emailAlert);
        password = view.findViewById(R.id.passwordAlert);
        //firebase
        auth = FirebaseAuth.getInstance();
        //
        //adding listener to button
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if the data supplied is not empty
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(getActivity(), "missing credentials not allowed!", Toast.LENGTH_LONG).show();
                    email.requestFocus();
                    email.setBackgroundColor(Color.MAGENTA);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        new VibratorLowly(getActivity());
                    }

                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(getActivity(), "missing credentials not allowed!", Toast.LENGTH_LONG).show();

                    email.requestFocus();
                    email.setBackgroundColor(Color.MAGENTA);

                    password.requestFocus();
                    password.setBackgroundColor(Color.MAGENTA);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        new VibratorLowly(getActivity());
                    }
                } else if (TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(getActivity(), "Worse,missing credentials not allowed!", Toast.LENGTH_LONG).show();
                    password.requestFocus();
                    password.setBackgroundColor(Color.MAGENTA);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        new MakeVibrator(getActivity());
                    }
                } else {
                    //everything finesse
                    //call the function to validate the data
                    functionValidateData();
                    //
                }
            }
        });
        //
        //

        return view;
    }

    private void functionValidateData() {
        String emailGotten = email.getText().toString();
        String passCodeGotten = password.getText().toString();

        auth.signInWithEmailAndPassword(emailGotten, passCodeGotten).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //dismiss this dialog class
                    dismiss();
                    //
                    //display toast successful
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    //taking him into drawerMain where will be directed to the devOps officials members fragment class
                    startActivity(new Intent(getActivity(), DrawerMainStarter.class).putExtra("fragment", "officials"));
                    CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                    //
                } else {
                    //alert the user error is happened
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Login Error!")
                            .setIcon(R.drawable.android2)
                            .setMessage("android encountered this error:\n\n(" + task.getException().getMessage().toUpperCase(Locale.ROOT) + ")")
                            .setCancelable(false)
                            .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //
                                    dialogInterface.dismiss();
                                    //showing the dialog again

                                }
                            }).create().show();
                    //
                }
            }
        });

    }
}
