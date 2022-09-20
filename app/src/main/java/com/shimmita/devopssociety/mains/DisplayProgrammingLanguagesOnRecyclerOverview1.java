package com.shimmita.devopssociety.mains;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.adapters.MyAdapterDisplayProgrammingLanguages;

public class DisplayProgrammingLanguagesOnRecyclerOverview1 extends AppCompatActivity {
    private static final String TAG = "DisplayProgrammingLanguagesOnRecyclerOverview1";
    RecyclerView recyclerView;

    int[] imageDisplayConstructor;  //missing images RxJava,GO,Flutter,React,Angular,Rust,C#,Vb,Html
    int[] imageLocksConstructor;
    String[] titlesConstructor;
    String[] descriptionConstructor;
    MyAdapterDisplayProgrammingLanguages myAdapterDisplayProgrammingLanguages;
    AnimationDrawable animationDrawable;
    String CHECK_LOCK_FROM_INTENT;
    //will be the reason why the programming languages are locked i.e ur in preview mode,or u need to upgrade account.
    String REASON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_programming_on_recycler_overview1);
        recyclerView = findViewById(R.id.recyclerView);

        animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();

        //a function to check the lock_from_intent
        functionCheckLock();
        //


        titlesConstructor = new String[]{
                "Python",
                "Java",
                "Rxjava",
                "Javascript",
                "Kotlin",
                "Go",
                "Dart",
                "Flutter",
                "React.js",
                "React Native",
                "Angular",
                "Php",
                "Ruby",
                "Rust",
                "C++",
                "C",
                "C#",
                "Visual Basic",
                "Swift",
                "Typescript",
                "Css And Html",
        };
        descriptionConstructor = new String[]
                {
                        "python doctor of programming",
                        "java programming for android",
                        "java library for concurrency",
                        "javascript language for all",
                        "kotlin language for android",
                        "lets learn google GO language",
                        "cross-platform applications ",
                        "an extended dart framework",
                        "javascript library for building UI",
                        "build cross-platform mobile aps",
                        "full-stack web dev framework",
                        "server side scripting language",
                        "general purpose language",
                        "write OS and microcontrollers",
                        "c superset for system apps",
                        "system oriented language",
                        "microsoft based language",
                        "develop GUI windows os apps",
                        "build iphone ios applications ",
                        "superset of javascript language",
                        "generate and format user interface"

                };
        imageDisplayConstructor = new int[]
                {
                        R.drawable.python,
                        R.drawable.java,
                        R.drawable.rxjava,
                        R.drawable.javascript,
                        R.drawable.kotlin,
                        R.drawable.go,
                        R.drawable.dart,
                        R.drawable.flutter2,
                        R.drawable.reactjs,
                        R.drawable.react_native2,
                        R.drawable.angular,
                        R.drawable.php,
                        R.drawable.ruby,
                        R.drawable.rust,
                        R.drawable.cpp,
                        R.drawable.c,
                        R.drawable.c_sharp,
                        R.drawable.visual_basic1,
                        R.drawable.swift,
                        R.drawable.typescript,
                        R.drawable.html,
                };
        imageLocksConstructor = new int[]
                {
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                };


        //create a function to load the data to the recyclerView
        //this enhances the code being clean
        functionLoadDataOnToTheRecyclerView();
        //
    }

    private void functionLoadDataOnToTheRecyclerView() {
        //BEGIN

        //basing from the value of CHECK_LOCK_FROM_INTENT we control the locking mode of the programming languages;
        //this will going be achieved putting the lock in the imageLocks constructor object in a loop

        //this handles the Put Extra from explore programming world
        if (CHECK_LOCK_FROM_INTENT.equals("locked_from_explore_programming_world")) {

            //setting reason to you are in preview mode, not allowed to open;
            //this one will be passed into the constructor of the myAdapterDisplayProgrammingLanguages which will then be displayed as toast to why the programming languages locked
            REASON = "you are in preview mode, not allowed to open ";
            //

            //generation of ForLoop To Put locks on the programming Languages

            for (int i = 0; i <= imageLocksConstructor.length - 1; i++) {
                if (imageLocksConstructor[i] == R.drawable.ic_baseline_lock_open_24) {
                    imageLocksConstructor[i] = R.drawable.ic_baseline_lock_24;
                }
            }

        }
        //
        //this handles Put Extra from Explore services(SOFTWARE)
        else if (CHECK_LOCK_FROM_INTENT.equals("software_languages")) {
            //here will leave locking status of the imageLockConstructor default as it is
            //also here we will control the locks in case the user account is upgraded to super account from the normal account
            //will check the value of account role and if it happens that the account is super or premium then remove the locking on the programming languages
            //appropriately
            //TODO: employ account checking role object here to determine if the user should open all the programming languages without the blue lock by looping and removing the locks
            // the same procedure used to lock the languages of explore programming world will be used to open the images. decide this scenerio at will

            //lets set the value of REASON to upgrade your account to premium account to open;
            REASON="upgrade your account to premium account to open ";
            //

        }
        //


        //adapter and recyclerView will obtain a data after some conditions are met to control their display behaviour and reasons too
        //basing on the constant CHECK_LOCK_FROM_INTENT and REASON
        myAdapterDisplayProgrammingLanguages = new MyAdapterDisplayProgrammingLanguages(DisplayProgrammingLanguagesOnRecyclerOverview1.this, imageDisplayConstructor, imageLocksConstructor, titlesConstructor, descriptionConstructor, REASON);
        recyclerView.setAdapter(myAdapterDisplayProgrammingLanguages);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DisplayProgrammingLanguagesOnRecyclerOverview1.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayProgrammingLanguagesOnRecyclerOverview1.this));

        recyclerView.setKeepScreenOn(true);

       /* GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        the Activity was Designed To be Using gridView But preference became linear
        */

        //END
    }

    @SuppressLint("LongLogTag")
    private void functionCheckLock() {
        String dataFromGetIntent = getIntent().getStringExtra("data_from_intent_launch");
        //checking if the data is null and set it to another name to avoid null pointer exception
        //which causes the application crush,very hectic to the user it will gonna be!
        if (dataFromGetIntent == null) {
            //lets log the value of dataFromGetIntent to se its content
            Log.d(TAG, "\nfunctionCheckLock: dataFromGetIntent: " + dataFromGetIntent);
            //

            //lets set the data to a none string value, its not equal to null value
            dataFromGetIntent = "none";
            //set the constant CHECK_LOCK_FROM_INTENT to new data after after modifying its value from null
            CHECK_LOCK_FROM_INTENT = dataFromGetIntent;
            //
        }
        //the data of dataFromGetIntent is not null it must be containing some value which needs to be adhered to with
        //an appropriate response
        else {
            //lets log the value of dataFromGetIntent to se its content
            Log.d(TAG, "\nfunctionCheckLock: dataFromGetIntent: " + dataFromGetIntent);
            //

            //directly set the dataFromGetIntent to the Constant CHECK_LOCK_FROM_INTENT since it is no null
            CHECK_LOCK_FROM_INTENT = dataFromGetIntent;
            //
        }


    }

}