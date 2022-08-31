package com.shimmita.devopssociety.mains;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.databases.Database;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Registration extends AppCompatActivity {
    public static final String FIREBASE_USER_COLLECTION ="DevOps Users";

    public static final int GALLERY_REQUEST_CODE = 100;
    public static final int CAMERA_REQUEST_CODE = 200;
    private static final String TAG = "RegistrationLog";
    static String string1, string2, string3, string4, string5, string6, string7_role_status_check;
    FirebaseAuth auth;
    String firebaseUser;
    FirebaseFirestore firebaseFirestore;
    CollectionReference documentReferenceAccountInformation;
    FirebaseStorage firebaseStorage;      //upload image
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;   //write to realtime
    DatabaseReference databaseReference;
    CircleImageView circleImageView_profile_picture_to_firebase;
    Uri imageUriPath = null;
    CheckBox checkBoxTermsAndConditionsAccept;
    Database db;
    TextInputEditText password_registration,
            confirm_password_registration,
            email_registration,
            username_registration,
            phoneNumber_registration;
    Animation animation;
    AnimationDrawable animationDrawable,
            animationDrawable_regimeMage,
            animationDrawable_layout_password,
            animationDrawable_layout_confirm,
            animationDrawable_layout_email,
            animationDrawable_layout_username,
            animationDrawable_layout_phoneNumber;
    LinearLayoutCompat linearLayoutCompat_parent;
    androidx.appcompat.widget.PopupMenu popupMenu_Registration_submission;

    String[] arrayCounties = {

            "Mombasa County",
            "Nairobi County",
            "Kisumu County",
            "Kwale County",
            "Kilifi County",
            "Lamu County",
            "Mandera County",
            "Garisa County",
            "Murang'a County",
            "Busia County",
            "Bungoma County",
            "Kakamega County",
            "Wajir County",
            "Tana River County",
            "Taita Taveta County",
            "Marsabit County",
            "Turkana County",
            "Kitui County",
            "Isiolo County",
            "Meru County",
            "Tharaka-Nithi County",
            "Machakos County",
            "Trans-Nzoia County",
            "West-Pokot County",
            "Samburu County",
            "Kiambu County",
            "Kajiado County",
            "Laikipia County",
            "Nyeri County",
            "Narok County",
            "Embu County",
            "Kericho County",
            "Migori County",
            "Bomet County",
            "Nyamira County",
            "Kisii County",
            "Siaya County",
            "Nakuru County",
            "Elgeyo/Marakwet County",
            "Uasin Gishu County",
            "Nyandarua County",
            "Kirinyaga County",
            "Homa Bay County",
            "Makueni County",
            "Nandi County",
            "Baringo County",
            "Vihiga County"
    };
    String[] passion = {
            "Software Engineering Deep",
            "Web Development Techniques Deep",
            "Networks Engineering Deep",
            "Database Management Deep",
            "Machine Learning Technology",
            "Artificial Intelligence Technology",
            "Penetration Testing (test security flaws)",
            "Reverse Engineering (modify apps or System) ",
            "Data Science (Python)",
            "Cloud Computing Technology",
            "Containerisation with (Docker)",
            "Systems Administration Deep",
            "Linux Operating System Deep",
            "Windows Operating System Deep",
            "Dark Web (how to get into)",
            "Ethical Hacking with Linux (white hat)",
            "Mobile Application Development(software)",
            "Desktop Application Development(software)",
            "Computer Virus And Counteracting ",
            "Cyber Security (Cyber War)->Linux",
            "Computer Forensics (trace a cause)"
    };
    String[] string_level_of_knowledge = {
            "Intermediate level",
            "Advanced Mode Level",
            "Professional Level"
    };
    String[] gender =
            {"Male",
                    "Female",
                    "Other"};
    String[] occupation = {"University Undergraduate Student",
            "University PostGraduate Student",
            "University Graduate Student"
    };

    String[] universitiesInKenya = {
            "Maseno University",
            "Nairobi University",
            "Laikipia University",
            "Meru University",
            "Gretsa University",
            "DayStar University",
            "Garissa University",
            "Technical University Of Mombasa",
            "Pwani University",
            "Jomo Kenyatta University",
            "Kenyatta University",
            "Moi University",
            "Chuka University",
            "Kibabii University",
            "Saint Paul's University",
            "Maasai Mara University",
            "Alupe University",
            "Kisii University",
            "Adventist University Of Africa",
            "Africa International University",
            "Africa Nazarene University",
            "Amref International University",
            "Dedan Kimathi University",
            "Egerton University",
            "Great Lakes University",
            "International Leadership University",
            "Jaramogi Oginga Odinga University",
            "Kabarak University",
            "KAG University",
            "Karatina Universty",
            "KCA University",
            "Kenya Highlands University",
            "Kenya Methodist University",
            "Kirinyaga University",
            "Kirir Women's University",
            "Lukenya University",
            "Machakos University",
            "Management University Of Africa",
            "Masinde Muliro University",
            "Mount Kenya University",
            "Multimedia University ",
            "Murang'a University",
            "Pan Africa Christian University",
            "Pioneer International University",
            "RAF International University",
            "Riara University",
            "Rongo University",
            "Scott Christian University",
            "South Eastern Kenya University",
            "Taita Taveta Universty",
            "Strathmore University",
            "Technical university Of Kenya",
            "Catholic University Of Eastern Africa",
            "East African University",
            "Presbyterian University",
            "Umma University",
            "United States International university",
            "Baraton University",
            "Embu University",
            "Kabianga University",
            "Zetech University",
            "Uzima University",
            "University Of Eldoret",
            "Turkana University",
            "Tom Mboya University",
            "Tharaka University",
            "Tangaza University",
            "Koitaleel Samoei University",
            "Kaimosi Univesity",
            "SEKU University",
            "Bomet University",
            "Co-operative University Of Kenya",
            "Marist International University",
            "Management University of Africa"
    };


    String[] registerAccountAs = {
            "Normal User (Less Privileges)",
            "Super User (More Privileges)"
    };


    ArrayAdapter<String> arrayAdapter_counties;
    ArrayAdapter<String> arrayAdapter_passion;
    ArrayAdapter<String> arrayAdapter_level_of_knowledge;
    ArrayAdapter<String> arrayAdapter_gender;
    ArrayAdapter<String> arrayAdapter_occupation;
    ArrayAdapter<String> arrayAdapter_universityName;
    ArrayAdapter<String> arrayAdapter_registerAccount_As;


    Spinner spinner_county,
            spinner_passion,
            spinner_level_of_knowledge,
            spinner_gender,
            spinner_occupation,
            spinnerUniversityRegistrationName,
            spinner_register_account_as;

    //String selected_county, selected_level_of_knowledge, selected_passion, selected_gender, selected_ocupation_level_of_study;

    String passwordReg,
            confirmPasswordReg,
            emailReg,
            usernameReg,
            phoneNumberReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        documentReferenceAccountInformation = firebaseFirestore.collection(FIREBASE_USER_COLLECTION);


        // db = new Database(Registration.this);

        checkInternet();

        this.setTitle(getString(R.string.registration_activity_title));
        animationDrawable = (AnimationDrawable) findViewById(R.id.parent_registration_constraint).getBackground();
        animationDrawable_regimeMage = (AnimationDrawable) findViewById(R.id.image_person_Registration).getBackground();
        animationDrawable_layout_password = (AnimationDrawable) findViewById(R.id.layoutPassword_registratrion).getBackground();
        animationDrawable_layout_confirm = (AnimationDrawable) findViewById(R.id.layout_confirmation_passd).getBackground();
        animationDrawable_layout_email = (AnimationDrawable) findViewById(R.id.layout_emailReg).getBackground();
        animationDrawable_layout_username = (AnimationDrawable) findViewById(R.id.layout_username_reg).getBackground();
        animationDrawable_layout_phoneNumber = (AnimationDrawable) findViewById(R.id.layout_phoneNumber_registration).getBackground();


        password_registration = findViewById(R.id.material_editText_input_password1_registration);
        confirm_password_registration = findViewById(R.id.material_editText_input_password2_registration);
        email_registration = findViewById(R.id.material_editText_input_email_registration);
        username_registration = findViewById(R.id.material_editText_input_username_registration);
        phoneNumber_registration = findViewById(R.id.material_editText_input_phoneNumber_registration);
        //checkcredentilFunction implementation of use

        linearLayoutCompat_parent = findViewById(R.id.parent_reg_linear_in_parent);
        spinner_county = findViewById(R.id.spinner_registration_county);
        spinner_passion = findViewById(R.id.spinner_registration_passion);
        spinner_level_of_knowledge = findViewById(R.id.spinner_registration_passion_level_of_knowledge);
        spinner_gender = findViewById(R.id.spinner_registration_gender);
        spinner_occupation = findViewById(R.id.spinner_registration_occupation);
        spinnerUniversityRegistrationName = findViewById(R.id.spinner_registration_universityName);
        spinner_register_account_as = (Spinner) findViewById(R.id.spinner_registration_role);

        //initialisation of profile pictureCircle
        circleImageView_profile_picture_to_firebase = (CircleImageView) findViewById(R.id.circularImageProfileHolder);
        //

        //checkBoxInit

        checkBoxTermsAndConditionsAccept = (CheckBox) findViewById(R.id.checkBoxTermsAndConditions);
        //


        spinner_county.setPrompt(getString(R.string.promp_head_spinner));
        arrayAdapter_counties = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayCounties);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arrayAdapter_counties.sort(Comparator.naturalOrder());
        }
        spinner_county.setAdapter(arrayAdapter_counties);
        spinner_county.requestFocus();


        arrayAdapter_passion = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, passion);
        spinner_passion.setPrompt(getString(R.string.passion_spinner_prompt_tittle));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arrayAdapter_passion.sort(Comparator.naturalOrder());
        }
        spinner_passion.setAdapter(arrayAdapter_passion);
        spinner_passion.requestFocus();


        spinner_level_of_knowledge.setPrompt(getString(R.string.spinner_dialog_knowlege_title));
        arrayAdapter_level_of_knowledge = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, string_level_of_knowledge);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arrayAdapter_level_of_knowledge.sort(Comparator.naturalOrder());
        }
        spinner_level_of_knowledge.setAdapter(arrayAdapter_level_of_knowledge);
        spinner_gender.setPrompt("gender Selection");
        arrayAdapter_gender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gender);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            arrayAdapter_gender.sort(Comparator.naturalOrder());
        }
        spinner_gender.setAdapter(arrayAdapter_gender);
        spinner_gender.requestFocus();


        spinner_occupation.setPrompt("student Level Of Education");
        arrayAdapter_occupation = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, occupation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            arrayAdapter_occupation.sort(Comparator.naturalOrder());
        }
        arrayAdapter_occupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_occupation.setAdapter(arrayAdapter_occupation);
        spinner_occupation.requestFocus();


        spinnerUniversityRegistrationName.setPrompt("Select The University Attended ");
        arrayAdapter_universityName = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, universitiesInKenya);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            arrayAdapter_universityName.sort(Comparator.naturalOrder());
        }
        spinnerUniversityRegistrationName.setAdapter(arrayAdapter_universityName);
        spinnerUniversityRegistrationName.requestFocus();


        spinner_register_account_as.setPrompt("Normal Or Admin User ?");
        arrayAdapter_registerAccount_As = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, registerAccountAs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            arrayAdapter_registerAccount_As.sort(Comparator.naturalOrder());
        }
        spinner_register_account_as.setAdapter(arrayAdapter_registerAccount_As);

        spinner_register_account_as.requestFocus();


        //
        spinner_county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string1 = parent.getSelectedItem().toString();
                Toast.makeText(Registration.this, "Selected:" + string1, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_county.requestFocus();
            }
        });


        spinner_passion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string2 = parent.getSelectedItem().toString();
                Toast.makeText(Registration.this, "Selected:" + string2, Toast.LENGTH_LONG).show();
                //implementation of separate thread since the exec of this part takes longer than the time main function allocated to It


                Handler handler = new Handler();

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        Log.d(TAG, "run: threadRunning " + Thread.currentThread().getName());

                        handler.post(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                checkDetailsOfPassionSelection(string2);
                            }
                        });
                    }
                });

                thread.setName("threadHandlingPassionSelection");
                thread.start();

                //

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                spinner_passion.requestFocus();
            }
        });


        spinner_level_of_knowledge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string3 = parent.getSelectedItem().toString();
                Toast.makeText(Registration.this, "Selected:" + string3, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_level_of_knowledge.requestFocus();
            }
        });


        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string4 = parent.getSelectedItem().toString();
                Toast.makeText(Registration.this, "Selected:" + string4, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_gender.requestFocus();
            }
        });

        spinner_occupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string5 = parent.getSelectedItem().toString();
                Toast.makeText(Registration.this, "Selected:" + string5, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_occupation.requestFocus();
            }
        });

        spinnerUniversityRegistrationName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                string6 = adapterView.getSelectedItem().toString();
                Toast.makeText(Registration.this, "Selected: " + string6, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                spinnerUniversityRegistrationName.requestFocus();
            }
        });


        spinner_register_account_as.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                string7_role_status_check = adapterView.getSelectedItem().toString();
                Toast.makeText(Registration.this, "Selected: " + string7_role_status_check, Toast.LENGTH_LONG).show();

                if (string7_role_status_check.contains("Super")) {
                    alertUserOfVerificationForPayment();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                spinner_register_account_as.requestFocus();
            }
        });


        //circleImageOnclickListener
        circleImageView_profile_picture_to_firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(Registration.this, "Yessss", Toast.LENGTH_SHORT).show();*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    new VibratorLowly(Registration.this);
                }
                PopupMenu popupMenuCameraOrGallary = new PopupMenu(Registration.this, view);
                popupMenuCameraOrGallary.inflate(R.menu.camera_or_gallary);
                popupMenuCameraOrGallary.setForceShowIcon(Boolean.parseBoolean("true"));
                popupMenuCameraOrGallary.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.camera:

                                cameraCallingFunction();
                                return true;
                            case R.id.gallery:

                                galleryCallingFunction();
                                return true;
                            case R.id.noneOfThem:
                                alertDialogNoneOfTheSelectedOnCamera();
                                return true;


                            default:
                                return false;
                        }

                    }
                });
                popupMenuCameraOrGallary.show();


            }
        });


        //checkbox

        checkBoxTermsAndConditionsAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkBoxTermsAndConditionsAccept.setChecked(true);
                //enabling submit floatingActionButton of Registration and giving it a new background Color
                findViewById(R.id.floatingActionButtonRegistration).setVisibility(View.VISIBLE);
                //Instantiation Of BottomSheet class With Terms And Conditions
                BottomSheetWithTerms bottomSheetWithTerms = new BottomSheetWithTerms();
                bottomSheetWithTerms.show(getSupportFragmentManager(), "Terms And Conditions");

            }
        });


        //

