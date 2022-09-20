package com.shimmita.devopssociety.fragments_loggedin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.DisplayProgrammingLanguagesOnRecyclerOverview1;
import com.shimmita.devopssociety.mains.ExploreLearningServicesMainActivity;
import com.shimmita.devopssociety.mains.PersistentCareerSAndMentors;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import maes.tech.intentanim.CustomIntent;

public class LearningLoggedInFragmentClass extends Fragment {
    //logcat TAG for Debugging purposes
    private static final String TAG = "LearningLoggedInFragment";
    //

    //GlobalisationOf View Object
    View view;
    //

    //declaration  constraint layout variables
    ConstraintLayout constraintLayoutExploreServices,
            constraintLayoutExploreProgrammingWorld,
            constraintLayoutExploreCareers,
            constraintLayoutExploreCareerMentors;
    //
    //declaration circleImageViews
    CircleImageView circleImageViewExploreServices,
            circleImageViewExploreProgrammingWorld,
            circleImageViewExploreCareers,
            circleImageViewExploreCareerMentors;
    //declaration NestedViewLayout for handling some null parent clicking
    NestedScrollView nestedScrollViewParentLearningLoggedInFragment;
    //


    //empty constructor required
    public LearningLoggedInFragmentClass() {
    }
    //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.learning_loggedin_fragment, container, false);
        //code here
        //init aforementionedConstraintLayoutVariables
        functionInitialisationOfVariables();
        //
        //adding a function to the initialised varibles to enhance listeners performance
        functionListenerToTheInitVariables();
        //

