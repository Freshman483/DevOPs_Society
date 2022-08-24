package com.shimmita.devopssociety;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import es.dmoral.toasty.Toasty;

public class ExitAppFragmentClass extends Fragment {
    public ExitAppFragmentClass() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //code here
        functionCallAlertDialog();

        //
        return inflater.inflate(R.layout.blank_exit_layout, container, false);
    }

    private void functionCallAlertDialog() {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle("EXIT")
                .setCancelable(false)
                .setMessage("Do You Want To Exit From The App?")
                .setIcon(R.drawable.ic_baseline_info_24)
                .setPositiveButton("Yes, I Do ", (dialogInterface, i) -> {
                    Toasty.custom(getActivity(), "DevOps App Shutting Down", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    //function Exit Be Defined Here
                    callFunctionExit();
                    //
                })
                .setNegativeButton("No, Lets Be Back", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    Toasty.custom(getActivity(), "Lets Continue ", R.drawable.ic_baseline_whatshot_24, R.color.purple_200, Toasty.LENGTH_LONG, true, true).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout, new HomeFragmentClass()).commit();

                })
                .create()
                .show();
    }

    private void callFunctionExit() {
        getActivity().finish();
        System.exit(0);
    }
}
