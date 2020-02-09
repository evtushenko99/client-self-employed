package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AdapterExpertScheduleDaysTime extends RecyclerView.Adapter<AdapterExpertScheduleDaysTime.ScheduleDaysTimeHolder> {
    private final List<Appointment> mExpertDayTimes;

    public AdapterExpertScheduleDaysTime(@NonNull List<Appointment> expertDayTimes) {
        mExpertDayTimes = new ArrayList<>(expertDayTimes);
    }

    @NonNull
    @Override
    public ScheduleDaysTimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_time, parent, false);

        return new ScheduleDaysTimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleDaysTimeHolder holder, int position) {
        holder.bindView(mExpertDayTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return mExpertDayTimes.size();
    }

    public static class ScheduleDaysTimeHolder extends RecyclerView.ViewHolder {

        private TextView mTime;

        public ScheduleDaysTimeHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mTime = itemView.findViewById(R.id.item_schedule_time);

        }

        public void bindView(@NonNull Appointment appointment) {
            mTime.setText(appointment.getStartTime());

        }


    }
}
