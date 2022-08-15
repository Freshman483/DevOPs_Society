package com.shimmita.devopssociety;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

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

    FloatingActionButton floatingActionButtonLoginMain;

    CircleImageView account_profile_picture,
            activity_profile_picture,
            activity_get_job_picture,
            lock_or_unlock_image_get_job,//depends on isAdmin? 0/1 lock/unlocked
            activity_post_job_picture,
            activity_members_picture,
            activity_devops_forum_picture,
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
        //init cards
        card_view_account_profile = (CardView) findViewById(R.id.cardviewAccountProfile);
        card_view_activity_profile = (CardView) findViewById(R.id.cardviewActivityProfile);
        card_view_available_jobs = (CardView) findViewById(R.id.cardViewAvaiableGetJobs);
        card_view_post_jobs = (CardView) findViewById(R.id.cardViewPostedJobs);
        card_view_devops_members = (CardView) findViewById(R.id.cardViewDevOpsMembers);
        card_view_devops_forum = (CardView) findViewById(R.id.cardViewDevOpsForum);
        card_view_whatsNew_Trending = (CardView) findViewById(R.id.cardViewWhatsNewOrTrending);
        card_view_contact_administrator = (CardView) findViewById(R.id.cardViewContactAdministrator);
        card_vie_support_developer = (CardView) findViewById(R.id.cardViewSupportDeveloper);

        //init floatingActionButton
        floatingActionButtonLoginMain = (FloatingActionButton) findViewById(R.id.floatingActionButton_main_login);

        //init CircleImages
        account_profile_picture = (CircleImageView) findViewById(R.id.profilePicture);
        activity_profile_picture = (CircleImageView) findViewById(R.id.circleImageViewActivityProfile);
        activity_get_job_picture = (CircleImageView) findViewById(R.id.circleImageViewGetJobs);
        lock_or_unlock_image_get_job = (CircleImageView) findViewById(R.id.lockOrOpenGettingJobs);
        activity_post_job_picture = (CircleImageView) findViewById(R.id.circleImageViewPostJob);
        activity_members_picture = (CircleImageView) findViewById(R.id.circleImageViewMembers);
        activity_devops_forum_picture = (CircleImageView) findViewById(R.id.circleImageViewForum);
        activity_whats_new_picture = (CircleImageView) findViewById(R.id.circleImageViewTrendingInIT);
        activity_contact_admin_picture = (CircleImageView) findViewById(R.id.circleImageViewContactAdmin);
        activity_support_developer_picture = (CircleImageView) findViewById(R.id.circleImageViewSupportDeveloper);


        //adding Listeners to CardViews

        card_view_account_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "Account Profile", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_view_activity_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "Activity Profile", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_view_available_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "Displaying Available Jobs", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_view_post_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toasty.custom(getApplicationContext(), "Post Job To The Society Lions!", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_view_devops_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "View Members Of The Society", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_view_devops_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "Lets You Exchange Ideas And Promote Ideologies", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_view_whatsNew_Trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "View The Trending Works Of Technology", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_view_contact_administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toasty.custom(getApplicationContext(), "Contact For Help And Support If Any", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        card_vie_support_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "Buy Me A Coffee ",R.drawable.ic_baseline_emoji_smile, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

            }
        });


        //floating Action Button onclick listener
        floatingActionButtonLoginMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenuMessage = new PopupMenu(LoginIndexMainPage.this, floatingActionButtonLoginMain);
                popupMenuMessage.inflate(R.menu.menu_msg_icon_clicked_floating);
                popupMenuMessage.setForceShowIcon(Boolean.parseBoolean("true"));

                popupMenuMessage.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.readMessage:

                                Toasty.custom(getApplicationContext(), "Opening Message...", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_SHORT, true, true).show();

                                return true;

                            case R.id.replyMessage:

                                Toasty.custom(getApplicationContext(), "Reply Message", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

                                return true;

                            case R.id.deleteMessage:

                                Toasty.custom(getApplicationContext(), "Delete Received Message(s)", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

                                return true;

                            default:
                                return false;
                        }

                    }
                });

                popupMenuMessage.show();
            }
        });


        //end of the oncreate method
    }


    //userEntry AlertDialog
    private void alertUserOnEntryDialog(String username) {
        new AlertDialog.Builder(LoginIndexMainPage.this)
                .setTitle("Highly Welcomed")
                .setMessage("\t\t\t\t" + username + "\n\nExplore More Functionalities Which Have Been  Provided By Developers Society Team " +
                        "to Ensure That Their Members Feel Proud Of Being in This Society. If Possible Refer Any Of Your Friends " +
                        "Perusing Any Field Of Computing and Let Their Frustrations Be On Diminish!")
                .setIcon(R.drawable.ic_baseline_emoji_smile)
                .create().show();
    }
}