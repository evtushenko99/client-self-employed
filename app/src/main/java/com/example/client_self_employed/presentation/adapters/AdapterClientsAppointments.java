package com.example.client_self_employed.presentation.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.adapters.items.ClientActiveAppointmentsItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointment;
import com.example.client_self_employed.presentation.clicklisteners.BestExpertItem;
import com.example.client_self_employed.presentation.clicklisteners.FindExpertButton;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterClientsAppointments extends RecyclerView.Adapter {
    private ActiveAppointment mItemClickListener;
    private NewRecordToBestExpertButtonItem mNewRecordToBestExpertButtonItem;
    private BestExpertItem mBestExpertItem;
    private FindExpertButton mFindExpertButton;
    private Resources mResources;
    private List<RowType> mDataSet = new ArrayList<>();

    public AdapterClientsAppointments(
            List<RowType> rowTypes,
            ActiveAppointment itemClickListener,
            NewRecordToBestExpertButtonItem newRecordToBestExpertButtonItem,
            BestExpertItem bestExpertItem,
            FindExpertButton findExpertListener, @NonNull Resources resources) {
        if (rowTypes != null) {
            mDataSet.addAll(rowTypes);
        }
        mItemClickListener = itemClickListener;
        mNewRecordToBestExpertButtonItem = newRecordToBestExpertButtonItem;
        mBestExpertItem = bestExpertItem;
        mFindExpertButton = findExpertListener;
        mResources = resources;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataSet.get(position);
        if (item instanceof ClientActiveAppointmentsItem) {
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
            case RowType.APPOINTMENT_ROW_TYPE: {
                View viewAppointment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_appointments, parent, false);
                return new AppointmentViewHolder(viewAppointment);
            }
            case RowType.EXPERT_PHOTO_ROW_TYPE: {
                View viewAppointment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_best_experts_recycler, parent, false);
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
        if (holder instanceof AppointmentViewHolder) {
            ((AppointmentViewHolder) holder).mRecycler.setAdapter(new AdapterActiveAppointments(((ClientActiveAppointmentsItem) mDataSet.get(position)).getClientSelectedExperts(), mItemClickListener));
        } else if (holder instanceof BestExpertHolder) {
            String expertName = ((ClientExpertItem) mDataSet.get(position)).getTitle();
            ((BestExpertHolder) holder).mRecycler.setAdapter(new AdapterClientExperts(((ClientExpertItem) mDataSet.get(position)).getClientSelectedExperts(), mBestExpertItem, mResources));
            ((BestExpertHolder) holder).bindView(expertName, mNewRecordToBestExpertButtonItem);
        }
    }


    @Override
    public int getItemCount() {
        if (mDataSet.size() != 0) {
            return mDataSet.size();
        } else {
            return 0;
        }
    }

    public void setRowTypes(@NonNull List<RowType> rowTypes) {
        mDataSet.clear();
        mDataSet.addAll(rowTypes);
        notifyDataSetChanged();
    }


    /**
     * Holder, отвечающий за дополнительный горизонтальный recycler, который показывает
     * самых лучших экспертов по рейтингу
     */
    public static class BestExpertHolder extends RecyclerView.ViewHolder {
        private TextView mRecyclerTitle;
        private RecyclerView mRecycler;
        private Button mNewRecordButton;


        public BestExpertHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerTitle = itemView.findViewById(R.id.recycler_title);
            mRecycler = itemView.findViewById(R.id.recycler_in_recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecycler.setLayoutManager(layoutManager);

            mNewRecordButton = itemView.findViewById(R.id.best_expert_new_appointment_button);
        }

        void bindView(@NonNull String title, NewRecordToBestExpertButtonItem newRecordToBestExpertButtonItem) {
            mRecyclerTitle.setText(title);
            mNewRecordButton.setOnClickListener(v -> newRecordToBestExpertButtonItem.onButtonItemClickListener());
        }
    }

    /**
     * Holder отвечающий за отображение активных записей клиента
     */
    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecycler;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecycler = itemView.findViewById(R.id.item_active_appointments_recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecycler.setLayoutManager(layoutManager);
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
