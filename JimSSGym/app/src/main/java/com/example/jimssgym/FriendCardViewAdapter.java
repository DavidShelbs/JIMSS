package com.example.jimssgym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendCardViewAdapter extends RecyclerView.Adapter<FriendCardViewHolder> {

    Context c;
    ArrayList<FriendCardViewModel> models;

    public FriendCardViewAdapter(Context c, ArrayList<FriendCardViewModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public FriendCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendrow, parent, false);
        return new FriendCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendCardViewHolder holder, int position) {
        holder.FName.setText(models.get(position).getFName());
        holder.LName.setText(models.get(position).getLName());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
