package com.shimmita.devopssociety;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayProgrammingOnRecyclerOverview1 extends AppCompatActivity {
    private static final String TAG = "DisplayProgrammingOnRecyclerOverview1";
    RecyclerView recyclerView;

    int[] imageDisplayConstructor;  //missing images RxJava,GO,Flutter,React,Angular,Rust,C#,Vb,Html
    int[] imageLocksConstructor;
    String[] titlesConstructor;
    String[] descriptionConstructor;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_programming_on_recycler_overview1);
        recyclerView = findViewById(R.id.recyclerView);

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
                        "ultimate python doctor of programming languages",
                        "ultimate Java Programming for android app",
                        "reactive java library for concurrency",
                        "javascript language for All",
                        "learn Kotlin Language for Android",
                        "lets Learn google Go language",
                        "cross-platform applications ",
                        "dart framework with extended cross-platform",
                        "javascript library for Building UI",
                        "build cross-platform mobile apps",
                        "full-stack web application framework",
                        "a server side scripting language",
                        "general purpose language",
                        "Write OS and Microcontrollers",
                        "C superset for System Apps",
                        "System Oriented Language",
                        "microsoft Based Language",
                        "Develop GUI Windows OS Apps",
                        "Build Iphone IOS Applications ",
                        "a superset of Javascript Language",
                        "generate and format user Interface"

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