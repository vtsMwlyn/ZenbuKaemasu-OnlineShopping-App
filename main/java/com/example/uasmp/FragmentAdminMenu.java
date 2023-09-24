package com.example.uasmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class FragmentAdminMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_menu, container, false);

        LinearLayout addProductIB = view.findViewById(R.id.add_product_button);
        LinearLayout updateProductIB = view.findViewById(R.id.update_product_button);
        LinearLayout deleteProductIB = view.findViewById(R.id.delete_product_button);

        addProductIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAddProduct()).commit();
            }
        });

        updateProductIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentUpdateProduct()).commit();
            }
        });

        deleteProductIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDeleteProduct()).commit();
            }
        });

        return view;
    }
}