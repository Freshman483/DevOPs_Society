package com.shimmita.devopssociety.mains;

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
import com.shimmita.devopssociety.R;

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
        //

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

            case R.id.refresh:

                new SpeechClass(this, "Refresh Successful");
                Toasty.custom(getApplicationContext(), "Refreshing...", R.drawable.ic_baseline_360_24, android.R.color.holo_green_dark, Toasty.LENGTH_SHORT, true, true).show();
                item.setChecked(!item.isChecked());
                new VibratorLowly(MainActivity.this);

                break;


            case R.id.helpMember:
                Toasty.custom(getApplicationContext(), "Help Message", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                item.setChecked(!item.isChecked());
                new VibratorLowly(MainActivity.this);

                //alert helping member either by reading the help message or self helping
                dialogAlertForHelpingMember();
                //
                break;
        }
        return true;

    }

    private void dialogAlertForHelpingMember() {
        String text = "DevOps society says i should read the message for you,should i?";

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
                            .setIcon(R.drawable.ic_baseline_message_text_message)
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

                    //start help fragment in the drawer Activity main
                    startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).putExtra("fragment", "help")
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
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
        materialAlertDialogBuilder.setNegativeButton(R.string.negBtn, (dialog, which) -> {
            //back the user to the drawer main activity since its the parent activity in the apoplication
            startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                   );
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
            //

        });
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
                    startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).putExtra("fragment", "login").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");


                    return true;

                case R.id.account_creation:
                    Toasty.custom(MainActivity.this, "Account Creation", R.drawable.ic_baseline_person_add2, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).putExtra("fragment", "register").
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
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

                    Toasty.custom(getApplicationContext(), "Developer Of DevOps Society Information", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).putExtra("fragment", "developer").
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
                            .setTitle("Navigate To Home")
                            .setCancelable(false)
                            .setMessage("you will be directed back to home menu where you will be given chance to either continue or indeed exit completely")
                            .setIcon(R.drawable.android2)
                            .setPositiveButton("Yes,Do ", (dialogInterface, i) -> {
                                Toasty.custom(MainActivity.this, "click on your choice ", R.drawable.ic_baseline_info_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                                //startActivity to back into Drawer Parent

                                startActivity(new Intent(MainActivity.this, DrawerMainStarter.class).
                                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtra("fragment", "exit").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                                //
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