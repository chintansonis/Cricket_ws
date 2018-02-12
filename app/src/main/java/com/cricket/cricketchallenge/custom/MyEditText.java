package com.cricket.cricketchallenge.custom;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.cricket.cricketchallenge.R;


public class MyEditText extends AppCompatEditText {
    private final Context context;
    private final Drawable imgCloseButton = getResources().getDrawable(R.drawable.ic_close_icon);


    public MyEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();

    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    void init() {
        setPadding(15, 15, 15, 15);
        imgCloseButton.setBounds(0, 0, imgCloseButton.getIntrinsicWidth(),
                imgCloseButton.getIntrinsicHeight());
        //setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Dosis-Regular.otf")/*, -1*/);
        handleClearButton();
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                MyEditText et = MyEditText.this;

                if (et.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > et.getWidth() - et.getPaddingRight()
                        - imgCloseButton.getIntrinsicWidth()) {
                    et.setText("");
                    MyEditText.this.handleClearButton();
                }
                return false;
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                MyEditText.this.handleClearButton();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });
    }

    void handleClearButton() {
        if (this.getText().toString().equals("")) {
            this.setCompoundDrawables(this.getCompoundDrawables()[0],
                    this.getCompoundDrawables()[1], null,
                    this.getCompoundDrawables()[3]);
        } else {
            this.setCompoundDrawables(this.getCompoundDrawables()[0],
                    this.getCompoundDrawables()[1], imgCloseButton,
                    this.getCompoundDrawables()[3]);

        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (this.getText().toString().equals("")) {
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
        } else {
            if (focused) {
                this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgCloseButton, this.getCompoundDrawables()[3]);
            } else {
                this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
            }

        }

    }
}