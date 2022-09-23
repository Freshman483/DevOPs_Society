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
import com.shimmita.devopssociety.general_alerts.ClassBottomSheetShow;
import com.shimmita.devopssociety.mains.DrawerMainStarter;
import com.shimmita.devopssociety.mains.LoggedInActivity;

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
       //code begins

        //creating the alert of ClassBottomSheet from which the user should select a choice
        ClassBottomSheetShow classBottomSheetShow=new ClassBottomSheetShow();
        classBottomSheetShow.show(requireActivity().getSupportFragmentManager(),"show class bottom sheet");
        //

        //code ends

        return view;
    }
}
