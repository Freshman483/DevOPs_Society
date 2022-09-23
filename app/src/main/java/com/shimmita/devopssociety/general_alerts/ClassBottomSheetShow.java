package com.shimmita.devopssociety.general_alerts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.DrawerMainStarter;
import com.shimmita.devopssociety.mains.LoggedInActivity;
import com.shimmita.devopssociety.mains.VibratorLowly;

import maes.tech.intentanim.CustomIntent;

public class ClassBottomSheetShow extends BottomSheetDialogFragment {
    //declaration of the global button variables
    Button buttonExitCompletely,
            buttonExitPartially,
            buttonNon,
            buttonHelp,
            buttonOkUnderstood;
    //

    //declaration of the TextView Display Help
    TextView textViewHelp;
    //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(requireActivity(),R.layout.class_bottom_sheet_show,null);
        //code begin
        //init of Globals
        buttonExitCompletely=view.findViewById(R.id.buttonLogoutWithSignOut);
        buttonExitPartially=view.findViewById(R.id.buttonLogoutWithoutSignOut);
        buttonHelp=view.findViewById(R.id.buttonHelp);
        buttonNon=view.findViewById(R.id.buttonNoneOfTheAbove);
        buttonOkUnderstood=view.findViewById(R.id.buttonUnderstoodHelp);
        textViewHelp=view.findViewById(R.id.textViewShowHelp);
        //
        //setting onClick Listener on the buttonExitCompletely
        buttonExitCompletely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //function Promote Back Drawer Menu
               callFunctionBackDrawerStarterMain();
                //

            }
        });
        //setting OnClickListener on Button ExitPartially
        buttonExitPartially.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call function migrate with no signOut
                callFunctionDrawerMainStarterNoSignOut();
                //

            }
        });

        //setting OnClickListener on the button None
        buttonNon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no dismiss the dialog
                callFunctionDismissNoChoice();
                //
            }
        });
        //setting onClickListener on the Button Help
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //function show help visibility gone all buttons; visible help text and okUnderstood button
                callFunctionShowHelp();
                //
            }
        });
        //setting OnClick Listener On The Button Ok Understood
        buttonOkUnderstood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //function visibility visible all button Except underStoodButton and showHelpTextView
                callFunctionShowViewsDisabled();
                //
            }
        });
        //



        //code end
        return view;
    }

    private void callFunctionShowViewsDisabled() {
        //Visibility Gone showTextView and Ok Understood Button
        textViewHelp.setVisibility(View.GONE);
        buttonOkUnderstood.setVisibility(View.GONE);
        //


        //visibility Visible all buttons except ok and show TextView
        buttonExitPartially.setVisibility(View.VISIBLE);
        buttonNon.setVisibility(View.VISIBLE);
        buttonExitCompletely.setVisibility(View.VISIBLE);
        buttonHelp.setVisibility(View.VISIBLE);
        //

    }

    private void callFunctionShowHelp() {
        //visibility gone all buttons
        buttonExitPartially.setVisibility(View.GONE);
        buttonNon.setVisibility(View.GONE);
        buttonExitCompletely.setVisibility(View.GONE);
        buttonHelp.setVisibility(View.GONE);
        //

        //Visibility Visible textView Show Help and Button Ok Understood
        textViewHelp.setVisibility(View.VISIBLE);
        buttonOkUnderstood.setVisibility(View.VISIBLE);
        //


    }

    private void callFunctionDismissNoChoice() {
        //back the user to the main LoggedIn Profile
        requireActivity().startActivity(new Intent(requireActivity(), LoggedInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        //
        //animate intent
        CustomIntent.customType(requireActivity(),"bottom-to-up");
        //

        //dismiss to avoid window leaked RT Exceptions
        dismiss();
        //
    }

    private void callFunctionDrawerMainStarterNoSignOut() {
        //code begin intent

        requireActivity().startActivity(new Intent(requireActivity(),DrawerMainStarter.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        CustomIntent.customType(requireActivity(),"fadein-to-fadeout");
        //end code

        //dismiss to avoid window leaked RT Exceptions
        dismiss();
        //
    }

    private void callFunctionBackDrawerStarterMain() {
        //sign Out the current user For Exiting Completely
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        //

        //starting the intent migration
        requireActivity().startActivity(new Intent(requireActivity(), DrawerMainStarter.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        //
        //animate the intent
        CustomIntent.customType(requireActivity(),"fadein-to-fadeout");
        //

        //dismiss to avoid window leaked RT Exceptions
        dismiss();
        //
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        //call function to show this bottom sheet again since user has no other choice but select an option
        callFunctionShowThisBottomSheetClassAgain();
        //
    }

    private void callFunctionShowThisBottomSheetClassAgain() {
        //Vibrate
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            new VibratorLowly(requireActivity());
        }
        //

        //on dismiss of this bottom sheet just show it again till user enters response
        new ClassBottomSheetShow().show(requireActivity().getSupportFragmentManager(),"show class bottom sheet");
        //
    }
}
