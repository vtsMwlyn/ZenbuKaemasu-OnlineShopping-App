package com.example.uasmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentDeleteProduct extends Fragment {
    DBAdapter dba;
    Product ptd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_product, container, false);

        dba = new DBAdapter(getActivity().getApplicationContext());

        TextView tvItemName = view.findViewById(R.id.item_name);
        ImageView ivItemImage = view.findViewById(R.id.item_image);
        TextView tvItemStock = view.findViewById(R.id.stocks_left);
        TextView tvItemPrice = view.findViewById(R.id.price);

        Button nextBtn = view.findViewById(R.id.next_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etItemName = view.findViewById(R.id.inp_name);
                String keyword = etItemName.getText().toString();

                if(keyword.equals("")){
                    Toast.makeText(getContext(), "Please fill the product name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<Product> allProducts = dba.getAllProducts();
                    ptd = null;
                    for(Product p : allProducts){
                        if(p.getName().equals(keyword)){
                            ptd = p;
                        }
                    }

                    if(ptd != null){
                        tvItemName.setText(ptd.getName());
                        tvItemStock.setText(ptd.getQty() + " stocks left");
                        tvItemPrice.setText("IDR " + ptd.getPrice() + ",00");

                        String selectedItem = ptd.getName();
                        if(selectedItem.equals("T-Shirt")){
                            ivItemImage.setImageResource(R.drawable.tshirt);
                        }
                        else if(selectedItem.equals("Trousers")){
                            ivItemImage.setImageResource(R.drawable.trousers);
                        }
                        else if(selectedItem.equals("Dress")){
                            ivItemImage.setImageResource(R.drawable.dress);
                        }
                        else if(selectedItem.equals("Blouse")){
                            ivItemImage.setImageResource(R.drawable.blouse);
                        }
                        else if(selectedItem.equals("Purse")){
                            ivItemImage.setImageResource(R.drawable.purse);
                        }
                        else if(selectedItem.equals("Hat")){
                            ivItemImage.setImageResource(R.drawable.hat);
                        }
                        else if(selectedItem.equals("Watch")){
                            ivItemImage.setImageResource(R.drawable.watch);
                        }
                        else if(selectedItem.equals("Belt")){
                            ivItemImage.setImageResource(R.drawable.belt);
                        }
                        else {
                            ivItemImage.setImageResource(R.drawable.unknownproductlogo);
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Product not found!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        Button proceedBtn = view.findViewById(R.id.proceed_button);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvItemName.equals("Item Name")){
                    dba.deleteTransactionByProductId(ptd.getId());
                    dba.deleteProductByName(ptd.getName());

                    Toast.makeText(getContext(), "Product successfully deleted!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAdminMenu()).commit();
                }
            }
        });

        return view;
    }
}