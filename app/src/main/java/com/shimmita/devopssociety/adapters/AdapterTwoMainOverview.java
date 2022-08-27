package com.shimmita.devopssociety.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.DisplayProgrammingOnRecyclerOverview1;
import com.shimmita.devopssociety.mains.SpeechClass;

public class AdapterTwoMainOverview extends RecyclerView.Adapter<AdapterTwoMainOverview.Viewholder> {

    @NonNull
    Context context;
    @NonNull
    int[] imageDisplay, imageLocks;
    @NonNull
    String[] descriptionTitle;
    Animation animationForLocked;
    Animation animationForOpen;

    public AdapterTwoMainOverview(@NonNull Context context, @NonNull int[] imageDisplay, @NonNull int[] imageLocks, @NonNull String[] descriptionTitle) {
        this.context = context;
        this.imageDisplay = imageDisplay;
        this.imageLocks = imageLocks;
        this.descriptionTitle = descriptionTitle;
    }

    @NonNull
    @Override
    public AdapterTwoMainOverview.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowdata2_main_overview, parent, false);
        return new AdapterTwoMainOverview.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTwoMainOverview.Viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.imageViewDisplay1.setImageResource(imageDisplay[position]);
        holder.imageviewLocks2.setImageResource(imageLocks[position]);
        holder.descriptionTitle1.setText(descriptionTitle[position]);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationForLocked = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
                animationForOpen = AnimationUtils.loadAnimation(context, R.anim.push_right_in);

                if (imageLocks[position] == R.drawable.ic_baseline_lock_24) {
                    view.startAnimation(animationForOpen);
                    new SpeechClass(context, " Access Denied,please Login To Unlock," + descriptionTitle[position]);
                } else {
                    if (descriptionTitle[position].contains("Software")) {
                        view.startAnimation(animationForLocked);
                        context.startActivity(new Intent(context.getApplicationContext(), DisplayProgrammingOnRecyclerOverview1.class));
                        new SpeechClass(context, descriptionTitle[position] + " Launched");
                    } else {
                        view.startAnimation(animationForLocked);

                        new SpeechClass(context, descriptionTitle[position] + " is ready for launching");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageDisplay.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageViewDisplay1, imageviewLocks2;
        TextView descriptionTitle1;
        ConstraintLayout constraintLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageViewDisplay1 = itemView.findViewById(R.id.imageviewMainOverview);
            imageviewLocks2 = itemView.findViewById(R.id.imageviewLockingStatusMainOverview);
            descriptionTitle1 = itemView.findViewById(R.id.textViewTitleMainDescription);
            constraintLayout = itemView.findViewById(R.id.parentMainOverviewConstraint);
        }
    }
}
