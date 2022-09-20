package com.shimmita.devopssociety.mains;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.adapters.AdapterPersistentCareersAndMentors;
import com.shimmita.devopssociety.modal.RetrieveCareersInIT;

import java.util.ArrayList;

public class PersistentCareerSAndMentors extends AppCompatActivity {
    //imagesArray
    int[] images = {
            R.drawable.hacking11,
            R.drawable.hacker1,
            R.drawable.hacking5,
            R.drawable.imagee_hacker,
            R.drawable.hacking7,
            R.drawable.developer_image,
            R.drawable.hacking8,
            R.drawable.hacking4,
            R.drawable.hacking6

    };
    //
    //the View will make it easier for converting the nestedScrollView Into Persistent sheet
    View nestedScrollViewViewPersistentBehaviour;
    //
    //declaration Of The RealRecyclerView that is inside nestedScrollViewPersistent for holding data
    RecyclerView recyclerViewDataInsideNestedScrollViewPersistent;
    //

    //declaration of Button And BottomSheetBehaviour which will get behaviour from the view nestedScrollView
    Button buttonExpandThePersistentSheet;
    BottomSheetBehavior bottomSheetBehavior;
    //

    //declaration Of The AdapterPersistentCareersAndMentors
    AdapterPersistentCareersAndMentors adapterPersistentCareersAndMentors;
    //

    //declaration Of the ArrayList that holds ModalClassRetrieveCareersInIt Objects
    ArrayList<RetrieveCareersInIT> retrieveCareersInITArrayList;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careers_and_mentor_layout);

        //setting the title to career
        this.setTitle("CAREERS IN  I.T SECTORS OF TODAY");
        //

        //init of Variables using the function
        functionInitialisationVariables();
        //

        //function populating the nestedScrollView of PersistentBehaviour Type i.e different to normal nestedScrollView
        //since this one acts as persistent Sheet;
        functionPopulateTheRecyclerViewOfPersistentBehaviour();
        //

        //button click function to show persistent sheet and its contents
        buttonExpandThePersistentSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function show persistent sheet
                functionShowPersistentSheet();
                //
            }
        });
        //


    }

    private void functionPopulateTheRecyclerViewOfPersistentBehaviour() {
        //creating a layout manager of linear Layout Type
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //

        //init of the arrayList which will be added with modal class objects
        retrieveCareersInITArrayList = new ArrayList<>();
        //
        //adding the modal class objects into the arraylist and remembers that in the modal class constructor
        //we have heading and description thus being the way its

        //TODO:implement the image paths which are null after being satisfied with logics
        //made imagePath Null temporarily for they will be loaded by help of glide which is not done now
        //till reality implementation;
        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Software Engineering", "Creating the mobile Applications and Desktop Applications as " +
                " By Profession", null));

        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Network Engineering", "Configure the Companies Network Infrastructures Despite Their " +
                " Complexity", null));

        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Systems Administrator", "Control The Users Accounts Of A Particular Organisation", null));
        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Ethical Hacker", "Learn the Internals Of Computer Programming And Networking and " +
                "Be able To Bypass Some Restrictions In Terms Of Security and Data Access Services ", null));

        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Computer Architect", "Design Abd Build The Structure Of Computers ", null));

        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Computer Forensics", "Explore the Computers Data And be able to figure out" +
                " the trace or cause of a particular scenario in both the computer sector and also in the realWorld Life Situation", null));

        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Data Analyst", "Use as Set Of Particular Data Provided By The Organisastion or " +
                " the Computer Itself and use The mathematical Knowledge to Delude Valid Conclusions  From Them", null));

        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Computer Advisor", "Using The Knowledge gained From The Field Of Computing You Can Use It " +
                "Then To Advice Individuals or Organisations Thus Helping Them To Outdo Some Unforeseen Circumstances", null));

        retrieveCareersInITArrayList.add(new RetrieveCareersInIT("Computer Engineering", "Become A person Who has the ability to Rectify the " +
                "Computer Components like the Computer Hardware", null));


        //


        //initialisation Of The AdapterPersistent and passing in the
        adapterPersistentCareersAndMentors = new AdapterPersistentCareersAndMentors(this, retrieveCareersInITArrayList, images);
        //

        //setting the adapter to the RecyclerViewData
        recyclerViewDataInsideNestedScrollViewPersistent.setAdapter(adapterPersistentCareersAndMentors);
        //

        //setting the linearLayout manager on To The RecyclerView
        recyclerViewDataInsideNestedScrollViewPersistent.setLayoutManager(layoutManager);
        //

    }

    private void functionShowPersistentSheet() {
        //setting the state of the persistent sheet to expanded state]]
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        //
    }

    private void functionInitialisationVariables() {
        //button init
        buttonExpandThePersistentSheet = findViewById(R.id.buttonShowPersistentBottomSheet);
        //
        //init of recycler for persistent sheet design
        nestedScrollViewViewPersistentBehaviour = findViewById(R.id.nestedScrollViewPersistentSheet);
        //

        //init Bottom sheet
        bottomSheetBehavior = BottomSheetBehavior.from(nestedScrollViewViewPersistentBehaviour);
        //

        //init of recyclerData
        recyclerViewDataInsideNestedScrollViewPersistent = findViewById(R.id.recyclerDataInsideNestedScrollViewPersistent);
        //


    }


}