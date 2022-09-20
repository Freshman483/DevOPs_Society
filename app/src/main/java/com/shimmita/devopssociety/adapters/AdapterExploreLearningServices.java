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
import com.shimmita.devopssociety.mains.DisplayProgrammingLanguagesOnRecyclerOverview1;
import com.shimmita.devopssociety.mains.SpeechClass;

import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class AdapterExploreLearningServices extends RecyclerView.Adapter<AdapterExploreLearningServices.Viewholder> {

    @NonNull
    Context context;
    @NonNull
    int[] imageDisplay, imageLocks;
    @NonNull
    String[] descriptionTitle;
    String reason;
    Animation animationForLocked;
    Animation animationForOpen;

    public AdapterExploreLearningServices(@NonNull Context context, @NonNull int[] imageDisplay, @NonNull int[] imageLocks, @NonNull String[] descriptionTitle, String reason) {
        this.context = context;
        this.imageDisplay = imageDisplay;
        this.imageLocks = imageLocks;
        this.descriptionTitle = descriptionTitle;
        this.reason = reason;
    }

    @NonNull
    @Override
    public AdapterExploreLearningServices.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowdata2_explore_learning_services, parent, false);
        return new AdapterExploreLearningServices.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExploreLearningServices.Viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.imageViewDisplay1.setImageResource(imageDisplay[position]);
        holder.imageviewLocks2.setImageResource(imageLocks[position]);
        holder.descriptionTitle1.setText(descriptionTitle[position]);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationForLocked = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
                animationForOpen = AnimationUtils.loadAnimation(context, R.anim.push_right_in);

                //service is locked,has a blue lock
                //are only two scenarios where services are locked;
                //1.the user is in OverView Mode; 2.the user account is not premium account
                //thus evaluation is required to know why service is locked and this is achieved as shown below

                if (imageLocks[position] == R.drawable.ic_baseline_lock_24) {
                    //check the value inside the string reason and talk responsively
                    //if reason contains/equal to (lock_services_intent_is_from_main_over_view) automatically say User is in preview mode
                    //too toast the value; IF THIS IS NOT THE CASE THEN THE USER SHOULD BE THAT HIS/HER IS NOT PREMIUM
                    if (reason.contains("you are currently in overview mode")) {

                        view.startAnimation(animationForOpen);

                        new SpeechClass(context, reason + "," + descriptionTitle[position]);

                        //toasty reason
                        Toasty.custom(context, reason, R.drawable.android2, R.color.teal_700, Toasty.LENGTH_LONG, true, true).show();
                        //
                    }
                    //user not in overview mode thus user is not using a premium/super account
                    else {
                        if (reason.equals("upgrade account"))
                        {
                            //converting the value of the reason to access denied,please upgrade your account to unlock
                            reason = "access denied,please upgrade your account to unlock";
                            //
                            view.startAnimation(animationForOpen);

                            new SpeechClass(context, reason + "," + descriptionTitle[position]);
                            //toasty the user to upgrade account
                            Toasty.custom(context, reason, R.drawable.android2, R.color.teal_700, Toasty.LENGTH_LONG, true, true).show();
                            //
                        }
                    }


                }

                //service not locked has an open lock
                else {
                    if (descriptionTitle[position].contains("Software")) {
                        view.startAnimation(animationForLocked);

                        //put a string extra which will be "software_languages"
                        //using the extras we will decide the display of the programming languages from software perspective
                        String key = "data_from_intent_launch";
                        String value = "software_languages";
                        //
                        context.startActivity(new Intent(context.getApplicationContext(), DisplayProgrammingLanguagesOnRecyclerOverview1.class).
                                putExtra(key, value));
                        //creating an animation style when the intent is launched to the programming languages
                        CustomIntent.customType(context, "bottom-to-top");
                        //crating a toast toasting service launched
                        Toasty.custom(context, "Software Launched Successfully", R.drawable.ic_baseline_menu_book_learning, R.color.teal_700, Toasty.LENGTH_LONG, true, true).show();
                        //

                        //creating a speech that will speak the service being launched
                        new SpeechClass(context, descriptionTitle[position] + " Launched");
                        //
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
