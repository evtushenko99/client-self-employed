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
import com.example.client_self_employed.presentation.adapters.items.ClientAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.ClientButtonItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppoinmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.clicklisteners.AppointmentsItemClickListener;
import com.example.client_self_employed.presentation.clicklisteners.ExpertsItemClickListener;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterClientsAppointments extends RecyclerView.Adapter {
    private AppointmentsItemClickListener mItemClickListener;
    private NewRecordToBestExpertButtonItemClickListener mNewRecordToBestExpertButtonItemClickListener;
    private ExpertsItemClickListener mExpertsItemClickListener;
    private List<RowType> mDataSet = new ArrayList<>();

    public AdapterClientsAppointments(
            List<RowType> rowTypes,
            AppointmentsItemClickListener itemClickListener,
            NewRecordToBestExpertButtonItemClickListener newRecordToBestExpertButtonItemClickListener,
            ExpertsItemClickListener expertsItemClickListener) {
        if (rowTypes != null) {
            mDataSet.addAll(rowTypes);
        }
        mItemClickListener = itemClickListener;
        mNewRecordToBestExpertButtonItemClickListener = newRecordToBestExpertButtonItemClickListener;
        mExpertsItemClickListener = expertsItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataSet.get(position);

        if (item instanceof ClientButtonItem) {
            return RowType.BUTTON_ROW_TYPE;
        } else if (item instanceof ClientAppointmentItem) {
            return RowType.APPOINTMENT_ROW_TYPE;
        } else if (item instanceof ClientExpertItem) {
            return RowType.EXPERT_PHOTO_ROW_TYPE;
        } else if (item instanceof ClientNoAppoinmentItem) {
            return RowType.NO_APPOINTMENT_ROW_TYPE;
        } else {
            throw new RuntimeException("The following item is not supported by adapter: " + item);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RowType.BUTTON_ROW_TYPE: {
                View viewButton = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_button, parent, false);
                return new ButtonViewHolder(viewButton, mNewRecordToBestExpertButtonItemClickListener);
            }
            case RowType.APPOINTMENT_ROW_TYPE: {
                View viewAppointment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_appointment, parent, false);
                return new AppointmentViewHolder(viewAppointment, mItemClickListener);
            }
            case RowType.EXPERT_PHOTO_ROW_TYPE: {
                View viewAppointment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
                return new BestExpertHolder(viewAppointment);
            }
            case RowType.NO_APPOINTMENT_ROW_TYPE: {
                View viewNoAppoinment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_appointment, parent, false);
                return new NoAppointmentViewHolder(viewNoAppoinment);
            }
            default:
                throw new IllegalArgumentException("ViewType " + viewType + " is not supported");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ButtonViewHolder) {
            ((ButtonViewHolder) holder).bindView();
        } else if (holder instanceof AppointmentViewHolder) {
            ((AppointmentViewHolder) holder).bindView((ClientAppointmentItem) mDataSet.get(position), position);
        } else if (holder instanceof BestExpertHolder) {
            String s = ((ClientExpertItem) mDataSet.get(position)).getTitle();
            ((BestExpertHolder) holder).mRecycler.setAdapter(new AdapterClientExperts(((ClientExpertItem) mDataSet.get(position)).getClientSelectedExportItems(), mExpertsItemClickListener));
            ((BestExpertHolder) holder).bindView(s);
        }
    }


    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    public void setRowTypes(@NonNull List<RowType> rowTypes) {
        mDataSet.clear();
        mDataSet.addAll(rowTypes);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        notifyItemRemoved(position);
    }

    /**
     * Holder, в котором кнопка записи к выбранному эксперту
     */
    public static class ButtonViewHolder extends RecyclerView.ViewHolder {

        private Button mButton;
        private NewRecordToBestExpertButtonItemClickListener mNewRecordToBestExpertButtonItemClickListener;

        public ButtonViewHolder(View itemView, NewRecordToBestExpertButtonItemClickListener newRecordToBestExpertButtonItemClickListener) {
            super(itemView);
            mButton = itemView.findViewById(R.id.pick_time_button);
            mNewRecordToBestExpertButtonItemClickListener = newRecordToBestExpertButtonItemClickListener;
        }

        void bindView() {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewRecordToBestExpertButtonItemClickListener.onButtonItemClickListener();
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
        // private NewRecordToBestExpertButtonItemClickListener mNewRecordToBestExpertButtonItemClickListener;


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

        void bindView(final ClientAppointmentItem appointment, int position) {
            mTimeTextView.setText(appointment.getStartTime());
            mCostTextView.setText(appointment.getCost() + " р");
            mExpertNameTextView.setText(appointment.getExpertName());
            mDateTextView.setText(appointment.getDate());
            mProfessionTextView.setText(appointment.getExpertProfession());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onAppointmentsItemClickListener(appointment, position);
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
