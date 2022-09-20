package com.shimmita.devopssociety.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.modal.RetrieveFirebaseCredentialsFromFirestore;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class MyAdapterAllUsers extends RecyclerView.Adapter<MyAdapterAllUsers.MyViewHolder> {

    Context context;
    ArrayList<RetrieveFirebaseCredentialsFromFirestore> fireStoreDataArrayList;

    public MyAdapterAllUsers(Context context, ArrayList<RetrieveFirebaseCredentialsFromFirestore> fireStoreDataArrayList) {
        this.context = context;
        this.fireStoreDataArrayList = fireStoreDataArrayList;
    }

    @NonNull
    @Override
    public MyAdapterAllUsers.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_all_user_row_data_to_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterAllUsers.MyViewHolder holder, int position) {
        //Todo stat here in the binder

        String imageUrl = fireStoreDataArrayList.get(position).getImagePath();
        String username = fireStoreDataArrayList.get(position).getUsername();
        String university = fireStoreDataArrayList.get(position).getUniversity();
        String email = fireStoreDataArrayList.get(position).getEmail();
        String phone = fireStoreDataArrayList.get(position).getPhoneNumber();
        String passion = fireStoreDataArrayList.get(position).getPassion();
        String role = fireStoreDataArrayList.get(position).getRole();

        //setting the counter with regard to the position current (convert the integer position into the string type data else app is gonna crush)
        //increment the value with one so that it does not 0 as number awkward to users
        holder.counterNumber.setText(String.valueOf(position+1));
        //

        //Todo: implement the button contact user after figuring out its best way of

        //setting the button text in correspondence with the username
        holder.contactUserButton.setText("Contact ("+username.toLowerCase(Locale.ROOT)+")");
        //
        //adding listener to the contact user button
        holder.contactUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //functionPlaySomeAnimationsOnTheButtonClick button contact the  user
                functionPlaySomeAnimationsOnTheButtonClick(view);
                //

                //create a popup menu to show the user what selection he/she is into
                PopupMenu popupMenu = new PopupMenu(context, view, Gravity.CENTER);
                popupMenu.inflate(R.menu.all_users_menu_pop_up_in_the_adapter);
                popupMenu.setForceShowIcon(true);

                //setting a listener on the selected item
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.callUserPopUpInAdapter:
                                //calling

                                //alerting the user with diaog informing what is going happen when accepts
                                new MaterialAlertDialogBuilder(context)
                                        .setTitle("Calling")
                                        .setMessage("(" + username.toLowerCase(Locale.ROOT) + ") number will be forwaded onto the dial screen of your device, call or save the user's number if interested")
                                        .setCancelable(false)
                                        .setIcon(R.drawable.ic_baseline_call_24)
                                        .setPositiveButton("Understood", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                //start the intent of phone number forwarding
                                                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
                                                CustomIntent.customType(context, "fadein-fadeout");
                                                Toasty.custom(context, "Number Forwarded Successfully", R.drawable.android2, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

                                                //
                                                //dismiss the dialog
                                                dialogInterface.dismiss();
                                                //
                                            }
                                        }).setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //dismissing the dialog
                                                dialogInterface.dismiss();
                                                //
                                            }
                                        }).create().show();
                                //

                                break;

                            case R.id.emailUserPopUpInAdapter:
                                //emailing

                                //alerting the user with dialog informing what is going happen when accepts (email intent)
                                new MaterialAlertDialogBuilder(context)
                                        .setTitle("Messaging")
                                        .setMessage("you will be directed to your default emailing application installed on your device, send your email request to the user (" + username + ").")
                                        .setCancelable(false)
                                        .setIcon(R.drawable.ic_baseline_message_text_message)
                                        .setPositiveButton("Understood", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //declaration of the email variables that will be used when the email intent service is started
                                                String[] emailSendToTheUserCurrentlyPositioned = {email};
                                                String emailSubject = "write your email subject here to make it easier for " + username.toUpperCase(Locale.ROOT) + " to understand your objective";
                                                String emailBody = "write your email message here that you intended to send it to " + username;
                                                //starting the email intent services
                                                context.startActivity(new Intent(Intent.ACTION_SEND).setDataAndType(Uri.parse("email"), "message/rfc822")
                                                        .putExtra(Intent.EXTRA_EMAIL, emailSendToTheUserCurrentlyPositioned).putExtra(Intent.EXTRA_SUBJECT, emailSubject).putExtra(Intent.EXTRA_TEXT, emailBody));
                                                CustomIntent.customType(context, "fadein-fadeout");
                                                //
                                                //toast to show things went good
                                                Toasty.custom(context, "emailing services opened successfully", R.drawable.android2, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                                                //
                                                //dismiss the dialog
                                                dialogInterface.dismiss();
                                                //
                                            }
                                        }).setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //dismissing the dialog
                                                dialogInterface.dismiss();
                                                //
                                            }
                                        }).create().show();


                                //

                                break;
                            case R.id.sendTextMessageUserInPopUp:

                                //messaging
                                //alerting the user with dialog informing what is going happen when accepts (sms intent)
                                new MaterialAlertDialogBuilder(context)
                                        .setTitle("Messaging")
                                        .setMessage("you will be directed to your default messaging application installed on your device, send your message request to the user")
                                        .setCancelable(false)
                                        .setIcon(R.drawable.ic_baseline_message_text_message)
                                        .setPositiveButton("Understood", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                //start the intent of sms  forwarding
                                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null)));
                                                CustomIntent.customType(context, "fadein-fadeout");
                                                Toasty.custom(context, "messaging application opened successfully", R.drawable.android2, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

                                                //
                                                //dismiss the dialog
                                                dialogInterface.dismiss();
                                                //
                                            }
                                        }).setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //dismissing the dialog
                                                dialogInterface.dismiss();
                                                //
                                            }
                                        }).create().show();
                                //

                                //

                                break;
                        }

                        return true;
                    }
                });

                //

                popupMenu.show();
            }
        });
        //
        //creating a function that will set data onto the layout all users views
        holder.SetData(imageUrl, username, university, email, phone, passion, role);
        //

    }

    //function start animation on contact user button click
    private void functionPlaySomeAnimationsOnTheButtonClick(View view) {
        //start the animation
        view.startAnimation(AnimationUtils.loadAnimation(context, com.tech.intentanim.R.anim.rotatein_out));
        //
    }

    @Override
    public int getItemCount() {
        return fireStoreDataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //init of views from layoutAllUsersRowData
        CircleImageView displayUserImage;
        TextView usernameText,
                universityText,
                emailText,
                phoneNumberText,
                passionText,
                accountRoleText,
                counterNumber;
        AppCompatButton contactUserButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            displayUserImage = itemView.findViewById(R.id.imageViewPicture);
            usernameText = itemView.findViewById(R.id.textViewUsername);
            universityText = itemView.findViewById(R.id.textViewUniversity);
            emailText = itemView.findViewById(R.id.textViewEmail);
            phoneNumberText = itemView.findViewById(R.id.textViewPhoneNumber);
            passionText = itemView.findViewById(R.id.textViewPassion);
            accountRoleText = itemView.findViewById(R.id.textViewAccountRole);
            counterNumber = itemView.findViewById(R.id.textViewCounterNumberAllUsers);

            contactUserButton = itemView.findViewById(R.id.buttonContactUser);

        }

        public void SetData(String imageUrl, String username, String university, String email, String phone, String passion, String role) {
            //using glider to add the image url
            Glide.with(context).load(imageUrl).into(displayUserImage);
            //

            //assigning the other variables their placeholders respectively
            usernameText.setText(username);
            universityText.setText(university);
            emailText.setText(email);
            phoneNumberText.setText(phone);
            passionText.setText(passion);

            if (role.toLowerCase().contains("normal"))
                role = "normal a/c";
            accountRoleText.setText(role);
            //

        }
    }
}

