package com.image.imagecloud;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.xml.transform.Result;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private List<Image> mlist;
    public Context mContext;
    private OnItemClickListener itemClickListener;


    public ImageAdapter(List<Image> mlist, Context mContext, OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.mlist = mlist;
        this.mContext = mContext;
    }

    public ImageAdapter(List<Image> mlist, Context mContext) {
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


        Image image = mlist.get(position);
        holder.tvName.setText(image.getName());
        Picasso.get()
                .load(image.getUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.ivAvatar);
        //holder.bind(mlist.get(position));


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setData(List<Image> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private ImageView ivAvatar;
        private TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.person_photo);
            tvName = itemView.findViewById(R.id.person_name);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    itemClickListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Selectaction");
            MenuItem doWhatever = menu.add(Menu.NONE,1,1,"Do Whatever");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if(itemClickListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){

                    switch (item.getItemId()){
                        case 1:
                            itemClickListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            itemClickListener.onDeleteClick(position);
                            return true;

                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onWhatEverClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){

        itemClickListener = listener;

    }
}
