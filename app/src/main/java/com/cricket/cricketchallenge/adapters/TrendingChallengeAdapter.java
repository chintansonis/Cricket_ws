package com.cricket.cricketchallenge.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.ui.BaseActivity;

/**
 * Created by chintans on 14-12-2017.
 */

public class TrendingChallengeAdapter extends RecyclerView.Adapter<TrendingChallengeAdapter.TrendingHolder> {

    private BaseActivity baseActivity;
    private int lastPosition = -1;

    public TrendingChallengeAdapter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public TrendingChallengeAdapter.TrendingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(baseActivity).inflate(R.layout.item_trending, parent, false);
        return new TrendingHolder(view);
    }

    @Override
    public void onBindViewHolder(TrendingChallengeAdapter.TrendingHolder holder, int position) {
        setAnimation(holder.cardviewTrending, position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TrendingHolder extends RecyclerView.ViewHolder {
        private CardView cardviewTrending;

        public TrendingHolder(View itemView) {
            super(itemView);
            cardviewTrending = (CardView) itemView.findViewById(R.id.cardviewTrending);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            viewToAnimate.setScaleX(0F);
            viewToAnimate.setScaleY(0F);
            viewToAnimate.animate()
                    .scaleX(1F)
                    .scaleY(1F)
                    .setDuration(300)
                    .setInterpolator(new DecelerateInterpolator());

            lastPosition = position;
        }
    }
}
