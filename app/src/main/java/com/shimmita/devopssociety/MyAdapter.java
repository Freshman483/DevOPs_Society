package com.shimmita.devopssociety;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    int[]imageDisplayConstructor;
    int[]imageLocksConstructor;
    String[] titlesConstructor;
    String[] descriptionConstructor;
    Animation animation;

    public MyAdapter(Context context, int[] imageDisplayConstructor, int[] imageLocksConstructor, String[] titlesConstructor, String[] descriptionConstructor)
    {
        this.context = context;
        this.imageDisplayConstructor = imageDisplayConstructor;
        this.imageLocksConstructor = imageLocksConstructor;
        this.titlesConstructor = titlesConstructor;
        this.descriptionConstructor = descriptionConstructor;

    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_data,parent,false);
        return new MyAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      holder.imageViewDisplay.setImageResource(imageDisplayConstructor[position]);
      holder.imageViewLocks.setImageResource(imageLocksConstructor[position]);
      holder.textViewTitle.setText(titlesConstructor[position]);
      holder.textViewDescription.setText(descriptionConstructor[position]);

      holder.parentLayoutForListener.setOnClickListener(view -> {

          if (imageLocksConstructor[position]==R.drawable.ic_baseline_lock_24)
          {
              //animation
              animation=AnimationUtils.loadAnimation(context,R.anim.push_left_in);
              view.startAnimation(animation);
              //

              String title_held=titlesConstructor[position];

              Toast.makeText(context, "DevOPS Says You Need Login in To Unlock !", Toast.LENGTH_LONG).show();

              if (title_held.equals("HTML"))
              {
                  title_held="Language for Generating The User interface Mostly On Websites";
              }
              new SpeechClass(context,"Please Login To Unlock "+title_held+" Programming");
              new VibratorLowly(context);
          }
          else
          {
              //animation
              animation=AnimationUtils.loadAnimation(context,R.anim.push_right_in);
              view.startAnimation(animation);
              //


              String title_held=titlesConstructor[position];
              Toast.makeText(context, "Service Is Open And Ready For Launching", Toast.LENGTH_LONG).show();
              new SpeechClass(context, title_held+" Programming Service is Ready for Launching");
          }
      });


    }

    @Override
    public int getItemCount() {
        return titlesConstructor.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDisplay,imageViewLocks;
        TextView textViewTitle,textViewDescription;
        ConstraintLayout parentLayoutForListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewDisplay=itemView.findViewById(R.id.imageViewDisplay);
            this.imageViewLocks=itemView.findViewById(R.id.lockIcon);
            this.textViewTitle=itemView.findViewById(R.id.textViewTitleRecycler);
            this.textViewDescription=itemView.findViewById(R.id.textViewDescription);
            this.parentLayoutForListener=itemView.findViewById(R.id.parentConstraintRecyclerForListener);

        }
    }
}
