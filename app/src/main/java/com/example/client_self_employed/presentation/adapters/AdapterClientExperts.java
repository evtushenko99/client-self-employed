package com.example.client_self_employed.presentation.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.clicklisteners.BestExpertItem;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AdapterClientExperts extends RecyclerView.Adapter<AdapterClientExperts.SelectedExpertHolder> {
    private List<ClientSelectedExpert> mExpertList;
    private BestExpertItem mBestExpertItem;
    private Resources mResources;
    private int index = -1;

    public AdapterClientExperts(@NonNull List<ClientSelectedExpert> expertList, BestExpertItem bestExpertItem, @NonNull Resources resources) {
        mExpertList = expertList;
        mBestExpertItem = bestExpertItem;
        mResources = resources;
    }

    @NonNull
    @Override
    public AdapterClientExperts.SelectedExpertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expert_photo, parent, false);
        return new AdapterClientExperts.SelectedExpertHolder(view, mBestExpertItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClientExperts.SelectedExpertHolder holder, int position) {
        holder.bindView(mExpertList.get(position), holder.getAdapterPosition());
        int color = index == position ? R.color.primaryLightColor : R.color.white;

        holder.mCardView.setBackgroundColor(mResources.getColor(color));
    }

    @Override
    public int getItemCount() {
        return mExpertList.size();
    }

    public class SelectedExpertHolder extends RecyclerView.ViewHolder {
        private TextView mExpertName;
        private ImageView mExpertPhoto;
        private BestExpertItem mBestExpertItem;
        private CardView mCardView;

        public SelectedExpertHolder(@NonNull View itemView, BestExpertItem bestExpertItem) {
            super(itemView);
            mExpertName = itemView.findViewById(R.id.item_selected_expert_name);
            mExpertPhoto = itemView.findViewById(R.id.item_selected_expert_photo);
            mBestExpertItem = bestExpertItem;
            mCardView = itemView.findViewById(R.id.material_expert_card);


        }

        void bindView(ClientSelectedExpert clientSelectedExpert, int adapterPosition) {
            mExpertName.setText(clientSelectedExpert.getExpertName());
            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            Glide
                    .with(itemView.getContext())
                    .load(clientSelectedExpert.getUri())
                    .apply(new RequestOptions()
                            .circleCrop()
                            .placeholder(R.mipmap.no_photo_available_or_missing)
                            .error(R.mipmap.no_photo_available_or_missing))
                    .transition(withCrossFade(factory))
                    .into(mExpertPhoto);
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBestExpertItem.onExpertItemClickListener(clientSelectedExpert.getExpertId());
                    index = adapterPosition;
                    notifyDataSetChanged();
                }
            });

        }

    }
}
