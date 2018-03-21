package com.cricket.cricketchallenge.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.api.responsepojos.UserModel;
import com.cricket.cricketchallenge.commonmodel.MobileContact;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.ui.BaseActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chintans on 14-12-2017.
 */

public class AppUserListAdapter extends RecyclerView.Adapter<AppUserListAdapter.TrendingHolder> {

    private BaseActivity baseActivity;
    private int lastPosition = -1;
    private int tempPosition = 0;
    private ArrayList<UserModel> pendingObjectArrayList;


    public AppUserListAdapter(BaseActivity baseActivity, ArrayList<UserModel> pendingObjectArrayList) {
        this.baseActivity = baseActivity;
        tempPosition = 0;
        this.pendingObjectArrayList = pendingObjectArrayList;
    }

    @Override
    public AppUserListAdapter.TrendingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(baseActivity).inflate(R.layout.row_item_add_invite, parent, false);
        return new TrendingHolder(view);
    }

    @Override
    public void onBindViewHolder(AppUserListAdapter.TrendingHolder holder, int position) {
        holder.tv_name_add_invite.setText(pendingObjectArrayList.get(position).getUserFullname());
        holder.tv_no_add_invite.setText(pendingObjectArrayList.get(position).getUserMobile());
        setAnimation(holder.llRowDetail, position);
        tempPosition = tempPosition + 1;

    }

    @Override
    public int getItemCount() {
        return pendingObjectArrayList.size();
    }

    public class TrendingHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_pic_add_invite;
        TfTextView tv_name_add_invite;
        TfTextView tv_no_add_invite;
        LinearLayout llRowDetail;
        TfTextView tvRowNoPrev;
        ImageView iv_right_icon_add_invite;
        UserModel datumContact;
        MobileContact mobileContact;

        public TrendingHolder(View itemView) {
            super(itemView);
            iv_pic_add_invite = (CircleImageView) itemView.findViewById(R.id.iv_pic_add_invite);
            tv_name_add_invite = (TfTextView) itemView.findViewById(R.id.tv_name_add_invite);
            llRowDetail = (LinearLayout) itemView.findViewById(R.id.llRowDetail);
            tvRowNoPrev = (TfTextView) itemView.findViewById(R.id.tvRowNoPrev);
            tv_no_add_invite = (TfTextView) itemView.findViewById(R.id.tv_no_add_invite);
            iv_right_icon_add_invite = (ImageView) itemView.findViewById(R.id.iv_right_icon_add_invite);
        }
    }

    public void addAll(ArrayList<UserModel> matchDetails) {
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
