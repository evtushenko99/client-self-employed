package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.databinding.AdapterBestExpertsBinding;
import com.example.client_self_employed.presentation.clicklisteners.BestExpertItemClickListener;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;
import com.example.client_self_employed.presentation.model.ClientSelectedExpertBinding;

import java.util.List;

public class AdapterBestExperts extends RecyclerView.Adapter<AdapterBestExperts.SelectedExpertHolder> {
    private List<ClientSelectedExpert> mExpertList;
    private BestExpertItemClickListener mBestExpertItemClickListener;

    private int index = -1;

    public AdapterBestExperts(List<ClientSelectedExpert> expertList, BestExpertItemClickListener bestExpertItemClickListener) {
        mExpertList = expertList;
        mBestExpertItemClickListener = bestExpertItemClickListener;
    }

    public void setRowTypes(@NonNull List<ClientSelectedExpert> expertList) {
        if (mExpertList != null) {
            mExpertList.clear();
        }
        mExpertList = expertList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterBestExperts.SelectedExpertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterBestExpertsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_expert_photo, parent, false);
        return new SelectedExpertHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBestExperts.SelectedExpertHolder holder, int position) {
        holder.bindView(mExpertList.get(position), holder.getAdapterPosition(), mBestExpertItemClickListener);
        //int color = index == position ? R.color.primaryLightColor : R.color.white;

        //holder.mCardView.setBackgroundColor(mResources.getColor(color));
    }

    @Override
    public int getItemCount() {
        return mExpertList != null ? mExpertList.size() : 0;
    }

    public class SelectedExpertHolder extends RecyclerView.ViewHolder {
        private AdapterBestExpertsBinding mBinding;

        //private CardView mCardView;

        public SelectedExpertHolder(@NonNull AdapterBestExpertsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            //mCardView = itemView.findViewById(R.id.material_expert_card);


        }

        void bindView(ClientSelectedExpert clientSelectedExpert, int adapterPosition, BestExpertItemClickListener bestExpertItemClickListener) {
            ClientSelectedExpertBinding clientSelectedExpertBinding = new ClientSelectedExpertBinding(clientSelectedExpert, adapterPosition);
            clientSelectedExpertBinding.setOnClickListener(bestExpertItemClickListener);
            mBinding.setSelectedExpert(clientSelectedExpertBinding);
            /*mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBestExpertItemClickListener.onExpertItemClickListener(clientSelectedExpert.getExpertId());
                    index = adapterPosition;
                    notifyDataSetChanged();
                }
            });*/

        }

    }
}
