package com.example.uasmp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SignUpPage extends AppCompatActivity {
    DBAdapter dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        dba = new DBAdapter(getApplicationContext());

        Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inpUsername = ((EditText) findViewById(R.id.inp_username)).getText().toString();
                String inpPassword = ((EditText) findViewById(R.id.inp_password)).getText().toString();
                String inpName = ((EditText) findViewById(R.id.inp_name)).getText().toString();
                String inpEmail = ((EditText) findViewById(R.id.inp_email)).getText().toString();
                String inpPhone = ((EditText) findViewById(R.id.inp_phone)).getText().toString();
                String inpAddress = ((EditText) findViewById(R.id.inp_address)).getText().toString();
                String inpBdate = ((EditText) findViewById(R.id.inp_bdate)).getText().toString();

                if(inpUsername.equals("") || inpPassword.equals("") || inpName.equals("") || inpEmail.equals("") || inpPhone.equals("") || inpAddress.equals("") || inpBdate.equals("")){
                    Toast.makeText(SignUpPage.this, "All data must be filled!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String inpId = generateId();
                    dba.insertUser(new User(inpId, inpUsername, inpPassword, inpName, inpEmail, inpPhone, inpAddress, inpBdate));

                    Toast.makeText(SignUpPage.this, "Successfully created a new account!", Toast.LENGTH_SHORT).show();

                    Intent intentus = getIntent();
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }
        });
    }

    public String generateId(){
        ArrayList<User> allUsers = dba.getAllUsers();
        String newId = "";
        boolean notUnique = true;

        while(notUnique){
            newId = "U";
            for(int i = 0; i < 3; i++){
                newId += (int) (0 + Math.random() * 10);
            }

            int found = 0;
            for(User u : allUsers){
                if(u.getId().equals(newId)){
                    found++;
                }
            }

            if(found == 0){
                notUnique = false;
            }
        }

        return newId;
    }
}