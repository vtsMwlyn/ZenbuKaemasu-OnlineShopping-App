package com.example.uasmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SignInPage extends AppCompatActivity {

    DBAdapter dba;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        //this.deleteDatabase("zenbukaemasuDB.db");

        dba = new DBAdapter(getApplicationContext());

        /*
        Outlet o1 = new Outlet("O001", "Outlet A", -6.915845285206341, 107.58613438261567);
        Outlet o2 = new Outlet("O002", "Outlet B", -6.916319633556482, 107.59370478791487);
        Outlet o3 = new Outlet("O003", "Outlet C", -6.912804868628957, 107.59174141113208);

        dba.insertOutlet(o1);
        dba.insertOutlet(o2);
        dba.insertOutlet(o3);

        dba.insertProduct(new Product("P001", "T-Shirt", 10, 120000, o1));
        dba.insertProduct(new Product("P002", "Trousers", 15, 150000, o1));
        dba.insertProduct(new Product("P003", "Dress", 10, 180000, o2));
        dba.insertProduct(new Product("P004", "Blouse", 15, 110000, o2));
        dba.insertProduct(new Product("P005", "Purse", 20, 300000, o3));
        dba.insertProduct(new Product("P006", "Hat", 25, 80000, o3));
        dba.insertProduct(new Product("P007", "Watch", 20, 400000, o3));
        dba.insertProduct(new Product("P008", "Belt", 25, 90000, o3));
        */

        sp = getSharedPreferences("sharpref", MODE_PRIVATE);

        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inpUsername = ((EditText) findViewById(R.id.inp_username)).getText().toString();
                String inpPassword = ((EditText) findViewById(R.id.inp_password)).getText().toString();

                if(inpUsername.equals("admin") && inpPassword.equals("admin")){
                    Intent intentus = new Intent(view.getContext(), AdminMainPage.class);
                    startActivity(intentus);

                    return;
                }

                ArrayList<User> allUsers = dba.getAllUsers();

                boolean res = false;
                for(User u : allUsers){
                    Log.d("DB", u.getId() + " - " + u.getUsername() + " - " + u.getPassword());
                    if(u.getUsername().equals(inpUsername)){
                        res = u.validate(inpUsername, inpPassword);
                        if(res){
                            break;
                        }
                    }
                }

                if(res){
                    String cuid = "";
                    for(User u : allUsers){
                        if(u.getUsername().equals(inpUsername)){
                            cuid = u.getId();
                            break;
                        }
                    }

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("currentuser", cuid);
                    editor.apply();

                    Intent intentus = new Intent(view.getContext(), UserMainPage.class);
                    intentus.putExtra("inpusername", inpUsername);
                    intentus.putExtra("inpuid", cuid);
                    startActivity(intentus);
                }
                else {
                    Toast.makeText(SignInPage.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        TextView createNewAccountLink = findViewById(R.id.new_account_link);
        createNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentus = new Intent(view.getContext(), SignUpPage.class);
                startActivity(intentus);
            }
        });
    }
}