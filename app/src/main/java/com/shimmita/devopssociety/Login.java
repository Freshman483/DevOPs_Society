package com.shimmita.devopssociety;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    private static final String TAG = "LoginActivity";
    ImageView imageView_login_logo;
    TextInputEditText email, password;
    ConstraintLayout parentLayout;
    Animation animation;
    androidx.appcompat.widget.PopupMenu popupMenu_login_submission;

    String emailValue, passwordValue;
    ProgressDialog pg;

    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("DevOPS Society Account Login");

        auth = FirebaseAuth.getInstance();

        imageView_login_logo = findViewById(R.id.image_person_login);
        password = findViewById(R.id.material_editText_input_login_password);
        email = findViewById(R.id.material_editText_input_login_mail);
        parentLayout = findViewById(R.id.parentConstraintLayout_login);
        animation = AnimationUtils.loadAnimation(this, R.anim.rotation);

        animationDrawable=(AnimationDrawable)parentLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        //checkInternet();
    }

    private void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        if (activeInfo == null) {

            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_baseline_hotspot_internet)
                    .setTitle("Internet Check")
                    .setMessage("detected no Internet Connection !")
                    .setCancelable(false)
                    .setPositiveButton("Open For Me", (dialogInterface, i) -> {
                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        dialogInterface.dismiss();
                    }).create()
                    .show();
        }
    }


    public void functionFloatingButtonLoginClicked(View view) {
        popupMenu_login_submission = new PopupMenu(Login.this, view);
        popupMenu_login_submission.inflate(R.menu.popup_login_menu);
        popupMenu_login_submission.setForceShowIcon(Boolean.parseBoolean("true"));
        popupMenu_login_submission.show();
        view.startAnimation(animation);

        popupMenu_login_submission.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.loginNow:
                    credentialCheckLogin();
                    return true;
                case R.id.forgottenCredential:

                    return true;
                default:
                    return false;

            }
        });


        popupMenu_login_submission.setOnDismissListener(menu -> view.clearAnimation());
    }

    private void credentialCheckLogin() {
        emailValue = email.getText().toString();
        passwordValue = password.getText().toString();

        Log.d(TAG, "credentialCheckLogin: email value" + emailValue);
        Log.d(TAG, "credentialCheckLogin: password Value" + passwordValue);

        if (TextUtils.isEmpty(emailValue)) {
            new AlertDialog.Builder(Login.this)
                    .setTitle("Credentials Unacceptable !")
                    .setMessage("email is empty,please enter your email.")
                    .create()
                    .show();
            email.requestFocus();
            new MakeVibrator(Login.this);
        } else if (!(emailValue.contains("@") && emailValue.contains(".com") || (emailValue.contains("gmail")) || emailValue.contains("yahoo"))) {
            new AlertDialog.Builder(Login.this)
                    .setTitle("Email Address !")
                    .setMessage("email is invalid,please enter a valid email address which you registered with DevOPS Society.")
                    .create()
                    .show();
            email.requestFocus();
            new MakeVibrator(Login.this);
        } else if (TextUtils.isEmpty(passwordValue)) {
            new AlertDialog.Builder(Login.this)
                    .setTitle("Credentials Unacceptable !")
                    .setMessage("password is empty,please enter your password.")
                    .create()
                    .show();
            password.requestFocus();
            new MakeVibrator(Login.this);
        } else {//eveting finesse
            //Toast.makeText(this, "Very ready", Toast.LENGTH_SHORT).show();

            auth.signInWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        pg.show();
                        Toast.makeText(Login.this, "Login Was Successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        new AlertDialog.Builder(Login.this)
                                .setTitle("LOGIN FAILED!")
                                .setMessage("\nLogin  Result:\n"  + "\n" +
                                        "\nDear (Unknown Member ) Your Login Was Unsuccessful This Might Be Due To:\n" +
                                        "\n1.Invalid Login Credentials Being Used ! Or User Doesn't Exist In The System \n"+"\n"+
                                        "\n2.Internet Connectivity Issues,If So Please Turn On The Internet And Retry The Registration Process Again." +
                                        "\n " +
                                        "\n3.Internal Server Errors; i.e Server Was Down During Your registration Process; Try Again.\n" +
                                        "\n4.Your Internet Browsing Data Bundles Completely Depleted ; Wifi Or Purchase Data Bundles  As A Solution And Try Again")
                                .setIcon(R.drawable.ic_baseline_clear_24)
                                .setCancelable(false)
                                .setPositiveButton("Ok,check internet", (dialogInterface, i) -> {
                                    startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                                })
                                .setNegativeButton("Buy Data Bundles", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:*544#")));
                                    }
                                })
                                .create()
                                .show();
                    }
                }
            });


        }
    }

}