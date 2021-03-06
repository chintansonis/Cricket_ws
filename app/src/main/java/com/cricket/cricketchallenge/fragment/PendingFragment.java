package com.cricket.cricketchallenge.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.adapters.PendingListAdapter;
import com.cricket.cricketchallenge.api.RestClient;
import com.cricket.cricketchallenge.api.responsepojos.PendingObject;
import com.cricket.cricketchallenge.api.responsepojos.ResponsePending;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;
import com.cricket.cricketchallenge.helper.Preferences;
import com.cricket.cricketchallenge.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shriji on 3/18/2018.
 */

public class PendingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private android.support.v4.widget.SwipeRefreshLayout swipeContainer;
    private android.support.v7.widget.RecyclerView rvPending;
    private com.cricket.cricketchallenge.custom.TfTextView txtNoData;
    private ArrayList<PendingObject> pendingObjectArrayList=new ArrayList<>();
    private PendingListAdapter pendingListAdapter;

    @SuppressLint({"ValidFragment", "Unused"})
    private PendingFragment() {

    }

    @SuppressLint("ValidFragment")
    private PendingFragment(BaseActivity activity) {
        setBaseActivity(activity);
    }

    public static BaseFragment getFragment(BaseActivity activity) {
        BaseFragment fragment = new PendingFragment(activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActivity((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending_challenges, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init(view);
        if (Functions.isConnected(getBaseActivity())) {
            getPendingListApi();
        } else {
          checkvisibility(pendingObjectArrayList);
            Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
        }
        getBaseActivity().getWidthAndHeight();
    }
    private void checkvisibility(ArrayList<PendingObject> pendingObjectArrayList) {
        if(pendingObjectArrayList.size()>0){
            swipeContainer.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        }else {
            swipeContainer.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }
    private void getPendingListApi() {
        getBaseActivity().showProgressDialog(false);
        RestClient.get().getPendingList(Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_USER_ID)).enqueue(new Callback<List<ResponsePending>>() {
            @Override
            public void onResponse(Call<List<ResponsePending>> call, Response<List<ResponsePending>> response) {
                getBaseActivity().hideProgressDialog();
                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == AppConstants.ResponseSuccess) {
                        pendingObjectArrayList.addAll(response.body().get(0).getData());
                        pendingListAdapter.addAll(pendingObjectArrayList);
                        checkvisibility(pendingObjectArrayList);
                    } else {
                        checkvisibility(pendingObjectArrayList);

                    }
                } else {
                    checkvisibility(pendingObjectArrayList);
                    Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<List<ResponsePending>> call, Throwable t) {
                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
                checkvisibility(pendingObjectArrayList);
                getBaseActivity().hideProgressDialog();
                Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
            }
        });

    }

    private void init(View view) {
        txtNoData = (TfTextView) view.findViewById(R.id.txtNoData);
        rvPending = (RecyclerView) view.findViewById(R.id.rvPending);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(false);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        setAdapter();
    }

    private void setAdapter() {
        rvPending.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        pendingListAdapter = new PendingListAdapter(getBaseActivity(), pendingObjectArrayList);
        rvPending.setAdapter(pendingListAdapter);
    }

    @Override
    public void onRefresh() {
        if (Functions.isConnected(getBaseActivity())) {
            getPendingListApi();
        } else {
            swipeContainer.setRefreshing(false);
            Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
        }
    }
}
