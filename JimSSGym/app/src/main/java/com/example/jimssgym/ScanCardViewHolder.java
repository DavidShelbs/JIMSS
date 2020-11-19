package com.example.jimssgym;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScanCardViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public ClipData.Item currentItem;
    private Context context;
    ImageView exerciseImage;
    TextView exerciseTitle, exerciseArea;

    public ScanCardViewHolder(@NonNull final View itemView) {
        super(itemView);

        this.exerciseImage = itemView.findViewById(R.id.exerciseImage);
        this.exerciseTitle = itemView.findViewById(R.id.FName);
        this.exerciseArea = itemView.findViewById(R.id.LName);

        view = itemView;
        context = view.getContext();
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putCharSequence("QR_RESULT", exerciseTitle.getText());
                Intent intent = new Intent(context, ScanActivity.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }
}