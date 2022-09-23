package com.shimmita.devopssociety.mains;

import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_ACCOUNT_TYPE;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_BUNDLE_DATA_HEADER_LOGGED_PROFILE;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_COUNTY;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_EMAIL;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_IMAGE_PATH;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_PASSION;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_PHONE;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_UNIVERSITY;
import static com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass.KEY_USERNAME;
import static com.shimmita.devopssociety.mains.Registration.FIREBASE_USER_COLLECTION;
import static com.shimmita.devopssociety.mains.Registration.PROFILE_IMAGE_PATH_IN_RDB;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.fragments_loggedin.ActiveMembersLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.DevelopersForumLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.JobsLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.LearningLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.LogoutLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.MembersRankingListLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.MessagingLoggedInFragmentClass;
import com.shimmita.devopssociety.fragments_loggedin.ProfileLoggedInFragmentClass;
import com.shimmita.devopssociety.general_alerts.ClassBottomSheetShow;
import com.shimmita.devopssociety.modal.RetrieveFirebaseCredentialsFromFirestore;
import com.shimmita.devopssociety.modal.RetrieveImageLinksFromRealtimeDB;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoggedInActivity extends AppCompatActivity {
    private static final String TAG = "LoggedInActivity";

    //firebase globals
    ProgressDialog progressDialog;
    Handler handler;
    //firebase Globals
    String firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DocumentReference documentReference;
    DatabaseReference databaseReference;
    //


    DrawerLayout drawerLayoutLoggedInUser;
    NavigationView navigationViewLoggedInUser;
    BottomNavigationView bottomNavigationViewLoggedInUser;

    ActionBarDrawerToggle actionBarDrawerToggleLoggedInUser;
    Toolbar toolbar;
    Fragment fragmentSelectedLoggedIn;
    //
    //values for drawer navView
    CircleImageView circleImageViewDrawerPicture;
    TextView textViewDrawerUsername,
            textViewDrawerUniversity,
            textViewDrawerCounty,
            textViewDrawerPassion,
            textViewDrawerEmail,
            textViewDrawerPhoneNumber,
            textViewDrawerAccountRole;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Log.d(TAG, "onCreate: LoggedIn");

        //setting the title for logged in window on action bar
        this.setTitle("Logged In Successfully");
        //
        //init of firebase Globals
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = null;
        //firebaseStorage and Storage reference init;
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_UserLoggedIn_frameLayout, new ProfileLoggedInFragmentClass()).commitNow();
        //setting the default title to account profile since it is the home profile by default on the loggedInActivity
        setTitleOfLoggedInMatchingTheCurrentFragment("Account Profile");
        //

        //adding listener to bottomNavigationViewUserLoggedIn
        bottomNavigationViewLoggedInUser.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.userLoggedInProfile:
                        //setting the title of the parent to profile
                        setTitleOfLoggedInMatchingTheCurrentFragment("Account Profile");
                        //
                        fragmentSelectedLoggedIn = new ProfileLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.jobsLoggedIn:
                        //setting the title of the parent to profile
                        setTitleOfLoggedInMatchingTheCurrentFragment("Jobs Profile");
                        //
                        fragmentSelectedLoggedIn = new JobsLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "jobs", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.messagingLoggedIn:
                        //setting the title of the parent to profile
                        setTitleOfLoggedInMatchingTheCurrentFragment("Message(s) Profile");
                        //
                        fragmentSelectedLoggedIn = new MessagingLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "messaging", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.logoutLoggedInUser:
                        //setting the title of the parent to profile
                        setTitleOfLoggedInMatchingTheCurrentFragment("Exit Profile");
                        //
                        fragmentSelectedLoggedIn = new LogoutLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "log out", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.LearningLoggedIn:
                        //setting the title of the parent to profile
                        setTitleOfLoggedInMatchingTheCurrentFragment("Explore Learning Services Offered");
                        //
                        fragmentSelectedLoggedIn = new LearningLoggedInFragmentClass();
                        Toast.makeText(LoggedInActivity.this, "learning", Toast.LENGTH_SHORT).show();
                        break;
                }
                //return the selected fragment appropriately
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_UserLoggedIn_frameLayout, fragmentSelectedLoggedIn).commitNow();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_UserLoggedIn_frameLayout, fragmentSelectedLoggedIn).commitNow();
                //

                return true;
            }
        });
        //

        //this method works i.e adding nav view header programmatically is better becoz you can modify its features easily
        //from the target container holding the header i.e View
        View view = navigationViewLoggedInUser.inflateHeaderView(R.layout.header_minor_details_user_logged_in_nav_view);
        //initialisation of nav drawer header values
        circleImageViewDrawerPicture = view.findViewById(R.id.drawerPicture);
        textViewDrawerUsername = view.findViewById(R.id.drawerUsername);
        textViewDrawerEmail = view.findViewById(R.id.drawerEmail);
        textViewDrawerCounty = view.findViewById(R.id.drawerCounty);
        textViewDrawerPassion = view.findViewById(R.id.drawerPassion);
        textViewDrawerPhoneNumber = view.findViewById(R.id.drawerPhoneNumber);
        textViewDrawerAccountRole = view.findViewById(R.id.drawerAccountType);
        textViewDrawerUniversity=view.findViewById(R.id.drawerUniversity);
        //

        //call a function which will determine if we have to fetch data from the firestore again in case data
        //received from the profile loggedIn  contains any null. if no null set the data received through fragment manager
        callFunctionDecideFetchingDataForDrawerHeaderFromFireStore();
        //

        //end of onCreateMethod
    }

    private void callFunctionDecideFetchingDataForDrawerHeaderFromFireStore() {
        //getting the data by use of Fragment result listener from the profileLoggedInBundle
        getSupportFragmentManager().setFragmentResultListener(KEY_BUNDLE_DATA_HEADER_LOGGED_PROFILE, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String imagePath,username,phone,county,email,university,passion,accountType;

                imagePath=result.getString(KEY_IMAGE_PATH);
                username= result.getString(KEY_USERNAME);
                phone= result.getString(KEY_PHONE);
                county=result.getString(KEY_COUNTY);
                email= result.getString(KEY_EMAIL);
                university= result.getString(KEY_UNIVERSITY);
                passion= result.getString(KEY_PASSION);
                accountType=result.getString(KEY_ACCOUNT_TYPE);

                //calling function that will either data on the drawer header views or downloading the data from fireStore

                callFunctionSetDataDrawerHeaderOrFetchDataFromFiresTore(imagePath,username,phone,county,email,university,passion,accountType);
                //

            }
        });
        //

    }

    private void callFunctionSetDataDrawerHeaderOrFetchDataFromFiresTore(String imagePath, String username, String phone, String county, String email, String university, String passion, String accountType) {
        //using text utils to check null data and if no null set the views with data else fetch from the Cloud
        if (TextUtils.isEmpty(imagePath)||TextUtils.isEmpty(username)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(county)||TextUtils.isEmpty(email)||
        TextUtils.isEmpty(university)||TextUtils.isEmpty(passion)||TextUtils.isEmpty(accountType))
        {
            //null data present call function to fetching the data from the cloudFireStore
            callFunctionDownloadDataFromFirestore();
            //
        }
        else
        {
            //set drawer header Views with the data obtained from the profile logged in via bundle using fragment manager
            //setting the values into the navigation drawer header since all the data we have fetched it
            //drawer picture
            Glide.with(LoggedInActivity.this).load(imagePath).into(circleImageViewDrawerPicture);
            //drawerUsername
            textViewDrawerUsername.setText(username.toUpperCase(Locale.ROOT));
            //drawerCounty
            textViewDrawerCounty.setText(county);
            //University
            textViewDrawerUniversity.setText(university);
            //passion
            textViewDrawerPassion.setText(passion);
            //email
            textViewDrawerEmail.setText(email);
            //phoneNumber
            textViewDrawerPhoneNumber.setText(phone);
            //account type or role
            textViewDrawerAccountRole.setText(accountType);
            //
            //

        }
        //


    }

    private void callFunctionDownloadDataFromFirestore() {
        //init of collection,document reference and firebase user of the currently logged in
        firebaseUser = firebaseAuth.getCurrentUser().getUid();
        //controlling firebase Auth null issues
       /* if (firebaseUser == null) {
            return;
        }*/
        //
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection(FIREBASE_USER_COLLECTION).document(firebaseUser);


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    //Creating a bundle object that will help in sending the data obtained from the server (Firebase) into the profile fragment
                    Bundle bundle = new Bundle();
                    //
                    Log.d(TAG, "onSuccess: ValueOF Snapshot->" + documentSnapshot.getData());
                    RetrieveFirebaseCredentialsFromFirestore retrieveFirebaseCredentialsFromFirestore = documentSnapshot.toObject(RetrieveFirebaseCredentialsFromFirestore.class);
                    //returns converted value from firebase
                    Log.d(TAG, "\nonSuccess: Username->" + retrieveFirebaseCredentialsFromFirestore.getUsername());
                    Log.d(TAG, "\nonSuccess: University->" + retrieveFirebaseCredentialsFromFirestore.getUniversity());
                    Log.d(TAG, "\nonSuccess: County->" + retrieveFirebaseCredentialsFromFirestore.getCounty());
                    Log.d(TAG, "\nonSuccess: Password->" + retrieveFirebaseCredentialsFromFirestore.getPassword());
                    Log.d(TAG, "\nonSuccess: Passion->" + retrieveFirebaseCredentialsFromFirestore.getPassion());
                    Log.d(TAG, "\nonSuccess: Email->" + retrieveFirebaseCredentialsFromFirestore.getEmail());
                    Log.d(TAG, "\nonSuccess: Role->" + retrieveFirebaseCredentialsFromFirestore.getRole());
                    Log.d(TAG, "\nonSuccess: PhoneNumber->" + retrieveFirebaseCredentialsFromFirestore.getPhoneNumber());
                    Log.d(TAG, "\nonSuccess: Occupation->" + retrieveFirebaseCredentialsFromFirestore.getOccupation() + "\n");


                    //starting the process of obtaining the imageUrl from the RealTime Database
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(PROFILE_IMAGE_PATH_IN_RDB).child(firebaseUser).child(retrieveFirebaseCredentialsFromFirestore.getUsername());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Log.d(TAG, "\nonDataChange: Snapshot Exists");

                                //logging the value of the snapshot
                                Log.d(TAG, "\nonDataChange: value of the snapshot=>" + snapshot.getValue());
                                //
                                //retrieving the image url only using the class object
                                RetrieveImageLinksFromRealtimeDB imageLinkFromRDB = snapshot.getValue(RetrieveImageLinksFromRealtimeDB.class);
                                Log.d(TAG, "\nonDataChange: imageLinkOnly=>" + imageLinkFromRDB.getImagePath());
                                String url = imageLinkFromRDB.getImagePath().toString();

                                //setting the values into the navigation drawer header since all the data we have fetched it
                                //drawer picture
                                Glide.with(LoggedInActivity.this).load(url).into(circleImageViewDrawerPicture);
                                //drawerUsername
                                textViewDrawerUsername.setText(retrieveFirebaseCredentialsFromFirestore.getUsername().toUpperCase(Locale.ROOT));
                                //drawerCounty
                                textViewDrawerCounty.setText(retrieveFirebaseCredentialsFromFirestore.getCounty());
                                //University
                                textViewDrawerUniversity.setText(retrieveFirebaseCredentialsFromFirestore.getUniversity());
                                //passion
                                textViewDrawerPassion.setText(retrieveFirebaseCredentialsFromFirestore.getPassion());
                                //email
                                textViewDrawerEmail.setText(retrieveFirebaseCredentialsFromFirestore.getEmail());
                                //phoneNumber
                                textViewDrawerPhoneNumber.setText(retrieveFirebaseCredentialsFromFirestore.getPhoneNumber());
                                //account type or role
                                textViewDrawerAccountRole.setText(retrieveFirebaseCredentialsFromFirestore.getRole());
                                //


                            } else {
                                Log.d(TAG, "\nonDataChange: Snapshot not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            if (error != null) {
                                Log.d(TAG, "onCancelled: error=>" + error.getMessage());
                            }
                        }
                    });
                    //
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                new AlertDialog.Builder(LoggedInActivity.this)
                        .setTitle("Request Unsuccessful")
                        .setMessage("dear user, android encountered an error while loading data from the database server.\nthis has happened due to:\n" + e.getMessage().toUpperCase(Locale.ROOT))
                        .setIcon(R.drawable.android2)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggleLoggedInUser.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //function that will facilitate changing if the title based on the current fragment
    private void setTitleOfLoggedInMatchingTheCurrentFragment(String title)
    {

        this.setTitle(title);
    }
    //


    //end of all
}