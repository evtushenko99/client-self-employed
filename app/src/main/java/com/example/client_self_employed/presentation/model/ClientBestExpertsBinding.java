package com.example.client_self_employed.presentation.model;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.presentation.adapters.AdapterBestExperts;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItemClickListener;

import java.util.List;

public class ClientBestExpertsBinding {
    private View.OnClickListener mOnClickListener;
    private NewRecordToBestExpertButtonItemClickListener mOnBestExpertClickLisner;
    private ObservableField<List<ClientSelectedExpert>> mLiveExpertList = new ObservableField<>();


    public ClientBestExpertsBinding(@NonNull List<ClientSelectedExpert> expertList) {
        mLiveExpertList.set(expertList);
    }


    @BindingAdapter({"loadData", "onBestClick"})
    public static void setRecycler(RecyclerView recycler, List<ClientSelectedExpert> expertList, NewRecordToBestExpertButtonItemClickListener NewRecordToBestExpertButtonItemClickListener) {
        if (expertList != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(recycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recycler.setLayoutManager(layoutManager);
            recycler.setAdapter(new AdapterBestExperts(expertList, NewRecordToBestExpertButtonItemClickListener));
            //  ((AdapterBestExperts) recycler.getAdapter()).setRowTypes(expertList);
        }
    }

    public NewRecordToBestExpertButtonItemClickListener getOnBestExpertClickLisner() {
        return mOnBestExpertClickLisner;
    }

    public void setOnBestExpertClickListener(NewRecordToBestExpertButtonItemClickListener bestExpertItemClickListener) {
        mOnBestExpertClickLisner = bestExpertItemClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

   /* public void setOnClickListener(NewRecordToBestExpertButtonItemClickListener newRecordToBestExpertButtonItemClickListener) {
        mOnClickListener = v -> newRecordToBestExpertButtonItemClickListener.onButtonItemClickListener();
    }*/


    public ObservableField<List<ClientSelectedExpert>> getLiveExpertList() {
        return mLiveExpertList;
    }

}
