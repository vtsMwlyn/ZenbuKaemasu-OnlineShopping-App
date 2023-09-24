package com.example.uasmp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminMainPage extends AppCompatActivity {
    DBAdapter dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_page);

        dba = new DBAdapter(getApplicationContext());

        ArrayList<Outlet> allOutlets = dba.getAllOutlets();
        ArrayList<Product> allProducts = dba.getAllProducts();

        for(Product p : allProducts){
            Log.d("DB", p.getId() + " - " + p.getName() + " - " + p.getPrice() + " - " + p.getQty());
        }

        for(Outlet o : allOutlets){
            Log.d("DB", o.getId() + " - " + o.getName() + " - " + o.getLatitude() + " - " + o.getLongitude());
        }

        Button signOutBtn = findViewById(R.id.sign_out_button);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentus = getIntent();
                setResult(RESULT_OK, intentus);
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAdminMenu()).commit();
    }
}