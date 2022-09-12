package com.shimmita.devopssociety.fragments_loggedin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.DrawerMainStarter;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class ActiveMembersLoggedInFragmentClass extends Fragment {
    private static final String TAG = "ActiveMembersLoggedInFr";

    //empty constructor required
    public ActiveMembersLoggedInFragmentClass() {
    }
    //


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.active_members_loggedin_fragment, container, false);
        //code here
        Log.d(TAG, "onCreateView: game started inside active members ");
        //because this fragment will also display the same members available in the society, lets direct an intent to trigger DevOpsOfficial fragment from the drawerMain

        //display alert dialog showing the user that the page is gonna be redirected toward fragment with all members officially registered
        new MaterialAlertDialogBuilder(getActivity())
                .setIcon(R.drawable.android2)
                .setTitle("Page Redirection")
                .setCancelable(false)
                //can add restrictions that the email must be verified before viewing the current genuine users list
                .setMessage("(" + FirebaseAuth.getInstance().getCurrentUser().getEmail() + ") your request to view active members list will be granted successfully " +
                        "when you accept")
                .setPositiveButton("Ok,Acccept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                        Toasty.custom(getActivity(), "all members list opening...", R.drawable.android2, R.color.white, Toasty.LENGTH_LONG, true, true);
                        startActivity(new Intent(getActivity(), DrawerMainStarter.class).putExtra("fragment", "all_members"));
                        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                        //

                        //dismiss the dialog to avoid window leak
                        dialogInterface.dismiss();
                        //
                    }
                }).create().show();
        //

        return view;
    }
}
