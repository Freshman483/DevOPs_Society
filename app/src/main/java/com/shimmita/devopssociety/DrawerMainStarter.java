package com.shimmita.devopssociety;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.Executor;

import es.dmoral.toasty.Toasty;

public class DrawerMainStarter extends AppCompatActivity {

    private static final int CODE = 202202;
    BiometricManager biometricManager_checking_fingerprint_support;
    Executor executor;
    BiometricPrompt biometricPrompt_functionality_implementation;
    BiometricPrompt.PromptInfo promptInfo_dialog;

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    Fragment fragmentSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main_starter);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView = (NavigationView) findViewById(R.id.navigationViewWindowDrawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //home fragment should be here(default pane)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new PrivacyAndPolicyFragmentClass()).commit();
        //


        //bottomNavigation View With Handlers

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_drawer_bottom_nav:
                        fragmentSelected = new HomeFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "home", R.drawable.ic_baseline_home_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        break;

                    case R.id.browser_drawer_bottom_nav:
                        fragmentSelected = new BrowserFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "google", R.drawable.ic_baseline_public_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                        break;

                    case R.id.devops_army_drawer_bottom_nav:
                        fragmentSelected = new DevOpsOfficialsFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "see our official members", R.drawable.ic_baseline_support_agent_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        break;

                    case R.id.technoTrends_drawer_bottom_nav:
                        fragmentSelected = new TechnologicalTrendsFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "technological trends", R.drawable.ic_baseline_title_tecno_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        break;

                    case R.id.other_drawer_bottom_nav:
                        fragmentSelected = new OtherDevOpsProductsFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "view our other applications!", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        break;
                }

                //get support fragment be here for selected one
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, fragmentSelected).commit();
                //
                return true;
            }
        });
        //


        //navigationViewHandlersOfDrawer

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.help_drawer:
                        fragmentSelected = new HelpFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "help and about information", R.drawable.ic_baseline_help_center_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.developer_drawer:
                        fragmentSelected = new DeveloperFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "developer profile", R.drawable.ic_baseline_emoji_smile, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.privacy_policy_drawer:
                        fragmentSelected = new PrivacyAndPolicyFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "privacy and policy information", R.drawable.ic_baseline_privacy_tip_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.login_drawer:
                        fragmentSelected = new LoginAccountFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "login into your DevOps account", R.drawable.ic_baseline_account_login, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.create_account_drawer:
                        fragmentSelected = new CreateAccountFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "create new DevOps account", R.drawable.ic_round_person_add, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.exit_app_drawer:
                        fragmentSelected = new ExitAppFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "exit from running the application", R.drawable.ic_baseline_exit, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.share_app_drawer:
                        fragmentSelected = new ShareAppFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "share app to expand DevOps", R.drawable.ic_baseline_share, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.rate_app_drawer:
                        fragmentSelected = new RateAppFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "opening google playStore....", R.drawable.ic_baseline_public_24, R.color.purple_200, Toasty.LENGTH_SHORT, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.chat_developer_drawer:
                        fragmentSelected = new ChatDeveloperFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "converse with the developer for more", R.drawable.ic_baseline_public_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.dark_theme_drawer:
                        Toasty.custom(DrawerMainStarter.this, "theme changed successfully", R.drawable.ic_baseline_dark_theme_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.more_settings_drawer:
                        fragmentSelected = new MoreSettingsFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "opening bundled extra app settings", R.drawable.ic_baseline_settings_applications_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.support_drawer:
                        fragmentSelected = new BuyMeCoffeeFragmentClass();
                        Toasty.custom(DrawerMainStarter.this, "keep me motivated", R.drawable.ic_baseline_emoji_smile, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                //return selected fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, fragmentSelected).commit();
                //
                return true;
            }
        });
        //

        //callAuthenticate
        // fingerprintAuthentication();
        //

        //try getting intent results from Other Intents which wants to launch a particular fragment menu
        //use bool function so that the menu of the fragments become selected too;

        functionCheckIfAnyFragmentIsCalledFromExternalActivities();

        //
    }

    private void functionCheckIfAnyFragmentIsCalledFromExternalActivities() {

        String valueReturnedFromSpecificIntent = getIntent().getStringExtra("fragment");
        if (valueReturnedFromSpecificIntent != null) {
            switch (valueReturnedFromSpecificIntent) {
                case "register":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new CreateAccountFragmentClass()).commit();
                    break;
                case "login":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new LoginAccountFragmentClass()).commit();
                    break;
                case "developer":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new DeveloperFragmentClass()).commit();
                    break;
                case "help":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new HelpFragmentClass()).commit();
                    break;
                case "home":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new HomeFragmentClass()).hashCode();
                    break;
                case "exit":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new ExitAppFragmentClass()).commit();
                    break;

            }
        }
    }

    private void functionSpeechCallWelcomeAndVibratorLowly() {
        new SpeechClass(DrawerMainStarter.this, "Welcome To Developers Society");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            new VibratorLowly(DrawerMainStarter.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //fingerprint encryption
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


        biometricPrompt_functionality_implementation = new BiometricPrompt(DrawerMainStarter.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                // Toast.makeText(MainActivity.this, "Cannot Proceed!->" + errString, Toast.LENGTH_LONG).show();

                //call functionBypass
                alertBypassFingerprint();
                //
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Toasty.custom(getApplicationContext(), "Authentication Successful, Welcome", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                //begin program here main function starter
                functionSpeechCallWelcomeAndVibratorLowly();
                //

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

        //speech access denied
        new SpeechClass(DrawerMainStarter.this, "access,denied");
        //


        //show alert dialog of fingerPrintBypass
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

                    new MaterialAlertDialogBuilder(DrawerMainStarter.this)
                            .setTitle("Authentication")
                            .setMessage("You Need To Allow Biometric Authentication On This Device In order to Proceed Using This Application.")
                            .setIcon(R.mipmap.dev_ops_main)
                            .setCancelable(false)
                            .setPositiveButton("Ok,Lets Do It", (dialogInterface12, i12) -> {
                                dialogInterface12.dismiss();
                                fingerprintAuthentication();
                            }).setNegativeButton("No,Exit Me", (dialogInterface1, i1) -> {
                                new SpeechClass(getApplicationContext(), "exited,Successfully");
                                dialogInterface1.dismiss();
                                finish();
                                Runtime.getRuntime().exit(0);

                            }).create().show();


                    ///
                })
                .create()
                .show();
        new MakeVibrator(this);
    }
//

}