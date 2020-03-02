package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.databinding.AdapterBestExpertsBinding;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItemClickListener;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;
import com.example.client_self_employed.presentation.model.ClientSelectedExpertBinding;

import java.util.List;

/**
 * Адаптер для отображения активных лучших экспертов
 */
public class AdapterBestExperts extends RecyclerView.Adapter<AdapterBestExperts.SelectedExpertHolder> {
    private List<ClientSelectedExpert> mExpertList;
    private NewRecordToBestExpertButtonItemClickListener mBestExpertItemClickListener;

    public AdapterBestExperts(List<ClientSelectedExpert> expertList, NewRecordToBestExpertButtonItemClickListener bestExpertItemClickListener) {
        mExpertList = expertList;
        mBestExpertItemClickListener = bestExpertItemClickListener;
    }

    @NonNull
    @Override
    public AdapterBestExperts.SelectedExpertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterBestExpertsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_best_expert_photo, parent, false);
        return new SelectedExpertHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBestExperts.SelectedExpertHolder holder, int position) {
        holder.bindView(mExpertList.get(position), holder.getAdapterPosition(), mBestExpertItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mExpertList != null ? mExpertList.size() : 0;
    }

    class SelectedExpertHolder extends RecyclerView.ViewHolder {
        private AdapterBestExpertsBinding mBinding;

        SelectedExpertHolder(@NonNull AdapterBestExpertsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindView(ClientSelectedExpert clientSelectedExpert, int adapterPosition, NewRecordToBestExpertButtonItemClickListener bestExpertItemClickListener) {
            ClientSelectedExpertBinding clientSelectedExpertBinding = new ClientSelectedExpertBinding(clientSelectedExpert, adapterPosition);
            clientSelectedExpertBinding.setOnClickListener(bestExpertItemClickListener);
            mBinding.setSelectedExpert(clientSelectedExpertBinding);
        }

    }
}
