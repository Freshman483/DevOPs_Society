package com.shimmita.devopssociety.fragments_drawer_main;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.adapters.MyAdapterAllUsers;
import com.shimmita.devopssociety.alert_dialog_devops_official_login.AlertUSerOfEnteringLoginCredentials;
import com.shimmita.devopssociety.mains.DrawerMainStarter;
import com.shimmita.devopssociety.modal.RetrieveFirebaseCredentialsFromFirestore;

import java.util.ArrayList;
import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class DevOpsOfficialsFragmentClass extends Fragment {
    private static final String TAG = "DevOpsOfficialsFragment";

    //progressDialog
    ProgressDialog progressDialog;
    //
    //declaration of the view
    View view;
    //
    //declaration of RecyclerView
    RecyclerView recyclerView;
    //

    //declaration of the MyAdapter
    MyAdapterAllUsers myAdapterAllUsers;
    //
    //declaration of arrayLists of Images And Data from Firestore
    ArrayList<RetrieveFirebaseCredentialsFromFirestore> fireStoreDataCredentialsArrayList;
    //
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //

    //emptyConstructorRequired
    public DevOpsOfficialsFragmentClass() {
    }
    //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_devops_officials, container, false);
        //init of progressDialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("All Members");
        progressDialog.setMessage("fetching data...");
        progressDialog.create();
        progressDialog.show();
        //


        //code here using Viewer mode
        firebaseAuth = FirebaseAuth.getInstance();
        //
        //checking if the firebase auth is null and respond accordingly by calling the appropriate function below
        if (firebaseAuth.getCurrentUser() != null) {
            //calling the functionStartTheProgramsAgenda
            callFunctionBeginFragmentAgenda();
            //callFunctionAddDataLisAndRecyclerView
            callFunctionAddDataToListAndRecyclerViewByHelpOfAdapter();
            //
        } else {
            //notify the user that they must be logged in first
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Response From Android")
                    .setIcon(R.drawable.android2)
                    .setCancelable(false)
                    .setMessage("you must be logged in into your valid account in order to access this feature")
                    .setPositiveButton("Ok, Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //displaying the alert dialogUserLogin dialog fragment
                            AlertUSerOfEnteringLoginCredentials loginDialog = new AlertUSerOfEnteringLoginCredentials();
                            loginDialog.show(getActivity().getSupportFragmentManager(), "alertLogin");
                            loginDialog.setCancelable(false);
                            //
                        }
                    }).setNegativeButton("create account", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //directing him into create account fragment where he/she gonna proceed to registration activity
                            startActivity(new Intent(getActivity(), DrawerMainStarter.class).putExtra("fragment", "register"));
                            CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                            Toast.makeText(getActivity(), "read the instructions before proceeding", Toast.LENGTH_LONG).show();
                            dialogInterface.dismiss();
                            //
                        }
                    }).setNeutralButton("no dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //dismiss the dialog
                            dialogInterface.dismiss();
                            //
                        }
                    }).create().show();
            //
        }
        //

        return view;
    }

    private void callFunctionAddDataToListAndRecyclerViewByHelpOfAdapter() {

    }

    private void callFunctionBeginFragmentAgenda() {
        Log.d(TAG, "\ncallFunctionBeginFragmentAgenda: inside intro part");
        //init recyclerView;
        recyclerView = view.findViewById(R.id.recyclerViewDisplayAllUSerDrawerMain);
        //

        //init of firebase variables
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(FIREBASE_USER_COLLECTION);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(PROFILE_IMAGE_PATH_IN_RDB);
        //

        //init arraylists of image and data
        fireStoreDataCredentialsArrayList = new ArrayList<>();
        //

        //call function fetch data from fireStore (credentials)
        callFunctionCredentialsFromFirestore();
        //


    }


    private void callFunctionCredentialsFromFirestore() {
        Log.d(TAG, "\ncallFunctionCredentialsFromFirestore: inside");
        //fireStore Begin

        //credentials

        //collectionReference data get
        collectionReference.get().addOnSuccessListener(getActivity(), new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //data of credentials from fireStore
                if (!queryDocumentSnapshots.isEmpty()) {
                    //log the data first
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        //log id and data to try if things are working perfectly
                        Log.d(TAG, "\n\nonSuccess: data:" + documentSnapshot.getId() + "=> " + documentSnapshot.getData() + "\n\n");
                        //
                        //creating a modal classes object
                        RetrieveFirebaseCredentialsFromFirestore modalDataFromFirestore = documentSnapshot.toObject(RetrieveFirebaseCredentialsFromFirestore.class);

                        //adding the modal class object onto the  arrayList
                        fireStoreDataCredentialsArrayList.add(modalDataFromFirestore);
                        //
                        //size of data list
                        Log.d(TAG, "\nonSuccess: size credentials list::" + fireStoreDataCredentialsArrayList.size());

                        //function carry the data into the images array function
                        callFunctionAddDataToRecycler(fireStoreDataCredentialsArrayList);
                        //
                    }


                } else {
                    //display cancellable alert of empty data
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Results Of Data From The Server")
                            .setIcon(R.drawable.android2)
                            .setMessage("the server has not yet loaded the data, try again later the data may become uploaded")
                            .create().show();
                    //
                }
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //display the error
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.android2)
                        .setTitle("Error")
                        .setMessage("this caused the error\n\n(" + e.getMessage().toUpperCase(Locale.ROOT) + ")")
                        .create().show();
                //
                //
            }
        });
        //
        //fireStore end
    }


    //start
    private void callFunctionAddDataToRecycler(ArrayList<RetrieveFirebaseCredentialsFromFirestore> fireStoreDataCredentialsArrayList) {

        Log.d(TAG, "\ncallFunctionAddDataToRecycler: inside");
        //begins

        //adapters and list view

        Log.d(TAG, "\ncallFunctionAddDataToRecycler: value arrayListFirestore=>" + fireStoreDataCredentialsArrayList.get(0));

        //adding the fetched data of arrayList For Both Credentials from FireStore And Images From RDB by help of modal classes
        myAdapterAllUsers = new MyAdapterAllUsers(getActivity(), fireStoreDataCredentialsArrayList);
        //

        //setting the adapter to the recycler view and layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapterAllUsers);

        //

        //dismiss the progress dialog since data is fetched
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        //
        //ends
    }

}
