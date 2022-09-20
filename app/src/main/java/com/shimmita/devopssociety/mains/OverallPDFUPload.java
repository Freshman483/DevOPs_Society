package com.shimmita.devopssociety.mains;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shimmita.devopssociety.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OverallPDFUPload extends AppCompatActivity {

    //declaration of the Request Codes for Activity Result Usage;
    public static final int REQUEST_CODE_PDF_IMAGE_CODE = 11111;
    //
    public static final int REQUEST_CODE_PDF_FILE_CODE = 22222;
    private static final String TAG = "OverallPDFUPload";
    private static Uri PDF_FILE_URI_DATA = null;
    private static Uri PDF_IMAGE_URI_DTA = null;
    //

    //declaration of a firebase global
    FirebaseStorage firebaseStorage;
    //declaration of the global value for spinner returning value
    String valueReturnedFomTheSpinnerAfterSelection = "";
    //


    //declaration Of the Variable Circle ImageView To Display imageRepresentative
    //for pdf file to be uploaded;
    CircleImageView circleImageViewPdfImage;
    //
    //declaration  of the button pick pdf file and submit to cloud storage
    Button buttonPickPdfFile,
            buttonSubmitToCloudStorage;
    //

    //declaration of the TextViews For Displaying the pdf image Uri and pdf File Uri
    TextView textViewDisplayPdfImageUri,
            textViewDisplayPdfFileUri;
    //

    //declaration of the editTexts Entering pdfName, and entering Pdf Author
    EditText editTextEnterPdfName,
            editTextEnterAuthorName;
    //
    //declaration of the spinner
    Spinner spinnerMainPath;
    //
    //declaration of the array adapter spinner
    ArrayAdapter arrayAdapterSpinner;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_pdfupload);
        //setting the title of the activity
        this.setTitle("Developer Activity Pdf Upload");
        //

        //initialisation of the declared  Globals
        //init of CircleImageView
        circleImageViewPdfImage = findViewById(R.id.circleImageViewPdfImage);
        //
        //init of Button picking pdf and sending data to cloud for storage
        buttonPickPdfFile = findViewById(R.id.buttonSelectPdfFile);
        buttonSubmitToCloudStorage = findViewById(R.id.buttonSubmitPdfToCloudStorage);
        //
        //init of the textViews for display of imagePdf and uriPdf File
        textViewDisplayPdfImageUri = findViewById(R.id.textViewDisplayPdfImageUri);
        textViewDisplayPdfFileUri = findViewById(R.id.textViewDisplayPdfFileUri);
        //
        //init of EditTexts For name and author entering
        editTextEnterPdfName = findViewById(R.id.editTextPdfName);
        editTextEnterAuthorName = findViewById(R.id.editTextPdfAuthorName);
        //
        //init of the spinner
        spinnerMainPath = findViewById(R.id.spinnerMainPathVariable);
        //


        //setting onclick listeners for circle image in order to pick a pdf image represent
        circleImageViewPdfImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call a function that will promote select an image from internal storage
                callFunctionPickImageRepresentPdfFile();
                //
            }
        });
        //

        //setting onclick listeners on the button pick pdf file
        buttonPickPdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calling a function that will trigger intent to open storage for picking pdf files only
                callFunctionPickPdfFileFromStorage();
                //
            }
        });
        //

        //setting onclick listener on button submit to send the data to the cloud storage
        buttonSubmitToCloudStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calling the function w/c will trigger off FirebaseOperations
                callFunctionPostingDataToCloudFirebase();
                //
            }
        });
        //

        //init of list,array adapter spinner and setting this adapter to the spinner;
        List<String> listOfPathVariablesToBeAddedIntoTheAdapter = new ArrayList<>();
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Java Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Javascript Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Python Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("C_and_C++ Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Dart Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Flutter Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Kotlin Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("RxJava Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Html_and_CSS Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("C# Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Visual Basic Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("PHP Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Swift Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("GO Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("React.js Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("React Native Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Angular Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Typescript Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Rust Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Ruby Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Network engineering Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("WebDevelopment");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Database Management");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Machine Learning");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Artificial Intelligence");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Android Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("IOS Programming");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Cloud Computing");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Containerisation");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Reverse Engineering");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Penetration Testing");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("IDES");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Linux OS");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Windows OS");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Dark Web");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Computer Hacking");
        listOfPathVariablesToBeAddedIntoTheAdapter.add("Computer Virus");
        //
        //array adapter init
        arrayAdapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfPathVariablesToBeAddedIntoTheAdapter);
        //
        //sorting the contents present in the array Adapter
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            arrayAdapterSpinner.sort(Comparator.naturalOrder());
        }
        //
        //setting dropdown style on the adapter contents
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //
        //setting the adapter on the spinner
        spinnerMainPath.setAdapter(arrayAdapterSpinner);
        //
        //setting listener on the spinner
        spinnerMainPath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueReturnedFomTheSpinnerAfterSelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing to code here
            }
        });
        //

        //init of FirebaseGlobal
        firebaseStorage = FirebaseStorage.getInstance();
        //

        //end of onCreate function
    }


    private void callFunctionPostingDataToCloudFirebase() {
        String pdfName = editTextEnterPdfName.getText().toString();
        String pdfAuthor = editTextEnterAuthorName.getText().toString();

        //verifying Pdf Name And Author are not null before starting the process of saving the dta to the cloud Store
        if (TextUtils.isEmpty(pdfName) || TextUtils.isEmpty(pdfAuthor)) {
            //alert the developer the process is not permitted with null fields
            new AlertDialog.Builder(this)
                    .setTitle("Null Field(s)")
                    .setMessage("hey,developer the process has been halted due to detection of null or empty fields!")
                    .setCancelable(false)
                    .setIcon(R.drawable.android2)
                    .setPositiveButton("Ok,Lets fix", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss dialog to avoid window leaked Runtime RT Exceptions
                            dialog.dismiss();
                            //
                        }
                    }).create().show();
            //
        }
        //checking the value returned from the spinner to ensure is not null since is a
        //mandatory place holder path in CloudFireStoreOperations
        else if (TextUtils.isEmpty(valueReturnedFomTheSpinnerAfterSelection)) {
            //toasting the error of null value returned
            Toast.makeText(this, "Value Returned From Spinner Is Null!", Toast.LENGTH_SHORT).show();
            //
        }
        //else everything is fine make proceed for cloud Firestore Operations
        else {

            //determining the path that will be taken concisely when storing the data basing on the valueReturnedByTheSpinner

            if (valueReturnedFomTheSpinnerAfterSelection.contains("Programming")) {
                //log if it contains programming
                Log.d(TAG, "callFunctionPostingDataToCloudFirebase: " + valueReturnedFomTheSpinnerAfterSelection + " contains programming");
                //
                //software will be the root parent

                //call function of uploading data to firebase with parent name software
                callFunctionSoftwareUploadToFireBase(valueReturnedFomTheSpinnerAfterSelection);
                //

            }

            //valueReturned does not contain programming
            else {
                //logging for debugging purposes
                Log.d(TAG, "callFunctionPostingDataToCloudFirebase: " + valueReturnedFomTheSpinnerAfterSelection + " does not contain programming");
                //

                //the name of value returned will be the root parent


                //

            }

            //

        }

    }

    private void callFunctionSoftwareUploadToFireBase(String valueReturnedFomTheSpinnerAfterSelection) {

        String nameOfPDF = editTextEnterPdfName.getText().toString();

        //creating a progressDialog showing the progression of the pdf file as its being uploaded to fireBaseStorage
        ProgressDialog progressDialogShowPdfFileUploading = new ProgressDialog(this);
        progressDialogShowPdfFileUploading.setTitle("Uploading " + nameOfPDF);
        progressDialogShowPdfFileUploading.setCancelable(false);
        progressDialogShowPdfFileUploading.create();
        progressDialogShowPdfFileUploading.show();
        //

        //initialisation of firebaseStorage to store Pdf file and getting its download uri and passing it to a function to upload
        //pdf image representative to fireBase Storage and too getting ist download url then passing the two uris to a function
        //that will eventually upload pdfName,authorName, downloadable pdfImageUrl and pdFile url to FireStore
        StorageReference storageReference = firebaseStorage.getReference().child("Software").child(valueReturnedFomTheSpinnerAfterSelection).child(String.valueOf(System.currentTimeMillis()));
        //path will be=> software/valueReturn/itemKey(timer)/resource
        //putting the PDF_URI to Storage
        storageReference.putFile(PDF_FILE_URI_DATA).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //task was successful
                if (task.isSuccessful()) {
                    //dismiss the progressDialog
                    progressDialogShowPdfFileUploading.dismiss();
                    //
                    //lets begin the process of getting download uri after alerting developer of success
                    //
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("PDF UPLOAD")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_baseline_check)
                            .setMessage("pdf file " + nameOfPDF + " has been uploaded successfully,lets locate download uri")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //progressDialogShowing pdfDownloadLink
                                    ProgressDialog progressDialogShowProgressGettingDownloadLink = new ProgressDialog(OverallPDFUPload.this);
                                    progressDialogShowProgressGettingDownloadLink.setTitle("PDF Download URI");
                                    progressDialogShowProgressGettingDownloadLink.setMessage("getting download uri...");
                                    progressDialogShowProgressGettingDownloadLink.setCancelable(false);
                                    progressDialogShowProgressGettingDownloadLink.create();
                                    progressDialogShowProgressGettingDownloadLink.show();

                                    //starting the process of obtaining the download Uri
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                //dismiss the uriProgressDialog
                                                progressDialogShowProgressGettingDownloadLink.dismiss();
                                                //

                                                //storing the downloaded uri into the string variable for traversing
                                                String pdfDownloadableUri = task.getResult().toString();
                                                //

                                                //alerting the user that the process of getting download uri for the uploaded pdf was successful
                                                new AlertDialog.Builder(OverallPDFUPload.this)
                                                        .setTitle("PDF URI")
                                                        .setMessage("gotten the pdf downloadable pdf uri file successfully")
                                                        .setCancelable(false)
                                                        .setIcon(R.drawable.ic_baseline_check)
                                                        .setPositiveButton("ok,proceed", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                //gotten the download uri successfully
                                                                //calling a functionThat will pass this download uri to it then will reverse it till to the firebase fireStore
                                                                //for storage; this function will also upload the image representative for uploaded pdf File
                                                                //
                                                                callFunctionUploadPdfIMageRepresentative(pdfDownloadableUri);
                                                                //
                                                                //dismiss the dialog to avoid window leaked exceptions
                                                                dialog.dismiss();
                                                                //
                                                            }
                                                        }).create().show();
                                                //
                                            } else {
                                                //error occurred alerting the developer
                                                new AlertDialog.Builder(OverallPDFUPload.this)
                                                        .setTitle("PDF URI Download")
                                                        .setIcon(R.drawable.ic_baseline_warning)
                                                        .setCancelable(false)
                                                        .setMessage("hey,developer an error was encountered while fetching the dowwnload uri of the  uploaded " +
                                                                " pdf file\n" + task.getException().getMessage())
                                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                //dismiss the dialog to avoid window leaked exceptions
                                                                dialog.dismiss();
                                                                //
                                                            }
                                                        }).create().show();
                                                //


                                            }


                                        }
                                    });

                                    //dismiss the Alert Dialog to Avoid Window Leaked Runtime Exception
                                    dialog.dismiss();
                                    //
                                }
                            }).create().show();

                    //


                } else {
                    //dismiss the dialog
                    progressDialogShowPdfFileUploading.dismiss();
                    //

                    //alert the developer error occurred while uploading the file
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("Error Encountered")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_baseline_warning)
                            .setMessage("hey, developer an error was encountered while uploading the pdf file to the cloud for storage\n" + task.getException().getMessage())
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
        }).addOnProgressListener(this, new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //getting a double value then converting it into percent
                Double valueProgression = 100.00 * (snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                //
                int valuePercent = valueProgression.intValue();

                progressDialogShowPdfFileUploading.setMessage("uploaded " + valuePercent + "%");
            }
        });

        //

    }

    private void callFunctionUploadPdfIMageRepresentative(String pdfDownloadableUri) {
        //SoftwarePdfImages(root)/valueReturnedSpinner/key(timer)/value

        //progressDialog To Show pdfImage Uploading progress
        ProgressDialog progressDialogPdfImageUploadStatus = new ProgressDialog(this);
        progressDialogPdfImageUploadStatus.setTitle("PDF Image Upload");
        progressDialogPdfImageUploadStatus.setMessage("uploading image of pdf....");
        progressDialogPdfImageUploadStatus.setCancelable(false);
        progressDialogPdfImageUploadStatus.create();
        progressDialogPdfImageUploadStatus.show();
        //


        //initialisation of Storage Reference then beginning the process of uploading pdfImageRepresentative
        StorageReference storageReference = firebaseStorage.getReference().child("SoftwarePdfImage").child(valueReturnedFomTheSpinnerAfterSelection).child(String.valueOf(System.currentTimeMillis()));
        //
        //beginning the process of putting the images to the storage
        storageReference.putFile(PDF_IMAGE_URI_DTA).addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //everything okay
                if (task.isSuccessful()) {
                    //dismiss the progressDialog
                    progressDialogPdfImageUploadStatus.dismiss();
                    //
                    //alerting the developer showing that pdf image representative uploaded successfully and that
                    //next is getting the download uri
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("Pdf Image Upload")
                            .setMessage("hey,developer pdf image representing the uploaded pdf file has been uploaded successfully,next is proceed getting the " +
                                    " downloadable uri link of it")
                            .setCancelable(false)
                            .setPositiveButton("ok,proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //declaring the progressDialogFetchPdfImageUri
                                    ProgressDialog progressDialogFetchPdfImageUri = new ProgressDialog(OverallPDFUPload.this);
                                    progressDialogFetchPdfImageUri.setTitle("PDF Image URi");
                                    progressDialogFetchPdfImageUri.setCancelable(false);
                                    progressDialogFetchPdfImageUri.setMessage("fetching pdf image uri...");
                                    progressDialogFetchPdfImageUri.create();
                                    progressDialogFetchPdfImageUri.show();
                                    //
                                    //starting the process of downloading the pdf image uri before passing it to the next function of firebaseFirestore for
                                    //storing the links and names concerning the uploaded pdf file
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            if (task.isSuccessful()) {
                                                //dismiss the progressDialog
                                                progressDialogFetchPdfImageUri.dismiss();
                                                //
                                                //storing the downloaded uri into a string
                                                String pdfImageUri = task.getResult().toString();
                                                //
                                                //alerting the user that the process of getting download uri for the uploaded pdf was successful
                                                new AlertDialog.Builder(OverallPDFUPload.this)
                                                        .setTitle("PDF Image Downloadable Link")
                                                        .setMessage("gotten the pdf image downloadable pdf successfully,proceed to the last last step of uploading the file" +
                                                                "name,author,pdfImagePath and pdfFilePath to the Cloud Firestore For Storage.")
                                                        .setCancelable(false)
                                                        .setIcon(R.drawable.ic_baseline_check)
                                                        .setPositiveButton("ok,proceed", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                //calling a function which will now traverse two uris for pdf file and its corresponding image
                                                                //where will then upload the files to the fireStore with name,author,pdf FileDownload Uri,pdf Image Download Uri
                                                                //
                                                                callFunctionLastStepStoreDataToFireStoreNow(pdfDownloadableUri, pdfImageUri);

                                                                //dismiss the dialog to avoid window leaked exceptions
                                                                dialog.dismiss();
                                                                //
                                                            }
                                                        }).create().show();

                                            } else {
                                                //error occurred
                                                //dismiss the progress dialog to avoid runtime Exceptions
                                                progressDialogFetchPdfImageUri.dismiss();
                                                //

                                                //alerting the developer
                                                //alert the developer error occurred while uploading the file
                                                new AlertDialog.Builder(OverallPDFUPload.this)
                                                        .setTitle("Error Encountered")
                                                        .setCancelable(false)
                                                        .setIcon(R.drawable.ic_baseline_warning)
                                                        .setMessage("hey, developer an error was encountered while fetching the pdf Image downloadable link from the uploaded " +
                                                                "pdf image representative to firebaseStorage\n" + task.getException().getMessage())
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
                            }).create().show();

                    //

                } //error occurred
                else {
                    //dismiss the progressDialog
                    progressDialogPdfImageUploadStatus.dismiss();
                    //

                    //alerting the developer of the error that has happened
                    //error occurred alerting the developer
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("Pdf Image Upload")
                            .setIcon(R.drawable.ic_baseline_warning)
                            .setCancelable(false)
                            .setMessage("hey,developer an error was encountered while uploading the pdf image to cloud storage  pdf file\n" + task.getException().getMessage())
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //dismiss the dialog to avoid window leaked exceptions
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

    private void callFunctionLastStepStoreDataToFireStoreNow(String pdfDownloadableUri, String pdfImageUri) {
        //path to fireStore=>valueReturnedFromSpinner(root)=collection/key(timer)=document/resourceValue
        //example=>java programming/48738748/name,author,pdfImagePath,pdfFilePath

        //init of ProgressDialogShowFirebaseFirestoreDataUploadStatus
        ProgressDialog progressDialogShowFirebaseFirestoreDataUploadStatus = new ProgressDialog(this);
        progressDialogShowFirebaseFirestoreDataUploadStatus.setTitle("FIRESTORE UPLOAD " + valueReturnedFomTheSpinnerAfterSelection);
        progressDialogShowFirebaseFirestoreDataUploadStatus.setMessage("uploading to firestore...");
        progressDialogShowFirebaseFirestoreDataUploadStatus.setCancelable(false);
        progressDialogShowFirebaseFirestoreDataUploadStatus.create();
        progressDialogShowFirebaseFirestoreDataUploadStatus.show();
        //

        //initialisation of Keys and values
        String keyPdfName = "PdfName";
        String keyAuthorName = "AuthorName";
        String keyPdfImagePath = "PdfImagePath";
        String keyPdfFilePath = "PdfFilePath";

        String valuePdfName = editTextEnterPdfName.getText().toString();
        String valueAuthorName = editTextEnterAuthorName.getText().toString();
        //

        //init of Map to store Values in Key Pair format to FireStore
        Map<String, Object> mapStorePdfData = new HashMap<>();
        mapStorePdfData.put(keyPdfName, valuePdfName);
        mapStorePdfData.put(keyAuthorName, valueAuthorName);
        mapStorePdfData.put(keyPdfImagePath, pdfImageUri);
        mapStorePdfData.put(keyPdfFilePath, pdfDownloadableUri);
        //

        //init of FirebaseFirestore and Collection reference for data upload
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        //starting the upload
        CollectionReference collectionReference = firebaseFirestore.collection(valueReturnedFomTheSpinnerAfterSelection);
        collectionReference.document(String.valueOf(System.currentTimeMillis())).set(mapStorePdfData).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //dismiss the progressDialog Since task of Uploading the Data To FireStore Was Successfully
                    progressDialogShowFirebaseFirestoreDataUploadStatus.dismiss();
                    //

                    //alert the developer that the data was saved Successfully
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("Data Upload Successful")
                            .setCancelable(false)
                            .setMessage("Congratulations the Data Has Been Saved Successfully To The Storage\n" +
                                    "\nPDF_NAME: " + valuePdfName.toUpperCase(Locale.ROOT) + "\n\nPDF_AUTHOR: " + valueAuthorName.toUpperCase(Locale.ROOT) + "\n\n" +
                                    "PDF_IMAGE_PATH:\n" + pdfImageUri + "\n\nPDF_FILE_PATH: " + pdfDownloadableUri)
                            .setPositiveButton("WonderFul", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog to avoid Window Leaked Exceptions
                                    dialog.dismiss();
                                    //
                                }
                            }).create().show();

                    //


                } else {
                    //dismiss the progressDialog to avoid runtime Exceptions from window leaked
                    progressDialogShowFirebaseFirestoreDataUploadStatus.dismiss();
                    //
                    //alert developer an error occurred
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("Error Encountered")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_baseline_warning)
                            .setMessage("hey,developer an error was encountered while finalising the process of uploading the data to the FireStore." +
                                    "\n" + task.getException().getMessage())
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the progress dialog tpo avoid the Exceptions of Window leaked
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

    private void callFunctionPickPdfFileFromStorage() {
        //crating an intent that will trigger off pdf file selection from the internal storage of the device
        Intent intentPickPdfFile = new Intent();
        intentPickPdfFile.setAction(Intent.ACTION_GET_CONTENT);
        intentPickPdfFile.setType("application/pdf");
        //starting the activity for result
        startActivityForResult(Intent.createChooser(intentPickPdfFile, "pick pdf file", null), REQUEST_CODE_PDF_FILE_CODE);
        //

    }

    private void callFunctionPickImageRepresentPdfFile() {

        //creating the intent to pick image from the internal storage a represent for pdf file that will be uploaded to FireStorage;
        Intent intentPickImage = new Intent();
        intentPickImage.setAction(Intent.ACTION_GET_CONTENT);
        intentPickImage.setType("image/*");
        //starting the activity for Result
        //surrounding the method  with try/catch since a Runtime exception might happen leading to the application crushing
        try {
            startActivityForResult(Intent.createChooser(intentPickImage, "pick image", null), REQUEST_CODE_PDF_IMAGE_CODE);
        } catch (Exception e) {
            //logging the error onto the debug console for debugging purposes
            Log.d(TAG, "callFunctionPickImageRepresentPdfFile:Error Occurred=>" + e.getMessage());
            //
            //Toasting the error to the user for simplicity
            Toast.makeText(this, "Error:=>" + e.getMessage(), Toast.LENGTH_SHORT).show();
            //
            //printing the error to the stack trace for debugging purposes extended
            e.printStackTrace();
            //
        }
        //
    }


    //overriding on activity result for data manipulation and retention
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //validating data before operations to avoid system crushing
        if (data != null && data.getData() != null) {
            //data is present lets transaction
            //checking if the data is of image using the image request code
            if (REQUEST_CODE_PDF_IMAGE_CODE == requestCode) {
                //checking if the developer cancelled the process or not using the result code
                if (resultCode == RESULT_OK) {
                    //developer never cancelled the process
                    PDF_IMAGE_URI_DTA = data.getData();
                    //
                    //setting the image to the circle imageView and also to the textView for path display
                    circleImageViewPdfImage.setImageURI(PDF_IMAGE_URI_DTA);
                    //
                    //setting the uri to the textView
                    textViewDisplayPdfImageUri.setText(String.valueOf(PDF_IMAGE_URI_DTA));
                    //

                    //Toasting successful transaction was
                    Toast.makeText(this, "Result Was Successful", Toast.LENGTH_SHORT).show();
                    //

                }
                //developer cancelled
                else if (resultCode == RESULT_CANCELED) {
                    //alert the developer of cancellation process
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("Image File Picking")
                            .setIcon(R.drawable.android2)
                            .setCancelable(false)
                            .setMessage("hey, developer you cancelled the process of obtaining the image file from the internal storage of your device.!")
                            .setPositiveButton("ok,intentionally", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the alert dialog to avoid window leaked runtime exception
                                    dialog.dismiss();
                                    //
                                }
                            })
                            .create().show();
                    //
                }

            }
            //checking if the data is of pdf file using the pdf request code
            else if (REQUEST_CODE_PDF_FILE_CODE == requestCode) {
                //checking if the developer acccepted the process using the resultCode
                if (resultCode == RESULT_OK) {
                    //get data of type URI
                    PDF_FILE_URI_DATA = data.getData();
                    //
                    //setting the pdfFile uri on the textView for path display
                    textViewDisplayPdfFileUri.setText(String.valueOf(PDF_FILE_URI_DATA));
                    //

                    //Toasting successful transaction was
                    Toast.makeText(this, "Result Was Successful", Toast.LENGTH_SHORT).show();
                    //
                }
                //developer cancelled the transaction process
                else if (resultCode == RESULT_CANCELED) {
                    //alert developer of cancellation
                    new AlertDialog.Builder(OverallPDFUPload.this)
                            .setTitle("Pdf File Picking")
                            .setIcon(R.drawable.android2)
                            .setCancelable(false)
                            .setMessage("hey, developer you cancelled the process of obtaining the pdf file from the internal storage of your device!.")
                            .setPositiveButton("ok,intentionally", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the alert dialog to avoid window leaked runtime exception
                                    dialog.dismiss();
                                    //
                                }
                            })
                            .create().show();
                    //


                }

            }
            //

        }
        //


    }

}