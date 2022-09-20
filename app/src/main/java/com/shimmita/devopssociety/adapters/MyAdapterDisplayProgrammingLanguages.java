package com.shimmita.devopssociety.adapters;

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

import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.SpeechClass;
import com.shimmita.devopssociety.mains.VibratorLowly;

import es.dmoral.toasty.Toasty;

public class MyAdapterDisplayProgrammingLanguages extends RecyclerView.Adapter<MyAdapterDisplayProgrammingLanguages.ViewHolder> {
    Context context;
    int[]imageDisplayConstructor;
    int[]imageLocksConstructor;
    String[] titlesConstructor;
    String[] descriptionConstructor;
    String reason;
    Animation animation;

    public MyAdapterDisplayProgrammingLanguages(Context context, int[] imageDisplayConstructor, int[] imageLocksConstructor, String[] titlesConstructor, String[] descriptionConstructor, String reason)
    {
        this.context = context;
        this.imageDisplayConstructor = imageDisplayConstructor;
        this.imageLocksConstructor = imageLocksConstructor;
        this.titlesConstructor = titlesConstructor;
        this.descriptionConstructor = descriptionConstructor;
        this.reason=reason;

    }

    @NonNull
    @Override
    public MyAdapterDisplayProgrammingLanguages.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_data,parent,false);
        return new MyAdapterDisplayProgrammingLanguages.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyAdapterDisplayProgrammingLanguages.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      holder.imageViewDisplay.setImageResource(imageDisplayConstructor[position]);
      holder.imageViewLocks.setImageResource(imageLocksConstructor[position]);
      holder.textViewTitle.setText(titlesConstructor[position]);
      holder.textViewDescription.setText(descriptionConstructor[position]);

      holder.parentLayoutForListener.setOnClickListener(view -> {

          if (imageLocksConstructor[position]==R.drawable.ic_baseline_lock_24)
          {
              //animation
              animation=AnimationUtils.loadAnimation(context,R.anim.push_right_in);
              view.startAnimation(animation);

              //storing the value of the title at that given index inside the title_held
              String title_held=titlesConstructor[position];
              //

              //crating a toast that will be displayed showing that the reason to why the programming language locked
              //its value is from the constructor ==reason;
              Toasty.custom(context, reason, R.drawable.android2, R.color.teal_700, Toasty.LENGTH_LONG, true, true).show();
              //

              if (title_held.equals("HTML"))
              {
                  title_held="Language for Generating The User interface Mostly On Websites";
              }
              //let the speech baby bot talks from the reason perspective
              new SpeechClass(context,reason+","+title_held);
              new VibratorLowly(context);
          }
          else
          {
              //animation
              animation=AnimationUtils.loadAnimation(context,R.anim.push_left_in);
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
