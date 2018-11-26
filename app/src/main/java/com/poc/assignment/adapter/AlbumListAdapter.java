package com.poc.assignment.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import com.poc.assignment.R;
import com.poc.assignment.interfaces.IBasicUI;
import com.poc.assignment.model.AlbumDataModel;

import java.util.ArrayList;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder> {
    private Context mContext;
    private ArrayList<AlbumDataModel> albumList;

    public AlbumListAdapter(Context context, ArrayList<AlbumDataModel> gstCategoryList) {
        this.albumList = gstCategoryList;
        this.mContext = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_list_item, viewGroup, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder rowHolder, int i) {
        rowHolder.tvTitle.setText(albumList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != albumList ? albumList.size() : 0);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView tvTitle;

        public AlbumViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }

        @Override
        public void onClick(View v) {

        }
    }
}