package com.cricket.cricketchallenge.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.custom.navigationDrawer.SlidingRootNavBuilder;
import com.cricket.cricketchallenge.custom.navigationDrawer.menu.DrawerAdapter;
import com.cricket.cricketchallenge.custom.navigationDrawer.menu.DrawerItem;
import com.cricket.cricketchallenge.custom.navigationDrawer.menu.SimpleItem;
import com.cricket.cricketchallenge.custom.navigationDrawer.menu.SpaceItem;
import com.cricket.cricketchallenge.fragment.DashBoardFragment;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.services.SyncContactsService;

import java.util.Arrays;

/**
 * Created by chintans on 30-11-2017.
 */

public class DashboardActivity extends BaseActivity implements DrawerAdapter.OnItemSelectedListener, View.OnClickListener {
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private static DashboardActivity dashboardActivity;
    private Toolbar toolbar;
    public static TfTextView txtTitle;
    MenuItem pointItem;
//First Commit


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        initDrawerMenu(savedInstanceState);
        selectItem(0);
    }

    private void init() {
        initToolbar();
        findViewById(R.id.container).setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startContactSync();
        } else {
            startContactSync();
        }
    }

    private void setPermission() {
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck2 == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 01);

        }
        int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck3 == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1234);
        }

    }

    private void startContactSync() {
        int permissionCheckcontact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permissionCheckcontact == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(DashboardActivity.this, SyncContactsService.class);
            startService(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, AppConstants.CONTACT_PERMISSION_CODE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.header_dashboard));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingRootNav.closeMenu();
            }
        });
        this.setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);
    }

    private void initDrawerMenu(Bundle savedInstanceState) {
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.left_drawer)
                .inject();
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                new SpaceItem(1),
                createItemFor(POS_PENDING_CHALLENGES),
                new SpaceItem(1),
                createItemFor(POS_REQUESTED_CHALLENGES),
                new SpaceItem(1),
                createItemFor(POS_ACCEPTED_CHALLENGES),
                new SpaceItem(1),
                createItemFor(POS_INVITE_EARN),
                new SpaceItem(1),
                createItemFor(POS_HOW_TO_PLAY),
                new SpaceItem(1),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.menuList);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(POS_HOME);
    }

    private void selectItem(int position) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getFragments().clear();
        if (position == 0) {
            Fragment fragmentToPush = DashBoardFragment.getFragment(this);
            pushAddFragments(fragmentToPush, false, true);
        }
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }


    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        Drawable drawable = menu.findItem(R.id.menu_noti).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
        menu.findItem(R.id.menu_noti).setIcon(drawable);
        return true;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onItemSelected(int position) {
        slidingRootNav.closeMenu();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container:
                slidingRootNav.closeMenu();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        setDashboardActivity(null);
        super.onDestroy();
    }

    public static void setHeaderTitle(String title) {
        txtTitle.setText(title);
    }

    public static void setDashboardActivity(DashboardActivity homeActivity) {
        DashboardActivity.dashboardActivity = homeActivity;
    }

    public static DashboardActivity getDashboardActivity() {
        return dashboardActivity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppConstants.CONTACT_PERMISSION_CODE:
                if (requestCode == AppConstants.CONTACT_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(DashboardActivity.this, SyncContactsService.class);
                    startService(intent);
                } else {
                    startContactSync();
                }
                break;
        }
    }
}
