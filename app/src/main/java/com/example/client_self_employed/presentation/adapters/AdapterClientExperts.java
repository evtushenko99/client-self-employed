package com.example.client_self_employed.presentation.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.clicklisteners.ExpertsItemClickListener;
import com.example.client_self_employed.presentation.adapters.items.ClientSelectedExportItem;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AdapterClientExperts extends RecyclerView.Adapter<AdapterClientExperts.SelectedExpertHolder> {
    private List<ClientSelectedExportItem> mExpertList;
    private ExpertsItemClickListener mExpertsItemClickListener;
    private int index = -1;

    public AdapterClientExperts(@NonNull List<ClientSelectedExportItem> expertList, ExpertsItemClickListener expertsItemClickListener) {
        mExpertList = expertList;
        mExpertsItemClickListener = expertsItemClickListener;
    }

    @NonNull
    @Override
    public AdapterClientExperts.SelectedExpertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expert_photo, parent, false);
        return new AdapterClientExperts.SelectedExpertHolder(view, mExpertsItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClientExperts.SelectedExpertHolder holder, int position) {
        holder.bindView(mExpertList.get(position), holder.getAdapterPosition());

        if (index == position) {
            holder.mCardView.setBackgroundColor(Color.parseColor("#ffab91"));
        } else {
            holder.mCardView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return mExpertList.size();
    }

    public class SelectedExpertHolder extends RecyclerView.ViewHolder {
        private TextView mExpertName;
        private ExpertsItemClickListener mExpertsItemClickListener;
        private MaterialCardView mCardView;

        public SelectedExpertHolder(@NonNull View itemView, ExpertsItemClickListener expertsItemClickListener) {
            super(itemView);
            mExpertName = itemView.findViewById(R.id.item_selected_expert_name);
            mExpertsItemClickListener = expertsItemClickListener;
            mCardView = itemView.findViewById(R.id.material_expert_card);

        }

        void bindView(ClientSelectedExportItem clientSelectedExportItem, int adapterPosition) {
            mExpertName.setText(clientSelectedExportItem.getExpertName());
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpertsItemClickListener.onExpertItemClickListener(clientSelectedExportItem.getExpertId());
                    index = adapterPosition;
                    notifyDataSetChanged();
                }
            });

        }

    }
}
