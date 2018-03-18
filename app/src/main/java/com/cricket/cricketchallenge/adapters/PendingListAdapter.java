package com.cricket.cricketchallenge.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.api.responsepojos.PendingObject;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;
import com.cricket.cricketchallenge.ui.BaseActivity;
import com.cricket.cricketchallenge.ui.MatchDetailActivity;

import java.util.ArrayList;

/**
 * Created by chintans on 14-12-2017.
 */

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.TrendingHolder> {

    private BaseActivity baseActivity;
    private int lastPosition = -1;
    private int tempPosition = 0;
    private ArrayList<PendingObject> pendingObjectArrayList;


    public PendingListAdapter(BaseActivity baseActivity, ArrayList<PendingObject> pendingObjectArrayList) {
        this.baseActivity = baseActivity;
        tempPosition = 0;
        this.pendingObjectArrayList = pendingObjectArrayList;
    }

    @Override
    public PendingListAdapter.TrendingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(baseActivity).inflate(R.layout.item_pending_adapter, parent, false);
        return new TrendingHolder(view);
    }

    @Override
    public void onBindViewHolder(PendingListAdapter.TrendingHolder holder, int position) {
        setAnimation(holder.cardviewPending, position);
        tempPosition = tempPosition + 1;
        if (tempPosition >= AppConstants.COLORS_PRIMARY.length) {
            tempPosition = 0;
        }
        holder.cardviewPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(baseActivity, MatchDetailActivity.class);
                baseActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        if(pendingObjectArrayList.get(position).getUserFullname()!=null){
            holder.txtBuzz.setText("Buzz "+pendingObjectArrayList.get(position).getUserFullname());
            holder.txtPending.setText(pendingObjectArrayList.get(position).getUserFullname()+" has not accepted your "+pendingObjectArrayList.get(position).getPredictionType()+" Challenge request yet.");
        }


    }

    @Override
    public int getItemCount() {
        return pendingObjectArrayList.size();
    }

    public class TrendingHolder extends RecyclerView.ViewHolder {
        private CardView cardviewPending;
        private LinearLayout linearStrip;
        private TextView txtPending;
        private TfTextView txtBuzz;

        public TrendingHolder(View itemView) {
            super(itemView);
            cardviewPending = (CardView) itemView.findViewById(R.id.cardviewPending);
            txtPending = (TextView) itemView.findViewById(R.id.txtPending);
            txtBuzz = (TfTextView) itemView.findViewById(R.id.txtBuzz);
        }
    }

    public void addAll(ArrayList<PendingObject> matchDetails) {
        pendingObjectArrayList = new ArrayList<>();
        pendingObjectArrayList = matchDetails;
        notifyDataSetChanged();
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
