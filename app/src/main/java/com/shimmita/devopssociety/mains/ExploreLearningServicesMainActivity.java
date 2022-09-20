package com.shimmita.devopssociety.mains;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.adapters.AdapterExploreLearningServices;

public class ExploreLearningServicesMainActivity extends AppCompatActivity {
    private static final String TAG = "OverviewLearningService";
   public static String LOCK_SERVICES;
    //making the default value of the reason which will be passed into the constructor of the adapterExploreLearningServices
    //to upgrade account;
    String default_reason="upgrade account";
    //this simply means if no reason is available to replace the default then the user is probably using not upgraded account;
    String REASON=default_reason;
    //

    int[] imagesDisplayMainOverview;
    int[] imageLocksMainOverview;
    String[] descriptionTitleOverview;
    RecyclerView recyclerView;

    AdapterExploreLearningServices adapterExploreLearningServices;

    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_main);
        this.setTitle("Learning Services Offered");

        recyclerView = findViewById(R.id.recyclerViewMainOverview);

        animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        //call a function that will checkIntent data from other intents
        callFunctionCheckIntentDataFromOtherIntents();
        //


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
                        "Virus Techniques"

                };
        imagesDisplayMainOverview = new int[]
                {
                        R.drawable.hacking7,
                        R.drawable.hacking4,
                        R.drawable.wordpress,
                        R.drawable.dropbox,
                        R.drawable.machine_learning,
                        R.drawable.hacking8,
                        R.drawable.android2,
                        R.drawable.apple,
                        R.drawable.cloud,
                        R.drawable.docker,
                        R.drawable.ic_baseline_settings_24,
                        R.drawable.hacking2,
                        R.drawable.ic_baseline_notes_24,
                        R.drawable.linuxdoll,
                        R.drawable.windows,
                        R.drawable.hacking5,
                        R.drawable.hacker_masked1,
                        R.drawable.virus,

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


        /*//call a function that will populate data onto the recyclerView from the adapter class;
        callFunctionPopulateDataOnRecyclerView();
        //*/

        //creating a new thread for the population of tree data  on the recyclerView be effective since the application
        //is doing too much work on its main thread to populate the data pon to the recyclerView

        new Thread(new Runnable() {
            @Override
            public void run() {
                callFunctionPopulateDataOnRecyclerView();
            }
        }).start();
        //

    }


    //function to check the data from other intents
    private void callFunctionCheckIntentDataFromOtherIntents() {
        LOCK_SERVICES = getIntent().getStringExtra("data_from_intent_launch");

        //lets log the data of lock service
        Log.d(TAG, "callFunctionCheckIntentDataFromOtherIntents: LOCK_SERVICE->:"+LOCK_SERVICES);
        //

        //checking conditions LOCK_SERVICES value
        //checking nullity presence in the constant LOCK_SERVICE
        if (LOCK_SERVICES==null)
        {
            //lock service is null thus can lead to null pointer exception thus
            //we have to some data into the Constant LOCK_SERVICE other than it being null;
            LOCK_SERVICES="none replacement (no data)";
        }
        //the LOCK_SERVICE is not null thus contains some data



    }


    //function populate data on to the recyclerView
    private void callFunctionPopulateDataOnRecyclerView() {
        //running if statement to verify that data intent was indeed came from main activity OverView selection thus
        //lock all the services because User is in preview or overView mode and services are only open in the Learning Fragment after the User Is Logged In;

        if (LOCK_SERVICES.equals("lock_services_intent_is_from_main_over_view"))
        {
            //set reason Of Constant REASON to you are currently in overview mode,login and select learning to open services
            REASON="you are currently in overview mode,login and select learning to open service";
            //

            //start a for loop to lock all the services since the user is in overView Mode thus cannot launch
            //the service but preview the service
            for (int i=0;i<= imageLocksMainOverview.length-1;i++)
            {
                //if the image locking  status is open, just set it locked with a blue lock
                if (imageLocksMainOverview[i]==R.drawable.ic_baseline_lock_open_24)
                {
                    imageLocksMainOverview[i]=R.drawable.ic_baseline_lock_24;
                }
            }

            //

        }

        //recycler will be populated only if some conditions are met
        adapterExploreLearningServices = new AdapterExploreLearningServices(this, imagesDisplayMainOverview, imageLocksMainOverview, descriptionTitleOverview,REASON);
        recyclerView.setAdapter(adapterExploreLearningServices);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        //
    }
    //

}