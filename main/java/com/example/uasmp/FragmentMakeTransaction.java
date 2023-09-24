package com.example.uasmp;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FragmentMakeTransaction extends Fragment {
    DBAdapter dba;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_transaction, container, false);

        dba = new DBAdapter(getActivity().getApplicationContext());
        sp = this.getActivity().getSharedPreferences("sharpref", Context.MODE_PRIVATE);
        Bundle bundle = getArguments();

        TextView tvItemName = view.findViewById(R.id.item_name);
        ImageView ivItemImage = view.findViewById(R.id.item_image);
        TextView tvItemStock = view.findViewById(R.id.stocks_left);
        TextView tvItemPrice = view.findViewById(R.id.price);

        String itemName = bundle.getString("itemname");
        int itemStock = bundle.getInt("itemstock");
        long itemPrice = bundle.getLong("itemprice");

        if(itemName.equals("T-Shirt")){
            ivItemImage.setImageResource(R.drawable.tshirt);
        }
        else if(itemName.equals("Trousers")){
            ivItemImage.setImageResource(R.drawable.trousers);
        }
        else if(itemName.equals("Dress")){
            ivItemImage.setImageResource(R.drawable.dress);
        }
        else if(itemName.equals("Blouse")){
            ivItemImage.setImageResource(R.drawable.blouse);
        }
        else if(itemName.equals("Purse")){
            ivItemImage.setImageResource(R.drawable.purse);
        }
        else if(itemName.equals("Hat")){
            ivItemImage.setImageResource(R.drawable.hat);
        }
        else if(itemName.equals("Watch")){
            ivItemImage.setImageResource(R.drawable.watch);
        }
        else if(itemName.equals("Belt")){
            ivItemImage.setImageResource(R.drawable.belt);
        }
        else {
            ivItemImage.setImageResource(R.drawable.unknownproductlogo);
        }

        tvItemName.setText(itemName);
        tvItemStock.setText(String.valueOf(itemStock) + " left");
        tvItemPrice.setText("IDR " + String.valueOf(itemPrice) + ",00");

        Button makePurchaseButton = view.findViewById(R.id.make_purchase_button);
        makePurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfItem = Integer.parseInt(((EditText) view.findViewById(R.id.inp_qty)).getText().toString());
                String paymentMethod = ((EditText) view.findViewById(R.id.inp_payment)).getText().toString();

                //String currentUserId = bundle.getString("currentuserid");
                String currentUserId = sp.getString("currentuser", "");
                User user = dba.getUserById(currentUserId);

                Product product = null;
                ArrayList<Product> allProducts = dba.getAllProducts();
                for(Product p : allProducts){
                    if(p.getName().equals(itemName)){
                        product = p;
                        break;
                    }
                }

                if(user == null){
                    Toast.makeText(v.getContext(), "User null", Toast.LENGTH_SHORT).show();
                }
                else if(product == null){
                    Toast.makeText(v.getContext(), "Product null", Toast.LENGTH_SHORT).show();
                }
                else {
                    dba.insertTransaction(new Transaction(generateId(), numberOfItem, paymentMethod, user, product));
                    Toast.makeText(v.getContext(), "Successfully made purchase transaction!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDashboard()).commit();
                }

            }
        });

        return view;
    }

    public String generateId(){
        ArrayList<Transaction> allTransactions = dba.getAllTransactions();
        String newId = "";
        boolean notUnique = true;

        while(notUnique){
            newId = "T";
            for(int i = 0; i < 3; i++){
                newId += (int) (0 + Math.random() * 10);
            }

            int found = 0;
            for(Transaction t : allTransactions){
                if(t.getId().equals(newId)){
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