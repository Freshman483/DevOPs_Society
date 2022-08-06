package com.shimmita.devopssociety;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    SpeechClass speechClass;

    private static final int CODE = 1033;
    private static final String TAG = "MainActivity";
    public static int count = 0;
    public static int maximum = 4;
    BiometricManager biometricManager_checking_fingerprint_support;
    Executor executor;
    BiometricPrompt biometricPrompt_functionality_implementation;
    BiometricPrompt.PromptInfo promptInfo_dialog;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    ConstraintLayout constraintLayout_parent;
    ImageView imageView;
    AnimationDrawable animationDrawable;
    AppCompatButton appCompatButton_start;
    Animation animation;
    PopupMenu popupMenu;
    AlertWarning alertWarningClass;
    ProgressDialog progressDialog;
    Database db;
    Snackbar mysnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // db = new Database(MainActivity.this);
        constraintLayout_parent = findViewById(R.id.parent);
        appCompatButton_start = findViewById(R.id.button_start);
        imageView = findViewById(R.id.imageView);

        imageView.setBackgroundResource(R.drawable.animation);
        animationDrawable = (AnimationDrawable) imageView.getBackground();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        //snackbar position to be decided
      /*  mysnack = Snackbar.make(constraintLayout_parent, "  The Game Of Technology\n  (@DEVOPS SOCIETY!)", Snackbar.LENGTH_SHORT)
                .setTextColor(Color.YELLOW).setBackgroundTint(Color.DKGRAY);

        mysnack.setBackgroundTint(Color.BLACK);
        mysnack.show();*/

        fingerprintAuthentication();
        imageViewOnclickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.setQwertyMode(true);
        new MenuInflater(this).inflate(R.menu.options_menu_main_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutMainMenu:

                Toast.makeText(this, "About Menu", Toast.LENGTH_SHORT).show();
                item.setChecked(!item.isChecked());
                new VibratorLowly(MainActivity.this);

                return true;

            case R.id.refresh:

                new SpeechClass(this,"Refresh Successful");
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();

                item.setChecked(!item.isChecked());

                new VibratorLowly(MainActivity.this);

                return true;

            case R.id.rating:

                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                item.setChecked(!item.isChecked());
                new VibratorLowly(MainActivity.this);

                return true;

            case R.id.helpMember:
                dialogAlertForHelpingMember();

                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                item.setChecked(!item.isChecked());

                new VibratorLowly(MainActivity.this);
                return true;

            default:
                return false;
        }

    }

    private void dialogAlertForHelpingMember() {
        String text="DevOps Society Says I  Should Read The Message For You, Should I?";

        String helpMessage=getString(R.string.helpTextInquiry);

        new SpeechClass(this,text);
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Android Response\n")
                .setMessage(text)
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_android_24)
                .setPositiveButton("Yes,Do Read It", (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                  //inner dialog
                    new SpeechClass(MainActivity.this,helpMessage);
                    new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_baseline_message_textmessage)
                            .setTitle("Help Message")
                            .setCancelable(false)
                            .setMessage(helpMessage)
                            .setPositiveButton("thats, great", (dialogInterface1, i1) -> {
                                dialogInterface1.dismiss();
                                new SpeechClass(MainActivity.this,"").textToSpeech.shutdown();
                            })
                            .create()
                            .show();
                    //inner Dialog

                })
                .setNegativeButton("Oh,no I Will ", (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                    //inner Dialog
                    new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_baseline_message_textmessage)
                            .setTitle("Help Message")
                            .setCancelable(false)
                            .setMessage(helpMessage)
                            .setPositiveButton("OK,Awesome One", (dialogInterface12, i12) -> {
                                Toast.makeText(MainActivity.this, "Congratulations!,Now You Can Use DevOPs Easily", Toast.LENGTH_LONG).show();
                                dialogInterface12.dismiss();
                            })
                            .create()
                            .show();
                    //
                })
                .create()
                .show();

    }

    private void imageViewOnclickListener() {
        imageView.setOnClickListener(view -> {
            new SpeechClass(this,"DevOps,Society");
            appCompatButton_start.startAnimation(animation);
            Toast.makeText(MainActivity.this, "DevOps Society,The KE Software Lions!", Toast.LENGTH_LONG).show();
        });
    }

    //fingerprint function
    public void fingerprintAuthentication() {

        biometricManager_checking_fingerprint_support = BiometricManager.from(this);
        switch (biometricManager_checking_fingerprint_support.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Toast.makeText(this, "Device Supports Fingerprint Requirements", Toast.LENGTH_LONG).show();

                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Fingerprint Support Service Currently Unreachable!", Toast.LENGTH_LONG).show();

                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Error, Device Does Not Support Fingerprint", Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "You're Being Directed To Fingerprint Enrolment Settings ...", Toast.LENGTH_SHORT).show();

                Intent fingerprintEnrolment = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                fingerprintEnrolment.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(fingerprintEnrolment, CODE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                Toast.makeText(this, "Error... BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED!", Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                Toast.makeText(this, "Device Fingerprint Status Unknown!", Toast.LENGTH_LONG).show();
                break;

        }

        executor = ContextCompat.getMainExecutor(this);


        biometricPrompt_functionality_implementation = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                // Toast.makeText(MainActivity.this, "Cannot Proceed!->" + errString, Toast.LENGTH_LONG).show();
                //snackbar position to be decided

                alertBypassFingerprint();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "FingerPrint Authentication  Successful Welcome", Toast.LENGTH_LONG).show();

                //alert Deveops Start Dialog proceed
                alertDialog();


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "FingerPrint Authentication Failure!", Toast.LENGTH_LONG).show();

            }
        });

        promptInfo_dialog = new BiometricPrompt.PromptInfo.Builder()

                .setTitle("DevOPS BIOMETRICS DETECTION")
                .setSubtitle("Fingerprint Security Encryption Enabled\n")
                .setDescription(" Developers Society Says Unlock with fingerprint technology to Continue")
                // .setNegativeButtonText("Cancel") if set device credential should be off
                .setDeviceCredentialAllowed(true) //alternative method of unlock i.e using password
                .setConfirmationRequired(true)
                .build();

        biometricPrompt_functionality_implementation.authenticate(promptInfo_dialog);
    }

    private void alertBypassFingerprint() {
        new SpeechClass(MainActivity.this,"Access,Denied");
        new AlertDialog.Builder(this)
                .setTitle("Security Guard")
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_lock_24)
                .setMessage("DevOPS Society Has Detected Fingerprint Bypass.You Cannot Proceed Without Biometric Authentication !")
                .setPositiveButton("Lets Biometric Authenticate", (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                    //Replace mysnackbar With Alert Dialog That is not cancellable to Void Bug error,System Crash
                    mysnack = Snackbar.make(constraintLayout_parent, "Resolve Fingerprint Bypass ", Snackbar.LENGTH_INDEFINITE).setTextColor(Color.YELLOW).setBackgroundTint(Color.DKGRAY);
                    mysnack.setAction("Fingerprint", view -> fingerprintAuthentication());
                    mysnack.setActionTextColor(Color.parseColor("#FF6F00"));
                    mysnack.setBackgroundTint(Color.BLACK);
                    mysnack.show();


                    ///
                })
                .create()
                .show();
        new MakeVibrator(this);
    }


    private void alertDialog() {
        //speech class initialisation
        new SpeechClass(this,"Welcome To Developers Society Kenya,@DevOPS");

        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle(R.string.titleDevOps);
        materialAlertDialogBuilder.setMessage(R.string.description_DevOPs_Alert);
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.setIcon(R.drawable.ic_baseline_whatshot_24);

        materialAlertDialogBuilder.setPositiveButton(R.string.positBtn, (dialog, which) -> {
            animationDrawable.start();
            appCompatButton_start.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_slide_in_top);
            appCompatButton_start.startAnimation(animation);

        });
        materialAlertDialogBuilder.setNegativeButton(R.string.negBtn, (dialog, which) -> finish());
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void functionClicked(View view) {
        //animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_fade_in);
        view.startAnimation(animation);
        //
        popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.inflate(R.menu.popup_selection_menu_main);
        popupMenu.setForceShowIcon(true);

        //menu selection Listener
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.account_login:
                    Toast.makeText(MainActivity.this, "Account Login", Toast.LENGTH_LONG).show();
                    progressDialog.setTitle("Service Login");
                    progressDialog.show();
                    startActivity(new Intent(MainActivity.this, Login.class));
                    CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                    finish();
                    return true;
                case R.id.account_creation:
                    Toast.makeText(MainActivity.this, "Account Creation", Toast.LENGTH_LONG).show();
                    progressDialog.setTitle("Service Registration");
                    progressDialog.show();
                    startActivity(new Intent(MainActivity.this, Registration.class));
                    finish();
                    return true;
                case R.id.account_overview:
                    Toast.makeText(MainActivity.this, "Account Overview", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, OverviewMainActivity.class));
                    return true;
                case R.id.account_share:
                    Toast.makeText(MainActivity.this, "Account Share", Toast.LENGTH_LONG).show();
                    Intent share_intent = new Intent(Intent.ACTION_SEND);
                    share_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    share_intent.setType("text/plain");
                    share_intent.putExtra(Intent.EXTRA_TEXT, "Hey Download DevOPS Society (Developers Society) App From Playstore and Promote The Application Purposiveness @ShimitaDouglas!.");
                    startActivity(share_intent);
                    finish();
                    return true;
                case R.id.account_aboutdeveloper:
                    progressDialog.setTitle("DEVELOPER");
                    progressDialog.setMessage("Fetching...");
                    progressDialog.show();
                    Toast.makeText(MainActivity.this, "Account About Developer", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, Developer.class));
                    finish();
                    return true;

                case R.id.forum:
                    Toast.makeText(this, "Lets Converse In the Forum ", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.trending:
                    Toast.makeText(this, "Get Updated In; With Technological Trends", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.jobSection:
                    Toast.makeText(this, "Our Members Will Have It Done Instantly", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.jobSectionDo:
                    Toast.makeText(this, "Work For A Client As Soon As Possible And Get Paid", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.account_exit:
                    new MaterialAlertDialogBuilder(MainActivity.this)
                            .setTitle("EXIT")
                            .setCancelable(false)
                            .setMessage("Do You Want To Exit From The App?")
                            .setIcon(R.drawable.ic_baseline_info_24)
                            .setPositiveButton("Yes, I Do ", (dialogInterface, i) -> Runtime.getRuntime().exit(0))
                            .setNegativeButton("No, Lets Be Back", (dialogInterface, i) -> dialogInterface.dismiss())
                            .create()
                            .show();
                    return true;

                case R.id.otherDevOpsProducts:
                    otherProductsFunction();

                    return true;

                default:
                    return false;
            }

        });
        //

        //dismisss Listener
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//                // Toast.makeText(MainActivity.this, "Nothing Selected !", Toast.LENGTH_LONG).show();
//                count++;
//                int results_of_deference = maximum - count;
//                if (results_of_deference < 1) {
//                    System.exit(0);
//                } else if (results_of_deference == 1) {
//                    MainActivity.this.setTitle("DevOPS Last Chance Operation Zone ");
//                }
//                alertWarningClass = new AlertWarning(MainActivity.this, results_of_deference);
//
//
//
//            }
//        });
        //

        popupMenu.show();


        //
    }

    private void otherProductsFunction() {
        Toast.makeText(this, "More Apps From Developers Society Team", Toast.LENGTH_SHORT).show();
        PopupMenu popupMenu_otherProducts = new PopupMenu(MainActivity.this, appCompatButton_start);
        popupMenu_otherProducts.setForceShowIcon(true);
        popupMenu_otherProducts.inflate(R.menu.other_devops_products);

        popupMenu_otherProducts.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation:

                    Toast.makeText(MainActivity.this, "DevOPS Navigator App @DevOPS Navi", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.professionals:
                    Toast.makeText(MainActivity.this, "DevOPS Army Generals App  @DevOPS Geni", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.trackingDevice:
                    Toast.makeText(MainActivity.this, "DevOps Tracker App @DevOPS Tracker", Toast.LENGTH_LONG).show();

                    return true;

                case R.id.comrades:
                    Toast.makeText(MainActivity.this, "Meet Legit Working Comrades App @DevOPSCom Power", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.masenoMarketing:
                    Toast.makeText(MainActivity.this, "Lets Market Easily With App @Devops Market ", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.reverseEngineer:

                    Toast.makeText(MainActivity.this, "Bypass android App for free Use @DevOPS Reverser", Toast.LENGTH_LONG).show();
                    return true;

                default:
                    return false;
            }
        });


        popupMenu_otherProducts.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
        Runtime.getRuntime().exit(0);
    }

}