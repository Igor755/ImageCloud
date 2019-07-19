package com.image.imagecloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.image.imagecloud._interface.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.xml.transform.Result;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private List<Image> mlist;
    public Context mContext;
    private OnItemClickListener itemClickListener;


    public ImageAdapter(List<Image> mlist, Context mContext, OnItemClickListener itemClickListener) {
        this.itemClickListener =itemClickListener;
        this.mlist = mlist;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_recycler, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.bind(mlist.get(position));


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setData(List<Image> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.person_photo);
            tvName = itemView.findViewById(R.id.person_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = ViewHolder.super.getAdapterPosition();
                    itemClickListener.onItemClick(v,pos);

                }
            });

        }

        public void bind(Image result) {

            tvName.setText(result.getName());
            Picasso.get().load(result.getUri()).into(ivAvatar);
        }
    }
}