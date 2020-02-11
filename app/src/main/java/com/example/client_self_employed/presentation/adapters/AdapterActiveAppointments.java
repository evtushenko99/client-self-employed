package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointment;
import com.example.client_self_employed.presentation.model.ClientAppointment;

import java.util.List;

public class AdapterActiveAppointments extends RecyclerView.Adapter<AdapterActiveAppointments.ActiveAppointmentViewHolder> {
    private List<ClientAppointment> mAppointments;
    private ActiveAppointment mItemClickListener;

    public AdapterActiveAppointments(@NonNull List<ClientAppointment> appointments, ActiveAppointment itemClickListener1) {
        mAppointments = appointments;
        mItemClickListener = itemClickListener1;
    }

    @NonNull
    @Override
    public ActiveAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewAppointment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_appointment, parent, false);
        return new ActiveAppointmentViewHolder(viewAppointment, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveAppointmentViewHolder holder, int position) {
        holder.bindView(mAppointments.get(position));
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public static class ActiveAppointmentViewHolder extends RecyclerView.ViewHolder {

        private TextView mTimeTextView;
        private TextView mExpertNameTextView;
        private TextView mCostTextView;
        private TextView mDateTextView;
        private TextView mProfessionTextView;

        private ActiveAppointment mItemClickListener;

        public ActiveAppointmentViewHolder(@NonNull View itemView, ActiveAppointment itemClickListener) {
            super(itemView);
            mItemClickListener = itemClickListener;
            mTimeTextView = itemView.findViewById(R.id.item_start_time);
            mExpertNameTextView = itemView.findViewById(R.id.item_expert_name);
            mCostTextView = itemView.findViewById(R.id.item_cost);
            mProfessionTextView = itemView.findViewById(R.id.item_expert_profession);
            mDateTextView = itemView.findViewById(R.id.item_date);
        }

        void bindView(final ClientAppointment appointment) {
            mTimeTextView.setText(appointment.getStartTime());
            mCostTextView.setText(appointment.getCost() + " р");
            mExpertNameTextView.setText(appointment.getExpertName());
            mDateTextView.setText(appointment.getDate());
            mProfessionTextView.setText(appointment.getExpertProfession());
            itemView.setOnClickListener(v -> mItemClickListener.onAppointmentsItemClickListener(appointment));
        }
    }
}
