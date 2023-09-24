package com.example.uasmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentDashboard extends Fragment {
    DBAdapter dba;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dba = new DBAdapter(getActivity().getApplicationContext());

        RecyclerView rv1 = view.findViewById(R.id.list_outfits);
        RecyclerView rv2 = view.findViewById(R.id.list_other_wears);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        LinearLayoutManager llm2 = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv1.setLayoutManager(llm);
        rv2.setLayoutManager(llm2);

        ArrayList<Product> allProducts = dba.getAllProducts();
        int n = allProducts.size();

        String[] outfitProductNames = new String[n - 4];
        String[] otherWearProductNames = new String[4];
        for(int i = 0; i < 4; i++){
            outfitProductNames[i] = allProducts.get(i).getName();
            otherWearProductNames[i] = allProducts.get(i + 4).getName();
        }

        for(int i = 4; i < n - 4; i++){
            outfitProductNames[i] = allProducts.get(i + 4).getName();
        }

        int[] outfitProductImages = new int[n - 4];
        int[] otherWearProductImages = new int[4];

        outfitProductImages[0] = R.drawable.tshirt;
        outfitProductImages[1] = R.drawable.trousers;
        outfitProductImages[2] = R.drawable.dress;
        outfitProductImages[3] = R.drawable.blouse;

        otherWearProductImages[0] = R.drawable.purse;
        otherWearProductImages[1] = R.drawable.hat;
        otherWearProductImages[2] = R.drawable.watch;
        otherWearProductImages[3] = R.drawable.belt;

        if(n > 8){
            for(int i = 4; i < n - 4; i++){
                outfitProductImages[i] = R.drawable.unknownproductlogo;
            }
        }

        //String cuid = getArguments().getString("currentuser");
        rv1.setAdapter(new CRVAdapter(outfitProductNames, outfitProductImages, getParentFragmentManager()));
        rv2.setAdapter(new CRVAdapter(otherWearProductNames, otherWearProductImages, getParentFragmentManager()));

        return view;
    }
}