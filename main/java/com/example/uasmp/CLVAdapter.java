package com.example.uasmp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CLVAdapter extends ArrayAdapter<String> {
    String[] itemNames;
    int[] itemImages;
    int[] qty;
    long[] total;

    Context c;

    public CLVAdapter(Context c, String[] itemNames, int[] itemImages, int[] qty, long[] total){
        super(c, R.layout.custom_list_view);
        this.itemNames = itemNames;
        this.itemImages = itemImages;
        this.qty = qty;
        this.total = total;
        this.c = c;
    }

    @Override
    public int getCount(){
        return itemNames.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent){
        ViewHolder vh = new ViewHolder();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_view, parent, false);

            vh.iName = view.findViewById(R.id.item_name);
            vh.iImage = view.findViewById(R.id.item_image);
            vh.iQty = view.findViewById(R.id.qty);
            vh.iPrice = view.findViewById(R.id.total);

            view.setTag(vh);
        }
        else {
            vh = (ViewHolder) view.getTag();
        }

        vh.iName.setText(itemNames[position]);
        vh.iImage.setImageResource(itemImages[position]);
        vh.iQty.setText(String.valueOf(qty[position]) + " items");
        vh.iPrice.setText("IDR " + String.valueOf(total[position]) + ",00");

        return view;
    }

    static class ViewHolder{
        TextView iName, iQty, iPrice;
        ImageView iImage;
    }
}
