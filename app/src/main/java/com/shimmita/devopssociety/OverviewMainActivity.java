package com.shimmita.devopssociety;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OverviewMainActivity extends AppCompatActivity {
    int[] imagesDisplayMainOverview;
    int[] imageLocksMainOverview;
    String[] descriptionTitleOverview;
    RecyclerView recyclerView;

    AdapterTwoMainOverview adapterTwoMainOverview;

    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_main);
        this.setTitle("Main Overview Window");

        recyclerView = findViewById(R.id.recyclerViewMainOverview);

        animationDrawable=(AnimationDrawable)recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        descriptionTitleOverview = new String[]
                {
                        "Software Engineering",
                        "Network Engineering",
                        "Web Development",
                        "Database Management",
                        "Machine Learning",
                        "Artificial Intelligence",
                        "Android Programming",
                        "IOS Programming",
                        "Cloud Computing",
                        "Containerisation",
                        "Reverse Engineering",
                        "Penetration Testing",
                        "Best IDEs Always",
                        "Linux OS",
                        "Windows OS",
                        "Dark Web",
                        "Hacking Techniques",
                        "Virus Techniques",
                        "Lets Chat"

                };
        imagesDisplayMainOverview = new int[]
                {
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.wordpress,
                        R.drawable.dropbox,
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.android2,
                        R.drawable.apple,
                        R.drawable.cloud,
                        R.drawable.docker,
                        R.drawable.ic_baseline_settings_24,
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.ic_baseline_notes_24,
                        R.drawable.linuxdoll,
                        R.drawable.windows,
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.ic_baseline_offline_bolt_24,
                        R.drawable.whatsappp

                };

        imageLocksMainOverview = new int[]
                {
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_open_24,
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
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_open_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                        R.drawable.ic_baseline_lock_24,
                };

        adapterTwoMainOverview = new AdapterTwoMainOverview(this, imagesDisplayMainOverview, imageLocksMainOverview, descriptionTitleOverview);
        recyclerView.setAdapter(adapterTwoMainOverview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);



    }
}