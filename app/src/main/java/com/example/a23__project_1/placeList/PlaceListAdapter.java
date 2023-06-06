package com.example.a23__project_1.placeList;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mainContext;

    public PlaceListAdapter(Context mainContext) {
        this.mainContext = mainContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
