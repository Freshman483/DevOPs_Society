package com.shimmita.devopssociety;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

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

        this.setTitle("DevOPS Society Home");


        AlertUserWelcomeHome();
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

                Toasty.custom(getApplicationContext(), "About Menu", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                item.setChecked(!item.isChecked());
                new VibratorLowly(MainActivity.this);

                return true;

            case R.id.refresh:

                new SpeechClass(this, "Refresh Successful");
                Toasty.custom(getApplicationContext(), "Refreshing...", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_SHORT, true, true).show();

                item.setChecked(!item.isChecked());

                new VibratorLowly(MainActivity.this);

                return true;

            case R.id.rating:

                Toasty.custom(getApplicationContext(), "Rate Us On PlayStore", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                item.setChecked(!item.isChecked());
                new VibratorLowly(MainActivity.this);

                return true;

            case R.id.helpMember:
                dialogAlertForHelpingMember();
                Toasty.custom(getApplicationContext(), "Help Message", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();

                item.setChecked(!item.isChecked());

                new VibratorLowly(MainActivity.this);
                return true;

            default:
                return false;
        }

    }

    private void dialogAlertForHelpingMember() {
        String text = "DevOps Society Says I Should Read The Message For You,Should I?";

        String helpMessage = getString(R.string.helpTextInquiry);

        new SpeechClass(this, text);
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Android Response\n")
                .setMessage(text)
                .setCancelable(false)
                .setIcon(R.drawable.android2)
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
                                Toasty.custom(getApplicationContext(), "Congratulations! Now You Can Use DevOPs Easily", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

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


    public void AlertUserWelcomeHome() {
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
                    Toasty.custom(getApplicationContext(), "Account Login", R.drawable.ic_baseline_account_login, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).putExtra("fragment", "login"));
                    CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                    return true;

                case R.id.account_creation:
                    Toasty.custom(MainActivity.this, "Account Creation", R.drawable.ic_baseline_person_add2, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).putExtra("fragment", "register"));
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

}