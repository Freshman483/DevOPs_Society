package com.shimmita.devopssociety;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.AlertDialog;
import android.content.Intent;
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

import java.util.concurrent.Executor;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    private static final int CODE = 1033;
    public static int count = 0;
    public static int maximum = 4;
    static int COUNTER_ACTIVITY_STATE_MONITOR = 0;
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


    @RequiresApi(api = Build.VERSION_CODES.O)
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

                new SpeechClass(this, "Refresh Successful");
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
        String text = "DevOps Society Says I  Should Read The Message For You, Should I?";

        String helpMessage = getString(R.string.helpTextInquiry);

        new SpeechClass(this, text);
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Android Response\n")
                .setMessage(text)
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_android_24)
                .setPositiveButton("Yes,Do Read It", (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                    //inner dialog
                    new SpeechClass(MainActivity.this, helpMessage);
                    new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_baseline_message_textmessage)
                            .setTitle("Help Message")
                            .setCancelable(false)
                            .setMessage(helpMessage)
                            .setPositiveButton("That's, Great", (dialogInterface1, i1) -> {
                                dialogInterface1.dismiss();
                                new SpeechClass(MainActivity.this, "").textToSpeech.shutdown();
                            })
                            .create()
                            .show();
                    //inner Dialog

                })
                .setNegativeButton("Oh,No I Will ", (dialogInterface, i) -> {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void imageViewOnclickListener() {
        imageView.setOnClickListener(view -> {
            new VibratorLowly(MainActivity.this);
            appCompatButton_start.startAnimation(animation);
            Toasty.custom(getApplicationContext(), "The Most Energetic and Enigmatic Upcoming Society in Kenya!", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

        });
    }

    //fingerprint function
    public void fingerprintAuthentication() {

        biometricManager_checking_fingerprint_support = BiometricManager.from(this);
        switch (biometricManager_checking_fingerprint_support.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Toasty.custom(getApplicationContext(), "Device Supports DevOps Society Authentication Requirements", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toasty.custom(getApplicationContext(), "Authentication Service currently Unavailable !", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:

                Toasty.custom(getApplicationContext(), "Device does Not Meet DevOps Society Requirements", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                Toasty.custom(getApplicationContext(), "Device Currently Not Enrolled Security Requirements", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                Intent fingerprintEnrolment = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                fingerprintEnrolment.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(fingerprintEnrolment, CODE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                Toasty.custom(getApplicationContext(), "Security Update Required!", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                break;
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:

                Toasty.custom(getApplicationContext(), "Device Security Status Unknown!", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

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

                Toasty.custom(getApplicationContext(), "Authentication Successful, Welcome", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                //alert Deveops Start Dialog proceed
                alertDialog();


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Toasty.custom(getApplicationContext(), "Authentication Failed !", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

            }
        });

        promptInfo_dialog = new BiometricPrompt.PromptInfo.Builder()

                .setTitle(getString(R.string.fingerprint_title_alert))
                .setSubtitle("Fingerprint Security Encryption Enabled\n")
                .setDescription(" Developers Society Says Unlock with fingerprint technology to Continue")
                // .setNegativeButtonText("Cancel") if set device credential should be off
                .setDeviceCredentialAllowed(true) //alternative method of unlock i.e using password
                .setConfirmationRequired(true)
                .build();

        biometricPrompt_functionality_implementation.authenticate(promptInfo_dialog);
    }

    private void alertBypassFingerprint() {
        Toasty.custom(getApplicationContext(), "Authentication Error,Please Try Again !", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

        new AlertDialog.Builder(this)
                .setTitle("Security Guard")
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_lock_24)
                .setMessage("DevOPS Society Detected Fingerprint Bypass.You Cannot Proceed Without Biometric Authentication !")
                .setPositiveButton("Lets Biometric Authenticate", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    //Replace mysnackbar With Alert Dialog That is not cancellable to Void Bug error,System Crash
                  /*
                    mysnack = Snackbar.make(constraintLayout_parent, "Resolve Fingerprint Bypass ", Snackbar.LENGTH_INDEFINITE).setTextColor(Color.YELLOW).setBackgroundTint(Color.DKGRAY);
                    mysnack.setAction("Fingerprint", view -> fingerprintAuthentication());
                    mysnack.setActionTextColor(Color.parseColor("#FF6F00"));
                    mysnack.setBackgroundTint(Color.BLACK);
                    mysnack.show();*/

                    new MaterialAlertDialogBuilder(MainActivity.this)
                            .setTitle("Authentication")
                            .setMessage("You Need To Allow Biometric Authentication On This Device In order to Proceed Using This Application.")
                            .setIcon(R.mipmap.dev_ops_main)
                            .setCancelable(false)
                            .setPositiveButton("Ok,Lets Do", (dialogInterface12, i12) -> {
                                dialogInterface12.dismiss();
                                fingerprintAuthentication();
                            }).setNegativeButton("No,Exit Me", (dialogInterface1, i1) -> {
                                dialogInterface1.dismiss();
                                finish();
                                new SpeechClass(getApplicationContext(), "exited,Successfully");
                                Runtime.getRuntime().exit(0);

                            }).create().show();


                    ///
                })
                .create()
                .show();
        new MakeVibrator(this);
    }


    private void alertDialog() {
        //checking Value Of The COUNTER From the App Class If Is greater than Zero in
        //order to call appropriate function when the app starts
        if (COUNTER_ACTIVITY_STATE_MONITOR == 0) {
            alertingUserAfterExitOnStartOrOnFirstLaunch();
        } else if (COUNTER_ACTIVITY_STATE_MONITOR == 1) {
            continueUsingMainActivityForUserNotExited();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        COUNTER_ACTIVITY_STATE_MONITOR = 1;
    }

    public void continueUsingMainActivityForUserNotExited() {
        Toasty.custom(getApplicationContext(), "Welcome Back User", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
        animationDrawable.start();
        appCompatButton_start.setVisibility(View.VISIBLE);
        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_slide_in_top);
        appCompatButton_start.startAnimation(animation);
    }

    public void alertingUserAfterExitOnStartOrOnFirstLaunch() {
        //speech class initialisation
        new SpeechClass(getApplicationContext(), "Welcome to Developers Society Kenya");
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
                  /*  Toasty.custom(getApplicationContext(), "Login Account", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, Login.class));
                    CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");*/

                    startActivity(new Intent(MainActivity.this,LoginIndexMainPage.class));


                    return true;

                case R.id.account_creation:
                    Toasty.custom(getApplicationContext(), "Account Creation", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, Registration.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                    return true;

                case R.id.account_overview:
                    Toasty.custom(getApplicationContext(), "DevOps Overview Page", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, OverviewMainActivity.class));
                    return true;
                case R.id.account_share:
                    Toasty.custom(getApplicationContext(), "Share And Promote DevOps Society", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    Intent share_intent = new Intent(Intent.ACTION_SEND);
                    share_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    share_intent.setType("text/plain");
                    share_intent.putExtra(Intent.EXTRA_TEXT, "Hey Download DevOPS Society (Developers Society) App From Play store and Promote The Application Purposiveness @ShimitaDouglas!.");
                    startActivity(share_intent);
                    return true;
                case R.id.account_aboutdeveloper:
                  /*  progressDialog.setTitle("DEVELOPER");
                    progressDialog.setMessage("Fetching...");
                    progressDialog.show();*/

                    Toasty.custom(getApplicationContext(), "Developer Of DevOps Society Information", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, Developer.class));
                    CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                    return true;

                case R.id.forum:
                    Toasty.custom(getApplicationContext(), "Converse And Exchange Ideas", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.trending:
                    Toasty.custom(getApplicationContext(), "Trending In Technology", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.jobSection:
                    Toasty.custom(getApplicationContext(), "Post A Job And We Will Work On It", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.jobSectionDo:
                    Toasty.custom(getApplicationContext(), "Complete Client's Task And Get Paid ", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.account_exit:
                    new MaterialAlertDialogBuilder(MainActivity.this)
                            .setTitle("EXIT")
                            .setCancelable(false)
                            .setMessage("Do You Want To Exit From The App?")
                            .setIcon(R.drawable.ic_baseline_info_24)
                            .setPositiveButton("Yes, I Do ", (dialogInterface, i) -> {
                                Toasty.custom(getApplicationContext(), "DevOps App Shutting Down", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                                Runtime.getRuntime().exit(0);
                            })
                            .setNegativeButton("No, Lets Be Back", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                                Toasty.custom(getApplicationContext(), "Lets Continue ", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                            })
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
        Toasty.custom(getApplicationContext(), "A Must Know Hot Products From DevOPS !", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

        PopupMenu popupMenu_otherProducts = new PopupMenu(MainActivity.this, appCompatButton_start);
        popupMenu_otherProducts.setForceShowIcon(true);
        popupMenu_otherProducts.inflate(R.menu.other_devops_products);

        popupMenu_otherProducts.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation:
                    Toasty.custom(getApplicationContext(), "DevOPS_Navigator Apk", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.professionals:
                    Toasty.custom(getApplicationContext(), "DevOPS_Invincible(s) Apk", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.trackingDevice:
                    Toasty.custom(getApplicationContext(), "DevOPS_Tracker Apk", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.comrades:
                    Toasty.custom(getApplicationContext(), "Comrades_Power Apk", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.masenoMarketing:
                    Toasty.custom(getApplicationContext(), "Devops_Market Apk", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                    return true;

                case R.id.reverseEngineer:
                    Toasty.custom(getApplicationContext(), "DevOPS_Reverser Apk", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
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