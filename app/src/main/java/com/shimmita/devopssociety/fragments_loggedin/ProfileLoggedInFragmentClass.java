package com.shimmita.devopssociety.fragments_loggedin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.bumptech.glide.Glide;
import com.shimmita.devopssociety.R;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileLoggedInFragmentClass extends Fragment {
    private static final String TAG = "ProfileLoggedInFragment";

    //TODO:Copy the whole code for data retrieval from the main loggedIn Activity into the profile fragment so that when the Overclocks on the main profile fragment,
    //TODO: the data should not get lost since we are getting it from the main  activity of loggedIn


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
                //loging the genuineness of the results first on the console screen
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
        });
        //

        return view;
    }

}
