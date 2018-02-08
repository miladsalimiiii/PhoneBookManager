package com.phonebookmanager.miladsi.phonebookmanager.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.phonebookmanager.miladsi.phonebookmanager.Model.Contact;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DatabaseName = "mycontact.db";
    private static final int Version = 1;
    private static final String TableName = "tbl_contact";
    private static final String dID = "id";
    private static final String dName = "name";
    private static final String dFamily = "famili";
    private static final String demail = "email";
    private static final String dphonenumber = "phonenumber";
    private static final String dmobilenumber = "mobilenumber";

    public DatabaseManager(Context cnt) {

        super(cnt, DatabaseName, null, Version);


    }

    @Override
    public void onCreate(SQLiteDatabase cdb) {

        String cQuery = "CREATE TABLE " + TableName + "(" +
                " " + dID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                " " + dName + " TEXT NOT NULL, " +
                " " + dFamily + " TEXT NOT NULL " +
                " " + demail + " TEXT NOT NULL " +
                " " + dphonenumber + " TEXT NOT NULL " +
                " " + dmobilenumber + " TEXT NOT NULL " +
                ");";
        cdb.execSQL(cQuery);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertContact(Contact contact) {

        SQLiteDatabase idb = this.getWritableDatabase();
        ContentValues icv = new ContentValues();
        icv.put(dName, contact.getName());
        icv.put(dFamily, contact.getFamili());
        icv.put(demail, contact.getEmail());
        icv.put(dphonenumber, contact.getPhoneNumber());
        icv.put(dmobilenumber, contact.getMobileNumber());
        idb.insert(TableName, null, icv);
        idb.close();


    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> ContactsList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TableName;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contacts = new Contact();
                contacts.setId(String.valueOf(Integer.parseInt(cursor.getString(0))));
                contacts.setName(cursor.getString(1));
                contacts.setFamili(cursor.getString(2));
                contacts.setEmail(cursor.getString(3));
                contacts.setPhoneNumber(cursor.getString(4));
                contacts.setMobileNumber(cursor.getString(5));


                ContactsList.add(contacts);
            } while (cursor.moveToNext());


        }
        return ContactsList;
    }

}
