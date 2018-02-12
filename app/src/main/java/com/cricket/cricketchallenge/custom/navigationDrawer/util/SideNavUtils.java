package com.cricket.cricketchallenge.custom.navigationDrawer.util;

public abstract class SideNavUtils {

    public static float evaluate(float fraction, float startValue, float endValue) {
        return startValue + fraction * (endValue - startValue);
    }
}
