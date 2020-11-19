package com.example.jimssgym;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendCardViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public ClipData.Item currentItem;
    private Context context;
    TextView FName, LName;

    public FriendCardViewHolder(@NonNull final View itemView) {
        super(itemView);

        this.FName = itemView.findViewById(R.id.FName);
        this.LName = itemView.findViewById(R.id.LName);

        view = itemView;
        context = view.getContext();
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

            }
        });
    }
}