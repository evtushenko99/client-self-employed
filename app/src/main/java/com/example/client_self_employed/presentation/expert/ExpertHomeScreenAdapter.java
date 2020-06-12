package com.example.client_self_employed.presentation.expert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.adapters.items.NoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExpertHomeScreenAdapter extends RecyclerView.Adapter {
    private ExpertAppointmentClickListener mAppointmentClickListener;
    private List<RowType> mList = new ArrayList<>();

    public ExpertHomeScreenAdapter(@Nullable List<Appointment> list, ExpertAppointmentClickListener clickListener) {
        if (list.size() != 0) {
            mList.clear();
            mList.addAll(list);
        } else {
            mList.add(new NoAppointmentItem());
        }
        mAppointmentClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case RowType.APPOINTMENT_ROW_TYPE: {
                View view = inflater.inflate(R.layout.item_expert_appointment, parent, false);
                return new AppointmentViewHolder(view, mAppointmentClickListener);
            }
            case RowType.NO_APPOINTMENT_ROW_TYPE: {
                View view = inflater.inflate(R.layout.item_expert_empty_recycler, parent, false);
                return new NoAppointmentViewHolder(view);
            }
            default:
                throw new IllegalArgumentException("ViewType " + viewType + " is not supported");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppointmentViewHolder) {
            ((AppointmentViewHolder) holder).bindView((Appointment) mList.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mList.get(position);
        if (item instanceof Appointment) {
            return RowType.APPOINTMENT_ROW_TYPE;
        } else if (item instanceof NoAppointmentViewHolder) {
            return RowType.NO_APPOINTMENT_ROW_TYPE;
        } else {
            throw new RuntimeException("The following item is not supported by adapter: " + item);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * Holder, отвечающий за отображение активных записей эксперта
     */
    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private ExpertAppointmentClickListener mClickListener;
        private CardView mCardView;
        private TextView mServiceName;
        private TextView mStartTime;
        private TextView mDate;
        private TextView mCost;


        public AppointmentViewHolder(@NotNull View view, ExpertAppointmentClickListener appointmentClickListener) {
            super(view);
            mCardView = view.findViewById(R.id.item_expert_appointment);
            mServiceName = view.findViewById(R.id.item_service_name);
            mStartTime = view.findViewById(R.id.item_start_time);
            mDate = view.findViewById(R.id.item_date);
            mCost = view.findViewById(R.id.item_cost);
            mClickListener = appointmentClickListener;
        }

        void bindView(@NotNull final Appointment appointment, int position) {
            if (!appointment.getClientId().equals("0")) {
                mCardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorRecycler));
            }
            mServiceName.setText(appointment.getServiceName());
            mStartTime.setText(appointment.getStringTime() + " 一 " + appointment.getSessionDuration());
            mDate.setText(appointment.getStringDate());
            mCost.setText(String.valueOf(appointment.getCost()) + " рублей");
            mCardView.setOnClickListener(v -> mClickListener.onExpertAppointmentClickListener(appointment, position));
        }
    }

    /**
     * Holder, отвечающий за отображение view, c информацией о том, что нет активных записей
     */
    static class NoAppointmentViewHolder extends RecyclerView.ViewHolder {
        public NoAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
