package com.shimmita.devopssociety.fragments_loggedin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.DrawerMainStarter;

import maes.tech.intentanim.CustomIntent;

public class LogoutLoggedInFragmentClass extends Fragment {

    FirebaseAuth firebaseAuth;

    //empty constructor required
    public LogoutLoggedInFragmentClass() {
    }
    //


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logout_loggedin_fragment, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        
        //code here
        new AlertDialog.Builder(getActivity())
                .setTitle("Logout Confirmation")
                .setMessage("Are Sure You Want To Logout From Your Current Account and Back To the Home Activity?")
                .setPositiveButton("yes,sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.signOut();
                        //user Confirmed Exit from the account so lets back him/her to the drawer main starter as the parent activity
                        Toast.makeText(getActivity(), "User Logged Out Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), DrawerMainStarter.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                        //
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
        //

        return view;
    }
}
