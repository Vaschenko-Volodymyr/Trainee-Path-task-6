package com.traineepath.volodymyrvashchenko.trainee_path_task_6.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mText;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.recycler_image);
        mText = (TextView) itemView.findViewById(R.id.recycler_text);
    }

    public ImageView getImage() {
        return mImageView;
    }

    public TextView getTextView() {
        return mText;
    }
}
