package com.example.client_self_employed.presentation.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.clicklisteners.NewAppointmentToFindedExpert;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AdapterFindExperts extends RecyclerView.Adapter<AdapterFindExperts.FindExpertsHolder> {

    private List<Expert> mExperts = new ArrayList<>();
    private NewAppointmentToFindedExpert mClickListener;
    private Resources mResources;
    private int index = -1;

    public AdapterFindExperts(@Nullable List<Expert> experts, NewAppointmentToFindedExpert newAppointmentToFindedExpert, @NonNull Resources resources) {
        if (experts != null) {
            mExperts = experts;
        }
        mClickListener = newAppointmentToFindedExpert;
        mResources = resources;
    }

    @NonNull
    @Override
    public FindExpertsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expert_holder, parent, false);

        return new FindExpertsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FindExpertsHolder holder, int position) {
        holder.bind(mExperts.get(position), mClickListener, position);
        int color = index == position ? R.color.primaryLightColor : R.color.white;
        holder.mConstraintLayout.setBackgroundColor(mResources.getColor(color));
    }

    public void setExperts(@NonNull List<Expert> experts) {
        mExperts.clear();
        mExperts.addAll(experts);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mExperts != null ? mExperts.size() : 0;
    }

    public class FindExpertsHolder extends RecyclerView.ViewHolder {
        private TextView mExpertName;
        private TextView mExpertProfession;
        private ImageView mExpertPhoto;
        private ConstraintLayout mConstraintLayout;

        public FindExpertsHolder(@NonNull View itemView) {
            super(itemView);
            mConstraintLayout = itemView.findViewById(R.id.item_expert_holder_constraint_layout);
            mExpertName = itemView.findViewById(R.id.item_expert_holder_full_name);
            mExpertPhoto = itemView.findViewById(R.id.item_expert_holder_image);
            mExpertProfession = itemView.findViewById(R.id.item_expert_holder_profession);
        }

        public void bind(Expert expert, NewAppointmentToFindedExpert clickListener, int adapterPosition) {
            mExpertName.setText(expert.getFullName());
            mExpertProfession.setText(expert.getProfession());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onNewAppointmentToFoundExpert(expert.getId());
                    index = adapterPosition;
                    notifyDataSetChanged();
                }
            });
            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            Glide
                    .with(itemView.getContext())
                    .load(expert.getExpertPhotoUri())
                    .apply(new RequestOptions()
                            .override(48, 48)
                            .centerCrop()
                            .placeholder(R.mipmap.no_photo_available_or_missing)
                            .error(R.mipmap.no_photo_available_or_missing))
                    .transition(withCrossFade(factory))
                    .into(mExpertPhoto);
        }
    }
}
