package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.databinding.ItemActiveAppointmentBinding;
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointmentClickListener;
import com.example.client_self_employed.presentation.model.ClientActiveAppointmentBinding;
import com.example.client_self_employed.presentation.model.ClientAppointment;

import java.util.List;

/**
 * Адаптер для отображения активных записей клиента
 */
public class AdapterActiveAppointments extends RecyclerView.Adapter<AdapterActiveAppointments.ActiveAppointmentViewHolder> {
    private List<ClientAppointment> mAppointments;
    private ActiveAppointmentClickListener mItemClickListener;

    public AdapterActiveAppointments(@NonNull List<ClientAppointment> appointments, ActiveAppointmentClickListener itemClickListener1) {
        mAppointments = appointments;
        mItemClickListener = itemClickListener1;
    }

    @NonNull
    @Override
    public ActiveAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemActiveAppointmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_client_appointment, parent, false);
        return new ActiveAppointmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveAppointmentViewHolder holder, int position) {
        holder.bindView(mAppointments.get(position), position, mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public static class ActiveAppointmentViewHolder extends RecyclerView.ViewHolder {
        private ItemActiveAppointmentBinding mBinding;

        public ActiveAppointmentViewHolder(@NonNull ItemActiveAppointmentBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        void bindView(final ClientAppointment appointment, int position, ActiveAppointmentClickListener itemClickListener) {
            ClientActiveAppointmentBinding clientActiveAppointmentBinding = new ClientActiveAppointmentBinding(appointment);
            clientActiveAppointmentBinding.setOnItemClickListener(itemClickListener, position);
            mBinding.setClientAppointment(clientActiveAppointmentBinding);
        }
    }
}
