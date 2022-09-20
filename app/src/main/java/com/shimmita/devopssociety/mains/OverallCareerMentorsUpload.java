package com.shimmita.devopssociety.mains;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shimmita.devopssociety.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class OverallCareerMentorsUpload extends AppCompatActivity {
    //
    //declaration of the Request code for permissions
    public static final int REQUEST_CODE_PERMISSIONS = 56445;
    //declaration variable strings which hold Collection Reference for either career in it(VALUE_CAREER)
    //or Career Mentors (VALUE_MENTORS)
    public static  String VALUE_CAREERS = "Careers";
    public static  String VALUE_MENTORS = "Career Mentors";
    //
    //declaration of the activity tag
    private static final String TAG = "OverallCareerMentors";
    //declaration Of The request Codes for Obtaining data on Activity Result
    private static final int REQUEST_CODE_IMAGE_PICK = 9965;
    private static final int REQUEST_CODE_HEAD_TEXT = 9964;
    private static final int REQUEST_CODE_DESCRIPTION = 9963;
    //

    //Declaration Of The String Which will ease data retrieve from EditText
    public static String STRING_HEADING = "";
    public static String STRING_DESCRIPTION = "";
    //
    //declaration of string to hold getData from intent of either mentors or careers
    public static String DATA_FROM_INTENT;
    //

    //
    //declaration of ImageUri And EdiText
    Uri imageUriCareerMentors = null;
    EditText editTextHeading,
            editTextDescription;
    //

    //Declaration Of The CircleImage Variable
    CircleImageView circleImageViewCareerMentors;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overal_career_mentors_upload);
        //setting the tittle of the activity to Actual Upload
        this.setTitle("Upload Career Or Mentor");
        //

        //function Init Of Variables
        editTextHeading = findViewById(R.id.editTextCareerOrMentorHeading);
        editTextDescription = findViewById(R.id.EditTextCareerOrMentorDescription);
        circleImageViewCareerMentors = findViewById(R.id.circleImageCareerAndMentorsInIT);
        //

        //setting Onclick Listeners On The EditText Heading And Description
        editTextHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//toast showing this editText Clicked
                Toast.makeText(OverallCareerMentorsUpload.this, "EditTextHeading", Toast.LENGTH_SHORT).show();
                //
                //function Intent To Obtain A Text .txt file from internal storage which we will extract Text from it
                functionObtainTextFileHeadingFromInternalStorageIntent();
                //
            }
        });
        //

        //setting Onclick Listener On The Description EditText

        editTextDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toasting editText description Clicked
                Toast.makeText(OverallCareerMentorsUpload.this, "EditText Description Clicked", Toast.LENGTH_SHORT).show();
                //
                //function open text file .tx from internal storage and extract its Data to Obtain the text
                functionObtainTexFileDescriptionFromInternalStorageIntent();
                //
            }
        });
        //


        //function for checking permissions Write and Internet
        functionCheckPermissionWriteAndInternet();
        //

        //function for checking data carried by intent getData and setting the hints for editText heading and Description
        //relevantly
        functionCheckIntentData();
        //

        //log the current user
       // Log.d(TAG, "onCreate: Current User:=>" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        //

    }

    private void functionCheckIntentData() {

        String intentGetData = getIntent().getStringExtra("intentData");

        if (intentGetData != null) {
            if (intentGetData.equals("Careers")) {
                //setting the hints of editText to a reasonable hint in relation to Careers
                editTextHeading.setHint("enter file for career heading");
                editTextDescription.setHint("enter file for career description");
                //
            } else if (intentGetData.equals("CareerMentors")) {
                //setting the hints of ediText to a reasonable hint in relation to Mentors
                editTextHeading.setHint("enter file for mentor heading");
                editTextDescription.setHint("enter file for mentor description");
                //

            }
        } else {
            //intent null data it's
            Toast.makeText(this, "No Data Present From Intents", Toast.LENGTH_SHORT).show();
            //
        }

    }

    private void functionCheckPermissionWriteAndInternet() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            //request the Permissions since not granted is
            functionRequestPermission();
            //
        }
    }

    private void functionRequestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET}, REQUEST_CODE_PERMISSIONS);
    }


    private void functionObtainTexFileDescriptionFromInternalStorageIntent() {
        //begin coding here
        //starting intent to open text .txt file from the internal storage
        Intent intentOpenTextFileDescription = new Intent();
        intentOpenTextFileDescription.setAction(Intent.ACTION_GET_CONTENT);
        intentOpenTextFileDescription.setType("text/*");

        startActivityForResult(Intent.createChooser(intentOpenTextFileDescription, "choose description text file", null), REQUEST_CODE_DESCRIPTION);

        //end coding here
    }

    private void functionObtainTextFileHeadingFromInternalStorageIntent() {
        //begin coding here

        //starting the intent of opening .txt files

        Intent intentObtainHeadingText = new Intent(Intent.ACTION_GET_CONTENT);
        intentObtainHeadingText.setType("text/*");

        startActivityForResult(Intent.createChooser(intentObtainHeadingText, "chose heading text file", null), REQUEST_CODE_HEAD_TEXT);
        //end coding

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSIONS == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Alert Dialog Permission Granted Successfully
                new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                        .setTitle("Permissions Granted")
                        .setIcon(R.drawable.ic_baseline_check)
                        .setMessage("Congratulations Permission to Read And Write Granted Successfully")
                        .create().show();
                //
            } else {
                //alert  that permission not granted and exit is the only option
                new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                        .setTitle("Permissions Denied")
                        .setMessage("you did not grant the whole permissions therefore application will be exited")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_baseline_warning)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //back the user to the main page of posting the data onto he DevOps Society platform
                                startActivity(new Intent(OverallCareerMentorsUpload.this, PostingDataActivity.class));
                                //
                                //animating the intent migration
                                CustomIntent.customType(OverallCareerMentorsUpload.this, "fadeion-to-fadeout");
                                //

                            }
                        }).create().show();
                //
            }
        }

    }

    public void functionUploadImageCarerMentorCircleImageView(View view) {
        //toast circleImageClicked
        Toast.makeText(this, "Circle Image Clicked", Toast.LENGTH_SHORT).show();
        //
        //vibrate to show responsiveness of the application
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            new VibratorLowly(this);
        }
        //

        //begin coding

        //start intent tpo open images by help of Activity For Result
        Intent openImagesForCareerMentorsIntent = new Intent();
        openImagesForCareerMentorsIntent.setAction(Intent.ACTION_GET_CONTENT);
        openImagesForCareerMentorsIntent.setType("image/*");

        startActivityForResult(Intent.createChooser(openImagesForCareerMentorsIntent, "select image", null), REQUEST_CODE_IMAGE_PICK);
        //

        //end coding
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //checking the REQUEST_CODE to  be matching with the request code
        //request code pick image
        if (REQUEST_CODE_IMAGE_PICK == requestCode) {
            //checking if the result code is equal to RESULT_CODE OK
            if (resultCode == RESULT_OK) {
                //checking conditions to ensure  that the data returned was indeed not null
                if (data != null && data.getData() != null) {
                    //obtaining the data that is in the form of Uri
                    Uri uriDataResult = data.getData();
                    //setting the image with uri dat obtained which contains data held from the internal  storage
                    imageUriCareerMentors = uriDataResult;
                    //setting the image with the uri data
                    circleImageViewCareerMentors.setImageURI(imageUriCareerMentors);
                    //
                    //logging the data of the uri on the debugging console
                    Log.d(TAG, "onActivityResult: ImageURI=>" + imageUriCareerMentors);
                    //

                }
            }
            //user cancelled the process;
            else if (resultCode == RESULT_CANCELED) {
                //alerting the user he cancelled the process of file locating
                new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                        .setTitle("Cancelled The Process")
                        .setMessage("you have cancelled the process of image file locating")
                        .setIcon(R.drawable.ic_baseline_info_24)
                        .create().show();

                //
            }
        }
        //request code select heading text
        else if (REQUEST_CODE_HEAD_TEXT == requestCode) {
            //checking if the result code is ok
            if (resultCode == RESULT_OK) {
                //checking to ensure that the data is not null to avoid application from crashing
                if (data != null && data.getData() != null) {
                    Uri dataUriHeading = data.getData();

                    //logging the uri data on the debugging console
                    Log.d(TAG, "onActivityResult: heading Uri Data:=>" + dataUriHeading);
                    //

                    //starting the process of converting the heading Uri which is a path o the file
                    //the process will be aided by InputStream Object and Content resolver to Extract the data to  a readable text
                    //TODO:remember that the inputStream is used for reading while OutPut stream For writing to A file

                    try {
                        //getting the input stream with a readable data in it
                        InputStream inputStream = getContentResolver().openInputStream(dataUriHeading);
                        //
                        //creating an int variable to temporarily hold the data
                        int valueTemp = 0;
                        //
                        //creating a StringBuilder object to append data of converted int to char
                        //when the input stream is at -1;
                        StringBuilder stringBuilder = new StringBuilder();

                        //
                        //starting the process of data reading from the inputStream and saving it into the int value in form of bytes
                        //till the last value of read is equal to -1, later the value will be converted to character data type and then appended to the string builder
                        //to form a String
                        while ((valueTemp = inputStream.read()) != -1) {
                            //appending the stringBuilder with valueTemp after convert it into char
                            stringBuilder.append((char) valueTemp);
                        }

                        //assigning the data of stringBuilder on to the Global STRING_HEADING
                        STRING_HEADING = stringBuilder.toString();
                        //
                        //logging the String Description on the debugging console for debugging purposes
                        Log.d(TAG, "onActivityResult: Data String Heading:\n" + STRING_HEADING);
                        //

                        //setting the value of String heading on the editText heading so that it displays the results too
                        editTextHeading.setText(STRING_HEADING);
                        //

                        //toasting data retrieve was successful
                        Toast.makeText(this, "data retrieved from the file successfully", Toast.LENGTH_SHORT).show();
                        //closing the inputStream
                        inputStream.close();
                        //
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        //logging the error on the console
                        Log.d(TAG, "onActivityResult: An expected error:=>" + e.getMessage());
                        //toasting the Error
                        Toast.makeText(this, "Sorry An Error Occurred", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        //logging the error of reading
                        Log.d(TAG, "onActivityResult: error:=>" + e.getMessage());
                        //
                        e.printStackTrace();
                    }


                    //


                }

            }
            //user cancelled the process;
            else if (resultCode == RESULT_CANCELED) {
                //alerting the user he cancelled the process of file locating
                new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                        .setTitle("Cancelled The Process")
                        .setMessage("you have cancelled the process of text file locating")
                        .setIcon(R.drawable.ic_baseline_info_24)
                        .create().show();

                //
            }

        }
        //request code is of pick description text file
        else if (REQUEST_CODE_DESCRIPTION == requestCode) {
            //checking if the result code is RESULT OK
            if (resultCode == RESULT_OK) {
                //checking to ensure data is not null to avoid runtime exceptions and application crushing
                if (data != null && data.getData() != null) {
                    //data is ok
                    //obtaining the Uri Data and Assigning it to the URi Object
                    Uri uriDataDescription = data.getData();
                    //

                    //lodging the uriDescription on the debugging console
                    Log.d(TAG, "onActivityResult: data uri Description=>" + uriDataDescription);
                    //

                    //using the content resolver and the inputStream to obtain content in the data uri object
                    try {
                        //creating an input stream and content resolvers for converting the data uri into a readable text
                        InputStream inputStream = getContentResolver().openInputStream(uriDataDescription);
                        //
                        //creating an int variable that will temporarily hold data bytes from InputStream while Reading
                        int valueTemp = 0;
                        //
                        //creating a StringBuilder
                        StringBuilder stringBuilder = new StringBuilder();
                        //
                        //starting the process of reading from the inputStream and saving the bytes into the valueTemp using the while loop
                        //and then converting the value temp into character then appending it onto the stringBuilder;

                        while ((valueTemp = inputStream.read()) != -1) {
                            //appending the value of valueTemp onto the StringBuilder after casting it into character
                            stringBuilder.append((char) valueTemp);
                            //

                        }

                        //toasting that data was retrieved successfully from the file
                        Toast.makeText(this, "data retrieved successfully from the file", Toast.LENGTH_SHORT).show();
                        //
                        //assigning the value of stringBuilder Onto Constant Global STRING_DESCRIPTION
                        STRING_DESCRIPTION = stringBuilder.toString();
                        //

                        //logging the String Description on the debugging console for debugging purposes
                        Log.d(TAG, "onActivityResult: Data String Description:\n" + STRING_DESCRIPTION);
                        //

                        //setting the editText with the Value of constant  STRING_DESCRIPTION
                        editTextDescription.setText(STRING_DESCRIPTION);
                        //

                        //closing the input stream
                        inputStream.close();
                        //
                        //
                    } catch (FileNotFoundException e) {
                        //toasting the Error
                        Toast.makeText(this, "Error Occurred=>" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //
                        //logging the error on the debug console
                        Log.d(TAG, "onActivityResult: Error:=>" + e.getMessage());
                        //
                        e.printStackTrace();

                    } catch (IOException e) {
                        //toasting the error for gist and hinting purposes
                        Toast.makeText(this, "Error Occurred:=>" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //
                        //logging the error on the  Debug Console for debugging purposes
                        Log.d(TAG, "onActivityResult: Error Occurred=>" + e.getMessage());
                        //
                        e.printStackTrace();
                    }
                    //


                }
            }

            //user cancelled the process;
            else if (resultCode == RESULT_CANCELED) {
                //alerting the user he cancelled the process of file locating
                new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                        .setTitle("Cancelled The Process")
                        .setMessage("you have cancelled the process of text file locating")
                        .setIcon(R.drawable.ic_baseline_info_24)
                        .create().show();

                //
            }
        }
    }

    public void functionButtonSubmitClicked(View view) {
        //checking if the minorChild is null or is empty to avoid exception and application  from crushing
        //before continuation of any firebase related activity since minor child holds data o heading which is to be goten from a file
        //and it may happen that the file was null!
        if (TextUtils.isEmpty(STRING_HEADING)) {
            Toast.makeText(this, "Detected Empty Field!", Toast.LENGTH_SHORT).show();

        } else {
            //toasting that button has been clicked
            Toast.makeText(this, "button Submit Clicked", Toast.LENGTH_SHORT).show();
            //
            //begin coding here

            //getting the imagePath from the global imageUriCareerMentors and too
            Uri imagePath = imageUriCareerMentors;
            //
            //first we will save the image into the FirebaseStorage using the putFile(uri) method  and then get the image Url download link which we will the use it
            //for creating a map for storing the image Uri and the other files heading and description onto the fireStore using a map

            //checking the data in intentGetData for determining the type of Collection reference to Use
            String intentDataReceived = getIntent().getStringExtra("intentData");

            if (intentDataReceived != null) {


                //checking  Careers is the intentGetDataReturned
                if (intentDataReceived.equals("Careers")) {
                    //logging the data on the debug console for debugging purposes
                    Log.d(TAG, "\nfunctionButtonSubmitClicked: Data Received From Intent:=>" + intentDataReceived);
                    //

                    //use Main child to store image in the firebaseStorage as CareerImages;
                    //use collection reference with name CAREER_VALUE for adding map values to fireStore

                    //setting the progressDialogLocal Visible For Showing The Progress
                    ProgressDialog progressDialogImageUpload = new ProgressDialog(OverallCareerMentorsUpload.this);
                    progressDialogImageUpload.setTitle(VALUE_CAREERS);
                    //progress message will be set with OnProgressListener method find it there
                    progressDialogImageUpload.show();
                    progressDialogImageUpload.setCancelable(false);
                    //

                    //initialisation of Firebase Storage and the Storage Reference;
                   /* String mainChild = VALUE_CAREERS;
                    String minorChild = STRING_HEADING;*/
                    //flow= Careers/Heading => (VALUE_CAREERS/STRING_HEADING)


                    //
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    StorageReference storageReference = firebaseStorage.getReference().child("Careers").child(String.valueOf(System.currentTimeMillis()));
                    //
                    //starting the process of putting the file into firebaseStorage
                    storageReference.putFile(imagePath).addOnCompleteListener(OverallCareerMentorsUpload.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //task was successful
                            if (task.isSuccessful()) {
                                //dismiss the progressDialogLocal for things are successfully
                                progressDialogImageUpload.dismiss();
                                //

                                //creating a progress Dialog of fetching download link
                                ProgressDialog progressDialogDownloadUri = new ProgressDialog(OverallCareerMentorsUpload.this);
                                progressDialogDownloadUri.setTitle("Download Uri");
                                progressDialogDownloadUri.setMessage("fetching Uri...");
                                progressDialogDownloadUri.setCancelable(false);
                                progressDialogDownloadUri.show();
                                //

                                //task successful so we can extract the download uri from the storageReference object
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            //task successful dismiss the progress Dialog_Show_Download_Uri
                                            progressDialogDownloadUri.dismiss();
                                            //
                                            //get the Uri to string and pass it into the function for download
                                            String imageUrl = task.getResult().toString();
                                            //
                                            callFunctionContinueForHeadingAndDescriptionCareers(imageUrl, VALUE_CAREERS);
                                            //
                                        }
                                        //alert developer error occurred while fetching the download uri
                                        else {
                                            //dismiss the progress dialog
                                            progressDialogDownloadUri.dismiss();
                                            //
                                            //
                                            new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                                                    .setTitle("DEVELOPER ALERT")
                                                    .setIcon(R.drawable.ic_baseline_warning)
                                                    .setMessage("hey,developer an error occurred fix it\n" + task.getException().getMessage())
                                                    .setCancelable(false)
                                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //dismiss the progress dialog
                                                            dialog.dismiss();
                                                            //
                                                        }
                                                    })
                                                    .create().show();
                                            //
                                        }

                                    }
                                });

                                //

                            }
                            //task failed to upload image to the FirebaseStorage
                            else {
                                //dismiss the progress dialog image
                                progressDialogImageUpload.dismiss();
                                //

                                //alert the Developer With  Reason To Why This Happened
                                new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                                        .setTitle("DEVELOPER ALERT")
                                        .setIcon(R.drawable.ic_baseline_warning)
                                        .setMessage("hey,developer an error has occurred fix it as developer:\n" + task.getException().getMessage())
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //dismiss the alert dialog
                                                dialog.dismiss();
                                                //
                                            }
                                        }).create();
                                //
                            }

                        }
                    }).addOnProgressListener(OverallCareerMentorsUpload.this, new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            //getting a double Variable which will cast into an integer to show percentage of progress
                            Double percentageDouble = 100.0 * (snapshot.getBytesTransferred()) / (snapshot.getTotalByteCount());
                            int percent = percentageDouble.intValue();
                            progressDialogImageUpload.setMessage("uploading image " + percent + "%");
                            //
                        }
                    });
                    //


                } //end of careers checking

                //checking if careerMentors  is getIntentData
                else if (intentDataReceived.equals("CareerMentors")) {
                    //logging data received on the debug Console for debugging purposes
                    Log.d(TAG, "functionButtonSubmitClicked: Data Received From Intent=>" + intentDataReceived);
                    //
                    //use Main child to store image in the firebaseStorage as CareerMentorsImages
                    //use collection reference with name MENTOR_VALUE

                    //setting the progressDialogLocal Visible For Showing The Progress
                    ProgressDialog progressDialogImageUpload = new ProgressDialog(OverallCareerMentorsUpload.this);
                    progressDialogImageUpload.setTitle(VALUE_MENTORS);
                    //progress message will be set with OnProgressListener method find it there
                    progressDialogImageUpload.setCancelable(false);
                    progressDialogImageUpload.show();
                    //

                   /* //initialisation of Firebase Storage and the Storage Reference;
                    String mainChild = VALUE_MENTORS;
                    String minorChild = STRING_HEADING;
                    //flow= Careers/Heading => (VALUE_MENTORS/STRING_HEADING)*/

                    //
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    StorageReference storageReference = firebaseStorage.getReference().child("Career Mentors").child(String.valueOf(System.currentTimeMillis()));
                    //
                    //starting the process of putting the file into firebaseStorage

                    storageReference.putFile(imagePath).addOnCompleteListener(OverallCareerMentorsUpload.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            //task was successful
                            if (task.isSuccessful()) {
                                //dismiss the progressDialogLocal for things are successfully
                                progressDialogImageUpload.dismiss();
                                //

                                //creating a progress Dialog of fetching download link
                                ProgressDialog progressDialogDownloadUri = new ProgressDialog(OverallCareerMentorsUpload.this);
                                progressDialogDownloadUri.setTitle("Mentors URI Download");
                                progressDialogDownloadUri.setMessage("fetching Uri...");
                                progressDialogDownloadUri.setCancelable(false);
                                progressDialogDownloadUri.show();
                                //

                                //task successful so we can extract the download uri from the storageReference object
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            //task successful dismiss the progress Dialog_Show_Download_Uri
                                            progressDialogDownloadUri.dismiss();
                                            //
                                            //get the Uri to string and pass it into the function for download
                                            String imageUrl = task.getResult().toString();
                                            //
                                            callFunctionContinueForHeadingAndDescriptionMentors(imageUrl, VALUE_MENTORS);
                                            //
                                        }
                                        //alert developer error occurred while fetching the download uri
                                        else {
                                            //dismiss the progress dialog
                                            progressDialogDownloadUri.dismiss();
                                            //
                                            //
                                            new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                                                    .setTitle("DEVELOPER ALERT")
                                                    .setIcon(R.drawable.ic_baseline_warning)
                                                    .setMessage("hey,developer an error occurred fix it\n" + task.getException().getMessage())
                                                    .setCancelable(false)
                                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //dismiss the progress dialog
                                                            dialog.dismiss();
                                                            //
                                                        }
                                                    })
                                                    .create().show();
                                            //
                                        }

                                    }
                                });

                                //

                            }
                            //task failed to upload image to the FirebaseStorage
                            else {
                                //dismiss the progress dialog image
                                progressDialogImageUpload.dismiss();
                                //

                                //alert the Developer With  Reason To Why This Happened
                                new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                                        .setTitle("DEVELOPER ALERT")
                                        .setIcon(R.drawable.ic_baseline_warning)
                                        .setMessage("hey,developer an error has occurred fix it as developer:\n" + task.getException().getMessage())
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //dismiss the alert dialog
                                                dialog.dismiss();
                                                //
                                            }
                                        }).create();
                                //
                            }

                        }
                    }).addOnProgressListener(OverallCareerMentorsUpload.this, new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            //getting a double Variable which will cast into an integer to show percentage of progress
                            Double percentageDouble = 100.0 * (snapshot.getBytesTransferred()) / (snapshot.getTotalByteCount());
                            int percent = percentageDouble.intValue();
                            progressDialogImageUpload.setMessage("uploading image " + percent + "%");
                            //
                        }
                    });

                    //end


                }

                //end of checking CareerMentors is the intentDataReturned

            }

            //intent data is null
            else {
                //logging the data on the debug console
                Log.d(TAG, "functionButtonSubmitClicked: Data Received From Intent=>" + null);
                //

                //toast Data Is NUlL
                Toast.makeText(this, "Intent Data Received Was NULL", Toast.LENGTH_SHORT).show();
                //
            }

            //

            //end coding

        }
    }

    //function to upload imageUri,heading,description to fireStore for CareerMentors
    private void callFunctionContinueForHeadingAndDescriptionMentors(String imageUrl, String valueMentors) {

        //begin
        //heading and descriptions from the  Global Constants STRING_HEADING and STRING_DESCRIPTION;
        String heading = STRING_HEADING;
        String description = STRING_DESCRIPTION;
        //
        //creating the keys for the values since we want ro store the data in key-value pairs of mapping style
        String keyHeading = "Heading";
        String keyDescription = "Description";
        String keyImagePath = "imagePath";
        //
        //collection reference path to documents is value passed in the function of VALUE_CAREERS
        String collection = valueMentors;
        //
        //document path its randomly generated in relation to time of system
        String document = String.valueOf(System.currentTimeMillis());
        //

        //creating a map to store variables into the fireStore
        Map<String, Object> mapValuesOfMentorsInIT = new HashMap<>();
        mapValuesOfMentorsInIT.put(keyHeading, heading);
        mapValuesOfMentorsInIT.put(keyDescription, description);
        mapValuesOfMentorsInIT.put(keyImagePath, imageUrl);
        //
        //creating a ProgressDialog to shoe the progression of the data careerS in it
        ProgressDialog progressDialogSavingDataFirestoreMentors = new ProgressDialog(OverallCareerMentorsUpload.this);
        progressDialogSavingDataFirestoreMentors.setTitle("Mentors Data Upload");
        progressDialogSavingDataFirestoreMentors.setMessage("uploading data...");
        progressDialogSavingDataFirestoreMentors.setCancelable(false);
        progressDialogSavingDataFirestoreMentors.create();
        progressDialogSavingDataFirestoreMentors.show();
        //

        //initialisation of the FirebaseFirestore and Collection reference
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection(collection);
        //
        //starting the process of adding the data of careers into the Firebase Firestore
        //path flow=collection/document==(value_mentors/LongTimeInMillis/filesHere)
        collectionReference.document(document).set(mapValuesOfMentorsInIT).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //dismiss the progressDialog since data upload was successful
                    progressDialogSavingDataFirestoreMentors.dismiss();
                    //
                    //alert the developer data upload to the fireStore was successful;
                    new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                            .setTitle("DEVELOPER ALERT")
                            .setIcon(R.drawable.ic_baseline_check)
                            .setMessage("hey,developer your data upload was successful:\n\nHeading:\n" + heading + "\nDescription:\n" + description + "\nimagePath:" + imageUrl + "\n" +
                                    "\nDataPath:" + collection + "/" + document)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                    dialog.dismiss();
                                    //
                                }
                            }).create().show();

                    //
                }
                //error Occurred while uploading Mentors  data to the fireStore
                else {
                    //dismiss since error also is also a form of result
                    progressDialogSavingDataFirestoreMentors.dismiss();
                    //
                    //alert the Developer of the error occurred and that a solution should be;
                    new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                            .setTitle("DEVELOPER ALERT")
                            .setIcon(R.drawable.ic_baseline_warning)
                            .setCancelable(false)
                            .setMessage("hey,developer an error occurred fix it please")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                    dialog.dismiss();
                                    //
                                }
                            }).create().show();
                    //

                }
            }
        });
        //


        //end

    }


    //function to store the image Url,heading,description into the fireStore for Careers In It
    private void callFunctionContinueForHeadingAndDescriptionCareers(String imageUrl, String value_career) {
        //heading and descriptions from the  Global Constants STRING_HEADING and STRING_DESCRIPTION;
        String heading = STRING_HEADING;
        String description = STRING_DESCRIPTION;
        //
        //creating the keys for the values since we want ro store the data in key-value_career pairs of mapping style
        String keyHeading = "Heading";
        String keyDescription = "Description";
        String keyImagePath = "imagePath";
        //
        //collection reference path to documents is value_career passed in the function of VALUE_CAREERS
        String collection = value_career;
        //
        //document path its randomly generated in relation to time of system
        String document = String.valueOf(System.currentTimeMillis());
        //

        //creating a map to store variables into the fireStore
        Map<String, Object> mapValuesOfCareersInIT = new HashMap<>();
        mapValuesOfCareersInIT.put(keyHeading, heading);
        mapValuesOfCareersInIT.put(keyDescription, description);
        mapValuesOfCareersInIT.put(keyImagePath, imageUrl);
        //
        //creating a ProgressDialog to shoe the progression of the data careerS in it
        ProgressDialog progressDialogSavingDataFirestoreCareers = new ProgressDialog(OverallCareerMentorsUpload.this);
        progressDialogSavingDataFirestoreCareers.setTitle("Careers Data Upload");
        progressDialogSavingDataFirestoreCareers.setMessage("uploading data...");
        progressDialogSavingDataFirestoreCareers.setCancelable(false);
        progressDialogSavingDataFirestoreCareers.create();
        progressDialogSavingDataFirestoreCareers.show();
        //

        //initialisation of the FirebaseFirestore and Collection reference
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection(collection);
        //
        //starting the process of adding the data of careers into the Firebase Firestore
        //path flow=collection/document==(Careers/LongTimeInMillis/filesHere)
        collectionReference.document(document).set(mapValuesOfCareersInIT).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //dismiss the progressDialog since data upload was successful
                    progressDialogSavingDataFirestoreCareers.dismiss();
                    //
                    //alert the developer data upload to the fireStore was successful;
                    new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                            .setTitle("DEVELOPER ALERT")
                            .setIcon(R.drawable.ic_baseline_check)
                            .setMessage("hey,developer your data upload was successful:\n\nHeading:\n" + heading + "\nDescription:\n" + description + "\nimagePath:" + imageUrl + "\n" +
                                    "\nDataPath:" + collection + "/" + document)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                    dialog.dismiss();
                                    //
                                }
                            }).create().show();

                    //
                }
                //error Occurred while uploading career data to the fireStore
                else {
                    //dismiss since error also is also a form of result
                    progressDialogSavingDataFirestoreCareers.dismiss();
                    //
                    //alert the Developer of the error occurred and that a solution should be;
                    new AlertDialog.Builder(OverallCareerMentorsUpload.this)
                            .setTitle("DEVELOPER ALERT")
                            .setIcon(R.drawable.ic_baseline_warning)
                            .setCancelable(false)
                            .setMessage("hey,developer an error occurred fix it please")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                    dialog.dismiss();
                                    //
                                }
                            }).create().show();
                    //

                }
            }
        });
        //


    }
}