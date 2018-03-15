package com.cricket.cricketchallenge.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.api.responsepojos.MatchDetail;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;
import com.cricket.cricketchallenge.ui.BaseActivity;
import com.cricket.cricketchallenge.ui.MatchDetailActivity;

import java.util.ArrayList;

/**
 * Created by chintans on 14-12-2017.
 */

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.TrendingHolder> {

    private BaseActivity baseActivity;
    private int lastPosition = -1;
    private int tempPosition = 0;
    private ArrayList<MatchDetail> matchDetailArrayList;


    public MatchListAdapter(BaseActivity baseActivity, ArrayList<MatchDetail> matchDetails) {
        this.baseActivity = baseActivity;
        tempPosition = 0;
        this.matchDetailArrayList = matchDetails;
    }

    @Override
    public MatchListAdapter.TrendingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(baseActivity).inflate(R.layout.item_match_list, parent, false);
        return new TrendingHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchListAdapter.TrendingHolder holder, int position) {
        setAnimation(holder.cardviewMatchList, position);
        tempPosition = tempPosition + 1;
        if (tempPosition >= AppConstants.COLORS_PRIMARY.length) {
            tempPosition = 0;
        }
        holder.linearStrip.setBackgroundColor(Color.parseColor(AppConstants.COLORS_PRIMARY[tempPosition]));
        holder.cardviewMatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(baseActivity, MatchDetailActivity.class);
                baseActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        holder.txtTeam1.setText(matchDetailArrayList.get(position).getTeam1Name());
        holder.txtTeam2.setText(matchDetailArrayList.get(position).getTeam2Name());
        holder.txtMatchDate.setText(matchDetailArrayList.get(position).getMatchDate());
        holder.txtMatchTime.setText(matchDetailArrayList.get(position).getMatchTime());
    }

    @Override
    public int getItemCount() {
        return matchDetailArrayList.size();
    }

    public class TrendingHolder extends RecyclerView.ViewHolder {
        private CardView cardviewMatchList;
        private LinearLayout linearStrip;
        private TfTextView txtMatchDate;
        private TfTextView txtTeam1;
        private TfTextView txtTeam2;
        private TfTextView txtMatchTime;

        public TrendingHolder(View itemView) {
            super(itemView);
            cardviewMatchList = (CardView) itemView.findViewById(R.id.cardviewMatchList);
            linearStrip = (LinearLayout) itemView.findViewById(R.id.linearStrip);
            txtMatchDate = (TfTextView) itemView.findViewById(R.id.txtMatchDate);
            txtMatchTime = (TfTextView) itemView.findViewById(R.id.txtMatchTime);
            txtTeam1 = (TfTextView) itemView.findViewById(R.id.txtTeam1);
            txtTeam2 = (TfTextView) itemView.findViewById(R.id.txtTeam2);

        }
    }

    public void addAll(ArrayList<MatchDetail> matchDetails) {
        matchDetailArrayList = new ArrayList<>();
        matchDetailArrayList = matchDetails;
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
