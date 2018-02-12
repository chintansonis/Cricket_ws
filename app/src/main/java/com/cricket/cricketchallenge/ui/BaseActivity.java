package com.cricket.cricketchallenge.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.navigationDrawer.SlidingRootNav;
import com.cricket.cricketchallenge.fragment.BaseFragment;
import com.cricket.cricketchallenge.helper.Functions;

import java.util.Stack;


/**
 * Created by chintans on 20-11-2017.
 */

public class BaseActivity extends AppCompatActivity {
    private Stack<Fragment> fragmentBackStack;
    public int screenWidth, screenHeight;
    public static final int POS_HOME = 0;
    public static final int POS_PENDING_CHALLENGES = 1;
    public static final int POS_REQUESTED_CHALLENGES = 2;
    public static final int POS_ACCEPTED_CHALLENGES = 3;
    public static final int POS_INVITE_EARN = 4;
    public static final int POS_HOW_TO_PLAY = 5;
    public static final int POS_LOGOUT = 6;

    private boolean showBackMessage = true;
    private Context context;
    public SlidingRootNav slidingRootNav;
    private DrawerLayout drawerLayout;
    private boolean doubleBackToExitPressedOnce;

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setShowBackMessage(boolean showBackMessage) {
        this.showBackMessage = showBackMessage;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }


    public Stack<Fragment> getFragments() {
        return fragmentBackStack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentBackStack = new Stack<>();
        getWidthAndHeight();
        context = this;
    }

    /**
     * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to show, in tab identified by tag shouldAnimate ->
     * should animate transaction. false when we switch tabs, or adding first fragment to a tab true when when we are pushing more
     * fragment into navigation stack. shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)). false when we are
     * switching tabs (except for the first time) true in all other cases.
     *
     * @param fragment      the fragment
     * @param shouldAnimate the should animate
     * @param shouldAdd     the should add
     */
    public synchronized void pushAddFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {
        if (fragment != null) {
            fragmentBackStack.push(fragment);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            Fragment currentFragmentForAdd = fragmentBackStack.get(fragmentBackStack.size() - 1);
            ft.replace(R.id.container, fragment, String.valueOf(fragmentBackStack.size()));
            ft.commit();
            manager.executePendingTransactions();
        }
    }

    /**
     * Select the second last fragment in current tab's stack.. which will be shown after the fragment transaction given below
     *
     * @param shouldAnimate the should animate
     */
    public synchronized void popFragments(boolean shouldAnimate) {
        Fragment fragment = null;
        if (fragmentBackStack.size() > 1) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 2);
        } else if (!fragmentBackStack.isEmpty()) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 1);
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (fragment != null) {
            if (fragment.isAdded()) {
                ft.remove(fragmentBackStack.elementAt(fragmentBackStack.size() - 1));
                if (fragmentBackStack.size() > 1) {
                    ft.show(fragment).commit();
                }
            } else {
                ft.replace(R.id.container, fragment).commit();
            }
            fragmentBackStack.pop();
            manager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (fragmentBackStack.size() <= 1) {
            if (showBackMessage) {
                doubleTapOnBackPress();
            } else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        } else {
            if (!((BaseFragment) fragmentBackStack.get(fragmentBackStack.size() - 1)).onFragmentBackPress()) {
                Fragment currentFragment = fragmentBackStack.get(fragmentBackStack.size() - 1);
                /*if (currentFragment instanceof NewsFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof AnnounceMentFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof EventFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof ArtistsFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof DashBoardFragment) {
                    doubleTapOnBackPress();
                } else {*/
                popFragments(true);
                /*}*/
            }
        }
    }

    /**
     * this method load dashboard fragment
     */
    /*public void loadHomeFragment() {
        setHeaderTitle(getString(R.string.tv_home_header_text));
        loadBottomUI(AppConstants.HOME);
        getFragments().clear();
        Fragment fragmentToPush = DashBoardFragment.getFragment(this);
        pushAddFragments(fragmentToPush, true, true);
    }*/

    /**
     * method handle for show notification for close the application & Stop app to Close when there is no any remaining Fragment
     * in Stack and User press Back Press
     */
    void doubleTapOnBackPress() {
        if (doubleBackToExitPressedOnce) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Functions.showToast(this, getResources().getString(R.string.click_back_to_exit));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 10000);
    }


    /**
     * Get device screen width and height
     */
    public void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }
}