/*
        selected_county = string1;

        selected_passion = string2;

        selected_level_of_knowledge = string3;

        selected_gender = string4;

        selected_ocupation_level_of_study = string5;
        */


        Log.d(TAG, "onCreate: START\n");
        Log.d(TAG, "\nregistrationCredentialCheck: county-> " + string1);
        Log.d(TAG, "\nregistrationCredentialCheck: knowledge->" + string2);
        Log.d(TAG, "\nregistrationCredentialCheck: passion->" + string3);
        Log.d(TAG, "\nregistrationCredentialCheck: gender->" + string4);
        Log.d(TAG, "\nregistrationCredentialCheck: ocuupation->" + string5);
        Log.d(TAG, "\nregistrationCredentialCheck: universityName->" + string6);
        Log.d(TAG, "\nonCreate: END");

    }

    private void cameraCallingFunction() {

        String[] null_camera_References = {
                "camera service is currently unavailable!",
                "camera service is busy try again!",
                "unknown error occurred while opening camera!",
                "try again with gallery option!",
                "process was forced to shutdown, unknown error!"
        };

        Random random1 = new Random();
        int random = random1.nextInt(5);

        Toasty.custom(getApplicationContext(), null_camera_References[random], R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {


                imageUriPath = data.getData();

                //
                circleImageView_profile_picture_to_firebase.setImageURI(imageUriPath);
                //sets the image on the circular

                //activating The Visibility of Terms And Conditions for CheckboksTerms..
                checkBoxTermsAndConditionsAccept.setVisibility(View.VISIBLE);
                //

                Toasty.custom(getApplicationContext(), "Congratulations! Lets Proceed", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                //

            } else if (resultCode == RESULT_CANCELED) {
                Toasty.custom(getApplicationContext(), "Cancelled By The User, No Image Was Selected!", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                circleImageView_profile_picture_to_firebase.requestFocus();
                circleImageView_profile_picture_to_firebase.setBorderColor(Color.MAGENTA);
            }

        }
    }

    private void galleryCallingFunction() {
        Intent intentGallery = new Intent();
        intentGallery.setType("image/*");

        intentGallery.setAction(Intent.ACTION_GET_CONTENT);  //all Works Well
        // intentGallery.setAction(Intent.ACTION_PICK);        //

        startActivityForResult(Intent.createChooser(intentGallery, "Pick The Image Of Your Choice"), GALLERY_REQUEST_CODE);

    }


    private void alertDialogNoneOfTheSelectedOnCamera() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("No Profile Picture")
                .setIcon(R.drawable.ic_baseline_block_24)
                .setMessage("Operation Is Currently Not Permitted. Cannot Have no Profile Image!")
                .setCancelable(false)
                .setPositiveButton("Ok, Lets Add Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toasty.custom(getApplicationContext(), "select either Camera or Gallery !", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                        circleImageView_profile_picture_to_firebase.setBorderColor(Color.MAGENTA);
                        circleImageView_profile_picture_to_firebase.requestFocus();


                    }
                }).setNegativeButton("Quit Me!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        startActivity(new Intent(Registration.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                        Runtime.getRuntime().exit(0);

                    }
                }).create().show();
    }


    //function Checking the passion user selected


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkDetailsOfPassionSelection(String string2) {

        if (string2.contains("Reverse")) {
            new MaterialAlertDialogBuilder(Registration.this)
                    .setTitle(string2)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_warning)
                    .setMessage("\nbe assured that,the skills gained in " + string2 + " is for educational purposes only " +
                            "if used to violet other developers terms and conditions concerning the use of their applications and products, " +
                            "you will be responsible for the damage caused\nNOT DEVOPS SOCIETY!")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toasty.custom(getApplicationContext(), "Good Lets Proceed", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                            dialogInterface.dismiss();

                        }
                    }).create().show();
        } else if (string2.contains("Hacking")) {
            new MaterialAlertDialogBuilder(Registration.this)
                    .setTitle(string2)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_warning)
                    .setMessage("\nbe assured that,the skills gained in " + string2 + " is for educational purposes only " +
                            "if used to violet other developers terms and conditions concerning the use of their applications and products, " +
                            "you will be responsible for the damage caused\nNOT DEVOPS SOCIETY!")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toasty.custom(getApplicationContext(), "Good Lets Proceed", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }


        if (string2.contains("Mobile")) {
            new MaterialAlertDialogBuilder(Registration.this)
                    .setTitle("Mobile App Development")
                    .setIcon(R.drawable.ic_baseline_info_24)
                    .setCancelable(false)
                    .setMessage("\nIt's Assumed That You Have Learnt The Basics and Internals Of Software Development Which Have Been Provided " +
                            "Under Software Engineering Or You Have Learned Anywhere Else. This Section Deals Into Mobile Development" +
                            "Theoretically And Practically In Advanced Mode. If This Is Not The Case Please Select Software Engineering Provided " +
                            "In The DropDown Menu To Learn The Basics First.\n")
                    .setPositiveButton("already having basics", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toasty.custom(getApplicationContext(), "Congratulations! Lets Proceed", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                            dialogInterface.dismiss();

                        }
                    }).setNegativeButton("software basics", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toasty.custom(getApplicationContext(), "Please Select Software Engineering Deep", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();

                            spinner_passion.requestFocus();
                            spinner_passion.setBackgroundColor(Color.MAGENTA);
                            new VibratorLowly(Registration.this);
                            dialogInterface.dismiss();

                        }
                    }).create().show();

        } else if (string2.contains("Desktop")) {

            new MaterialAlertDialogBuilder(Registration.this)
                    .setTitle("Desktop App Development")
                    .setIcon(R.drawable.ic_baseline_info_24)
                    .setCancelable(false)
                    .setMessage("\nIt's Assumed That You Have Learnt The Basics and Internals Of Software Development Which Have Been Provided " +
                            "Under Software Engineering Or You Have Learned Anywhere Else. This Section Deals Into Desktop Development " +
                            "Theoretically And Practically In Advanced Mode. If This Is Not The Case Please Select Software Engineering Provided" +
                            "In The DropDown Menu To Learn The Basics First.\n")
                    .setPositiveButton("already have software basics", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toasty.custom(getApplicationContext(), "Congratulations! Lets Proceed", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                            dialogInterface.dismiss();
                        }
                    }).setNegativeButton("software basics", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toasty.custom(getApplicationContext(), "Please Select Software Engineering Deep", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                            spinner_passion.requestFocus();
                            spinner_passion.setBackgroundColor(Color.MAGENTA);
                            new VibratorLowly(Registration.this);
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }


    }


    //


    //function alert User Of Verification Of Payment To Be approved as Admin

    public void alertUserOfVerificationForPayment() {
        new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                .setIcon(R.drawable.ic_baseline_payment_24)
                .setCancelable(false)
                .setTitle("Payment of Ksh. 100")
                .setMessage("\nYou will get registered as a user with  privileged roles on payment of non refundable fee of ksh.100 only.\n" +
                        "\n Benefits :\n1.you will be able to work on jobs posted by clients on this platform,by selecting the one which is appropriate for you.\n" +
                        "\n2.you will unlock very golden study materials of software(programming),networking,hacking,virus_tricks,the power of linux OS and many more.\n" +
                        "\n3.marketing of your final computing Projects if any to the public, to show your incredible abilities\n" +
                        "\n4.connecting your account to the most superior developers in the country and around the world who have approved DeVOps Society\n" +
                        "\n5.Enhancing your job connection to the available clients,institutions or company by forwarding their legitimate links to you !\n")
                .setPositiveButton("Understood, Make Payment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        new MaterialAlertDialogBuilder(Registration.this)
                                .setTitle("Payment Confirmation")
                                .setIcon(R.drawable.ic_baseline_payment_24)
                                .setCancelable(false)
                                .setMessage("\nDeveloper's number will be forwarded on the dial Screen of your phone use it in making " +
                                        "M-Pesa Payment, once done, (message your registration name or email to the Number). then your account will be approved to SUPPER USER.")
                                .setPositiveButton("Ok,forward the number", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+254757450727")));
                                        Toasty.custom(getApplicationContext(), "Number Forwarded Successfully", R.drawable.ic_baseline_whatshot_24, R.color.purple_700, Toasty.LENGTH_LONG, true, true).show();
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();

                    }
                }).setNegativeButton("Understood, not Interested", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        findViewById(R.id.parent_reg_linear_in_parent).setVisibility(View.INVISIBLE);
                        new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                                .setTitle("Further Notification")
                                .setCancelable(false)
                                .setIcon(R.drawable.ic_baseline_info_24)
                                .setMessage("you will continue  using your account freely but with limited functionalities. " +
                                        "Still you can upgrade later when interested on successful login into your account profile after registration as NORMAL USER\n")
                                .setPositiveButton("wonderful,thanks a million", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Snackbar.make(findViewById(R.id.parent_registration_constraint), "continue registration as NORMAL USER", Snackbar.LENGTH_INDEFINITE)
                                                .setAction("OK", view -> {
                                                    findViewById(R.id.parent_reg_linear_in_parent).setVisibility(View.VISIBLE);
                                                    spinner_register_account_as.requestFocus();
                                                    spinner_register_account_as.setBackgroundColor(Color.MAGENTA);
                                                }).setBackgroundTint(Color.BLACK).setActionTextColor(Color.parseColor("#FF8411")).show();

                                        dialogInterface.dismiss();

                                    }
                                }).create().show();


                    }
                }).create().show();
    }


    ///


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void functionFloatingButtonSubmitClicked(View view) {
        animation = AnimationUtils.loadAnimation(Registration.this, R.anim.abc_slide_in_top);
        view.startAnimation(animation);

        popupMenu_Registration_submission = new androidx.appcompat.widget.PopupMenu(Registration.this, view);
        popupMenu_Registration_submission.inflate(R.menu.submission_menu_registration);
        popupMenu_Registration_submission.setForceShowIcon(Boolean.parseBoolean("true"));
        popupMenu_Registration_submission.show();

        popupMenu_Registration_submission.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.submit_now_registration:
                    registrationCredentialCheck();

                    return true;

                case R.id.submit_foreigner_registration:
                    Snackbar.make(linearLayoutCompat_parent, "Currently Unavailable.\nWill Be Updated Soon", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", v -> Snackbar.make(v, "Welcome", Snackbar.LENGTH_SHORT)
                                    .setTextColor(getResources().getColor(R.color.teal_700))
                                    .setBackgroundTint(Color.parseColor("#FFA737"))
                                    .show())
                            .setTextColor(Color.parseColor("#9EFF12"))
                            .setActionTextColor(Color.parseColor("#FF0A05"))
                            .setBackgroundTint(getResources().getColor(R.color.purple_200))
                            .show();

                    return true;
                default:
                    return false;
            }

        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registrationCredentialCheck() {

        passwordReg = Objects.requireNonNull(password_registration.getText()).toString();
        confirmPasswordReg = Objects.requireNonNull(confirm_password_registration.getText()).toString();
        emailReg = Objects.requireNonNull(email_registration.getText()).toString();
        usernameReg = Objects.requireNonNull(username_registration.getText()).toString();
        phoneNumberReg = Objects.requireNonNull(phoneNumber_registration.getText()).toString();

        if (TextUtils.isEmpty(passwordReg)) {
            password_registration.setError("cannot be empty !");
            password_registration.requestFocus();
            new MakeVibrator(Registration.this);

        } else if (TextUtils.isEmpty(confirmPasswordReg)) {
            confirm_password_registration.setError("cannot be empty !");
            confirm_password_registration.requestFocus();
            new MakeVibrator(Registration.this);

        } else if (TextUtils.isEmpty(emailReg)) {
            email_registration.setError("cannot be empty !");
            email_registration.requestFocus();
            new MakeVibrator(Registration.this);

        } else if (!(emailReg.contains("@") && emailReg.contains(".com") || (emailReg.contains("gmail")) || emailReg.contains("yahoo"))) {
            email_registration.setError("detected invalid email type !\nfix with (@) or (.com) or both");
            email_registration.requestFocus();
            new MakeVibrator(Registration.this);

        } else if (TextUtils.isEmpty(usernameReg)) {
            username_registration.setError("cannot be empty !");
            username_registration.requestFocus();
            new MakeVibrator(Registration.this);

        } else if (!(usernameReg.contains(" "))) {
            new AlertDialog.Builder(Registration.this)
                    .setTitle("Profile Name Validation")
                    .setIcon(R.drawable.hacker_masked1)
                    .setMessage("please enter a valid name. the name(" + usernameReg + ")is invalid")
                    .setCancelable(false)
                    .setPositiveButton("validate", (dialogInterface, i) -> {
                        username_registration.requestFocus();
                        new MakeVibrator(Registration.this);
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
        } else if (TextUtils.isEmpty(phoneNumberReg)) {
            phoneNumber_registration.setError("cannot be empty !");
            phoneNumber_registration.requestFocus();
            new MakeVibrator(Registration.this);
        } else // no missing Credentials
        {
            if (passwordReg.equals(confirmPasswordReg)) {
                if (passwordReg.length() < 8) {
                    dialogWeakPassword();
                } else if (phoneNumberReg.contains("+254") && (phoneNumberReg.length() < 13)) {
                    Toast.makeText(this, phoneNumberReg + " is less than the required standard !", Toast.LENGTH_LONG).show();
                    Snackbar.make(linearLayoutCompat_parent, "number is less than 13 characters\nwhen country code's included !", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK,I Fix", v -> Snackbar.make(v, " simply fix and get registered", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(Color.parseColor("#3E644B"))
                                    .show())
                            .setBackgroundTint(getResources().getColor(R.color.white))
                            .setTextColor(getResources().getColor(R.color.purple_700))
                            .setActionTextColor(Color.parseColor("#FF6A04"))
                            .show();

                    phoneNumber_registration.setError("number less than 13 characters !\ninclusive is (+254)");
                    phoneNumber_registration.requestFocus();
                    new MakeVibrator(Registration.this);
                } else if (phoneNumberReg.length() < 13 && (!phoneNumberReg.contains("+254"))) {
                    phoneNumber_registration.setError("detected invalid number type!\nDoes not contain 13 characters long.\nAlso missing is(+254). begin with (+254...)");
                    phoneNumber_registration.requestFocus();
                    new MakeVibrator(Registration.this);
                } else if (!(phoneNumberReg.contains("+254"))) {
                    phoneNumber_registration.setError("detected invalid number type!\nbegin with (+254...)");
                    phoneNumber_registration.requestFocus();
                    new MakeVibrator(Registration.this);
                } else if (phoneNumberReg.contains(".") || phoneNumberReg.contains("#") || phoneNumberReg.contains("(") || phoneNumberReg.contains(")")
                        || phoneNumberReg.contains("N") || phoneNumberReg.contains("/") || phoneNumberReg.contains(",") || phoneNumberReg.contains(";")
                        || phoneNumberReg.contains("-") || phoneNumberReg.contains("*")) {
                    phoneNumber_registration.setError(" ERROR: Very Incorrectly Typed Number !");
                    phoneNumber_registration.requestFocus();
                    new MakeVibrator(Registration.this);

                } else //Everything @finest
                {
                    Log.d(TAG, "\n\nregistrationCredentialCheck: FloatingButtonBEGIN\n");
                    Log.d(TAG, "\nregistrationCredentialCheck: county-> " + string1);
                    Log.d(TAG, "\nregistrationCredentialCheck: passion->" + string2);
                    Log.d(TAG, "\nregistrationCredentialCheck: knowledge->" + string3);
                    Log.d(TAG, "\nregistrationCredentialCheck: gender->" + string4);
                    Log.d(TAG, "\nregistrationCredentialCheck: ocupation->" + string5);
                    Log.d(TAG, "\nregistrationCredentialCheck: universityName->" + string6);
                    Log.d(TAG, "\nregistrationCredentialCheck: FloatingButtonEND");

                    registerUser(emailReg, passwordReg);

                }

            } else  //password matching failure
            {
                Toast.makeText(this, "passwords not matching!", Toast.LENGTH_LONG).show();

                password_registration.setError("not matching !");
                confirm_password_registration.setError("not matching !");
                password_registration.requestFocus();
                confirm_password_registration.requestFocus();
                new MakeVibrator(Registration.this);
            }

        }


    }

    private void registerUser(String emailReg, String passwordReg) {


        //defining Progress Dialog
        ProgressDialog pg = new ProgressDialog(Registration.this);
        pg.setTitle(usernameReg.toUpperCase(Locale.ROOT));
        pg.setMessage("Registering");
        pg.create();
        pg.show();


        auth.createUserWithEmailAndPassword(emailReg, passwordReg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    //current user ID
                    firebaseUser = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                    //

                    pg.dismiss();
                    new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                            .setTitle("Registration Step 1/3 Completed")
                            .setCancelable(false)
                            .setMessage(usernameReg + " You have successfully Completed Step One Of registration Process You're are allowed to make proceed ")
                            .setIcon(R.drawable.ic_baseline_check)
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    //function entering data into the firebase Firestore
                                    functionEnteringDataIntoFirestore();
                                    //
                                }
                            }).create().show();

                } else {
                    functionErrorOfRegistrationProcess(task, pg);
                }

            }
        });


    }

    private void functionEnteringDataIntoFirestore() {
        //progressDialogSaving Account details FireStore
        ProgressDialog progressDialogLocally = new ProgressDialog(Registration.this);
        progressDialogLocally.setCancelable(false);
        progressDialogLocally.setTitle(usernameReg);
        progressDialogLocally.setMessage("saving account details...");
        progressDialogLocally.create();


        //showing the progress
        progressDialogLocally.show();

        //

        String keyUsername = "Username";    //usernameReg;
        String keyEmail = "Email";        //emailReg;
        String keyPhoneNumber="PhoneNumber";
        String keyPassword = "Password";    //passwordReg;
        String keyCounty = "County";        //string1;
        String keyPassion = "Passion";      //string2;
        String keyKnowledge = "Knowledge";     //string3;
        String keyGender = "Gender";           //string4;
        String keyOccupation = "Occupation";    //string5;
        String keyUniversityAttended = "University";    //string6;
        String keyIsAdmin = "Role";                 //string7_role_status_check

        //added string values
        String keyAdminChatSend = "AdminChatSend";   //sends to admin
        String valueAdminChatSend = "Send";
        //
        String keyAdminChatReceive = "AdminChatReceive";   //receives from admin
        String valueAdminChatReceive = "Receive";
        //
        //chat implementation for members to be completed here after grasp of logic
        //

        Map<String, Object> mapAccountDetails = new HashMap<>();

        mapAccountDetails.put(keyUsername, usernameReg);
        mapAccountDetails.put(keyEmail, emailReg);
        mapAccountDetails.put(keyPhoneNumber,phoneNumberReg);
        mapAccountDetails.put(keyPassword, passwordReg);
        mapAccountDetails.put(keyCounty, string1);
        mapAccountDetails.put(keyPassion, string2);
        mapAccountDetails.put(keyKnowledge, string3);
        mapAccountDetails.put(keyGender, string4);
        mapAccountDetails.put(keyOccupation, string5);
        mapAccountDetails.put(keyUniversityAttended, string6);
        mapAccountDetails.put(keyIsAdmin, string7_role_status_check);
        mapAccountDetails.put(keyAdminChatSend, valueAdminChatSend);
        mapAccountDetails.put(keyAdminChatReceive, valueAdminChatReceive);

        //adding firestore the data
        documentReferenceAccountInformation.document(firebaseUser).set(mapAccountDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //dismiss the progress
                    progressDialogLocally.dismiss();
                    //

                    new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                            .setTitle("Registration Step 2/3 Successful!")
                            .setCancelable(false)
                            .setMessage(usernameReg + " You have successfully Completed Step Two Of registration Process You're are allowed to make the last step " +
                                    "of registration")
                            .setIcon(R.drawable.ic_baseline_check)
                            .setPositiveButton("Lets Complete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    //call function complete registration of uploading image
                                    functionUploadImageToFirebaseStorage();

                                    //
                                }
                            }).create().show();


                } else {
                    //dismiss progress
                    progressDialogLocally.dismiss();
                    //

                    new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                            .setTitle("Something Went Wrong!")
                            .setCancelable(false)
                            .setMessage(usernameReg + " Your Registration  Process Halted Due To:\n" + task.getException().getMessage())
                            .setPositiveButton("Retry Registration", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                }
                            }).create().show();

                }
            }
        });

    }

    private void functionUploadImageToFirebaseStorage() {

        //progress for setting Profile Picture
        ProgressDialog progressDialogLocallyImage = new ProgressDialog(Registration.this);
        progressDialogLocallyImage.setCancelable(false);
        progressDialogLocallyImage.setTitle(usernameReg);
        progressDialogLocallyImage.setMessage("Updating Profile...");
        progressDialogLocallyImage.create();


        //showing the progress
        progressDialogLocallyImage.show();

        //
        //

        String profileImagesLocally = "ProfileImages";

        storageReference = firebaseStorage.getReference().child(profileImagesLocally).child(firebaseUser).child(usernameReg);
        storageReference.putFile(imageUriPath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()) {
                    //
                    progressDialogLocallyImage.dismiss();
                    //

                    //alerting User that last step is completed
                    new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                            .setTitle("Registration Step 3/3 Successful!")
                            .setCancelable(false)
                            .setMessage(usernameReg + " You have successfully Completed the Last Step Of registration Process, accept to Finish the Process " +
                                    "of registration")
                            .setIcon(R.drawable.ic_baseline_check)
                            .setPositiveButton("Accept", (dialogInterface, i) -> {
                                dialogInterface.dismiss();

                                //call get download uri if success then add it to the realtime database
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task1) {
                                        //calling the function to carry out the operations off writing the image to realtime database
                                        functionAddImageUriToDatabase(task1);
                                        //

                                    }
                                });

                                //
                            }).create().show();

                } else {
                    progressDialogLocallyImage.dismiss();
                    new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                            .setTitle("Something Went Wrong !")
                            .setCancelable(false)
                            .setMessage(usernameReg + " your registration halted due to:\n" + task.getException().getMessage())
                            .setPositiveButton("Lets Try Again Registration", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //dismiss dialog
                                    dialogInterface.dismiss();
                                    //
                                }
                            }).create().show();
                }
            }
        });

    }

    private void functionAddImageUriToDatabase(Task<Uri> task) {
        //

        ProgressDialog progressDialogImageUrlToRealTimeDatabase = new ProgressDialog(Registration.this);
        progressDialogImageUrlToRealTimeDatabase.setCancelable(false);
        progressDialogImageUrlToRealTimeDatabase.setTitle(usernameReg);
        progressDialogImageUrlToRealTimeDatabase.setMessage("saving account details...");
        progressDialogImageUrlToRealTimeDatabase.create();


        //showing the progress
        progressDialogImageUrlToRealTimeDatabase.show();

        //
        //


        String imagePathLocally = "ProfileImagePaths";

        String keyImageUriLocally = "imagePath";
        String valueStoredUrl = task.getResult().toString();

        Map<String, Object> mapImageUrlToRealtimeDatabase = new HashMap<>();
        mapImageUrlToRealtimeDatabase.put(keyImageUriLocally, valueStoredUrl);

        databaseReference = firebaseDatabase.getReference().child(imagePathLocally).child(firebaseUser).child(usernameReg); //imagePaths-userID-name-map

        databaseReference.setValue(mapImageUrlToRealtimeDatabase).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //progress dismiss
                    progressDialogImageUrlToRealTimeDatabase.dismiss();
                    //

                    //first success dialog
                    functionFinalRegistrationShowDetailedAll();
                    //
                } else {
                    //dismiss
                    progressDialogImageUrlToRealTimeDatabase.dismiss();

                    //
                    //alert User Of Wrong happening
                    new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                            .setTitle("Something Went Wrong!")
                            .setCancelable(false)
                            .setMessage(usernameReg + " Your Registration  Process Halted Due To:\n" + task.getException().getMessage())
                            .setPositiveButton("Retry Registration", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();

                    //
                }
            }
        });
    }

    private void functionFinalRegistrationShowDetailedAll() {


        new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                .setIcon(R.drawable.ic_baseline_check)
                .setCancelable(false)
                .setTitle(usernameReg + "\nRegistered Successfully")
                .setMessage("\nCongratulations!,Your Registration Was Successful And your Information Is Kept Securely\nWelcome To DevOps Society and Explore The Unseen Technology To Be Seen!")
                .setPositiveButton("Ok,view Registration Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //call function registration Details Viewing
                        functionRegistrationDetailsViewing();
                        //

                    }
                }).create().show();
    }

    private void functionRegistrationDetailsViewing() {

        //contains alert dialog of Success registration

        new MaterialAlertDialogBuilder(Registration.this)
                .setTitle("REGISTRATION SUCCESSFUL\n\n")
                .setMessage("\t(Account Holder: " + usernameReg.toUpperCase(Locale.ROOT) + ")\n" +
                        "\nWelcome To Developers  Society Family, Your Credentials Are Now Saved Successfully" +
                        "Please Don't Forget Your Username,Email and Password!." +
                        "These Are the Unique Identifiers of Your DevOps Society Account.\n" +
                        "\nUSERNAME: " + usernameReg + "\n\nEMAIL: " + emailReg + "\n\nPASSWORD: " + passwordReg + "\n" +
                        "\n\nYour Other Selected Details Are:\n" + "" +
                        "\nCOUNTY: " + string1 + "\n" +
                        "\nPASSION: " + string2 + "\n" +
                        "\nKNOWLEDGE: " + string3 + "\n" +
                        "\nGENDER:  " + string4 + "\n" +
                        "\nOCCUPATION: " + string5 + "\n" +
                        "\nUNIVERSITY ATTENDED: " + string6 + "\n" +
                        "\nACCOUNT TYPE: " + string7_role_status_check + "\n\n")
                .setIcon(R.drawable.ic_baseline_check_24)
                .setCancelable(false)
                .setPositiveButton("Ok,Login", (dialog, which) -> {
                    dialog.cancel();
                    startActivity(new Intent(Registration.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                })
                .setNegativeButton("Login Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        startActivity(new Intent(Registration.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                    }
                })
                .create()
                .show();
        //
    }


    private void functionErrorOfRegistrationProcess(Task<AuthResult> task, ProgressDialog pg) {

        pg.dismiss();

        new MakeVibrator(Registration.this);
        new AlertDialog.Builder(this)
                .setTitle("REGISTRATION FAILED!")
                .setMessage("Dear (" + usernameReg.toUpperCase(Locale.ROOT) + ") " +
                        "Your Registration Was Unsuccessful This Might Be Due To:\n" +
                        "\nMajor Reason:\n" + task.getException().getMessage().toUpperCase(Locale.ROOT) + "\n\nOther Reasons Include:\n" +
                        "\n1.Internet Connectivity Issues,If So Please Turn On The Internet And Retry The Registration Process Again." +
                        "\n " +
                        "\n2.Internal Server Errors; i.e Server Was Down During Your registration Process; Try Again.\n" +
                        "\n3.Your Internet Browsing Data Bundles Completely Depleted ; Wifi Or Purchase Data As Solution And Try Again")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setCancelable(false)
                .setPositiveButton("Ok,check internet", (dialogInterface, i) -> {
                    startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                })
                .setNegativeButton("Buy Data Bundles", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:*544#")));
                    }
                })
                .create()
                .show();
    }


    public void dialogWeakPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DevOPS Security Alert!");
        builder.setMessage("Dear Applicant, Password Provided Is Too Weak.\n" +
                "Its less than 8 Characters Long Which is Unrecommended.\n" +
                "\nFor Security Reasons And Required Standards" +
                "\nPlease Provide A Stronger Password With 8 or more Characters Long.\n" +
                "Thank You!\n");
        builder.setIcon(R.drawable.ic_baseline_info_24);
        builder.setCancelable(false);
        builder.setPositiveButton("OK,Understood", (dialog, which) -> {
            password_registration.requestFocus();
            password_registration.setError(null);
            confirm_password_registration.setError(null);
            new MakeVibrator(Registration.this);
        });
        builder.setNegativeButton("Disagree!", (dialog, which) -> finish());
        builder.create();
        builder.show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        super.onWindowFocusChanged(hasFocus);
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        animationDrawable_regimeMage.setEnterFadeDuration(2000);
        animationDrawable_regimeMage.setExitFadeDuration(4000);
        animationDrawable_regimeMage.start();

        animationDrawable_layout_password.setEnterFadeDuration(2000);
        animationDrawable_layout_password.setExitFadeDuration(4000);
        animationDrawable_layout_password.start();

        animationDrawable_layout_confirm.setEnterFadeDuration(2000);
        animationDrawable_layout_confirm.setExitFadeDuration(4000);
        animationDrawable_layout_confirm.start();

        animationDrawable_layout_email.setEnterFadeDuration(2000);
        animationDrawable_layout_email.setExitFadeDuration(4000);
        animationDrawable_layout_email.start();

        animationDrawable_layout_username.setEnterFadeDuration(2000);
        animationDrawable_layout_username.setExitFadeDuration(4000);
        animationDrawable_layout_username.start();

        animationDrawable_layout_phoneNumber.setEnterFadeDuration(2000);
        animationDrawable_layout_phoneNumber.setExitFadeDuration(4000);
        animationDrawable_layout_phoneNumber.start();
        checkInternet();

    }

    private void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        if (activeInfo == null) {

            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_baseline_hotspot_internet)
                    .setTitle("Internet Check")
                    .setMessage("detected no Internet Connection !")
                    .setCancelable(false)
                    .setPositiveButton("Open For Me", (dialogInterface, i) -> {
                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        dialogInterface.dismiss();
                    }).create()
                    .show();
        }


    }


}
