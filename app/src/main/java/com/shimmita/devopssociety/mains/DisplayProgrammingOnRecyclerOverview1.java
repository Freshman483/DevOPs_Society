package com.shimmita.devopssociety.mains;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.adapters.MyAdapter;

public class DisplayProgrammingOnRecyclerOverview1 extends AppCompatActivity {
    private static final String TAG = "DisplayProgrammingOnRecyclerOverview1";
    RecyclerView recyclerView;

    int[] imageDisplayConstructor;  //missing images RxJava,GO,Flutter,React,Angular,Rust,C#,Vb,Html
    int[] imageLocksConstructor;
    String[] titlesConstructor;
    String[] descriptionConstructor;
    MyAdapter myAdapter;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_programming_on_recycler_overview1);
        recyclerView = findViewById(R.id.recyclerView);

        animationDrawable=(AnimationDrawable)recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();


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
                        R.drawable.ic_baseline_offline_bolt_24,
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

        myAdapter = new MyAdapter(DisplayProgrammingOnRecyclerOverview1.this, imageDisplayConstructor, imageLocksConstructor, titlesConstructor, descriptionConstructor);
        recyclerView.setAdapter(myAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DisplayProgrammingOnRecyclerOverview1.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayProgrammingOnRecyclerOverview1.this));

        recyclerView.setKeepScreenOn(true);

       /* GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        the Activity was Designed To be Using gridView But preference became linear
        */


    }

}