package com.shimmita.devopssociety.databases;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shimmita.devopssociety.R;

import java.util.Locale;

public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "client.db";
    public static final String TABLE_NAME = "client_record_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PHONE_NUMBER = "PHONE";
    public static final String COLUMN_COUNTY = "COUNTY";
    public static final String COLUMN_PASSION = "PASSION";
    public static final String COLUMN_LEVEL_OF_KNOWLEDGE = "KNOWLEDGE";
    public static final String COLUMN_GENDER = "GENDER";
    public static final String COLUMN_OCCUPATION = "OCCUPATION";


    Context context;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PASSWORD + " TEXT," + COLUMN_EMAIL + " TEXT," + COLUMN_USERNAME + " TEXT," + COLUMN_PHONE_NUMBER + "TEXT," +
                COLUMN_COUNTY + " TEXT," + COLUMN_PASSION + " TEXT," + COLUMN_LEVEL_OF_KNOWLEDGE + " TEXT," +
                COLUMN_GENDER + " TEXT," + COLUMN_OCCUPATION + " TEXT);";
        db.execSQL(SQL_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(SQL_DELETE);

        onCreate(db);
    }

    public void insertDataFunction(
            @Nullable
                    String password,
            @Nullable
                    String email,
            @Nullable
                    String username,
            @Nullable
                    String phone,
            @Nullable
                    String county,
            @Nullable
                    String passion,
            @Nullable
                    String knowledge,
            @Nullable
                    String gender,
            @Nullable
                    String occupation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PASSWORD, password);

        contentValues.put(COLUMN_EMAIL, email);

        contentValues.put(COLUMN_USERNAME, username);

        contentValues.put(COLUMN_PHONE_NUMBER, phone);

        contentValues.put(COLUMN_COUNTY, county);

        contentValues.put(COLUMN_PASSION, passion);

        contentValues.put(COLUMN_LEVEL_OF_KNOWLEDGE, knowledge);

        contentValues.put(COLUMN_GENDER, gender);

        contentValues.put(COLUMN_OCCUPATION, occupation);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Registration Error", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, username.toUpperCase(Locale.ROOT) +
                            ":Your data has been successfully\n" +
                            "submitted to DevOPs Society Databases",
                                    Toast.LENGTH_LONG).show();
        }
        //

    }

    public Cursor readDataFunction() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void deleteAllFunction(Context context) {
        materialAlertDialogBuilder.setTitle("RISKY OPERATION");
        materialAlertDialogBuilder.setMessage("Dear DevOPS User Your Data Will Be Completely Wiped Off From Our Systems!.\n" +
                "\nThis Process Is Completely Irreversible and Uncontrollable; At your own Risk." +
                "\nAre Sure You Want To Perform This Operation ?");
        materialAlertDialogBuilder.setIcon(R.drawable.ic_baseline_warning);
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.setNegativeButton("Yes,I Risk!", (dialog, which) -> {

            //  Snackbar.make(view.getContext(), view, "ACCOUNT SHUTDOWN!", Snackbar.LENGTH_LONG).setTextColor(Color.RED);
            Toast.makeText(context, "ACCOUNT DELETING....", Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = this.getWritableDatabase();
            String query_delete = "DELETE FROM " + TABLE_NAME;
            db.execSQL(query_delete);

        });

        materialAlertDialogBuilder.setPositiveButton("No, Don't", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                materialAlertDialogBuilder.setCancelable(true);
                Toast.makeText(context, "The Process has Been Halted, thank you", Toast.LENGTH_LONG).show();
            }
        });
        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();

    }

    //under construction UpdateFunction
/*
    public void updateDataFunction(
            @Nullable
                    String username,
            @Nullable
                    String password,
            @Nullable
                    String email,
            @Nullable
                    String phone
    ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (!(username.isEmpty()))
        {contentValues.put(COLUMN_USERNAME, username);
        }
        else if (!(password.isEmpty()))
        {
            contentValues.put(COLUMN_PASSWORD, password);
        }
        else if (!(email.isEmpty()))
        {
            contentValues.put(COLUMN_EMAIL, email);
        }
        else if (!(phone.isEmpty()))
        {
            contentValues.put(COLUMN_PHONE_NUMBER, phone);
        }


    }*/


}
