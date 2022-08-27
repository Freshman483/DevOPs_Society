package com.shimmita.devopssociety.mains;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shimmita.devopssociety.R;

import es.dmoral.toasty.Toasty;

public class BottomSheetWithTerms extends BottomSheetDialogFragment {

    CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_containing_terms_and_condition, container);
        Button button;
        checkBox = view.findViewById(R.id.checkBoxBottomSheet);
        button = view.findViewById(R.id.buttonAcceptTermsToProceedBottomSheet);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    button.setEnabled(true);
                    button.setBackgroundColor(Color.parseColor("#D77907"));
                } else if (!checkBox.isChecked()) {
                    button.setEnabled(false);
                    button.setBackgroundColor(Color.WHITE);
                }

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toasty.custom(getActivity().getApplicationContext(), "Congratulations! Submit Now", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                dismiss();

            }
        });


        return view;
    }
}
