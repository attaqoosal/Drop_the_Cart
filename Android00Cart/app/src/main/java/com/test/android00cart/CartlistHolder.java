package com.test.android00cart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CartlistHolder extends RecyclerView.ViewHolder {
    TextView numView;
    TextView nameView;
    TextView priceView;
    ImageView imgView;
    TextView ctCuCheckView;

    public CartlistHolder(View itemView) {
        super(itemView);
        numView = ( TextView ) itemView.findViewById(R.id.xNumView);
        nameView = ( TextView ) itemView.findViewById(R.id.xNameView);
        priceView = ( TextView ) itemView.findViewById(R.id.xPriceView);
        imgView = ( ImageView ) itemView.findViewById(R.id.xImgView);
        ctCuCheckView = ( TextView ) itemView.findViewById(R.id.xCtCuCheckView);
    }
}