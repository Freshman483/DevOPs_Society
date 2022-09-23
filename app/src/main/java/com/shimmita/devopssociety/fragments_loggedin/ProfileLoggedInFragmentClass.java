package com.shimmita.devopssociety.fragments_loggedin;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.shimmita.devopssociety.mains.Registration.CAMERA_REQUEST_CODE;
import static com.shimmita.devopssociety.mains.Registration.FIREBASE_USER_COLLECTION;
import static com.shimmita.devopssociety.mains.Registration.GALLERY_REQUEST_CODE;
import static com.shimmita.devopssociety.mains.Registration.PROFILE_IMAGE_PATH_IN_RDB;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shimmita.devopssociety.R;
import com.shimmita.devopssociety.mains.LoggedInActivity;
import com.shimmita.devopssociety.mains.Login;
import com.shimmita.devopssociety.mains.PostingDataActivity;
import com.shimmita.devopssociety.modal.RetrieveFirebaseCredentialsFromFirestore;
import com.shimmita.devopssociety.modal.RetrieveImageLinksFromRealtimeDB;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class ProfileLoggedInFragmentClass extends Fragment {
    public static final int REQUEST_CODE_CHANGE_IMAGE = 367443;
    public static final String SHARED_PREFERENCE_NAME = "shared_prefs";
    //
    //declaration of shared preference keys
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ACCOUNT_TYPE = "account";
    public static final String KEY_COUNTY = "county";
    public static final String KEY_PASSION = "passion";
    public static final String KEY_IMAGE_PATH = "imagePath";
    public static final String KEY_UNIVERSITY = "university";
    public static final String KEY_SHOW_EMAIL_CONGRATULATIONS_VERIFIED = "key_email_congratulations";
    public static final String KEY_BUNDLE_DATA_HEADER_LOGGED_PROFILE = "key_header_data";
    //
    private static final String TAG = "ProfileLoggedInFragment";
    private static final String DEVELOPERS_NAME = "shimita douglas";
    private static final String COMMANDER_ADMINISTRATOR = "commanderAdmin";
    //declaration of Uri Path Of Image. if image is changed clear the
    //shared preference data of imagePath to null this will necessitate the calling
    //of fetching data from firestore. do it inside onSuccessListener method
    Uri imagePathChangerUri;
    //declaration of the shared preference for storing the data temporarily
    SharedPreferences sharedPreferences;
    //creating progress dialog and dismiss it when data arrives
    ProgressDialog progressDialog;
    //

    //TODO:Copy the whole code for data retrieval from the main loggedIn Activity into the profile fragment so that when the user clicks on the main profile fragment,
    //TODO: the data should not get lost since we are getting it from the main  activity of loggedIn

    //firebase Globals
    String firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    DatabaseReference databaseReference;
    //


    //values Home Fragment
    CircleImageView circleImageViewAccountProfilePicture;
    TextView textViewUsernameLoggedInName,
            textViewUsernameUniversityLoggedIn,
            textViewUsernameLoggedInCounty,
            textViewUsernameLoggedInPassion,
            textViewUserNameLoggedInEmail,
            textViewUserNameLoggedInPhoneNumber,
            textViewUserNameLoggedInAccountType,
            textViewAccountDescriptionText;
    AppCompatButton buttonUpgradeAccount,
            buttonVerifyEmail,
            buttonAdministrationPostingCommander;

    Button buttonChangeProfilePicture;
//

    //empty constructor required
    public ProfileLoggedInFragmentClass() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_loggedin_fragment, container, false);

        //code here
        buttonUpgradeAccount = view.findViewById(R.id.buttonUpgradeAccount);
        buttonVerifyEmail = view.findViewById(R.id.buttonVerifyEmailProfile);
        buttonChangeProfilePicture = view.findViewById(R.id.buttonChangeProfilePicture);
        buttonAdministrationPostingCommander = view.findViewById(R.id.buttonCommander);


        //values Home fragment init from firebase
        circleImageViewAccountProfilePicture = view.findViewById(R.id.headerProfilePicture);
        textViewUsernameLoggedInName = view.findViewById(R.id.headerUsername);
        textViewUsernameUniversityLoggedIn = view.findViewById(R.id.headerUniversity);
        textViewAccountDescriptionText = view.findViewById(R.id.textviewAccountDescription);
        textViewUsernameLoggedInCounty = view.findViewById(R.id.headerCounty);
        textViewUsernameLoggedInPassion = view.findViewById(R.id.headerPassion);
        textViewUserNameLoggedInEmail = view.findViewById(R.id.headerEmail);
        textViewUserNameLoggedInPhoneNumber = view.findViewById(R.id.headerPhoneNumber);
        textViewUserNameLoggedInAccountType = view.findViewById(R.id.headerAccountType);

        //init of progressDialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Profile Data");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.create();
        //
        //
        callFunctionRetrieveData2();
        //

        //creating function to check if the email is verified
        functionCheckEmailVerification();
        //

        //function button verify email clicked
        functionButtonVerifyEmailClicked();
        //

        //setting onclick listener on the buttonCommander
        buttonAdministrationPostingCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call  function to start intent to another activity where we will add posting data functionality to the DevOps Society Kenya
                callFunctionPostingDataDevOpsSociety();
                //
            }
        });
        //

        //setting onclick listener on the button changeProfilePicture

        buttonChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call a functionToUpdateProfilePicture
                callFunctionChangeProfilePicture();
                //

            }
        });
        //

        return view;
    }

    private void callFunctionChangeProfilePicture() {

        //call check permission WriteExternal Storage if Granted or if not request
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "callFunctionChangeProfilePicture: PERMISSION NOT GRANTED");

            //request permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            //
        } else {
            //permission already granted lets continue with process
            Log.d(TAG, "callFunctionChangeProfilePicture: INSIDE INTENT TO PICK PICTURE");
            //starting the intent of opening files of images
            Intent intentChangeProfilePicture = new Intent();
            intentChangeProfilePicture.setAction(Intent.ACTION_PICK);
            intentChangeProfilePicture.setType("image/*");
            startActivityForResult(intentChangeProfilePicture, REQUEST_CODE_CHANGE_IMAGE);
            //
            //
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onRequestPermissionsResult: PERMISSION GRANTED");
            //also permission granted here lets continue again with the intent of picking the imag
            //starting the intent of opening files of images
            Intent intentChangeProfilePicture = new Intent();
            intentChangeProfilePicture.setAction(Intent.ACTION_PICK);
            intentChangeProfilePicture.setType("image/*");
            startActivityForResult(intentChangeProfilePicture, REQUEST_CODE_CHANGE_IMAGE);
            //

        } else {
            //denied to grant permission
            Toast.makeText(requireActivity(), "Permission Denied, Process Halted!", Toast.LENGTH_SHORT).show();
            //
        }
    }


    //override On Activity Result Method
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CHANGE_IMAGE) {
            Log.d(TAG, "onActivityResult: REQUEST_CODE MATCH");
            if (resultCode == RESULT_OK) {
                if (data != null && data.getData() != null) {
                    Log.d(TAG, "onActivityResult: inside Ok Activity Result");
                    //user accepted and has successfully picked an image
                    imagePathChangerUri = data.getData();
                    //
                    //temporarily setting the image so that the user sees it before proceeding
                    //if cancels the process of uploading the image, revert back the image that was
                    //by help of the shared preference data of the image
                    circleImageViewAccountProfilePicture.setImageURI(imagePathChangerUri);
                    //

                    //callFunctionUpdateImage
                    callFunctionStartUpdatingTheImage(imagePathChangerUri);
                    //
                }


            } else if (resultCode == RESULT_CANCELED) {
                //user cancelled
                Log.d(TAG, "onActivityResult: INSIDE Cancelled Activity Result");

                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.ic_baseline_emoji_help_people)
                        .setCancelable(false)
                        .setTitle("Profile Picture Selection")
                        .setMessage("you cancelled the process of choosing a picture that was to update your current profile picture,thus the process failed!")
                        .setPositiveButton("ok,didn't want to change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog to avoid window leaked RT Exceptions
                                dialog.dismiss();
                                //

                            }
                        }).create().show();
                //
            }
        }

    }

    private void callFunctionStartUpdatingTheImage(Uri imagePathChangerUri) {
        //alert the user to  upload the profile picture or else revert back in case
        //declines to upload or uploads and there is failure and no success as per the response from the fireBaseStorage and FireStore
        new AlertDialog.Builder(requireActivity())
                .setTitle("Profile Picture")
                .setMessage("update the newly selected profile picture?")
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_info_24)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d(TAG, "onClick: Call function update image with uri parameter");
                        //call function now upload the profile picture and pass the image uri as parameter
                        callFunctionUpdateProfilePictureNow(imagePathChangerUri);
                        //

                        //dismiss dialog
                        dialog.dismiss();
                        //

                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //call function revert back the image that was initially that will facilitate
                        //the revert back of the image by help of the shared Preference
                        callFunctionRevertTheInitialImage();
                        //

                    }
                }).create().show();
        //


    }

    private void callFunctionUpdateProfilePictureNow(Uri imagePathChangerUri) {
        Log.d(TAG, "callFunctionUpdateProfilePictureNow: ");
        //init of progress dialog
        ProgressDialog progressDialogUpdateProFilePicture = new ProgressDialog(requireActivity());
        progressDialogUpdateProFilePicture.setCancelable(false);
        progressDialogUpdateProFilePicture.setTitle("Profile Picture Update");
        progressDialogUpdateProFilePicture.setMessage("please wait while updating your profile ...");
        progressDialogUpdateProFilePicture.create();
        progressDialogUpdateProFilePicture.show();
        //

        //starting update of the firebase storage image present first then getting its download link
        //w/c we will then use that link to update path in the fireStore
        //init of shared pref to obtain saved username
        SharedPreferences sharedPreferenceInternalVariable = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String userNameFromSharedPreferenceValue = sharedPreferenceInternalVariable.getString(KEY_USERNAME, "");
        //
        //deleting the previous image before adding latest One
        sharedPreferenceInternalVariable = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String originalUri = sharedPreferenceInternalVariable.getString(KEY_IMAGE_PATH, "");
        Log.d(TAG, "callFunctionUpdateProfilePictureNow: Original image path=>" + originalUri);
        //

        //TODO:Begin here in the Name of YASHUA HAMASIACHI
        //call a function that will delete the image from the storage using reference uri and then calling a function that will update the new image
        //uri path in the fireStore and RDB since we saved same image uri in RDB and in FireStore During  registration thus must be adhered too

        callFunctionDeleteImageFirstFromStorage(progressDialogUpdateProFilePicture, originalUri, userNameFromSharedPreferenceValue);
        //


    }

    private void callFunctionDeleteImageFirstFromStorage(ProgressDialog progressDialogUpdateProFilePicture, String originalUri, String userNameFromSharedPreferenceValue) {
        //setting message of the progressDialog to removing the old picture since it is wha we are doing inside this function
        progressDialogUpdateProFilePicture.setMessage("removing old photo...");
        //

        //starting the process of deleting the image from the storage using the  getReference fromUri else would be impossible task
        //init FirebaseStorage
        //the uri would be that of the original image w/c is currently stored in the shared preference (originalUri passed as parameter in the current function)
        //
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(originalUri);
        //

        //starting the process of deleting the image from the storage
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: inside successfully deleted the initial image function of storageReference");
                //setting the message of the progress dialog to successfully deleted
                progressDialogUpdateProFilePicture.setMessage("successfully deleted....");
                //
                //successfully deleted from the storage
                //callFunction that will now propagate storing new image as a replace of the one deleted from the storage and also get the new image download ur
                //which we will the use it to update firestoreUri and RDB
                //path=>ProfileImages/firebaseUserId/username(obtainFromSharedPreference)
                String mainChild = "ProfileImages";
                String secondChild = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                //third child value will be from parameter sharedPreference username value
                String thirdChild = userNameFromSharedPreferenceValue;
                //
                callFunctionAddNewImageUriToFireBaseStorageReplaceInitialImage(mainChild, secondChild, thirdChild, progressDialogUpdateProFilePicture, originalUri);
                //

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: failed to delete initial image inside failure method of storage");
                //dismiss the progress dialog and alert the user of an error occurred
                progressDialogUpdateProFilePicture.dismiss();
                //

                //revert back to initial image since its deletion failed
                //alert the user of an error while deleting the initial image
                new AlertDialog.Builder(requireActivity())
                        .setIcon(R.drawable.ic_baseline_warning)
                        .setTitle("Error Deleting Profile Picture")
                        .setCancelable(false)
                        .setMessage("system encountered an error while trying to remove your initial profile picture. try again later\n" + e.getMessage())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (TextUtils.isEmpty(originalUri))
                                {
                                    circleImageViewAccountProfilePicture.setImageResource(R.color.black);
                                    //dismiss dialog
                                    dialog.dismiss();
                                    //
                                }
                                else {
                                    //reverting back to the initial image using the glide library
                                    Glide.with(requireActivity()).load(originalUri).into(circleImageViewAccountProfilePicture);
                                    //

                                    //dismiss dialog
                                    dialog.dismiss();
                                    //
                                }
                            }
                        }).create().show();
                //

            }
        });
        //


    }

    private void callFunctionAddNewImageUriToFireBaseStorageReplaceInitialImage(String mainChild, String secondChild, String thirdChild, ProgressDialog progressDialogUpdateProFilePicture, String originalUri) {
        //setting the message of the progressDialog to
        progressDialogUpdateProFilePicture.setMessage("Saving the new image....");
        //
        //init of FireVBaseStorage and Storage reference to facilitate the operations of adding the the new image into The Storage
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child(mainChild).child(secondChild).child(thirdChild);
        //

        //starting the process of putting the new image into the Storage using the Global uri obtained from pick image replacer
        storageReference.putFile(imagePathChangerUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //logging the success for debugging purposes
                Log.d(TAG, "onSuccess: new image successfully added into the storage");
                //
                //setting the message of progressDialog to
                progressDialogUpdateProFilePicture.setMessage("new image successfully saved.... ");
                //

                //successfully updated the image into the storage starting the process o downloading the imageUri W/c we will then store it
                //into the FireStore And RDB as a replacement of the original Image imagesPath respectively
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //logging the results for debugging purposes
                        Log.d(TAG, "onSuccess: Successfully retrieved the new  imageDownloadUri hence ready for FireStore ");
                        //
                        //setting the message of the progressDialog to
                        progressDialog.setMessage("validating results...");

                        //calling the function which will now be the last to upload data to fireStore and RDB
                        String imageUriString = uri.toString();
                        callFunctionUpdateOnFireStoreAndRDBNow(imageUriString, progressDialogUpdateProFilePicture);
                        //

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //dismiss the progressDialog
                        progressDialogUpdateProFilePicture.dismiss();
                        //

                        //logging the failure for debugging purposes
                        Log.d(TAG, "onFailure: Failed retrieving the new  image download Uri, not ready for firesStore");
                        //
                        //alert the user user of failure to fetch download Uri revert back to the original uri saved in shared preference
                        new AlertDialog.Builder(requireActivity())
                                .setIcon(R.drawable.ic_baseline_warning)
                                .setTitle("Profile Picture Upload Failed")
                                .setCancelable(false)
                                .setMessage("system encountered an error while trying to update your profile picture. try again later\n" + e.getMessage())
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (TextUtils.isEmpty(originalUri)) {
                                            circleImageViewAccountProfilePicture.setImageResource(R.color.black);
                                            //dismiss dialog to avoid RT Exceptions w/c causes an application to crush
                                            dialog.dismiss();
                                            //
                                        } else {
                                            //reverting back to the original image uri Using glide library
                                            Glide.with(requireActivity()).load(originalUri).into(circleImageViewAccountProfilePicture);
                                            //

                                            //dismiss dialog to avoid RT Exceptions w/c causes an application to crush
                                            dialog.dismiss();
                                            //
                                        }
                                    }
                                }).create().show();
                        //

                    }
                });

                //


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //logging the failure for debugging purposes
                Log.d(TAG, "onFailure: Failed To Save Your New Image To The CloudStorage");
                //


                //progressDialog Dismiss since Failed To Store the New Image in the CloudStorage
                progressDialogUpdateProFilePicture.dismiss();
                //

                //alert the user of the failure and revert back to the original image from sharedPreference
                new AlertDialog.Builder(requireActivity())
                        .setIcon(R.drawable.ic_baseline_warning)
                        .setTitle("Profile Picture Upload Failed")
                        .setCancelable(false)
                        .setMessage("system encountered an error while trying to update your profile picture. try again later\n" + e.getMessage())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (TextUtils.isEmpty(originalUri)) {
                                    circleImageViewAccountProfilePicture.setImageResource(R.color.black);
                                    //dismiss dialog to avoid RT Exceptions w/c causes an application to crush
                                    dialog.dismiss();
                                    //

                                } else {
                                    //using the Glide Library to set The Image
                                    Glide.with(requireActivity()).load(originalUri).into(circleImageViewAccountProfilePicture);
                                    //

                                    //dismiss dialog to avoid RT Exceptions w/c causes an application to crush
                                    dialog.dismiss();
                                    //

                                }

                            }
                        }).create().show();

                //

            }
        });
        //

    }


    private void callFunctionUpdateOnFireStoreAndRDBNow(String imageUriString, ProgressDialog progressDialogUpdateProFilePicture) {
        //setting the message of the progressDialog
        progressDialogUpdateProFilePicture.setMessage("Finishing Validating The Profile Picture...");
        //

        //
        String keyImagePath = "imagePath";
        //creating a map that will help in updating the imagePath In the fireStore
        Map<String, Object> mapUpdateImage = new HashMap<>();
        mapUpdateImage.put(keyImagePath, imageUriString);

        //init firebase user to give us the unique key(uid) of the user
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //


        //updating the image of of the RDB first before to the fireStore;
        //init of RDB and its reference
        sharedPreferences=requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        String userNameFromRDB=sharedPreferences.getString(KEY_USERNAME,"");
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        assert firebaseUser != null;
        DatabaseReference databaseReference=firebaseDatabase.getReference().child(PROFILE_IMAGE_PATH_IN_RDB).child(firebaseUser.getUid()).child(userNameFromRDB);
        //starting the process of updating the imagePath in the RDB with the path of the New Image
        databaseReference.updateChildren(mapUpdateImage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                //setting the message of the ProgressDialog to
                progressDialogUpdateProFilePicture.setMessage("successfully finished,Congrats!");
                //
            }
        });
        //


        //fireStore imagePath Update
        //init of the FireStore to update the image
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection(FIREBASE_USER_COLLECTION).document(firebaseUser.getUid());
        //

        //updating the data of the existing image uri with update method
        documentReference.update(mapUpdateImage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //dismiss the progress dialog
                progressDialogUpdateProFilePicture.dismiss();
                //

                // alert Profile Picture updated successFully
                new AlertDialog.Builder(requireActivity())
                        .setTitle("Profile Picture Updated")
                        .setMessage("Congratulations!, your profile picture has been uploaded successfully.Examine the looks of your newly updated profile now?")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_baseline_check)
                        .setPositiveButton("yes,that's great", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //set the imageValue store in the shared preference to null so that android will automatically fetch whole data from the cloud to counter the
                                //null value of imagePath in the shared preference
                                sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_IMAGE_PATH, "");
                                editor.apply();
                                //
                                //refresh the activity by animations the user back to the LoggedIn Home Profile where data will automatically be downloaded again and saved in the
                                //shared preference as it was programmed
                                requireActivity().startActivity(new Intent(requireActivity(), LoggedInActivity.class));
                                //
                                //animating the intent
                                CustomIntent.customType(requireActivity(), "up-to-bottom");
                                //

                                //dismiss the progress dialog to evade RT Exceptions
                                dialog.dismiss();
                                //
                            }
                        }).create().show();
                //


            }
        }).addOnFailureListener(new OnFailureListener() {

            //dismiss

            //

            @Override
            public void onFailure(@NonNull Exception e) {
                //
                progressDialogUpdateProFilePicture.dismiss();
                //

                new AlertDialog.Builder(requireActivity())
                        .setIcon(R.drawable.ic_baseline_warning)
                        .setTitle("Profile Picture")
                        .setCancelable(false)
                        .setMessage("system encountered an error while trying to update your profile picture. try again later\n" + e.getMessage())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                                String imageOriginalPath = sharedPreferences.getString(KEY_IMAGE_PATH, "");
                                //
                                if (TextUtils.isEmpty(imageOriginalPath)) {
                                    circleImageViewAccountProfilePicture.setImageResource(R.color.black);

                                    //dismiss the dialog to avoid RT Exceptions
                                    dialog.dismiss();
                                    //
                                } else {
                                    Glide.with(requireActivity()).load(imageOriginalPath).into(circleImageViewAccountProfilePicture);

                                    //dismiss the dialog to avoid RT Exceptions
                                    dialog.dismiss();
                                    //
                                }
                                //

                            }
                        }).create().show();
            }
        });
        //

    }


    private void callFunctionRevertTheInitialImage() {

        //init of shared preference and getting the image path of profile image store inside it
        sharedPreferences=requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        String image_initially_stored=sharedPreferences.getString(KEY_IMAGE_PATH,"");
        //

       if (TextUtils.isEmpty(image_initially_stored))
        {
            //image is empty/null from the shared preference hence can lead to RT Exceptions and application crush
            //thus is better to replace the image with any applicable resource
            circleImageViewAccountProfilePicture.setImageResource(R.color.black);
        }
        else
       {
           //initial image is present and no null
           //using the Glide Library to set The Image
           Glide.with(requireActivity()).load(image_initially_stored).into(circleImageViewAccountProfilePicture);
           //

       }



    }


    private void callFunctionPostingDataDevOpsSociety() {
        //toasty account migration was successfully

        //
        //creating an intent to trigger migration into the posting data profile
        requireActivity().startActivity(new Intent(requireActivity(), PostingDataActivity.class));
        //
        //animating the account migration
        CustomIntent.customType(requireActivity(), "fadein-to-fadeout");
        //

    }

    private void functionButtonVerifyEmailClicked() {
        //setting onclick listener to the button verify the email account
        buttonVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lets verify this user by sending an email verification to their emails
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        new AlertDialog.Builder(getActivity())
                                .setMessage("an email verification has been sent to your email address " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + " " +
                                        "if the email is not located in the inbox folder, then  check it in the SPAM folder and verify the genuineness of your account")
                                .setCancelable(false)
                                .setIcon(R.drawable.ic_baseline_info_24)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //sign out this user to prevent issues with emil verification status cox tho might have been verified but says false
                                        FirebaseAuth.getInstance().signOut();
                                        //
                                        dialogInterface.dismiss();
                                        //directing the user to the login activity
                                        startActivity(new Intent(getActivity(), Login.class));
                                        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                                        //

                                    }
                                }).create().show();

                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //checking if the Exception e is not null meaning it contains some error information
                        new MaterialAlertDialogBuilder(getActivity())
                                .setCancelable(false)
                                .setMessage("Unknown error occurred while trying to send the the email verification message to your email " + FirebaseAuth.getInstance().getCurrentUser().getEmail() +
                                        " Please the process again.\nError Cause:\n" + e.getMessage().toUpperCase(Locale.ROOT))
                                .setPositiveButton("Re-try", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create().show();
                        //
                    }
                });
                //
            }
        });
    }

    private void functionCheckEmailVerification() {
        //logging the email verification status
        Log.d(TAG, "\n\nfunctionCheckEmailVerification: Is email verified? " + FirebaseAuth.getInstance().getCurrentUser().isEmailVerified() + "\n\n");
        //

        if (!(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())) {
            //alert user that the email is not verified
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Email Verification!")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_baseline_info_24)
                    .setMessage("dear user your email:" + FirebaseAuth.getInstance().getCurrentUser().getEmail().toUpperCase(Locale.ROOT) + " is not verified, prevent this account from being " +
                            " disabled or deleted permanently by clicking the button verify email displayed in the account  profile.\n\n" +
                            "Accounts with non-verified email addresses are not regarded as genuine accounts and have a lifeSpan of 2 days only !!")
                    .setPositiveButton("Ok,Imma Verify", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        } //email verified successfully
        else {
            //basing on the value returned from the shared preference on email verification
            //will decide showing of the congratulations email verified
            //another init of shared preference after of saving credentials and don't show again congrats email  from user
            SharedPreferences sharedPreferences2 = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            //init string that store value of shared prefs concerning showing of the email
            String showEmail = sharedPreferences2.getString(KEY_SHOW_EMAIL_CONGRATULATIONS_VERIFIED, "");
            //logging the value show email for debugging purposes
            Log.d(TAG, "\nfunctionCheckEmailVerification: showEmailCongratulationsVerified value=>" + showEmail + "\n");
            //


            //conditional checking
            if (showEmail.equals("no")) {
                //show  button of upgrade account and disable button verify email since the email has already been verified

                //enabling upgrade the account button
                buttonUpgradeAccount.setVisibility(View.VISIBLE);
                //disabling the verify email button
                buttonVerifyEmail.setVisibility(View.GONE);
                //

                //
            }
            //user has not  opted don't show email again thus show him anyways
            else {
                callFunctionShowCongratulationsEmailVerified();
            }

        }
    }

    private void callFunctionShowCongratulationsEmailVerified() {
        //visibility visible button upgrade account and visibility gone verify email since email verified

        //enabling upgrade the account button
        buttonUpgradeAccount.setVisibility(View.VISIBLE);
        //disabling the verify email button
        buttonVerifyEmail.setVisibility(View.GONE);
        //

        //

        new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations Email Verified Successfully")
                .setIcon(R.drawable.ic_baseline_check)
                .setMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail().toUpperCase(Locale.ROOT) + " the email is verified, you can now continue using your account. we offer " +
                        "two types of account:\n\n1.The Normal account with less privileges unless upgraded to premium or super user account.\n\n" +
                        "2.The Premium(Super User Account) with unlocked features which are locked in normal account. upgrade fee @Ksh.100")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("don't show again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //init of shared preference and adding don't show again value of this congratulations
                        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                        //init of shared preference editor
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_SHOW_EMAIL_CONGRATULATIONS_VERIFIED, "no");
                        editor.apply();
                        //

                    }
                })
                .create().show();
    }


    private void callFunctionRetrieveData2() {
        //first we will fetch the data from the shared preference and figure out if its null then call the function to download the data from from the fireStore
        //2nd init of sharedPreference and fishing  out its content
        SharedPreferences sharedPreferences1 = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        //declarations of the strings that will hold the data returned from the shared shared preferences
        String username,
                county,
                email,
                imagePath,
                phone,
                passion,
                accountType,
                university;
        //
        //assigning the data  into the variables
        username = sharedPreferences1.getString(KEY_USERNAME, "");
        county = sharedPreferences1.getString(KEY_COUNTY, "");
        email = sharedPreferences1.getString(KEY_EMAIL, "");
        imagePath = sharedPreferences1.getString(KEY_IMAGE_PATH, "");
        phone = sharedPreferences1.getString(KEY_PHONE, "");
        passion = sharedPreferences1.getString(KEY_PASSION, "");
        accountType = sharedPreferences1.getString(KEY_ACCOUNT_TYPE, "");
        university = sharedPreferences1.getString(KEY_UNIVERSITY, "");
        //
        //begin log of shared preference
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference Phone=>" + phone);
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference username=>" + username);
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference email=>" + email);
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference county=>" + county);
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference university=>" + university);
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference accountRole=>" + accountType);
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference imagePath=>" + imagePath);
        Log.d(TAG, "\ncallFunctionRetrieveData2: sharedPreference Passion=>" + passion);
        //end log of shared preference

        //checking some conditions on the data that is returned by the shared preference such that if it's null true
        //then fetch data from fireStore else show the data that is saved in shared Preferences
        //someone is missing the data hence fetch it must from CloudStorage
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(county) || TextUtils.isEmpty(university) ||
                TextUtils.isEmpty(accountType) || TextUtils.isEmpty(imagePath) || TextUtils.isEmpty(passion)) {
            callFunctionDownloadDataFromFirestore();
        }//data is present thus set it on the appropriated Views
        else {
            //call function to do the job
            callFunctionSetDataOnTheViewsFromDataSharedPreferences(username, passion, email, phone, imagePath, accountType, county, university);
            //

        }


    }

    private void callFunctionSetDataOnTheViewsFromDataSharedPreferences(String username, String passion, String email, String phone, String imagePath, String accountType, String county, String university) {
        //begin setting data

        //dismiss the global progress Dialog since there is data
        progressDialog.dismiss();
        //


        //loading the image url onto the circleImageView Using Glide Library
        Glide.with(getActivity()).load(imagePath).into(circleImageViewAccountProfilePicture);
        //loading other account credentials onto the required fields
        //username
        textViewUsernameLoggedInName.setText(username.toUpperCase(Locale.ROOT));
        //university
        textViewUsernameUniversityLoggedIn.setText(university);
        //email
        textViewUserNameLoggedInEmail.setText(email);
        //phoneNumber
        textViewUserNameLoggedInPhoneNumber.setText(phone);
        //county
        textViewUsernameLoggedInCounty.setText(county);
        //account role
        textViewUserNameLoggedInAccountType.setText(accountType);
        //checking If the account role and Name contains or is equal to Developers Name,and  commanderAdmin is role,then we set
        //the button commander Visibility to visible else we set the button commander invisible because all users are not developers
        //but one of em is he.me;
        //when we click the button commander we will be directed to an activity to post data which will be available fo all users for access

        if (textViewUsernameLoggedInName.getText().toString().toUpperCase(Locale.ROOT).equals(DEVELOPERS_NAME.toUpperCase(Locale.ROOT))
                && textViewUserNameLoggedInAccountType.getText().toString().toUpperCase(Locale.ROOT).equals(COMMANDER_ADMINISTRATOR.toUpperCase(Locale.ROOT))) {
            //enabling the administration Commander Button that facilitates posting of the services to the users
            //distinguishes all users from the Developer
            buttonAdministrationPostingCommander.setVisibility(View.VISIBLE);
            //
            //setting the textView Description to Developer/Owner
            textViewAccountDescriptionText.setText("Prime Owner,Developer Of The Society");
            textViewAccountDescriptionText.setTextColor(textViewUserNameLoggedInAccountType.getTextColors().getDefaultColor());
            //
        }


        //passion
        textViewUsernameLoggedInPassion.setText(passion);
        //
        //end setting data on


        //start bundle
        //create bundle which will forward the data to profile navigation view header in case its data is null using fragment manager
        Bundle bundleSendDataProfileNavHeader = new Bundle();
        bundleSendDataProfileNavHeader.putString(KEY_USERNAME, username);
        bundleSendDataProfileNavHeader.putString(KEY_EMAIL, email);
        bundleSendDataProfileNavHeader.putString(KEY_IMAGE_PATH, imagePath);
        bundleSendDataProfileNavHeader.putString(KEY_PHONE, phone);
        bundleSendDataProfileNavHeader.putString(KEY_PASSION, passion);
        bundleSendDataProfileNavHeader.putString(KEY_ACCOUNT_TYPE, accountType);
        bundleSendDataProfileNavHeader.putString(KEY_UNIVERSITY, university);
        bundleSendDataProfileNavHeader.putString(KEY_COUNTY, county);
        //end bundle data for nav header profile logged in
        //sending the data to the main loggedIn activity using the fragment manager set_result method
        requireActivity().getSupportFragmentManager().setFragmentResult(KEY_BUNDLE_DATA_HEADER_LOGGED_PROFILE, bundleSendDataProfileNavHeader);
        //

    }


    //function to download data from fireStore
    private void callFunctionDownloadDataFromFirestore() {
        //init of collection,document reference and firebase user of the currently logged in
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser().getUid();
        //controlling firebase Auth null issues
       /* if (firebaseUser == null) {
            return;
        }*/
        //
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection(FIREBASE_USER_COLLECTION).document(firebaseUser);


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    //
                    Log.d(TAG, "onSuccess: ValueOF Snapshot->" + documentSnapshot.getData());
                    RetrieveFirebaseCredentialsFromFirestore retrieveFirebaseCredentialsFromFirestore = documentSnapshot.toObject(RetrieveFirebaseCredentialsFromFirestore.class);
                    //


                    //begin of shared preference account

                    //init of shared preference here and putting the data from fireStore into it to help in avoiding reloads everytime the user logs into
                    //the account this prevents wastage of the data too
                    sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                    //init of shared pref editor that facilitates data storage
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //putting username in shared preference
                    editor.putString(KEY_USERNAME, retrieveFirebaseCredentialsFromFirestore.getUsername());
                    //putting the email into shared preferences
                    editor.putString(KEY_EMAIL, retrieveFirebaseCredentialsFromFirestore.getEmail());
                    //
                    Log.d(TAG, "onSuccess: Email Here Here=" + retrieveFirebaseCredentialsFromFirestore.getEmail());
                    //
                    //putting university in the shared preference
                    editor.putString(KEY_UNIVERSITY, retrieveFirebaseCredentialsFromFirestore.getUniversity());
                    //putting county in the shared preference
                    editor.putString(KEY_COUNTY, retrieveFirebaseCredentialsFromFirestore.getCounty());
                    //putting account type in the shared preferences
                    editor.putString(KEY_ACCOUNT_TYPE, retrieveFirebaseCredentialsFromFirestore.getRole());
                    //putting the phone number in the shared preferences
                    editor.putString(KEY_PHONE, retrieveFirebaseCredentialsFromFirestore.getPhoneNumber());
                    //putting passion into the shared preference
                    editor.putString(KEY_PASSION, retrieveFirebaseCredentialsFromFirestore.getPassion());
                    //putting imagePath into shared preference
                    editor.putString(KEY_IMAGE_PATH, retrieveFirebaseCredentialsFromFirestore.getImagePath());
                    //applying saving the data into the shared preference using apply method
                    editor.apply();
                    //

                    //end of shared preference for account


                    //begin
                    //returns converted value from firebase
                    Log.d(TAG, "\nonSuccess: Username->" + retrieveFirebaseCredentialsFromFirestore.getUsername());
                    Log.d(TAG, "\nonSuccess: University->" + retrieveFirebaseCredentialsFromFirestore.getUniversity());
                    Log.d(TAG, "\nonSuccess: County->" + retrieveFirebaseCredentialsFromFirestore.getCounty());
                    // Log.d(TAG, "\nonSuccess: Password->" + retrieveFirebaseCredentialsFromFirestore.getPassword());
                    Log.d(TAG, "\nonSuccess: Passion->" + retrieveFirebaseCredentialsFromFirestore.getPassion());
                    Log.d(TAG, "\nonSuccess: Email->" + retrieveFirebaseCredentialsFromFirestore.getEmail());
                    Log.d(TAG, "\nonSuccess: Role->" + retrieveFirebaseCredentialsFromFirestore.getRole());
                    Log.d(TAG, "\nonSuccess: PhoneNumber->" + retrieveFirebaseCredentialsFromFirestore.getPhoneNumber());
                    Log.d(TAG, "\nonSuccess: Occupation->" + retrieveFirebaseCredentialsFromFirestore.getOccupation() + "\n");

                    //end


                    //starting the process of obtaining the imageUrl from the RealTime Database
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(PROFILE_IMAGE_PATH_IN_RDB).child(firebaseUser).child(retrieveFirebaseCredentialsFromFirestore.getUsername());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {

                                //
                                Log.d(TAG, "\nonDataChange: Snapshot Exists");
                                //


                                //logging the value of the snapshot
                                Log.d(TAG, "\nonDataChange: value of the snapshot=>" + snapshot.getValue());
                                //
                                //retrieving the image url only using the class object
                                RetrieveImageLinksFromRealtimeDB imageLinkFromRDB = snapshot.getValue(RetrieveImageLinksFromRealtimeDB.class);
                                Log.d(TAG, "\nonDataChange: imageLinkOnly=>" + imageLinkFromRDB.getImagePath());
                                String url = imageLinkFromRDB.getImagePath().toString();

                                //re adding the data again when user clicks on the profile icon of bottom nav else it will display empty fields!
                                //loading the image url onto the circleImageView Using Glide Library
                                Glide.with(getActivity()).load(url).into(circleImageViewAccountProfilePicture);
                                //loading other account credentials onto the required fields
                                //username
                                textViewUsernameLoggedInName.setText(retrieveFirebaseCredentialsFromFirestore.getUsername().toUpperCase(Locale.ROOT));
                                //university
                                textViewUsernameUniversityLoggedIn.setText(retrieveFirebaseCredentialsFromFirestore.getUniversity());
                                //email
                                textViewUserNameLoggedInEmail.setText(retrieveFirebaseCredentialsFromFirestore.getEmail());
                                //phoneNumber
                                textViewUserNameLoggedInPhoneNumber.setText(retrieveFirebaseCredentialsFromFirestore.getPhoneNumber());
                                //county
                                textViewUsernameLoggedInCounty.setText(retrieveFirebaseCredentialsFromFirestore.getCounty());
                                //account role
                                textViewUserNameLoggedInAccountType.setText(retrieveFirebaseCredentialsFromFirestore.getRole());
                                //checking If the account role and Name contains or is equal to Developers Name,and  commanderAdmin is role,then we set
                                //the button commander Visibility to visible else we set the button commander invisible because all users are not developers
                                //but one of em is he.me;
                                //when we click the button commander we will be directed to an activity to post data which will be available fo all users for access

                                if (textViewUsernameLoggedInName.getText().toString().toUpperCase(Locale.ROOT).equals(DEVELOPERS_NAME.toUpperCase(Locale.ROOT))
                                        && textViewUserNameLoggedInAccountType.getText().toString().toUpperCase(Locale.ROOT).equals(COMMANDER_ADMINISTRATOR.toUpperCase(Locale.ROOT))) {
                                    //enabling the administration Commander Button that facilitates posting of the services to the users
                                    //distinguishes all users from the Developer
                                    buttonAdministrationPostingCommander.setVisibility(View.VISIBLE);
                                    //
                                    //setting the textView Description to Developer/Owner
                                    textViewAccountDescriptionText.setText("Prime Owner,Developer Of The Society");
                                    textViewAccountDescriptionText.setTextColor(textViewUserNameLoggedInAccountType.getTextColors().getDefaultColor());
                                    //

                                }


                                //passion
                                textViewUsernameLoggedInPassion.setText(retrieveFirebaseCredentialsFromFirestore.getPassion());
                                //

                                //dismiss the progress dialog since the data has been retrieved
                                if (progressDialog.isShowing()) {
                                    progressDialog.cancel();
                                    progressDialog.dismiss();
                                    //
                                }
                                //

                            } else {
                                Log.d(TAG, "\nonDataChange: Snapshot not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            if (error != null) {
                                Log.d(TAG, "onCancelled: error=>" + error.getMessage());
                            }
                        }
                    });
                    //
                }
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Request Unsuccessful")
                        .setMessage("dear user, android encountered an error while loading data from the database server.\nthis has happened due to:\n" + e.getMessage().toUpperCase(Locale.ROOT))
                        .setIcon(R.drawable.android2)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
            }
        });

    }

    private void callFunctionVerifyThisCurrentlyLoggedInUser() {
        FirebaseUser userCurrentlyLogged = FirebaseAuth.getInstance().getCurrentUser();

        userCurrentlyLogged.sendEmailVerification().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Email Verification Sent To " + userCurrentlyLogged.getEmail(), Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

    }

    private void functionDeleteUserAccount(FirebaseUser userCurrentlyLogged) {
        userCurrentlyLogged.delete().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Account  Deleted Successfully", Toast.LENGTH_SHORT).show();
                } else if (!task.isSuccessful()) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Failed To Delete Account")
                            .setMessage("android failed to perform the requested operation,the message has been sent to the developer to delete the account fully.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            }
        });
    }
    //


}
