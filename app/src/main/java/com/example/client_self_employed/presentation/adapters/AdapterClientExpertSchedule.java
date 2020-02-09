package com.example.client_self_employed.presentation.adapters;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.Utils.IResourceWrapper;

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
public class AdapterClientExpertSchedule extends RecyclerView.Adapter {
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public AdapterClientExpertSchedule(List<Appointment> expertSchedule, IResourceWrapper resourceWrapper) {
        mExpertSchedule = expertSchedule;
        mResourceWrapper = resourceWrapper;
        groupAppointmentByDate(mExpertSchedule);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void groupAppointmentByDate(List<Appointment> expertSchedule) {

        mAdapterItems = new ArrayList<>();
        if (expertSchedule.size() != 0) {
            ArrayList<Appointment> timesForDay = new ArrayList<>();

            int day = 0;
            for (Appointment appointment : expertSchedule) {

                if (appointment.getDayOfMonth() > day) {
                    day = appointment.getDayOfMonth();
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

    @Override
    public int getItemViewType(int position) {
        Object item = mAdapterItems.get(position);
        if (item instanceof ArrayList) {
            return ITEM_VIEW_TYPE_TIME;
        } else if (item instanceof String) {
            return ITEM_VIEW_TYPE_DATE;
        } else {
            throw new RuntimeException("The following item is not supported by adapter: " + item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_TIME: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_time_recycler, parent, false);
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
                List<Appointment> appointments1 = new ArrayList<>((List<Appointment>) item);
                // ((ScheduleHolder) holder).bindView((Appointment) item);
                ((ScheduleHolder) holder).mRecycler.setAdapter(new AdapterExpertScheduleDaysTime(appointments1));
                break;
            case ITEM_VIEW_TYPE_DATE:
                ((DateHolder) holder).bindView((String) item);
                break;
            default:
                throw new RuntimeException("The following item is not supported by adapter: " + item);
        }
    }


    @Override
    public int getItemCount() {
        return mAdapterItems.size();
    }

    public static class ScheduleHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecycler;

        public ScheduleHolder(@NonNull View itemView) {
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
}
