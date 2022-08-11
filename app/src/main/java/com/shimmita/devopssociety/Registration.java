package com.shimmita.devopssociety;

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
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Registration extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    CollectionReference documentReference;

    ProgressDialog progressDialogDatabase;


    private static final String TAG = "RegistrationLog";
    static String string1, string2, string3, string4, string5, string6;
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


    ArrayAdapter<String> arrayAdapter_counties;
    ArrayAdapter<String> arrayAdapter_passion;
    ArrayAdapter<String> arrayAdapter_level_of_knowledge;
    ArrayAdapter<String> arrayAdapter_gender;
    ArrayAdapter<String> arrayAdapter_occupation;
    ArrayAdapter<String> arrayAdapter_universityName;

    Spinner spinner_county,
            spinner_passion,
            spinner_level_of_knowledge,
            spinner_gender,
            spinner_occupation,
            spinnerUniversityRegistrationName;

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
        documentReference = firebaseFirestore.collection("DevOps Users");


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

                } else if (string2.contains("Mobile")) {
                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Mobile Development")
                            .setCancelable(false)
                            .setMessage("Dear " + usernameReg.toUpperCase(Locale.ROOT) + "\n" +
                                    "\nIt's Assumed That You Have Learnt The Basics and Internals Of Software Development Which Have Been Provided" +
                                    "Under Software Engineering Or You Have Learned Anywhere Else. This Section Deals Into Mobile Development" +
                                    "Theoretically And Practically In Advanced Mode. If this Is Not The Case Please Select Software Engineering Provided" +
                                    "in the DropDown Menu To Learn The Basics First.\n\n")
                            .setPositiveButton("Already Have Basics, Lets Proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Toast.makeText(Registration.this, "Welcome To Mobile App Development", Toast.LENGTH_LONG).show();
                                }
                            }).setNegativeButton("Back to Software,For Basics", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    spinner_passion.requestFocus();
                                    spinner_passion.setBackgroundColor(Color.MAGENTA);
                                    new VibratorLowly(Registration.this);
                                    dialogInterface.dismiss();
                                    Toast.makeText(Registration.this, "Please Select Software Engineering Deep", Toast.LENGTH_LONG).show();
                                    string2 = "";
                                }
                            }).create().show();

                } else if (string2.contains("Desktop")) {

                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Desktop Development")
                            .setCancelable(false)
                            .setMessage("Dear " + usernameReg.toUpperCase(Locale.ROOT) + "\n" +
                                    "\nIt's Assumed That You Have Learnt The Basics and Internals Of Software Development Which Have Been Provided" +
                                    "Under Software Engineering Or You Have Learned Anywhere Else. This Section Deals Into Desktop Development" +
                                    "Theoretically And Practically In Advanced Mode. If this Is Not The Case Please Select Software Engineering Provided" +
                                    "in the DropDown Menu To Learn The Basics First.\n\n")
                            .setPositiveButton("Already Have Basics, Lets Proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Toast.makeText(Registration.this, "Welcome To Desktop App Development", Toast.LENGTH_LONG).show();
                                }
                            }).setNegativeButton("Back to Software,For Basics", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    spinner_passion.requestFocus();
                                    spinner_passion.setBackgroundColor(Color.MAGENTA);
                                    new VibratorLowly(Registration.this);
                                    dialogInterface.dismiss();
                                    Toast.makeText(Registration.this, "Please Select Software Engineering Deep", Toast.LENGTH_LONG).show();
                                    string2 = "";
                                }
                            }).create().show();
                } else if (string2.equals("")) {
                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Passion Cannot Be Null !")
                            .setCancelable(false)
                            .setMessage("Cannot Register Member With No Place Of Passion Or Interest for This Field Is Mandatory For" +
                                    "Your Registration Process To Proceed,Please Select One Of your Passion In The DropDown List!")
                            .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    spinner_passion.requestFocus();
                    new VibratorLowly(Registration.this);
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
        ProgressDialog pg = new ProgressDialog(Registration.this);
        pg.setTitle(usernameReg.toUpperCase(Locale.ROOT));
        pg.setMessage("Registering");
        pg.create();
        pg.show();

        auth.createUserWithEmailAndPassword(emailReg, passwordReg).addOnCompleteListener(Registration.this, task -> {
            if (task.isSuccessful()) {
                pg.dismiss();
                //HashMap For Storing the User Data On to The Database At FireBase
                Map<String, Object> map = new HashMap<>();
                map.put("Username", usernameReg);
                map.put("Email", emailReg);
                map.put("Password", passwordReg);
                map.put("County", string1);
                map.put("Passion", string2);
                map.put("Knowledge", string3);
                map.put("Gender", string4);
                map.put("Occupation", string5);
                map.put("University", string6);
                //
                //adding map values to firebase
                firebaseUser = auth.getCurrentUser();
                String userID = firebaseUser.getUid();

                documentReference.document(userID).set(map).addOnCompleteListener(Registration.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

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
                                            "\nUNIVERSITY ATTENDED: " + string6 + "\n\n")
                                    .setIcon(R.drawable.ic_baseline_check_24)
                                    .setCancelable(false)
                                    .setPositiveButton("Ok,Login", (dialog, which) -> {
                                        dialog.cancel();
                                        pg.dismiss();
                                        startActivity(new Intent(Registration.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    })
                                    .setNegativeButton("Login Later", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            pg.dismiss();
                                            dialogInterface.dismiss();
                                            startActivity(new Intent(Registration.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();

                        } else {
                            new androidx.appcompat.app.AlertDialog.Builder(Registration.this)
                                    .setTitle("Error From The Database!")
                                    .setCancelable(false)
                                    .setMessage("\n\n" + "Dear User " + usernameReg.toUpperCase(Locale.ROOT) + "" +
                                            "Your Data Was Not Stored Into The Database Records Due To:\n" + task.getException().getMessage() + "" +
                                            "Please Try Again Registration Process")
                                    .setPositiveButton("Ok Let Me try Again", (dialogInterface, i) -> dialogInterface.dismiss())
                                    .setNegativeButton("No,Quit!", (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                        startActivity(new Intent(Registration.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        finish();
                                    }).create().show();
                        }
                    }
                });

            } else {
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
        });
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