package com.shimmita.devopssociety.mains;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shimmita.devopssociety.R;

import maes.tech.intentanim.CustomIntent;

public class PostingDataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_data);
        //setting the tittle of this activity to match the legitimacy of its relevance
        this.setTitle("Posting Data Activity");
        //

    }


    public void functionCareersInIT(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Careers In IT Selected", Toast.LENGTH_SHORT).show();
        //
        //starting intent launching OverallCareerMentors activity
        String key="intentData";
        String valueCareers="Careers";
        this.startActivity(new Intent(this, OverallCareerMentorsUpload.class)
                .putExtra(key,valueCareers));
        //
        //adding animation on the intent
        CustomIntent.customType(this, "fadein-to-fadeout");
        //

        //end code
    }

    public void functionCareerMentors(View view) {

        //toasting the clicked selection
        Toast.makeText(this, "Post Career Mentors In IT Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here
        //starting intent launching OverallCareerMentors activity
        String key="intentData";
        String valueMentors="CareerMentors";
        this.startActivity(new Intent(this, OverallCareerMentorsUpload.class)
                .putExtra(key,valueMentors));
        //
        //adding animation on the intent
        CustomIntent.customType(this, "fadein-to-fadeout");
        //

        //end code
    }

    public void functionProgrammingSoftware(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Programming Software Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionWebDevelopment(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post WebDevelopment Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionDatabaseManagement(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Database Management Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionMachineLearning(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Machine Learning Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionArtificialIntelligence(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Artificial Intelligence   Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionAndroidProgramming(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Android Programming  Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionIOSProgramming(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post IOS Programming  Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionCloudComputing(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Cloud Computing Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionContainerisationDockerOption(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Containerisation  Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionReverseEngineering(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Reverse Engineering  Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionPenetrationTesting(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Penetration Testing Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionBestIDES(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Best IDES Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionWindowsOperatingSystem(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Windows Operating System Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionDarkWebContent(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Dark Web Content Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionHackingTechniques(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Hacking Techniques Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionVirusTechniques(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Virus Techniques Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }

    public void functionLinuxOperatingSystem(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Linux Operating Systems Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here


        //end code
    }
}