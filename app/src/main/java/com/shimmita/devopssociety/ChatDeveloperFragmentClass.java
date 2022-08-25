package com.shimmita.devopssociety;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class ChatDeveloperFragmentClass extends Fragment implements View.OnClickListener {
    //
    LinearLayout
            linearLayoutOnlineChatMe,
            linearLayoutEmailMe,
            linearLayoutMessageMe;

    FirebaseAuth firebaseAuth;

    //empty constructor required
    public ChatDeveloperFragmentClass() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_with_developer, container, false);
        //code here using Viewer mode
        linearLayoutOnlineChatMe = view.findViewById(R.id.linearChatMeOnline);
        linearLayoutEmailMe = view.findViewById(R.id.linearChatMeEmail);
        linearLayoutMessageMe = view.findViewById(R.id.linearChatMeMessage);

        //

        //adding listeners onclick
        linearLayoutMessageMe.setOnClickListener(this::onClick);
        linearLayoutOnlineChatMe.setOnClickListener(this::onClick);
        linearLayoutEmailMe.setOnClickListener(this::onClick);
        //

        //initialisation of firebase auth so as to find out if logged in is the user
        firebaseAuth=FirebaseAuth.getInstance();
        //

        return view;
    }

    @Override
    public void onClick(View view) {


        if (view == linearLayoutMessageMe) {
            Toast.makeText(getActivity(), "Messaging Developer", Toast.LENGTH_LONG).show();

            //text message
            String phone_number = "+254757450727";
            String message_body = "hey,write your text here and send it to me, i will be glad to feedback you";


            Intent intent_messaging = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone_number, null));
            startActivity(Intent.createChooser(intent_messaging, "Launch SMS APP"));

            CustomIntent.customType(getActivity(), "fadein-to-fadeout");

            //


        } else if (view == linearLayoutEmailMe) {
            Toast.makeText(getActivity(), "E-Mailing Developer", Toast.LENGTH_LONG).show();

            //start email action intent
            String[] emailsMyEmails = {"shimitadouglas@gmail.com", "tranzeer@gmail.com"};
            String emailSubject = "help or question";
            String messageBodyText = "write your message here";
            Intent intentEmail = new Intent();
            intentEmail.setAction(Intent.ACTION_SEND);
            intentEmail.setDataAndType(Uri.parse("email"), "message/rfc822");
            intentEmail.putExtra(Intent.EXTRA_EMAIL, emailsMyEmails);
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            intentEmail.putExtra(Intent.EXTRA_TEXT, messageBodyText);
            startActivity(Intent.createChooser(intentEmail, "Launch Email"));

            CustomIntent.customType(getActivity(), "fadein-to-fadeout");

            //

        } else if (view == linearLayoutOnlineChatMe) {
            //checking some things
            ConnectivityManager connectivityManager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
            if (networkInfo==null)
            {
                Toast.makeText(getActivity(), "Damn,Connect To The Internet !", Toast.LENGTH_LONG).show();
            }
            else if (firebaseAuth.getCurrentUser()==null)
            {
                Toast.makeText(getActivity(), "Dear User,Please Login To Your Account !", Toast.LENGTH_LONG).show();
            }
            else  if (firebaseAuth.getCurrentUser()!=null){
                //user is on the network and logged in to the account,thus display to him a dialog to enter message;

                //
            }


            //

        } else {
            return;
        }
    }
}
