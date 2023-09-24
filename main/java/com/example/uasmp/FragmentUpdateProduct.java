package com.example.uasmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentUpdateProduct extends Fragment {
    DBAdapter dba;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_product, container, false);

        dba = new DBAdapter(getActivity().getApplicationContext());

        Button addProductBtn = view.findViewById(R.id.add_product_button);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inpName = view.findViewById(R.id.inp_name);
                EditText inpPrice = view.findViewById(R.id.inp_price);
                EditText inpQty = view.findViewById(R.id.inp_qty);
                EditText inpLocation = view.findViewById(R.id.inp_location);

                String pName = inpName.getText().toString();
                long pPrice = Long.parseLong(inpPrice.getText().toString());
                int pQty = Integer.parseInt(inpQty.getText().toString());
                String pLocation = inpLocation.getText().toString();
                String pId = "";

                ArrayList<Product> allProducts = dba.getAllProducts();
                for(Product p : allProducts){
                    if(p.getName().equals(pName)){
                        pId = p.getId();
                    }
                }

                Outlet outlet = null;
                ArrayList<Outlet> allOutlets = dba.getAllOutlets();
                for(Outlet o : allOutlets){
                    if(o.getName().equals(pLocation)){
                        outlet = o;
                    }
                }

                if(pId.equals("") && outlet == null){
                    Toast.makeText(getContext(), "Product and outlet null", Toast.LENGTH_SHORT).show();
                }
                else {
                    dba.updateProduct(new Product(pId, pName, pQty, pPrice, outlet));
                    Toast.makeText(getContext(), "Successfully updated the product!", Toast.LENGTH_SHORT).show();
                }

                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAdminMenu()).commit();
            }
        });

        return view;
    }
}