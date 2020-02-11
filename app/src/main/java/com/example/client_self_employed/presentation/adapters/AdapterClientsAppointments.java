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
import com.example.client_self_employed.presentation.adapters.items.ClientButtonItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointment;
import com.example.client_self_employed.presentation.clicklisteners.BestExpertItem;
import com.example.client_self_employed.presentation.clicklisteners.FindExpertButton;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItem;
import com.example.client_self_employed.presentation.model.ClientAppointment;

import java.util.ArrayList;
import java.util.List;

public class AdapterClientsAppointments extends RecyclerView.Adapter {
    private ActiveAppointment mItemClickListener;
    private NewRecordToBestExpertButtonItem mNewRecordToBestExpertButtonItem;
    private BestExpertItem mBestExpertItem;
    private FindExpertButton mFindExpertButton;
    private List<RowType> mDataSet = new ArrayList<>();

    public AdapterClientsAppointments(
            List<RowType> rowTypes,
            ActiveAppointment itemClickListener,
            NewRecordToBestExpertButtonItem newRecordToBestExpertButtonItem,
            BestExpertItem bestExpertItem,
            FindExpertButton findExpertListener) {
        if (rowTypes != null) {

            mDataSet.addAll(rowTypes);
        }
        mItemClickListener = itemClickListener;
        mNewRecordToBestExpertButtonItem = newRecordToBestExpertButtonItem;
        mBestExpertItem = bestExpertItem;
        mFindExpertButton = findExpertListener;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataSet.get(position);

        if (item instanceof ClientButtonItem) {
            return RowType.BUTTON_ROW_TYPE;
        } else if (item instanceof ClientAppointment) {
            return RowType.APPOINTMENT_ROW_TYPE;
        } else if (item instanceof ClientExpertItem) {
            return RowType.EXPERT_PHOTO_ROW_TYPE;
        } else if (item instanceof ClientNoAppointmentItem) {
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
                return new ButtonViewHolder(viewButton, mNewRecordToBestExpertButtonItem);
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
                return new NoAppointmentViewHolder(viewNoAppoinment, mFindExpertButton);
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
            ((AppointmentViewHolder) holder).bindView((ClientAppointment) mDataSet.get(position), position);
        } else if (holder instanceof BestExpertHolder) {
            String expertName = ((ClientExpertItem) mDataSet.get(position)).getTitle();
            ((BestExpertHolder) holder).mRecycler.setAdapter(new AdapterClientExperts(((ClientExpertItem) mDataSet.get(position)).getClientSelectedExperts(), mBestExpertItem));
            ((BestExpertHolder) holder).bindView(expertName);
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


    /**
     * Holder, в котором кнопка записи к выбранному эксперту
     */
    public static class ButtonViewHolder extends RecyclerView.ViewHolder {

        private Button mButton;
        private NewRecordToBestExpertButtonItem mNewRecordToBestExpertButtonItem;

        public ButtonViewHolder(View itemView, NewRecordToBestExpertButtonItem newRecordToBestExpertButtonItem) {
            super(itemView);
            mButton = itemView.findViewById(R.id.pick_time_button);
            mNewRecordToBestExpertButtonItem = newRecordToBestExpertButtonItem;
        }

        void bindView() {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewRecordToBestExpertButtonItem.onButtonItemClickListener();
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
        // private NewRecordToBestExpertButtonItem mNewRecordToBestExpertButtonItem;


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

        private ActiveAppointment mItemClickListener;

        public AppointmentViewHolder(@NonNull View itemView, ActiveAppointment itemClickListener) {
            super(itemView);
            mItemClickListener = itemClickListener;
            mTimeTextView = itemView.findViewById(R.id.item_start_time);
            mExpertNameTextView = itemView.findViewById(R.id.item_expert_name);
            mCostTextView = itemView.findViewById(R.id.item_cost);
            mProfessionTextView = itemView.findViewById(R.id.item_expert_profession);
            mDateTextView = itemView.findViewById(R.id.item_date);
        }

        void bindView(final ClientAppointment appointment, int position) {
            mTimeTextView.setText(appointment.getStartTime());
            mCostTextView.setText(appointment.getCost() + " р");
            mExpertNameTextView.setText(appointment.getExpertName());
            mDateTextView.setText(appointment.getDate());
            mProfessionTextView.setText(appointment.getExpertProfession());
            itemView.setOnClickListener(v -> mItemClickListener.onAppointmentsItemClickListener(appointment, position));
        }
    }

    public static class NoAppointmentViewHolder extends RecyclerView.ViewHolder {

        public NoAppointmentViewHolder(@NonNull View itemView, FindExpertButton findExpertButton) {
            super(itemView);
            itemView.findViewById(R.id.item_search_for_expert)
                    .setOnClickListener(v -> findExpertButton.onFindExpertButton());
        }
    }

}
