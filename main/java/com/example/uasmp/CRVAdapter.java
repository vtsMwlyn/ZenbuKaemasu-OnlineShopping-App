package com.example.uasmp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class CRVAdapter extends RecyclerView.Adapter<CRVAdapter.ViewHolder>{

    String[] itemNames;
    int[] itemImages;

    String selectedItem = "Item Name";
    //String currentUserId = "";
    FragmentManager fm;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;
        private final Button button;

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.item_name);
            imageView = (ImageView) view.findViewById(R.id.item_image);
            button = (Button) view.findViewById(R.id.buy_now_button);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public Button getButton(){
            return button;
        }
    }

    public CRVAdapter(String[] itemNames, int[] itemImages, FragmentManager fm) {
        this.itemNames = itemNames;
        this.itemImages = itemImages;
        this.fm = fm;
        //currentUserId = cuid;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_recycler_view, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(itemNames[position]);
        viewHolder.getImageView().setImageResource(itemImages[position]);
        viewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = itemNames[viewHolder.getAdapterPosition()];
                Log.d("Test", selectedItem);

                Bundle bundle = new Bundle();
                bundle.putString("selected", selectedItem);
                //bundle.putString("currentuser", currentUserId);

                FragmentProductDetail fpd = new FragmentProductDetail();
                fpd.setArguments(bundle);
                fm.beginTransaction().replace(R.id.fragment_container, fpd).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemNames.length;
    }

}
