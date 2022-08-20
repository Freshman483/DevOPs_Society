package com.shimmita.devopssociety;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class LoginIndexMainPage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference_for_access_database_fireStore;
    String userID;


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

    TextView username_from_firebase,
            passion_from_firebase,
            university_from_firebase,
            county_from_firebase,
            role_from_firebase, //isAdmin? 0/1
            online_status_from_phone;


    TextView string_members_learning_from_firebase, //same value with online counter from firebase
            string_jobs_counter_from_firebase,
            string_posted_jobs_counter_from_firebase,
            string_total_members_counter_from_firebase,
            string_online_members_counter_from_firebase,
            sting_email_display_from_firebase,
            string_message_indicator_firebase_decision;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_index_main_page);
        this.setTitle("DevOps User Profile");
        //init firebase services
        firebaseAuth = FirebaseAuth.getInstance();

        //

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
        //init strings

        /*
        username_from_firebase,
            passion_from_firebase,
            university_from_firebase,
            county_from_firebase,
            role_from_firebase, //isAdmin? 0/1
            online_status_from_phone;

            string_members_learning_from_firebase, //same value with online counter from firebase
            string_jobs_counter_from_firebase,
            string_posted_jobs_counter_from_firebase,
            string_total_members_counter_from_firebase,
            string_online_members_counter_from_firebase,
            string_message_indicator_firebase_decision;
        */

        username_from_firebase = findViewById(R.id.userNameDisplayText);
        passion_from_firebase = findViewById(R.id.passionStatusText);
        university_from_firebase = findViewById(R.id.universityDisplayText);
        county_from_firebase = findViewById(R.id.countyTextDisplay);
        role_from_firebase = findViewById(R.id.isAdminiStrator);
        online_status_from_phone = findViewById(R.id.onlineStatusPhone);

        string_members_learning_from_firebase = findViewById(R.id.membersLearningCounter); //will check its value from firebase under online path
        string_jobs_counter_from_firebase = findViewById(R.id.stringJobsCounterGettingJob);
        string_posted_jobs_counter_from_firebase = findViewById(R.id.stringJobsPostedCounter);
        string_total_members_counter_from_firebase = findViewById(R.id.counterMembersTotal);

        string_online_members_counter_from_firebase = findViewById(R.id.stringMembersOnlineCounter);//equal to members learning so equated
        string_online_members_counter_from_firebase.setText(string_members_learning_from_firebase.getText().toString());
        sting_email_display_from_firebase = findViewById(R.id.emailDisplay);


        //adding Listeners to CardViews
        card_view_account_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.custom(getApplicationContext(), "Account Profile", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

                functionDisplayModalSheetForOptions();
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
                Toasty.custom(getApplicationContext(), "Buy Me A Coffee ", R.drawable.ic_baseline_emoji_smile, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

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


        //checkingOnlineStatus
        onlineStatusPhone();

    }//end of the onCreate method

    public void functionDisplayModalSheetForOptions() {
        ClassShowAccountBottomSheetDialog classShowAccountBottomSheetDialog = new ClassShowAccountBottomSheetDialog();
        classShowAccountBottomSheetDialog.show(getSupportFragmentManager(), "show_dialog_account");
    }

    private void onlineStatusPhone() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            //
            online_status_from_phone.setTextColor(getResources().getColor(R.color.teal_700));
            online_status_from_phone.setText("Online");
            Toasty.custom(getApplicationContext(), "online", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_SHORT, true, true).show();

        } else {
            online_status_from_phone.setTextColor(getResources().getColor(R.color.black));
            online_status_from_phone.setText("Offline");
            Toasty.custom(getApplicationContext(), "offline", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_SHORT, true, true).show();
        }
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        onlineStatusPhone();
    }

    public static class ClassShowAccountBottomSheetDialog extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            Button button_superUser,
                    button_profile_picture,
                    button_logout,
                    button_other;

            View view = inflater.inflate(R.layout.show_account_profile_options, container, false);

            button_superUser = view.findViewById(R.id.upgrade_super_user);
            button_logout = view.findViewById(R.id.logout_user);
            button_profile_picture = view.findViewById(R.id.change_profile_picture);
            button_other = view.findViewById(R.id.change_Other_credentials);

            button_logout.setOnClickListener(view1 -> {

                Toasty.custom(getActivity().getApplicationContext(), "Logged Out Successfully", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                dismiss();
            });

            button_other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Other", Toast.LENGTH_SHORT).show();
                }
            });

            button_superUser.setOnClickListener(view12 -> {

                startActivity(new Intent(getActivity(), MakePayments.class));
                CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                dismiss();
            });

            return view;
        }
    }
}