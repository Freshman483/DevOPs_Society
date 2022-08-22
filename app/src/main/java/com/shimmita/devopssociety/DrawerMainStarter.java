package com.shimmita.devopssociety;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DrawerMainStarter extends AppCompatActivity {
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    Fragment fragmentSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main_starter);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView = (NavigationView) findViewById(R.id.navigationViewWindowDrawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //home fragment should be here(default pane)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new HomeFragmentClass()).commit();
        //


        //bottomNavigation View With Handlers
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_drawer_bottom_nav:
                        fragmentSelected = new HomeFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Help", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.browser_drawer_bottom_nav:
                        fragmentSelected = new BrowserFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Browse On The Internet Protected!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.devops_army_drawer_bottom_nav:
                        fragmentSelected = new DevOpsOfficialsFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "DevOPS Army Officials", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.technoTrends_drawer_bottom_nav:
                        fragmentSelected = new TechnologicalTrendsFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Explore Technological Trends", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.other_drawer_bottom_nav:
                        fragmentSelected = new OtherDevOpsProductsFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Other Powerful Devops Products", Toast.LENGTH_SHORT).show();
                        break;
                }

                //get support fragment be here for selected one
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, fragmentSelected).commit();
                //
                return true;
            }
        });
        //


        //navigationViewHandlersOfDrawer

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.help_drawer:
                        fragmentSelected = new HelpFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.developer_drawer:
                        fragmentSelected = new DeveloperFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Developer", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.privacy_policy_drawer:
                        fragmentSelected = new PrivacyAndPolicyFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Privacy and Policy", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.login_drawer:
                        fragmentSelected = new LoginAccountFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Login To Your Registered Account", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.create_account_drawer:
                        fragmentSelected = new CreateAccountFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Create New Account With Benefits!", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.exit_app_drawer:
                        fragmentSelected = new ExitAppFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Be Exited From The Application!", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.share_app_drawer:
                        fragmentSelected = new ShareAppFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Share App And Expand The Society", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.rate_app_drawer:
                        fragmentSelected = new RateAPPFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Directing App To Google PlayStore", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.chat_developer_drawer:
                        fragmentSelected = new ChatDeveloperFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Converse With The Developer For More", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.dark_theme_drawer:
                        Toast.makeText(DrawerMainStarter.this, "Dark Mode Enabled", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.more_settings_drawer:
                        fragmentSelected = new MoreSettingsFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Opening Extra Settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.support_drawer:
                        fragmentSelected = new BuyMeCoffeeFragmentClass();
                        Toast.makeText(DrawerMainStarter.this, "Keep Me Motivated", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                //return selected fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, fragmentSelected).commit();
                //
                return true;
            }
        });

        //


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}