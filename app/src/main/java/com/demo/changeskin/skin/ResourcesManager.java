package com.demo.changeskin.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ResourcesManager {

    private Resources mResources;
    private String mPkgName;

    public ResourcesManager(Resources resources, String pkgName) {
        this.mResources = resources;
        this.mPkgName = pkgName;
    }

    public Drawable getDrawableByResName(String name) {
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, "drawable", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByResName(String name) {
        try {
            return mResources.getColorStateList(mResources.getIdentifier(name, "color", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStringByResName(String name) {
        try {
            return mResources.getString(mResources.getIdentifier(name, "string", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
