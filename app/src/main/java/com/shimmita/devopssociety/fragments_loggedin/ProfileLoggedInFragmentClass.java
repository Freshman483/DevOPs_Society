package com.shimmita.devopssociety.fragments_loggedin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.shimmita.devopssociety.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileLoggedInFragmentClass extends Fragment {


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
    AppCompatButton buttonUpgradeAccount;
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
        buttonChangeProfilePicture = view.findViewById(R.id.buttonChangeProfilePicture);

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
        //

        //

        return view;
    }

}
