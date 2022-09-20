package com.shimmita.devopssociety.fragments_loggedin;

import static com.shimmita.devopssociety.mains.Registration.FIREBASE_USER_COLLECTION;
import static com.shimmita.devopssociety.mains.Registration.PROFILE_IMAGE_PATH_IN_RDB;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.shimmita.devopssociety.mains.Login;
import com.shimmita.devopssociety.mains.PostingDataActivity;
import com.shimmita.devopssociety.modal.RetrieveFirebaseCredentialsFromFirestore;
import com.shimmita.devopssociety.modal.RetrieveImageLinksFromRealtimeDB;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class ProfileLoggedInFragmentClass extends Fragment {
    private static final String TAG = "ProfileLoggedInFragment";
    private static final String DEVELOPERS_NAME = "shimita douglas";
    private static final String COMMANDER_ADMINSTRATOR = "commanderadmin";

    //creating progress dialog and dismiss it when data arrives
    ProgressDialog progressDialog;
    //

    //TODO:Copy the whole code for data retrieval from the main loggedIn Activity into the profile fragment so that when the user clicks on the main profile fragment,
    //TODO: the data should not get lost since we are getting it from the main  activity of loggedIn

    //firebase Globals
    String firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DocumentReference documentReference;
    DatabaseReference databaseReference;
    //


    //values Home Fragment
    CircleImageView circleImageViewAccountProfilePicture;
    TextView textViewUsernameLoggedInName,
            textViewUsernameUniversityLoggedIn,
            textViewUsernameLoggedInCounty,
            textViewUsernameLoggedInPassion,
            textViewUserNameLoggedInEmail,
            textViewUserNameLoggedInPhoneNumber,
            textViewUserNameLoggedInAccountType,
            textViewAccountDescriptionText;
    AppCompatButton buttonUpgradeAccount,
            buttonVerifyEmail,
            buttonCommand;

    Button buttonChangeProfilePicture;
//

    //empty constructor required
    public ProfileLoggedInFragmentClass() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_loggedin_fragment, container, false);

        //code here
        buttonUpgradeAccount = view.findViewById(R.id.buttonUpgradeAccount);
        buttonVerifyEmail = view.findViewById(R.id.buttonVerifyEmailProfile);
        buttonChangeProfilePicture = view.findViewById(R.id.buttonChangeProfilePicture);
        buttonCommand=view.findViewById(R.id.buttonCommander);


        //values Home fragment init from firebase
        circleImageViewAccountProfilePicture = view.findViewById(R.id.headerProfilePicture);
        textViewUsernameLoggedInName = view.findViewById(R.id.headerUsername);
        textViewUsernameUniversityLoggedIn = view.findViewById(R.id.headerUniversity);
        textViewAccountDescriptionText = view.findViewById(R.id.textviewAccountDescription);
        textViewUsernameLoggedInCounty = view.findViewById(R.id.headerCounty);
        textViewUsernameLoggedInPassion = view.findViewById(R.id.headerPassion);
        textViewUserNameLoggedInEmail = view.findViewById(R.id.headerEmail);
        textViewUserNameLoggedInPhoneNumber = view.findViewById(R.id.headerPhoneNumber);
        textViewUserNameLoggedInAccountType = view.findViewById(R.id.headerAccountType);

        //frozen because it similarly works fine in retrieving the data like the function retrieve data below it. but has some weakness since  it is unstable alone
        //good method of passing data between fragments


        /*//
        getActivity().getSupportFragmentManager().setFragmentResultListener("Key", getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                //obtaining the data from the loggedIn MAinActivity
                String imageUrl = result.getString("url", "no username");
                String username = result.getString("username", "no username");
                String email = result.getString("email", "no email");
                String phone = result.getString("phone", "no phone");
                String passion = result.getString("passion", "no passion");
                String role = result.getString("role", "no role");
                String occupation = result.getString("occupation", "no occupation");
                String county = result.getString("county", "no county");
                String university = result.getString("university", "no university");
                String password = result.getString("password", "no password");

                //
                //logging the genuineness of the results first on the console screen
                Log.d(TAG, "\n\nonFragmentResult: result of image Url=>" + imageUrl);
                Log.d(TAG, "\n\nonFragmentResult: result of Username=>" +username);
                Log.d(TAG, "\n\nonFragmentResult: result of email=>" +email);
                Log.d(TAG, "\n\nonFragmentResult: result of University=>" +university);
                Log.d(TAG, "\n\nonFragmentResult: result of passion=>" +passion);
                Log.d(TAG, "\n\nonFragmentResult: result of password=>" +password);
                Log.d(TAG, "\n\nonFragmentResult: result of account role=>" +role);
                Log.d(TAG, "\n\nonFragmentResult: result of occupation=>" +occupation);
                Log.d(TAG, "\n\nonFragmentResult: result of phone number=>" +phone);
                Log.d(TAG, "\n\nonFragmentResult: result of Username=>" +county+"\n\n");
                //
                //loading the image url onto the circleImageView Using Glide Library
                Glide.with(getActivity()).load(imageUrl).into(circleImageViewAccountProfilePicture);
                //loading other account credentials onto the required fields
                //username
                textViewUsernameLoggedInName.setText(username.toUpperCase(Locale.ROOT));
                //university
                textViewUsernameUniversityLoggedIn.setText(university);
                //email
                textViewUserNameLoggedInEmail.setText(email);
                //phoneNumber
                textViewUserNameLoggedInPhoneNumber.setText(phone);
                //county
                textViewUsernameLoggedInCounty.setText(county);
                //account role
                textViewUserNameLoggedInAccountType.setText(role);
                //passion
                textViewUsernameLoggedInPassion.setText(passion);
                //


            }
        });*/

        //init of progressDialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Profile Data");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.create();
        //
        //
        callFunctionRetrieveData2();
        //

        //creating function to check if the email is verified
        functionCheckEmailVerification();
        //

        //function button verify email clicked
        functionButtonVerifyEmailClicked();
        //

        //setting onclick listener on the buttonCommander
        buttonCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call  function to start intent to another acttivity where we will add posting data functionality to the DevOps Society Kenya
                callFunctionPostingDataDevOpsSociety();
                //
            }
        });
        //

        return view;
    }

    private void callFunctionPostingDataDevOpsSociety() {
        //toasty account migration was successfully

        //
        //creating an intent to trigger migration into the posting data profile
        requireActivity().startActivity(new Intent(requireActivity(), PostingDataActivity.class));
        //
        //animating the account migration
        CustomIntent.customType(requireActivity(),"fadein-to-fadeout");
        //

    }

    private void functionButtonVerifyEmailClicked() {
        //setting onclick listener to the button verify the email account
        buttonVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lets verify this user by sending an email verification to their emails
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        new AlertDialog.Builder(getActivity())
                                .setMessage("an email verification has been sent to your email address " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + " " +
                                        "if the email is not located in the inbox folder, then  check it in the SPAM folder and verify the genuineness of your account")
                                .setCancelable(false)
                                .setIcon(R.drawable.ic_baseline_info_24)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //sign out this user to prevent issues with emil verification status cox tho might have been verified but says false
                                        FirebaseAuth.getInstance().signOut();
                                        //
                                        dialogInterface.dismiss();
                                        //directing the user to the login activity
                                        startActivity(new Intent(getActivity(), Login.class));
                                        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                                        //

                                    }
                                }).create().show();

                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //checking if the Exception e is not null meaning it contains some error information
                        new MaterialAlertDialogBuilder(getActivity())
                                .setCancelable(false)
                                .setMessage("Unknown error occurred while trying to send the the email verification message to your email " + FirebaseAuth.getInstance().getCurrentUser().getEmail() +
                                        " Please the process again.\nError Cause:\n" + e.getMessage().toUpperCase(Locale.ROOT))
                                .setPositiveButton("Re-try", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create().show();
                        //
                    }
                });
                //
            }
        });
    }

    private void functionCheckEmailVerification() {
        //logging the email verification status
        Log.d(TAG, "\n\nfunctionCheckEmailVerification: Is email verified? " + FirebaseAuth.getInstance().getCurrentUser().isEmailVerified() + "\n\n");
        //

        if (!(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())) {
            //alert user that the email is not verified
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Email Verification!")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_info_24)
                    .setMessage("dear user your email:" + FirebaseAuth.getInstance().getCurrentUser().getEmail().toUpperCase(Locale.ROOT) + " is not verified, prevent this account from being " +
                            " disabled or deleted permanently by clicking the button verify email displayed in the account  profile.\n\n" +
                            "Accounts with non-verified email addresses are not regarded as genuine accounts and have a lifeSpan of 2 days only !!")
                    .setPositiveButton("Ok,Imma Verify", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        } else {
            //enabling upgrade the account button
            buttonUpgradeAccount.setVisibility(View.VISIBLE);
            //disabling the verify email button
            buttonVerifyEmail.setVisibility(View.GONE);
            //
            new AlertDialog.Builder(getActivity())
                    .setTitle("Congratulations Email Verified Successfully")
                    .setIcon(R.drawable.ic_baseline_check)
                    .setMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail().toUpperCase(Locale.ROOT) + " the email is verified, you can now continue using your account. we offer " +
                            "two types of account:\n\n1.The Normal account with less privileges unless upgraded to premium or super user account.\n\n" +
                            "2.The Premium(Super User Account) with unlocked features which are locked in normal account. upgrade fee @Ksh.100")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
    }

    private void callFunctionRetrieveData2() {

        callFunctionDownloadDataFromFirestore();
    }

    //function to download data from fireStore

    private void callFunctionDownloadDataFromFirestore() {
        //init of collection,document reference and firebase user of the currently logged in
        firebaseAuth = FirebaseAuth.getInstance();

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



                   /* //putting the  obtained data from the firebase into the bundle bundle
                    //username
                    bundle.putString("username", retrieveFirebaseCredentialsFromFirestore.getUsername());
                    //email
                    bundle.putString("email", retrieveFirebaseCredentialsFromFirestore.getEmail());
                    //University
                    bundle.putString("university", retrieveFirebaseCredentialsFromFirestore.getUniversity());
                    //phoneNumber
                    bundle.putString("phone", retrieveFirebaseCredentialsFromFirestore.getPhoneNumber());
                    //passion
                    bundle.putString("passion", retrieveFirebaseCredentialsFromFirestore.getPassion());
                    //county
                    bundle.putString("county", retrieveFirebaseCredentialsFromFirestore.getCounty());
                    //account role
                    bundle.putString("role", retrieveFirebaseCredentialsFromFirestore.getRole());
                    //occupation
                    bundle.putString("occupation", retrieveFirebaseCredentialsFromFirestore.getOccupation());
                    //password
                    bundle.putString("password", retrieveFirebaseCredentialsFromFirestore.getPassword());
                    //
                    //*/

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

                                //re adding the data again when user clicks on the profile icon of bottom nav else it will display empty fields!
                                //loading the image url onto the circleImageView Using Glide Library
                                Glide.with(getActivity()).load(url).into(circleImageViewAccountProfilePicture);
                                //loading other account credentials onto the required fields
                                //username
                                textViewUsernameLoggedInName.setText(retrieveFirebaseCredentialsFromFirestore.getUsername().toUpperCase(Locale.ROOT));
                                //university
                                textViewUsernameUniversityLoggedIn.setText(retrieveFirebaseCredentialsFromFirestore.getUniversity());
                                //email
                                textViewUserNameLoggedInEmail.setText(retrieveFirebaseCredentialsFromFirestore.getEmail());
                                //phoneNumber
                                textViewUserNameLoggedInPhoneNumber.setText(retrieveFirebaseCredentialsFromFirestore.getPhoneNumber());
                                //county
                                textViewUsernameLoggedInCounty.setText(retrieveFirebaseCredentialsFromFirestore.getCounty());
                                //account role
                                textViewUserNameLoggedInAccountType.setText(retrieveFirebaseCredentialsFromFirestore.getRole());
                                //checking If the account role and Name contains or is equal to Developers Name,and  commanderAdmin is role,then we set
                                //the button commander Visibility to visible else we set the button commander invisible because all users are not developers
                                //but one of em is he.me;
                                //when we click the button commander we will be directed to an activity to post data which will be available fo all users for access

                                if (textViewUsernameLoggedInName.getText().toString().toUpperCase(Locale.ROOT).equals(DEVELOPERS_NAME)
                                &&textViewUserNameLoggedInAccountType.getText().toString().toUpperCase(Locale.ROOT).equals(COMMANDER_ADMINSTRATOR))
                                {
                                    buttonCommand.setVisibility(View.VISIBLE);
                                }



                                //passion
                                textViewUsernameLoggedInPassion.setText(retrieveFirebaseCredentialsFromFirestore.getPassion());
                                //

                                //dismiss the progress dialog since the data has been retrieved
                                if (progressDialog.isShowing()) {
                                    progressDialog.cancel();
                                    progressDialog.dismiss();
                                    //
                                }
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
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                new AlertDialog.Builder(getActivity())
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

    private void callFunctionVerifyThisCurrentlyLoggedInUser() {
        FirebaseUser userCurrentlyLogged = FirebaseAuth.getInstance().getCurrentUser();

        userCurrentlyLogged.sendEmailVerification().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Email Verification Sent To " + userCurrentlyLogged.getEmail(), Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

    }

    private void functionDeleteUserAccount(FirebaseUser userCurrentlyLogged) {
        userCurrentlyLogged.delete().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Account  Deleted Successfully", Toast.LENGTH_SHORT).show();
                } else if (!task.isSuccessful()) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Failed To Delete Account")
                            .setMessage("android failed to perform the requested operation,the message has been sent to the developer to delete the account fully.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            }
        });
    }
    //


}
