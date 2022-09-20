package com.shimmita.devopssociety.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.modal.RetrieveCareersInIT;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPersistentCareersAndMentors extends RecyclerView.Adapter<AdapterPersistentCareersAndMentors.MyViewHolder> {

    //Creating Context
    Context context;
    //
    //creating an arraylist which will hold the modal class retrieveCareers in IT its  Numbers or classes objects
    ArrayList<RetrieveCareersInIT> retrieveCareersInITArrayList;
    //

    //imageArray
    int[] images;
    //

    public AdapterPersistentCareersAndMentors(Context context,ArrayList<RetrieveCareersInIT> retrieveCareersInITArrayList,int[] images) {
        this.retrieveCareersInITArrayList = retrieveCareersInITArrayList;
        this.context=context;
        this.images=images;
    }

    @NonNull
    @Override
    public AdapterPersistentCareersAndMentors.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        View view = LayoutInflater.from(context).inflate(R.layout.populate_career_mentors,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPersistentCareersAndMentors.MyViewHolder holder, int position) {

        //
        String heading=retrieveCareersInITArrayList.get(position).getHeading();
        String description=retrieveCareersInITArrayList.get(position).getDescription();
        //

        //function that will facilitate setting the Heading String to Its respective TextView and Too For Description;
        holder.setDataOnToTheTetViewsHeadingAndDescription(heading,description);
        //

        holder.circleImageView.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return retrieveCareersInITArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //declaration of TextViews Header And Description respectively
        TextView textViewHeadingCareersAndMentorsITSector,
                textViewDescriptionCareersAndMentorsInITSector;
        //

        //declaration of the circleImageView
        CircleImageView circleImageView;
        //

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initialisation of TextViews Header And Description respectively
            textViewHeadingCareersAndMentorsITSector=itemView.findViewById(R.id.textViewHeadingCareersMentorsInIT);
            textViewDescriptionCareersAndMentorsInITSector=itemView.findViewById(R.id.textViewDescriptionCareerMentorsInIt);
            //
            //init of the imageView
            circleImageView=itemView.findViewById(R.id.imageCareersAndMentorIT);
            //

        }

        public void setDataOnToTheTetViewsHeadingAndDescription(String heading, String description) {
            //setting the text heading content onto the TextViewHeading
            textViewHeadingCareersAndMentorsITSector.setText(heading);
            //
            //setting the Text Description Onto The TextViewDescription
            textViewDescriptionCareersAndMentorsInITSector.setText(description);
            //
        }
    }
}
