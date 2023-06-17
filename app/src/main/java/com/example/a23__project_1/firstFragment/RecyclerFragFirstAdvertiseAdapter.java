package com.example.a23__project_1.firstFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataPage;

import java.util.ArrayList;

public class RecyclerFragFirstAdvertiseAdapter extends RecyclerView.Adapter<RecyclerFragFirstAdvertisePage> {

    private ArrayList<DataPage> listData;

    RecyclerFragFirstAdvertiseAdapter(ArrayList<DataPage> data) {
        this.listData = data;
    }

    @Override
    public RecyclerFragFirstAdvertisePage onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, parent, false);
        return new RecyclerFragFirstAdvertisePage(view);
    }

    @Override
    public void onBindViewHolder(RecyclerFragFirstAdvertisePage holder, int position) {
        if(holder instanceof RecyclerFragFirstAdvertisePage){
            RecyclerFragFirstAdvertisePage viewHolder = (RecyclerFragFirstAdvertisePage) holder;
            viewHolder.onBind(listData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}