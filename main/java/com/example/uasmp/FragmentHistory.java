package com.example.uasmp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentHistory extends Fragment {
    DBAdapter dba;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        dba = new DBAdapter(getActivity().getApplicationContext());
        sp = this.getActivity().getSharedPreferences("sharpref", Context.MODE_PRIVATE);

        ArrayList<Transaction> allTransactions = dba.getAllTransactions();
        for(Transaction t : allTransactions){
            Log.d("DB", t.getId() + " - " + t.getBuyer().getUsername() + " - " + t.getItem().getName() + " - " + t.getQty());
        }

        String cuid = sp.getString("currentuser", "");
        //String cuid = getArguments().getString("currentuser");
        ArrayList<Transaction> thisAccountOnly = new ArrayList<>();
        for(Transaction t : allTransactions){
            if(t.getBuyer().getId().equals(cuid)){
                thisAccountOnly.add(t);
            }
        }
        int n = thisAccountOnly.size();

        String[] itemNames = new String[n];
        int[] itemImages = new int[n];
        int[] itemQty = new int[n];
        long[] itemTotal = new long[n];

        for(int i = 0; i < n; i++){
            Transaction t = thisAccountOnly.get(i);
            itemNames[i] = t.getItem().getName();
            itemQty[i] = t.getQty();
            itemTotal[i] = itemQty[i] * t.getItem().getPrice();

            String selectedItem = itemNames[i];
            if(selectedItem.equals("T-Shirt")){
                itemImages[i] = R.drawable.tshirt;
            }
            else if(selectedItem.equals("Trousers")){
                itemImages[i] = R.drawable.trousers;
            }
            else if(selectedItem.equals("Dress")){
                itemImages[i] = R.drawable.dress;
            }
            else if(selectedItem.equals("Blouse")){
                itemImages[i] = R.drawable.blouse;
            }
            else if(selectedItem.equals("Purse")){
                itemImages[i] = R.drawable.purse;
            }
            else if(selectedItem.equals("Hat")){
                itemImages[i] = R.drawable.hat;
            }
            else if(selectedItem.equals("Watch")){
                itemImages[i] = R.drawable.watch;
            }
            else if(selectedItem.equals("Belt")){
                itemImages[i] = R.drawable.belt;
            }
            else {
                itemImages[i] = R.drawable.unknownproductlogo;
            }
        }

        ListView purchaseHistory = view.findViewById(R.id.purchase_history_list);
        purchaseHistory.setAdapter(new CLVAdapter(getContext(), itemNames, itemImages, itemQty, itemTotal));

        return view;
    }
}