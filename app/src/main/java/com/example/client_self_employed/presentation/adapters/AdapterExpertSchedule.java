package com.example.client_self_employed.presentation.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.Utils.IResourceWrapper;
import com.example.client_self_employed.presentation.clicklisteners.ExpertScheduleDetailedAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Адаптер для отображения расписания эксперта
 */
public class AdapterExpertSchedule extends RecyclerView.Adapter {
    /**
     * Тип элемента списка - дата
     */
    private static final int ITEM_VIEW_TYPE_DATE = 0;
    /**
     * Тип элемента списка - время
     */
    private static final int ITEM_VIEW_TYPE_TIME = 1;

    private List<Object> mAdapterItems;
    private List<Appointment> mExpertSchedule;
    private IResourceWrapper mResourceWrapper;
    private final FilterActiveAppointmentsInteractor mFilterInteractor;

    private ExpertScheduleDetailedAppointment mClickListeners;


    public AdapterExpertSchedule(List<Appointment> expertSchedule, IResourceWrapper resourceWrapper, ExpertScheduleDetailedAppointment expertScheduleDetailedAppointment, FilterActiveAppointmentsInteractor filterInteractor) {
        mFilterInteractor = filterInteractor;
        mExpertSchedule = expertSchedule;
        mResourceWrapper = resourceWrapper;
        mClickListeners = expertScheduleDetailedAppointment;
        groupAppointmentByDate(mExpertSchedule);

    }

    @Override
    public int getItemViewType(int position) {
        Object item = mAdapterItems.get(position);
        if (item instanceof ArrayList) {
            return ITEM_VIEW_TYPE_TIME;
        } else if (item instanceof String) {
            return ITEM_VIEW_TYPE_DATE;
        } else {
            throw new RuntimeException(mResourceWrapper.getString(R.string.exception_item_is_not_supportend_by_adapter) + item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_TIME: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expert_schedule_for_one_day, parent, false);
                return new ScheduleHolder(view);
            }
            case ITEM_VIEW_TYPE_DATE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_date, parent, false);
                return new DateHolder(view);
            }
            default:
                throw new IllegalArgumentException("ViewType " + viewType + " is not supported");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = mAdapterItems.get(position);
        switch (getItemViewType(position)) {
            case ITEM_VIEW_TYPE_TIME:
                ((ScheduleHolder) holder).mRecycler.setAdapter(new AdapterExpertScheduleDaysTime((List<Appointment>) item, mClickListeners));
                break;
            case ITEM_VIEW_TYPE_DATE:
                ((DateHolder) holder).bindView((String) item);
                break;
            default:
                throw new RuntimeException(mResourceWrapper.getString(R.string.exception_item_is_not_supportend_by_adapter) + item);
        }
    }


    @Override
    public int getItemCount() {
        return mAdapterItems.size();
    }

    public static class ScheduleHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecycler;

        ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            mRecycler = itemView.findViewById(R.id.item_schedule_time_recycler);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 4);
            mRecycler.setLayoutManager(gridLayoutManager);

        }
    }

    private static class DateHolder extends RecyclerView.ViewHolder {
        private final TextView mDate;

        private DateHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.item_schedule_date);
        }

        private void bindView(String date) {
            mDate.setText(date);
        }
    }


    private void groupAppointmentByDate(List<Appointment> expertSchedule) {
        long now = System.currentTimeMillis();
        mExpertSchedule = mFilterInteractor.filterActiveAppointments(now, mExpertSchedule);
        mAdapterItems = new ArrayList<>();
        if (expertSchedule.size() != 0) {
            ArrayList<Appointment> timesForDay = new ArrayList<>();
            int day = 0;
            int month = 0;
            for (Appointment appointment : expertSchedule) {
                if (appointment.getMonth() > month || appointment.getDayOfMonth() > day) {
                    day = appointment.getDayOfMonth();
                    month = appointment.getMonth();
                    timesForDay = new ArrayList<>();
                    mAdapterItems.add(ruDateFormat(appointment));
                    mAdapterItems.add(timesForDay);
                }
                timesForDay.add(appointment);
            }
        } else {
            mAdapterItems.add(mResourceWrapper.getString(R.string.expert_schedule_no_time));
        }
    }

    private String ruDateFormat(Appointment appointment) {
        String dateFormat = "";
        try {
            String date = new StringBuilder().append(appointment.getDayOfMonth()).append(".").append(appointment.getMonth()).append(".").append(appointment.getYear()).toString();
            @SuppressLint("SimpleDateFormat")
            Date jud = new SimpleDateFormat("dd.MM.yyyy").parse(date);
            if (jud != null) {
                dateFormat = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("ru")).format(jud);
                String[] month = dateFormat.split(" ");
                dateFormat = month[0] + " " + month[1];
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat;
    }
}
