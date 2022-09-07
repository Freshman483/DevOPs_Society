package com.shimmita.devopssociety.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.modal.RetrieveFirebaseCredentialsFromFirestore;

import java.util.ArrayList;

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

        String imageUrl =fireStoreDataArrayList.get(position).getImagePath();
        String username = fireStoreDataArrayList.get(position).getUsername();
        String university = fireStoreDataArrayList.get(position).getUniversity();
        String email = fireStoreDataArrayList.get(position).getEmail();
        String phone = fireStoreDataArrayList.get(position).getPhoneNumber();
        String passion = fireStoreDataArrayList.get(position).getPassion();
        String role = fireStoreDataArrayList.get(position).getRole();

        //Todo: implement the button contact user after figuring out its best way of
        //
        //creating a function that will set data onto the layout all users views
        holder.SetData(imageUrl, username, university, email, phone, passion, role);
        //

    }

    @Override
    public int getItemCount() {
        return fireStoreDataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //init of views from layoutAllUsersRowData
        ImageView displayUserImage;
        TextView usernameText,
                universityText,
                emailText,
                phoneNumberText,
                passionText,
                accountRoleText;
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

