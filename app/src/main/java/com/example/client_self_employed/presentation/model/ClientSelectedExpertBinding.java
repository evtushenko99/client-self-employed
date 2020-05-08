package com.example.client_self_employed.presentation.model;

import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItemClickListener;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ClientSelectedExpertBinding implements RowType {
    private ObservableField<String> mExpertName = new ObservableField<>();
    private ObservableField<String> mUri = new ObservableField<>();
    private ObservableInt mCardColor = new ObservableInt();
    private int mIndex = -1;
    private int mPosition;
    private String mExpertId;
    private View.OnClickListener mOnClickListener;

    private int mColor;

    public ClientSelectedExpertBinding(ClientSelectedExpert clientSelectedExpert, int position) {
        mExpertName.set(clientSelectedExpert.getExpertName());
        mUri.set(clientSelectedExpert.getUri());
        mExpertId = clientSelectedExpert.getExpertId();
        mPosition = position;
        colormethod();

    }

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(NewRecordToBestExpertButtonItemClickListener onClickListener) {
        mOnClickListener = v -> {
            onClickListener.onButtonItemClickListener(mExpertId);
            // mIndex = mPosition;
            // colormethod();
        };
    }

    private void colormethod() {
        mColor = mIndex == mPosition ? R.color.primaryLightColor : R.color.white;
        mCardColor.set(mColor);

    }

    @BindingAdapter("cardColor")
    public static void setBackgroundColor(CardView cardView, int color) {
        cardView.setBackgroundColor(cardView.getContext().getColor(color));
    }

    public ObservableInt getCardColor() {
        return mCardColor;
    }

    public ObservableField<String> getExpertName() {
        return mExpertName;
    }

    public ObservableField<String> getUri() {
        return mUri;
    }

    @BindingAdapter("URI")
    public static void loadImage(ImageView view, String imageUri) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        Glide
                .with(view.getContext())
                .load(imageUri)
                .apply(new RequestOptions()
                        .circleCrop()
                        .placeholder(R.mipmap.no_photo_available_or_missing)
                        .error(R.mipmap.no_photo_available_or_missing))
                .transition(withCrossFade(factory))
                .into(view);

    }
}
