package com.example.uasmp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class UserMainPage extends AppCompatActivity {
    DBAdapter dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_page);

        dba = new DBAdapter(getApplicationContext());

        ArrayList<Product> allProducts = dba.getAllProducts();
        ArrayList<Outlet> allOutlets = dba.getAllOutlets();

        for(Product p : allProducts){
            Log.d("DB", p.getId() + " - " + p.getName() + " - " + p.getPrice() + " - " + p.getQty());
        }

        for(Outlet o : allOutlets){
            Log.d("DB", o.getId() + " - " + o.getName() + " - " + o.getLatitude() + " - " + o.getLongitude());
        }

        Intent intentus = getIntent();

        TextView username = findViewById(R.id.username);
        String curruser = intentus.getStringExtra("inpusername");
        String cuid = intentus.getStringExtra("inpuid");

        username.setText(curruser);

        BottomNavigationView bottomMenu = findViewById(R.id.bottom_navigation_menu);
        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                Fragment selectedFragment = null;

                switch(item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new FragmentDashboard();
                        /*Bundle bundle = new Bundle();
                        bundle.putString("currentuser", cuid);
                        selectedFragment.setArguments(bundle);*/
                        break;

                    case R.id.nav_history:
                        selectedFragment = new FragmentHistory();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("currentuser", cuid);
                        selectedFragment.setArguments(bundle2);
                        break;

                    case R.id.nav_location:
                        break;
                }

                ft.replace(R.id.fragment_container, selectedFragment).commit();

                return false;
            }
        });

        bottomMenu.setSelectedItemId(R.id.nav_home);

        Button logOutButton = findViewById(R.id.sign_out_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentus2 = getIntent();
                setResult(RESULT_OK, intentus2);
                finish();
            }
        });
    }
}