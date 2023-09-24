package com.example.uasmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentProductDetail extends Fragment {
    DBAdapter dba;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        dba = new DBAdapter(getActivity().getApplicationContext());

        TextView itemName = view.findViewById(R.id.item_name);
        ImageView itemImage = view.findViewById(R.id.item_image);
        TextView itemStock = view.findViewById(R.id.stocks_left);
        TextView itemPrice = view.findViewById(R.id.price);

        Bundle bundle = getArguments();
        String selectedItem = bundle.getString("selected");
        //String cuid = bundle.getString("currentuser");

        ArrayList<Product> allProducts = dba.getAllProducts();
        Product selectedProduct = null;
        for(Product p : allProducts){
            if(p.getName().equals(selectedItem)){
                selectedProduct = p;
                break;
            }
        }

        itemName.setText(selectedProduct.getName());
        itemStock.setText(String.valueOf(selectedProduct.getQty()));
        itemPrice.setText("IDR " + String.valueOf(selectedProduct.getPrice()) + ",00");

        if(selectedItem.equals("T-Shirt")){
            itemImage.setImageResource(R.drawable.tshirt);
        }
        else if(selectedItem.equals("Trousers")){
            itemImage.setImageResource(R.drawable.trousers);
        }
        else if(selectedItem.equals("Dress")){
            itemImage.setImageResource(R.drawable.dress);
        }
        else if(selectedItem.equals("Blouse")){
            itemImage.setImageResource(R.drawable.blouse);
        }
        else if(selectedItem.equals("Purse")){
            itemImage.setImageResource(R.drawable.purse);
        }
        else if(selectedItem.equals("Hat")){
            itemImage.setImageResource(R.drawable.hat);
        }
        else if(selectedItem.equals("Watch")){
            itemImage.setImageResource(R.drawable.watch);
        }
        else if(selectedItem.equals("Belt")){
            itemImage.setImageResource(R.drawable.belt);
        }
        else {
            itemImage.setImageResource(R.drawable.unknownproductlogo);
        }

        final Product prd = selectedProduct;
        Button purchaseButton = view.findViewById(R.id.purchase_button);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();

                bundle2.putString("itemname", prd.getName());
                bundle2.putInt("itemstock", prd.getQty());
                bundle2.putLong("itemprice", prd.getPrice());
                //bundle2.putString("currentuser", cuid);

                FragmentMakeTransaction fmt = new FragmentMakeTransaction();
                fmt.setArguments(bundle2);

                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, fmt).commit();
            }
        });

        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDashboard()).commit();
            }
        });

        return view;
    }
}