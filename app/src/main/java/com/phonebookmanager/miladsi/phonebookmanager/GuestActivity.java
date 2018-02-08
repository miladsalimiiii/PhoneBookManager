package com.phonebookmanager.miladsi.phonebookmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.phonebookmanager.miladsi.phonebookmanager.Adapter.CustomAdapter;
import com.phonebookmanager.miladsi.phonebookmanager.Database.DatabaseManager;
import com.phonebookmanager.miladsi.phonebookmanager.Model.Contact;
import com.phonebookmanager.miladsi.phonebookmanager.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GuestActivity extends AppCompatActivity {


    ListView lstContacts;
    ArrayList<Contact> contacts = new ArrayList<>();
    CustomAdapter customAdapter;
    String username;
    //DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        lstContacts = (ListView) findViewById(R.id.lstContacts);


        //databaseManager = new DatabaseManager(this);
        //contacts = databaseManager.getAllContacts();
        new GetAllContacts(getApplicationContext()).execute();

        customAdapter = new CustomAdapter(this, contacts);
        lstContacts.setAdapter(customAdapter);


    }


    public class GetAllContacts extends AsyncTask<Void, Void, String> {


        String name, famili, email, phonenumber, mobilenumber, username;
        Context context;
        ProgressDialog progressDialog;

        public GetAllContacts(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(GuestActivity.this);
            progressDialog.setMessage("pleas wait ...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {


            String Address = "http://salimisaffron.ir/phonebook/All_contacts.php";


            return Utils.getData(Address);
        }

        @Override
        protected void onPostExecute(String JasonData) {


            progressDialog.dismiss();
            if (JasonData != null) {

                try {
                    JSONArray jsonArray = new JSONArray(JasonData);
                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String famili = jsonObject.getString("famili");
                        String email = jsonObject.getString("email");
                        String phonenumber = jsonObject.getString("phonenumber");
                        String mobilenumber = jsonObject.getString("mobilenumber");
                        String username = jsonObject.getString("username");

                        contacts.add(new Contact(id,name, famili, email, phonenumber, mobilenumber));


                    }
                    ((BaseAdapter) lstContacts.getAdapter()).notifyDataSetChanged();
                } catch (Exception e) {

                    e.printStackTrace();
                }


            } else {
                Toast.makeText(context, "No Data", Toast.LENGTH_LONG).show();

            }
        }
    }
}

