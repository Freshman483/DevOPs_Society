package com.shimmita.devopssociety.mains;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.fragments_loggedin.ActiveMembersLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.DevelopersForumLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.JobsLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.LearningLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.LogoutLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.MembersRankingListLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.MessagingLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoggedInActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutLoggedInUser;
    NavigationView navigationViewLoggedInUser;
    BottomNavigationView bottomNavigationViewLoggedInUser;

    ActionBarDrawerToggle actionBarDrawerToggleLoggedInUser;
    Toolbar toolbar;
    Fragment fragmentSelectedLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        //setting the title for logged in window on action bar
        this.setTitle("Logged In Successfully");
        //

        //init of main variables
        drawerLayoutLoggedInUser = findViewById(R.id.drawerLayoutLoggedInUser);
        navigationViewLoggedInUser = findViewById(R.id.navigationViewLoggedInUser);
        bottomNavigationViewLoggedInUser = findViewById(R.id.bottomNavigationViewLoggedInUser);

        //initialisation of actionBarDrawerToggle
        actionBarDrawerToggleLoggedInUser = new ActionBarDrawerToggle(LoggedInActivity.this, drawerLayoutLoggedInUser, R.string.Open, R.string.Close);
        //

        //adding drawer listener to the drawerLayout and also enabling home icon navigation on the top of the screen or toolbar
        drawerLayoutLoggedInUser.setDrawerListener(actionBarDrawerToggleLoggedInUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //

        //making profile fragment the default on launch of the logged in activity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_UserLoggedIn_frameLayout, new ProfileLoggedInFragmentClass()).commit();
        //

        //adding listener to bottomNavigationViewUserLoggedIn
        bottomNavigationViewLoggedInUser.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.userLoggedInProfile:
                        fragmentSelectedLoggedIn = new ProfileLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.jobsLoggedIn:
                        fragmentSelectedLoggedIn = new JobsLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "jobs", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.messagingLoggedIn:
                        fragmentSelectedLoggedIn = new MessagingLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "messaging", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.logoutLoggedInUser:
                        fragmentSelectedLoggedIn = new LogoutLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "log out", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.LearningLoggedIn:
                        fragmentSelectedLoggedIn = new LearningLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "learning", Toast.LENGTH_SHORT).show();
                        break;
                }
                //return the selected fragment appropriately
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_UserLoggedIn_frameLayout, fragmentSelectedLoggedIn).commit();
                //
                return true;
            }
        });
        //


        //adding listener to navigationViewUserLoggedIn
        navigationViewLoggedInUser.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.developersLoggedInForum:

                        fragmentSelectedLoggedIn = new DevelopersForumLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "developers Forum", Toast.LENGTH_SHORT).show();

                        //closing the drawer after a navigation view item is selected
                        drawerLayoutLoggedInUser.closeDrawer(GravityCompat.START);
                        //
                        break;

                    case R.id.activeMembersLoggedIn:
                        fragmentSelectedLoggedIn = new ActiveMembersLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "active members at Developers society", Toast.LENGTH_SHORT).show();

                        //closing the drawer after a navigation view item is selected
                        drawerLayoutLoggedInUser.closeDrawer(GravityCompat.START);
                        //
                        break;

                    case R.id.membersLoggedInRankingList:
                        fragmentSelectedLoggedIn = new MembersRankingListLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "members ranking at developers society", Toast.LENGTH_SHORT).show();

                        //closing the drawer after a navigation view item is selected
                        drawerLayoutLoggedInUser.closeDrawer(GravityCompat.START);
                        //
                        break;

                }

                //return the appropriately selected fragment class
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_UserLoggedIn_frameLayout, fragmentSelectedLoggedIn).commit();
                //

                return true;
            }
        });
        //

        //this method works i.e adding nav view header programmatically is better becoz you can modify its features easily
        //from the target container holding the header i.e View 
        View view = navigationViewLoggedInUser.inflateHeaderView(R.layout.header_minor_details_user_logged_in_nav_view);
        TextView textView = view.findViewById(R.id.drawerUsername);
        textView.setText("Welcome Douglas");
        CircleImageView circleImageView=view.findViewById(R.id.drawerPicture);
        circleImageView.setImageResource(R.drawable.developer_image);

        //


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggleLoggedInUser.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}