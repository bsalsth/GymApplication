package com.softlica.bishal.gymapp.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softlica.bishal.gymapp.R;
import com.softlica.bishal.gymapp.models.GymObjectDB;
import com.softlica.bishal.gymapp.views.ui.DetailActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by bishal on 3/19/2017.
 */

public class GymRecycleViewAdapter extends RecyclerView.Adapter<GymRecycleViewAdapter.ViewHolder> {
    private Context context;

    public void setGymObjectList(List<GymObjectDB> GymObjectDBList) {
        this.GymObjectDBList = GymObjectDBList;
        notifyItemRangeChanged(0, GymObjectDBList.size());
    }

    private List<GymObjectDB> GymObjectDBList = Collections.emptyList();

    public GymRecycleViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gym_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GymObjectDB gymObjectDB = GymObjectDBList.get(position);
        holder.gymName.setText(gymObjectDB.getName());
        holder.gymAddress.setText(gymObjectDB.getAddress());
        String distance = String.format("%.1f", gymObjectDB.getDistance());
        holder.gymDistance.setText(distance + " Mi");
        holder.gymMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MAP_ICON", "map clicked");
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("POS", position);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return GymObjectDBList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView gymName;
        TextView gymAddress;
        TextView gymDistance;
        ImageView gymMap;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            gymName = (TextView) itemView.findViewById(R.id.gymName);
            gymAddress = (TextView) itemView.findViewById(R.id.gymAddress);
            gymDistance = (TextView) itemView.findViewById(R.id.gymDistance);
            gymMap = (ImageView) itemView.findViewById(R.id.gymMap);
        }
    }
}