        return view;
    }

    private void functionListenerToTheInitVariables() {
        //listener for constraint explore services
        constraintLayoutExploreServices.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View viewClicked) {
                //logcat monitor
                Log.d(TAG, "\nonClick: Inside constraintExploreServices");
                //

                Toast.makeText(getActivity(), "Explore Available Services Clicked", Toast.LENGTH_SHORT).show();
                //creating a popup list which will go act act as popupMenu
                List<String> listExploreServicesPopUp = new ArrayList<>();
                listExploreServicesPopUp.add("What's In Here? Click to Find Out");
                listExploreServicesPopUp.add("Click To Open Services Offered");
                listExploreServicesPopUp.add("Dismiss This Window");
                //creating arrayAdapter to populate the array list inside it;
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listExploreServicesPopUp);
                //
                //creating popUpListWindow
                ListPopupWindow listPopupWindow = new ListPopupWindow(requireActivity());
                //resizing the width of the window
                listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                //
                //setting the adapter on the listPopUpWindow to populate it with arraylist items
                listPopupWindow.setAdapter(stringArrayAdapter);
                //

                //setting a listener to the list popUp Window
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //logcat monitor the flow of the android operating System To Determine Whats bypassed inside the onItemClickedListener
                        Log.d(TAG, "onItemClick: insideTheOnItemClickListener");
                        //

                        //logcat monitor the selected value from a broad of choices offered
                        Log.d(TAG, "onItemClick: Item Clicked Is=>" + adapterView.getItemAtPosition(i).toString());
                        //

                        //using the if conditions to perform logical window redirection flow
                        //explanation of whats in here in a dialog style
                        if (adapterView.getItemAtPosition(i).toString().contains("What's In Here? Click to Find Out")) {

                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //

                            //call function explanation Of Whats In Here
                            functionExplanationOfWhatsInHere();
                            //
                        }
                        //open the main overview Window of grid view type which is of recycler view and modify
                        //features whenever possible
                        else if (adapterView.getItemAtPosition(i).toString().contains("Click To Open Services Offered")) {

                            //function to open intent programming Services of Gridview type
                            functionOpenLearningServicesOfGridViewStyle();
                            //

                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //
                        }
                        //dismiss operation
                        else if (adapterView.getItemAtPosition(i).toString().contains("Dismiss This Window")) {
                            //dismiss the popUp Window
                            listPopupWindow.dismiss();
                            //
                        }


                        //


                        //dismiss the window after every selection rto avoid Window Leaked Exception
                        listPopupWindow.dismiss();
                        //
                    }
                });
                //

                //setting AnchorView On to Which the List popup Is gonna be Upheld
                listPopupWindow.setAnchorView(viewClicked);
                listPopupWindow.show();
                //
            }
        });
        //

        //listener for constraint explore programing world
        constraintLayoutExploreProgrammingWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewProgrammingWorld) {
                //toasting showing explore world of programming clicked
                Toast.makeText(getActivity(), "Explore Programming World Clicked", Toast.LENGTH_SHORT).show();
                //

                //creating an array list of strings which will be populated in a popUpListWindow
                List<String> listFeaturesProgrammingWorld = new ArrayList<String>();
                listFeaturesProgrammingWorld.add("what's in here?click to find out");
                listFeaturesProgrammingWorld.add("open explore programming world");
                listFeaturesProgrammingWorld.add("dismiss this window");

                //creating an array adapter that will populate values into the listPopUpWindow
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1, listFeaturesProgrammingWorld);
                //

                //creating a list PopUpWindow
                ListPopupWindow listPopupWindow = new ListPopupWindow(requireActivity());
                listPopupWindow.setAdapter(stringArrayAdapter);
                //
                //setting the AnchorView Which Design The Upholding Of the PopUp The AnchorView
                listPopupWindow.setAnchorView(viewProgrammingWorld);
                //
                //setting onclick listener on the listPopUpWindow
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //logcat monitor
                        Log.d(TAG, "onItemClick: Inside OnclickMenuItemExploreProgrammingWorld");
                        //
                        //adding if condition to control page redirection
                        if (adapterView.getItemAtPosition(i).toString().contains("what's in here?click to find out")) {
                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //

                            //function to show alert dialog about what are features offered in programming world
                            functionWhatFeaturesOfferedInProgrammingWorld();
                            //

                        } else if (adapterView.getItemAtPosition(i).toString().contains("open explore programming world")) {
                            //function start Intent to show the programming languages offered of recyclerView Type
                            //too can be modified to suite relevance

                            //the function

                            functionOpenProgrammingLanguages();
                            //


                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //
                            //
                        } else if (adapterView.getItemAtPosition(i).toString().contains("dismiss this window")) {
                            //dismiss the listPopUpoWindow
                            listPopupWindow.dismiss();
                            //
                        }
                        //


                    }
                });
                //

                //showing the listPopUpWindow Using The Show Method w/c usually shown last
                listPopupWindow.show();
                //
            }
        });
        //

        //listener for constraint explore available careers in IT
        constraintLayoutExploreCareers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewExploreCareersInIT) {
                //toast showing careers available in It
                Toast.makeText(getActivity(), "Explore Careers Clicked", Toast.LENGTH_SHORT).show();
                //


                //creating an array list of strings which will be populated in a popUpListWindow
                List<String> listExploreAvailableCareersInIt = new ArrayList<String>();
                listExploreAvailableCareersInIt.add("what's in here?click to find out");
                listExploreAvailableCareersInIt.add("open explore career in IT");
                listExploreAvailableCareersInIt.add("dismiss this window");

                //creating an array adapter that will populate values into the listPopUpWindow
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1, listExploreAvailableCareersInIt);
                //

                //creating a list PopUpWindow
                ListPopupWindow listPopupWindow = new ListPopupWindow(requireActivity());
                listPopupWindow.setAdapter(stringArrayAdapter);
                //

                //setting anchor for listPopUpWindow from Where it will be held and shown
                listPopupWindow.setAnchorView(viewExploreCareersInIT);
                //

                //setting onclickListener on the list popUp
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //log cat monitor
                        Log.d(TAG, "onItemClick: InsideSetNItemClickListener Explore Careers In IT");
                        //


                        //adding if condition to make decisions decisively
                        if (adapterView.getItemAtPosition(i).toString().equals("what's in here?click to find out")) {
                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //

                            //function to show alert dialog about careers
                            functionShowAlertDialogAboutCareers();
                            //

                        } else if (adapterView.getItemAtPosition(i).toString().equals("open explore career in IT")) {
                            //intent to persistent sheet activity containing the careers and mentors
                            startActivity(new Intent(getActivity(),PersistentCareerSAndMentors.class));
                            CustomIntent.customType(requireActivity(),"fadein-to-fadeout");
                            //

                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //


                        } else if (adapterView.getItemAtPosition(i).toString().contains("dismiss this window")) {
                            //dismiss the list PopUp
                            listPopupWindow.dismiss();
                            //
                        }
                        //

                    }
                });
                //
                //showing the list popUp Using The show method
                listPopupWindow.show();
                //


            }
        });
        //

        //listener for constraint explore career mentors
        constraintLayoutExploreCareerMentors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewCareerMentors) {
                //toast showing career mentors clicked
                Toast.makeText(getActivity(), "Career Mentors Clicked", Toast.LENGTH_SHORT).show();
                //


                //creating an array list of strings which will be populated in a popUpListWindow
                List<String> listExploreCareerMentors = new ArrayList<String>();
                listExploreCareerMentors.add("what's in here?click to find out");
                listExploreCareerMentors.add("open explore career mentors");
                listExploreCareerMentors.add("dismiss this window");

                //creating an array adapter that will populate values into the listPopUpWindow
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1, listExploreCareerMentors);
                //

                //creating a list PopUpWindow
                ListPopupWindow listPopupWindow = new ListPopupWindow(requireActivity());
                listPopupWindow.setAdapter(stringArrayAdapter);
                //

                //setting anchor for which the list PopUp will be shown
                listPopupWindow.setAnchorView(viewCareerMentors);
                //

                //assigning onItem Click Listener on the listPopUP
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //logcat monitor
                        Log.d(TAG, "onItemClick: InsideOnItem Selected Listener Of Career Mentors");
                        //

                        //adding if conditions for decision making
                        if (adapterView.getItemAtPosition(i).toString().contains("what's in here?click to find out")) {
                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //

                            //function to call alertDialog showing what is that  of members about
                            functionDisplayMentorsAbout();
                            //
                        } else if (adapterView.getItemAtPosition(i).toString().contains("open explore career mentors")) {


                            //dismiss listPopUpWindowAfterClicking Item
                            listPopupWindow.dismiss();
                            //
                        } else if (adapterView.getItemAtPosition(i).toString().contains("dismiss this window")) {
                            //dismiss the list PoPUpWindow
                            listPopupWindow.dismiss();
                            //

                        }
                        //

                    }
                });

                //showing the popUpList Window
                listPopupWindow.show();
                //
            }
        });
        //
    }

    private void functionOpenProgrammingLanguages() {
        //start an intent which will trigger off the opening of the Programming languages present in that particular activity
        //lets put a string extra which will make the all programming languages get locked, because the intent opens the programming languages only for
        //a preview session not for learning as this will be done in the explore services only
        String keyLock = "data_from_intent_launch";
        String putLockOnTheLanguages = "locked_from_explore_programming_world";

        requireActivity().startActivity(new Intent(requireActivity(), DisplayProgrammingLanguagesOnRecyclerOverview1.class).
                putExtra(keyLock, putLockOnTheLanguages));
        //starting the intent animation  when the activity launches
        CustomIntent.customType(requireActivity(), "fadein-to-fadeout");
        //
        //toasty the successful launch
        Toasty.custom(requireActivity(), "Successfully Launched", R.drawable.android2, R.color.teal_700, Toasty.LENGTH_LONG, true, true).show();
        //
    }

    private void functionOpenLearningServicesOfGridViewStyle() {

        //creation of intent that will gonna trigger of opening of the services offered in gridView style. its the main center of learning where
        //the user will select a course from the variety offered;
        requireActivity().startActivity(new Intent(requireActivity(), ExploreLearningServicesMainActivity.class));
        //
        //starting the intent animation design using the custom intent library
        CustomIntent.customType(requireActivity(), "fadein-to-fadeout");
        //Toasting the successful launch
        Toasty.custom(requireActivity(), "Successfully Launched", R.drawable.ic_baseline_menu_book_learning, R.color.teal_700, Toasty.LENGTH_LONG, true, true).show();

        //

    }

    private void functionDisplayMentorsAbout() {
        //creating alertDialog Of mentors detailed information
        new MaterialAlertDialogBuilder(requireActivity())
                .setTitle("What's In Here")
                .setIcon(R.drawable.ic_baseline_info_24)
                .setMessage("if you select open explore career mentors options, a display list of available professional IT " +
                        "career mentors will get shown with their quality assurance links provided on how to get their " +
                        "goldly advices.from them you  will be able to figure out your windings and let them go and regenerate your new superbowl thoughts " +
                        "into a straightforward path of success.")
                .create().show();
        //
    }

    private void functionShowAlertDialogAboutCareers() {
        //alert dialog to show careers contents
        new MaterialAlertDialogBuilder(requireActivity())
                .setIcon(R.drawable.ic_baseline_info_24)
                .setTitle("What's In Here")
                .setMessage("when open explore careers in it option is clicked, a window showing various available careers " +
                        "in IT field of today Will be displayed.\nfrom there you will get acquainted with basic knowledge on that " +
                        "selected career and how to get focused on to your path progressively and successfully.")
                .create().show();
        //
    }

    private void functionWhatFeaturesOfferedInProgrammingWorld() {

        //create an alert dialog to show features

        new MaterialAlertDialogBuilder(requireActivity())
                .setTitle("What's In Here")
                .setIcon(R.drawable.ic_baseline_info_24)
                .setMessage("when you click on the open explore programming world option,you will be forwarded towards " +
                        "various programming languages which makes the world controlled into a single village like " +
                        "perspective.")
                .create().show();

        //

    }

    private void functionExplanationOfWhatsInHere() {
        //creating an alert dialog which will display information
        new MaterialAlertDialogBuilder(requireActivity())
                .setCancelable(true)
                .setIcon(R.drawable.ic_baseline_info_24)
                .setTitle("What's In Here")
                .setMessage("when you click on the open services offered option provided, you will be directed towards a window" +
                        " which you will be able to explore lots of the functionalities offered by the DevOps Society Team Kenya, from which you will select the appropriate " +
                        " one that suits your capabilities and too basing on your account type status.for instance;\n \nUsers With Premium or Super Account(s) are able to " +
                        "access most of the features offered while Users with normal account types will have limitations of accessing some functionalities.")
                .create().show();
        //
    }

    private void functionInitialisationOfVariables() {

        //init constraintDeclarations
        constraintLayoutExploreServices = view.findViewById(R.id.exploreAvailableServices);
        constraintLayoutExploreProgrammingWorld = view.findViewById(R.id.exploreProgrammingWorld);
        constraintLayoutExploreCareers = view.findViewById(R.id.exploreAvailableCareers);
        constraintLayoutExploreCareerMentors = view.findViewById(R.id.exploreCareerMentor);
        //

        //init circleImageViews
        circleImageViewExploreCareers = view.findViewById(R.id.circleImageExploreServices);
        circleImageViewExploreProgrammingWorld = view.findViewById(R.id.circleImageExploreProgrammingLanguages);
        circleImageViewExploreCareers = view.findViewById(R.id.circleImageExploreCareersDeeply);
        circleImageViewExploreCareerMentors = view.findViewById(R.id.circleImageExploreCareerMentors);
        //
    }
}
