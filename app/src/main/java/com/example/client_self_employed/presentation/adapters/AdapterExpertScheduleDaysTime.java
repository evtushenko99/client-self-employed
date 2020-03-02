package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.clicklisteners.ExpertScheduleDetailedAppointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер для отображения списка записей эксперта, относящихся к конкретному дню
 */
public class AdapterExpertScheduleDaysTime extends RecyclerView.Adapter<AdapterExpertScheduleDaysTime.ScheduleDaysTimeHolder> {
    private final List<Appointment> mExpertDayTimes;
    private ExpertScheduleDetailedAppointment mClickListners;

    AdapterExpertScheduleDaysTime(@NonNull List<Appointment> expertDayTimes, ExpertScheduleDetailedAppointment clickListeners) {
        mExpertDayTimes = new ArrayList<>(expertDayTimes);
        mClickListners = clickListeners;
    }

    @NonNull
    @Override
    public ScheduleDaysTimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_time, parent, false);

        return new ScheduleDaysTimeHolder(view, mClickListners);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleDaysTimeHolder holder, int position) {
        holder.bindView(mExpertDayTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return mExpertDayTimes.size();
    }

    static class ScheduleDaysTimeHolder extends RecyclerView.ViewHolder {

        private TextView mTime;
        private ExpertScheduleDetailedAppointment mClickListners;

        ScheduleDaysTimeHolder(@NonNull View itemView, ExpertScheduleDetailedAppointment clickListners) {
            super(itemView);
            mTime = itemView.findViewById(R.id.item_schedule_time);

            mClickListners = clickListners;

        }

        void bindView(@NonNull Appointment appointment) {
            mTime.setText(appointment.getStringTime());
            if (appointment.getClientId() != 0) {
                mTime.setBackgroundColor(itemView.getResources().getColor(R.color.primaryColor));
                mTime.setTextColor(itemView.getResources().getColor(R.color.white));
            } else {
                itemView.setOnClickListener(v -> mClickListners.onExpertScheduleDetailedAppointmentClickListners(appointment, 2));
            }
        }
    }
}
