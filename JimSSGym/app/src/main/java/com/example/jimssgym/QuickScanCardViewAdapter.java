package com.example.jimssgym;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuickScanCardViewAdapter extends RecyclerView.Adapter<QuickScanCardViewHolder> {

    Context c;
    ArrayList<QuickScanCardViewModel> models;

    public QuickScanCardViewAdapter(Context c, ArrayList<QuickScanCardViewModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public QuickScanCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new QuickScanCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickScanCardViewHolder holder, int position) {
        holder.exerciseTitle.setText(models.get(position).getTitle());
        holder.exerciseArea.setText(models.get(position).getDescription());
        holder.exerciseImage.setImageResource(models.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
