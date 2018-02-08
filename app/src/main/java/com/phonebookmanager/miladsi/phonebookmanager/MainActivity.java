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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.phonebookmanager.miladsi.phonebookmanager.Adapter.CustomAdapter;
import com.phonebookmanager.miladsi.phonebookmanager.Database.DatabaseManager;
import com.phonebookmanager.miladsi.phonebookmanager.Model.Contact;
import com.phonebookmanager.miladsi.phonebookmanager.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    ListView lstContacts;
    ArrayList<Contact> contacts = new ArrayList<>();
    CustomAdapter customAdapter;
    String username;
    //DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        lstContacts = (ListView) findViewById(R.id.lstContacts);

        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertdialogDU(contacts.get(position).getName(), contacts.get(position).getFamili(), contacts.get(position).getEmail(), contacts.get(position).getPhoneNumber(), contacts.get(position).getMobileNumber(), position, id);

            }


        });


        //databaseManager = new DatabaseManager(this);
        //contacts = databaseManager.getAllContacts();
        new GetAllContacts(getApplicationContext()).execute();

        customAdapter = new CustomAdapter(this, contacts);
        lstContacts.setAdapter(customAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });

    }

    private void AlertdialogDU(String name, String famili, String email, String phonenumber, String mobilenumber, final int position, long id) {

        final EditText edtName, edtFamili, edtEmail, edtPhoneNumber, edtMobileNumber;


        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_contact_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("UPDATE", null);
        builder.setNegativeButton("DELETE", null);
        builder.setNeutralButton("CANCEL", null);

        final AlertDialog alertDialog = builder.create();


        edtName = (EditText) view.findViewById(R.id.edtName);
        edtFamili = (EditText) view.findViewById(R.id.edtFamily);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        edtMobileNumber = (EditText) view.findViewById(R.id.edtMobileNumber);

        edtName.setText(name);
        edtFamili.setText(famili);
        edtEmail.setText(email);
        edtPhoneNumber.setText(phonenumber);
        edtMobileNumber.setText(mobilenumber);


        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button btnpositive = alertDialog.getButton(alertDialog.BUTTON_POSITIVE);

                btnpositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //amaliate marbot be update

                        if (edtName.getText().toString().equals("") || edtFamili.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtPhoneNumber.getText().toString().equals("") || edtMobileNumber.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "fill all", Toast.LENGTH_LONG).show();
                        } else {
                            contacts.get(position).setName(edtName.getText().toString());
                            contacts.get(position).setFamili(edtFamili.getText().toString());
                            contacts.get(position).setEmail(edtEmail.getText().toString());
                            contacts.get(position).setPhoneNumber(edtPhoneNumber.getText().toString());
                            contacts.get(position).setMobileNumber(edtMobileNumber.getText().toString());


                            new UpdateRequest(contacts.get(position).getId(), contacts.get(position).getName(), contacts.get(position).getFamili(), contacts.get(position).getEmail(), contacts.get(position).getPhoneNumber(), contacts.get(position).getMobileNumber(), username, getApplicationContext()).execute();
                        }
                        alertDialog.dismiss();
                    }
                });

                Button btnnegative = alertDialog.getButton(alertDialog.BUTTON_NEGATIVE);

                btnnegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //amaliate marbot be delete

                        String m = contacts.get(position).getId();
                        new DeleteRequest(contacts.get(position).getId(), getApplicationContext()).execute();
                        Toast.makeText(getApplicationContext(), m, Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }
                });

                Button btnneutral = alertDialog.getButton(alertDialog.BUTTON_NEUTRAL);
                btnneutral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        alertDialog.show();
    }


    private void customDialog() {

        final EditText edtName, edtFamily, edtEmail, edtPhoneNumber, edtMobileNumber;
        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.dialog_contact_add, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("SAVE", null);
        alertDialogBuilder.setNegativeButton("CANCEL", null);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtFamily = (EditText) view.findViewById(R.id.edtFamily);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        edtMobileNumber = (EditText) view.findViewById(R.id.edtMobileNumber);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button btnPositive = alertDialog.getButton(alertDialog.BUTTON_POSITIVE);

                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = edtName.getText().toString();
                        String email = edtEmail.getText().toString();
                        String famili = edtFamily.getText().toString();
                        String phoneNumber = edtPhoneNumber.getText().toString();
                        String mobileNumber = edtMobileNumber.getText().toString();


                        new SetInfoRequest(name, famili, email, phoneNumber, mobileNumber, username, getApplicationContext()).execute();
//                        contacts.add(new Contact(name, famili, email, phoneNumber, mobileNumber));
//                        ((BaseAdapter) lstContacts.getAdapter()).notifyDataSetChanged();
//                        databaseManager.insertContact(new Contact(name, famili, email, phoneNumber, mobileNumber));
//
//                        Toast.makeText(getApplicationContext(), "save to database", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }
                });

                Button btnNegative = alertDialog.getButton(alertDialog.BUTTON_NEGATIVE);

                btnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
            }
        });

        alertDialog.show();
    }

    public class SetInfoRequest extends AsyncTask<Void, Void, String> {


        String name, famili, email, phonenumber, mobilenumber, username;
        Context context;

        public SetInfoRequest(String name, String famili, String email, String phonenumber, String mobilenumber, String username, Context context) {
            this.name = name;
            this.famili = famili;
            this.email = email;
            this.phonenumber = phonenumber;
            this.mobilenumber = mobilenumber;
            this.username = username;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String Address = "http://salimisaffron.ir/phonebook/SubmitText.php";

            HashMap hashMap = new HashMap();

            hashMap.put("name", name);
            hashMap.put("famili", famili);
            hashMap.put("email", email);
            hashMap.put("phonenumber", phonenumber);
            hashMap.put("mobilenumber", mobilenumber);
            hashMap.put("username", username);


            return Utils.sendData(Address, hashMap);
        }

        @Override
        protected void onPostExecute(String JasonData) {

            if (JasonData != null) {

                try {

                    JSONArray jsonArray = new JSONArray(JasonData);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String famili = jsonObject.getString("famili");
                    String email = jsonObject.getString("email");
                    String phonenumber = jsonObject.getString("phonenumber");
                    String mobilenumber = jsonObject.getString("mobilenumber");
                    String username = jsonObject.getString("username");

                    contacts.add(new Contact(id, name, famili, email, phonenumber, mobilenumber));

                    ((BaseAdapter) lstContacts.getAdapter()).notifyDataSetChanged();


                } catch (Exception e) {

                    e.printStackTrace();
                }


            } else {
                Toast.makeText(context, "No Data", Toast.LENGTH_LONG).show();

            }
        }
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

            progressDialog = new ProgressDialog(MainActivity.this);
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

                        contacts.add(new Contact(id, name, famili, email, phonenumber, mobilenumber));


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

    public class UpdateRequest extends AsyncTask<Void, Void, String> {


        String id, name, famili, email, phonenumber, mobilenumber, username;
        Context context;

        public UpdateRequest(String id, String name, String famili, String email, String phonenumber, String mobilenumber, String username, Context context) {
            this.id = id;
            this.name = name;
            this.famili = famili;
            this.email = email;
            this.phonenumber = phonenumber;
            this.mobilenumber = mobilenumber;
            this.username = username;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String Address = "http://salimisaffron.ir/phonebook/UpdateContacts.php";

            HashMap hashMap = new HashMap();

            hashMap.put("id", id);
            hashMap.put("name", name);
            hashMap.put("famili", famili);
            hashMap.put("email", email);
            hashMap.put("phonenumber", phonenumber);
            hashMap.put("mobilenumber", mobilenumber);
            hashMap.put("username", username);


            return Utils.sendData(Address, hashMap);
        }

        @Override
        protected void onPostExecute(String JasonData) {


            ((BaseAdapter) lstContacts.getAdapter()).notifyDataSetChanged();
            Toast.makeText(context, "Data Change Successfully", Toast.LENGTH_LONG).show();


        }
    }

    public class DeleteRequest extends AsyncTask<Void, Void, String> {


        String id;
        Context context;

        public DeleteRequest(String id, Context context) {
            this.id = id;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String Address = "http://salimisaffron.ir/phonebook/DeleteContact.php";

            HashMap hashMap = new HashMap();

            hashMap.put("id", id);

            return Utils.sendData(Address, hashMap);
        }

        @Override
        protected void onPostExecute(String JasonData) {

            ((BaseAdapter) lstContacts.getAdapter()).notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), JasonData, Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());

        }
    }


}
