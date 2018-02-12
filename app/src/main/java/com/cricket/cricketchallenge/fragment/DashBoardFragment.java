package com.cricket.cricketchallenge.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.helper.Functions;
import com.cricket.cricketchallenge.ui.BaseActivity;
import com.cricket.cricketchallenge.ui.CricketChallengeActivity;
import com.cricket.cricketchallenge.ui.TrendingChallengesActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by chintans on 04-12-2017.
 */

public class DashBoardFragment extends BaseFragment {
    private AdView mAdView;

    @SuppressLint({"ValidFragment", "Unused"})
    private DashBoardFragment() {
    }

    @SuppressLint("ValidFragment")
    private DashBoardFragment(BaseActivity activity) {
        setBaseActivity(activity);
    }

    public static BaseFragment getFragment(BaseActivity activity) {
        BaseFragment fragment = new DashBoardFragment(activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActivity((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init(view);
        getBaseActivity().getWidthAndHeight();
    }

    private void init(View view) {
        view.findViewById(R.id.linearTrending).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(getBaseActivity(), TrendingChallengesActivity.class);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        view.findViewById(R.id.linearCricket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(getBaseActivity(), CricketChallengeActivity.class);
                getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        mAdView = (AdView) view.findViewById(R.id.adView);
        //loadAd();
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

}
