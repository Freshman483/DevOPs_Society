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
        this.setTitle("Developer Activity Posting Data");
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

    public void functionPostPdfServiceToUsers(View view) {
        //toasting the clicked selection
        Toast.makeText(this, "Post Programming Software Selected", Toast.LENGTH_SHORT).show();
        //
        //begin coding here
        //migrating the activity to OverallODF class
        startActivity(new Intent(this, OverallServicePdfUpload.class));
        //animating the intent  migration
        CustomIntent.customType(this,"fadein-to-fadeout");
        //
        //end code
    }


}