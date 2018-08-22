package com.test.android00cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CtAdapter extends RecyclerView.Adapter<CartlistHolder> {

    private ArrayList<CartlistVO> list;
    private Context context;

    CtAdapter(ArrayList<CartlistVO> list, Context context) {
        if (list == null) {
            throw new IllegalArgumentException(
                    "modelData must not be null");
        }
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cartlist_holder, parent, false);
        CartlistHolder cartlistHolder = new CartlistHolder(view);
        return cartlistHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartlistHolder holder, int position) {
        holder.numView.setText(list.get(position).getNum());
        holder.nameView.setText(list.get(position).getName());
        holder.priceView.setText(list.get(position).getPrice());
        Glide.with(context).load(list.get(position).getImgUrl()).apply(new RequestOptions().fitCenter()).into(holder.imgView);
        holder.ctCuCheckView.setText(list.get(position).getCtCuCheck());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
