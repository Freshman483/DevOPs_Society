package com.shimmita.devopssociety;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BuyMeCoffeeFragmentClass extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //code here using Viewer mode

        //

       return inflater.inflate(R.layout.fragment_buy_me_coffee,container,false);
    }
}
