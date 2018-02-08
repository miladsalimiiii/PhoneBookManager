package com.phonebookmanager.miladsi.phonebookmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.phonebookmanager.miladsi.phonebookmanager.Utils.Utils;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText Reg_User, Reg_Pass, Reg_RPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Reg_User = (EditText) findViewById(R.id.Reg_edtUser);
        Reg_Pass = (EditText) findViewById(R.id.Reg_edtPassword);
        Reg_RPass = (EditText) findViewById(R.id.Reg_edtRepeatPass);
    }

    public void btnClick(View view) {

        int id = view.getId();

        if (id == R.id.Register_btnRegister) {

            if (Reg_User.getText().toString().equals("") || Reg_Pass.getText().toString().equals("") || Reg_RPass.getText().toString().equals("")) {

                Toast.makeText(getApplicationContext(), "fill all pls", Toast.LENGTH_LONG).show();
            }
            else {

                if (Reg_Pass.getText().toString().equals(Reg_RPass.getText().toString())) {

                    new RegisterRequest(Reg_User.getText().toString(), Reg_Pass.getText().toString(), getApplicationContext()).execute();

                } else {
                    Toast.makeText(getApplicationContext(), "passwords are not same", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public class RegisterRequest extends AsyncTask<Void, Void, String> {


        String Username, Password;
        Context context;

        public RegisterRequest(String username, String password, Context context) {
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

            String Address = "http://salimisaffron.ir/phonebook/register.php";

            HashMap hashMap = new HashMap();

            hashMap.put("username", Username);
            hashMap.put("password", Password);


            return Utils.sendData(Address, hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
}
