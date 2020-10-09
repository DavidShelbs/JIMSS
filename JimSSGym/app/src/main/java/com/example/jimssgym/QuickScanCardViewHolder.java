package com.example.jimssgym;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuickScanCardViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public ClipData.Item currentItem;
    private Context context;
    ImageView exerciseImage;
    TextView exerciseTitle, exerciseArea;

    public QuickScanCardViewHolder(@NonNull final View itemView) {
        super(itemView);

        this.exerciseImage = itemView.findViewById(R.id.exerciseImage);
        this.exerciseTitle = itemView.findViewById(R.id.exerciseTitle);
        this.exerciseArea = itemView.findViewById(R.id.exerciseArea);

        view = itemView;
        context = view.getContext();
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(context, QuickScanActivity.class);
                intent.putExtra("QR_RESULT", exerciseTitle.getText());
                context.startActivity(intent);
            }
        });
    }
}