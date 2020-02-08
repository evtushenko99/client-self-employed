package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Appointment;

import java.util.List;

public class AdapterClientExpertSchedule extends RecyclerView.Adapter <AdapterClientExpertSchedule.ScheduleHolder>{
    private List<Appointment> mExpertSchedule;

    public AdapterClientExpertSchedule(List<Appointment> expertSchedule) {
        mExpertSchedule = expertSchedule;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_recycler, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.bindView(mExpertSchedule.get(position));
    }

    @Override
    public int getItemCount() {
        return mExpertSchedule.size();
    }

    public static class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView mTime;
        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.item_schedule_time);
        }
        public void bindView(@NonNull Appointment appointment){
            mTime.setText(appointment.getStartTime());
           // itemView.setOnClickListener();
        }
    }
}
