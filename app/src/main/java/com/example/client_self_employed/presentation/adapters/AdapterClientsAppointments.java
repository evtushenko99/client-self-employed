package com.example.client_self_employed.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.clicklisteners.AppointmentsItemClickListener;
import com.example.client_self_employed.presentation.clicklisteners.ButtonItemClickListener;
import com.example.client_self_employed.presentation.clicklisteners.ExpertsItemClickListener;
import com.example.client_self_employed.presentation.adapters.items.ClientAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.ClientButtonItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppoinmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;

import java.util.ArrayList;
import java.util.List;

public class AdapterClientsAppointments extends RecyclerView.Adapter {
    private AppointmentsItemClickListener mItemClickListener;
    private ButtonItemClickListener mButtonItemClickListener;
    private ExpertsItemClickListener mExpertsItemClickListener;
    private List<RowType> mDataSet;

    public AdapterClientsAppointments(
            @NonNull List<RowType> rowTypes,
            AppointmentsItemClickListener itemClickListener,
            ButtonItemClickListener buttonItemClickListener,
            ExpertsItemClickListener expertsItemClickListener) {
        mDataSet = new ArrayList<>(rowTypes);
        mItemClickListener = itemClickListener;
        mButtonItemClickListener = buttonItemClickListener;
        mExpertsItemClickListener = expertsItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.get(position) instanceof ClientButtonItem) {
            return RowType.BUTTON_ROW_TYPE;
        } else if (mDataSet.get(position) instanceof ClientAppointmentItem) {
            return RowType.APPOINTMENT_ROW_TYPE;
        } else if (mDataSet.get(position) instanceof ClientExpertItem) {
            return RowType.EXPERT_PHOTO_ROW_TYPE;
        } else if (mDataSet.get(position) instanceof ClientNoAppoinmentItem) {
            return RowType.NO_APPOINTMENT_ROW_TYPE;
        } else {
            return -1;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RowType.BUTTON_ROW_TYPE) {
            View viewButton = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_button, parent, false);
            return new ButtonViewHolder(viewButton, mButtonItemClickListener);
        } else if (viewType == RowType.APPOINTMENT_ROW_TYPE) {
            View viewAppointment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_appointment, parent, false);
            return new AppointmentViewHolder(viewAppointment, mItemClickListener);
        } else if (viewType == RowType.EXPERT_PHOTO_ROW_TYPE) {
            View viewAppointment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
            return new BestExpertHolder(viewAppointment);
        } else if (viewType == RowType.NO_APPOINTMENT_ROW_TYPE) {
            View viewNoAppoinment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_appointment, parent, false);
            return new NoAppointmentViewHolder(viewNoAppoinment);
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ButtonViewHolder) {
            ((ButtonViewHolder) holder).bindView();
        } else if (holder instanceof AppointmentViewHolder) {
            ((AppointmentViewHolder) holder).bindView((ClientAppointmentItem) mDataSet.get(position));
        } else if (holder instanceof BestExpertHolder) {
            String s = ((ClientExpertItem) mDataSet.get(position)).getTitle();
            ((BestExpertHolder) holder).mRecycler.setAdapter(new AdapterClientExperts(((ClientExpertItem) mDataSet.get(position)).getClientSelectedExportItems(), mExpertsItemClickListener));
            ((BestExpertHolder) holder).bindView(s);
        }
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    /**
     * Holder, в котором кнопка записи к выбранному эксперту
     */
    public static class ButtonViewHolder extends RecyclerView.ViewHolder {

        private Button mButton;
        private ButtonItemClickListener mButtonItemClickListener;

        public ButtonViewHolder(View itemView, ButtonItemClickListener buttonItemClickListener) {
            super(itemView);
            mButton = itemView.findViewById(R.id.pick_time_button);
            mButtonItemClickListener = buttonItemClickListener;
        }

        void bindView() {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonItemClickListener.onButtonItemClickListener();
                }
            });
        }
    }

    /**
     * Holder, отвечающий за дополнительный горизонтальный recycler, который показывает
     * самых лучших экспертов по рейтингу
     */
    public static class BestExpertHolder extends RecyclerView.ViewHolder {
        private TextView mRecyclerTitle;
        private RecyclerView mRecycler;
        // private ButtonItemClickListener mButtonItemClickListener;


        public BestExpertHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerTitle = itemView.findViewById(R.id.recycler_title);
            mRecycler = itemView.findViewById(R.id.recycler_in_recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecycler.setLayoutManager(layoutManager);

        }

        void bindView(@NonNull String title) {
            mRecyclerTitle.setText(title);
        }
    }

    /**
     * Holder отвечающий за отображение активных записей клиента
     */
    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private TextView mTimeTextView;
        private TextView mExpertNameTextView;
        private TextView mCostTextView;
        private TextView mDateTextView;
        private TextView mProfessionTextView;

        private AppointmentsItemClickListener mItemClickListener;

        public AppointmentViewHolder(@NonNull View itemView, AppointmentsItemClickListener itemClickListener) {
            super(itemView);
            mItemClickListener = itemClickListener;
            mTimeTextView = itemView.findViewById(R.id.item_start_time);
            mExpertNameTextView = itemView.findViewById(R.id.item_expert_name);
            mCostTextView = itemView.findViewById(R.id.item_cost);
            mProfessionTextView = itemView.findViewById(R.id.item_expert_profession);
            mDateTextView = itemView.findViewById(R.id.item_date);
        }

        void bindView(final ClientAppointmentItem appointment) {
            mTimeTextView.setText(appointment.getStartTime());
            mCostTextView.setText(appointment.getCost() + " р");
            mExpertNameTextView.setText(appointment.getExpertName());
            mDateTextView.setText(appointment.getStringDate());
            mProfessionTextView.setText(appointment.getExpertProfession());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onAppointmentsItemClickListener(appointment);
                }
            });
        }
    }
    public static class NoAppointmentViewHolder extends RecyclerView.ViewHolder {
        public NoAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
