package com.cricket.cricketchallenge.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.cricket.cricketchallenge.helper.Functions;

public class TfButton extends android.support.v7.widget.AppCompatButton {

    private Context _ctx;

    public TfButton(Context context) {
        super(context);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    public TfButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    private void init() {
        try {
            // setTypeface(Functions.getLatoFont(_ctx));
            setTypeface(Functions.getFontType(_ctx, FontType.SemiBold.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
