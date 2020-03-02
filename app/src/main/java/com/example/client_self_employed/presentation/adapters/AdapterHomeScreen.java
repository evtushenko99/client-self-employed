package com.example.client_self_employed.presentation.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.databinding.ClientActiveAppointmentsBinding;
import com.example.client_self_employed.databinding.ItemBestExpertsBinding;
import com.example.client_self_employed.presentation.adapters.items.ClientActiveAppointmentsItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointmentClickListener;
import com.example.client_self_employed.presentation.clicklisteners.FindExpertButtonClickListener;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItemClickListener;
import com.example.client_self_employed.presentation.model.ClientAppointment;
import com.example.client_self_employed.presentation.model.ClientBestExpertsBinding;
import com.example.client_self_employed.presentation.model.ClientRecyclerActiveAppointmentsBinding;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер для  для главного ресайклера , в котором размещаются лучшие эксперты и активные записи
 */
public class AdapterHomeScreen extends RecyclerView.Adapter {
    private ActiveAppointmentClickListener mItemClickListener;
    private NewRecordToBestExpertButtonItemClickListener mNewRecordToBestExpertButtonItemClickListener;
    private FindExpertButtonClickListener mFindExpertButtonClickListener;
    private Resources mResources;
    private List<RowType> mDataSet = new ArrayList<>();

    public AdapterHomeScreen(
            List<RowType> rowTypes,
            ActiveAppointmentClickListener itemClickListener,
            NewRecordToBestExpertButtonItemClickListener newRecordToBestExpertButtonItemClickListener,
            FindExpertButtonClickListener findExpertListener, @NonNull Resources resources) {
        if (rowTypes != null) {
            mDataSet.addAll(rowTypes);
        }
        mItemClickListener = itemClickListener;
        mNewRecordToBestExpertButtonItemClickListener = newRecordToBestExpertButtonItemClickListener;
        mFindExpertButtonClickListener = findExpertListener;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case RowType.APPOINTMENT_ROW_TYPE: {
                ClientActiveAppointmentsBinding appointmentsBinding = DataBindingUtil.inflate(inflater, R.layout.item_active_appointments, parent, false);
                return new AppointmentViewHolder(appointmentsBinding);
            }
            case RowType.EXPERT_PHOTO_ROW_TYPE: {
                ItemBestExpertsBinding bestExpertsBinding = DataBindingUtil.inflate(inflater, R.layout.item_best_experts, parent, false);
                return new BestExpertHolder(bestExpertsBinding);
            }
            case RowType.NO_APPOINTMENT_ROW_TYPE: {
                View viewNoAppoinment = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_active_appointment, parent, false);
                return new NoAppointmentViewHolder(viewNoAppoinment, mFindExpertButtonClickListener);
            }
            default:
                throw new IllegalArgumentException("ViewType " + viewType + " is not supported");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppointmentViewHolder) {
            ((AppointmentViewHolder) holder).bindView(((ClientActiveAppointmentsItem) mDataSet.get(position)).getClientAppointmentList(), mItemClickListener);
        } else if (holder instanceof BestExpertHolder) {
            ((BestExpertHolder) holder).bindView(((ClientExpertItem) mDataSet.get(position)).getClientSelectedExperts(), mNewRecordToBestExpertButtonItemClickListener);
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

        private ItemBestExpertsBinding mBinding;

        public BestExpertHolder(@NonNull ItemBestExpertsBinding bestExpertsBinding) {
            super(bestExpertsBinding.getRoot());
            mBinding = bestExpertsBinding;
        }

        void bindView(List<ClientSelectedExpert> clientSelectedExperts,
                      NewRecordToBestExpertButtonItemClickListener newRecordToBestExpertButtonItemClickListener) {
            ClientBestExpertsBinding clientBestExpertsBinding = new ClientBestExpertsBinding(clientSelectedExperts);
            clientBestExpertsBinding.setOnBestExpertClickListener(newRecordToBestExpertButtonItemClickListener);
            mBinding.setBestExperts(clientBestExpertsBinding);

        }
    }

    /**
     * Holder отвечающий за отображение активных записей клиента
     */
    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private ClientActiveAppointmentsBinding mAppointmentsBinding;

        public AppointmentViewHolder(@NonNull ClientActiveAppointmentsBinding binding) {
            super(binding.getRoot());
            mAppointmentsBinding = binding;
        }


        void bindView(List<ClientAppointment> clientAppointmentList, ActiveAppointmentClickListener itemClickListener) {
            ClientRecyclerActiveAppointmentsBinding activeAppointmentsBinding = new ClientRecyclerActiveAppointmentsBinding(clientAppointmentList);
            activeAppointmentsBinding.setAppointmentClickListener(itemClickListener);
            mAppointmentsBinding.setRecyclerAppointment(activeAppointmentsBinding);
        }
    }

    public static class NoAppointmentViewHolder extends RecyclerView.ViewHolder {

        public NoAppointmentViewHolder(@NonNull View itemView, FindExpertButtonClickListener findExpertButtonClickListener) {
            super(itemView);
            itemView.findViewById(R.id.item_search_for_expert)
                    .setOnClickListener(v -> findExpertButtonClickListener.onFindExpertButton());
        }
    }

}
