package com.mypooja.androidapp.Common.View;

import android.graphics.drawable.Drawable;

/**
 * Created by poliveira on 24/10/2014.
 */
public class NavigationItem {
    private int mId;
    private String mText;
    private Drawable mDrawable;
    private Drawable mDrawableSelected;
    private boolean mAddSeparatorBefore;

    public NavigationItem(int id, String text, Drawable drawable, Drawable drawableSel, boolean addSep) {
        mId = id;
        mText = text;
        mDrawable = drawable;
        mDrawableSelected = drawableSel;
        mAddSeparatorBefore = addSep;
    }

    public String getText() {
        return mText;
    }

    public int GetId () { return mId; }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public Drawable getDrawableSelected() {
        return mDrawableSelected;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public void setDrawableSelected(Drawable drawable) {
        mDrawableSelected = drawable;
    }
    public boolean addSeparatorBefore () {
        return mAddSeparatorBefore;
    }
}
