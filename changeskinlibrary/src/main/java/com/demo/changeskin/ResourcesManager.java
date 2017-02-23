package com.demo.changeskin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ResourcesManager {

    private Resources mResources;
    private String mPkgName;
    //应用内换肤，通过后缀名称区分资源
    private String mSuffix;

    public ResourcesManager(Resources resources, String pkgName, String suffix) {
        this.mResources = resources;
        this.mPkgName = pkgName;
        if (suffix == null) {
            mSuffix = "";
        } else {
            mSuffix = suffix;
        }
    }

    public Drawable getDrawableByResName(String name) {
        name = appendSuffix(name);
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, "drawable", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByResName(String name) {
        name = appendSuffix(name);
        try {
            return mResources.getColorStateList(mResources.getIdentifier(name, "color", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStringByResName(String name) {
        name = appendSuffix(name);
        try {
            return mResources.getString(mResources.getIdentifier(name, "string", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix)) {
            name += "_" + mSuffix;
        }
        return name;
    }
}
