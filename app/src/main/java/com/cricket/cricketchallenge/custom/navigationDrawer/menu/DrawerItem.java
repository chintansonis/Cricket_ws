package com.cricket.cricketchallenge.custom.navigationDrawer.menu;

import android.view.ViewGroup;

public abstract class DrawerItem<T extends DrawerAdapter.ViewHolder> {

    protected boolean isChecked;

    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder);



    public boolean isSelectable() {
        return true;
    }

}
