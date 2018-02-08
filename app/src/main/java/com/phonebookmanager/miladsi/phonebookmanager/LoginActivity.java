package com.phonebookmanager.miladsi.phonebookmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.phonebookmanager.miladsi.phonebookmanager.Utils.Utils;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.Login_edtUser);
        password = (EditText) findViewById(R.id.Login_edtPass);


    }

    public void btnClick(View view) {

        int id = view.getId();

        if (id == R.id.Login_btnRegister) {

            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

        }

        if (id == R.id.Login_btnGuest) {

            startActivity(new Intent(LoginActivity.this, GuestActivity.class));

        }

        if (id == R.id.Login_btnSingin)


        {
            if (username.getText().toString().equals("") || password.getText().toString().equals("")) {

                Toast.makeText(getApplicationContext(), "fill all", Toast.LENGTH_LONG).show();
            } else {
                new LoginRequest(username.getText().toString(), password.getText().toString(), getApplicationContext()).execute();

            }

        }
    }

    public class LoginRequest extends AsyncTask<Void, Void, String> {


        String Username, Password;
        Context context;

        public LoginRequest(String username, String password, Context context) {
            Username = username;
            Password = password;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String Address = "http://salimisaffron.ir/phonebook/login.php";

            HashMap hashMap = new HashMap();

            hashMap.put("username", Username);
            hashMap.put("password", Password);


            return Utils.sendData(Address, hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            if (s.equals("welcome to your profile")) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                intent.putExtra("username", Username);
                startActivity(intent);

            }
        }
    }

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_exit,null);
        builder.setView(view);
        builder.setTitle(R.string.app_name);
        builder.setMessage("آیا می خواهید خارج شوید ؟")
                .setCancelable(false)
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
