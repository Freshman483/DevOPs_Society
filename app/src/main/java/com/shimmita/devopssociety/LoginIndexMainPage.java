package com.shimmita.devopssociety;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginIndexMainPage extends AppCompatActivity {

    CardView card_view_account_profile,
            card_view_activity_profile,
            card_view_available_jobs,
            card_view_post_jobs,
            card_view_devops_members,
            card_view_devops_forum,
            card_view_whatsNew_Trending,
            card_view_contact_administrator,
            card_vie_support_developer;

    CircleImageView account_profile_picture,
            activity_profile_picture,
            activity_get_job_picture,
            lock_or_unlock_image_get_job,//depends on isAdmin? 0/1 lock/unlocked
            activity_post_job_picture,
            activity_members_picture,
            activity_devops_forum,
            activity_whats_new_picture,
            activity_contact_admin_picture,
            activity_support_developer_picture;

    String username_from_firebase,
            passion_from_firebase,
            university_from_firebase,
            county_from_firebase,
            role_from_firebase, //isAdmin? 0/1
            online_status_from_phone;


    String string_members_learning_from_firebase, //same value with online counter from firebase
            string_jobs_counter_from_firebase,
            string_posted_jobs_counter_from_firebase,
            string_total_members_counter_from_firebase,
            string_online_members_counter_from_firebase,
            string_message_indicator_firebase_decision;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_index_main_page);
        this.setTitle("DevOps User Profile");


    }
}