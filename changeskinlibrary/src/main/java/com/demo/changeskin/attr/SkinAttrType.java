package com.demo.changeskin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.changeskin.ResourcesManager;

/**
 * Created by Administrator on 2017/2/21.
 */

public enum SkinAttrType {

    BACKGROUND("background") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourcesManager().getDrawableByResName(resName);
            if (drawable == null) {
                return;
            }
            view.setBackgroundDrawable(drawable);
        }
    },
    COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof TextView) {
                ColorStateList colorStateList = getResourcesManager().getColorByResName(resName);
                if (colorStateList == null) {
                    return;
                }
                ((TextView) view).setTextColor(colorStateList);
            }
        }
    },
    SRC("src") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof ImageView) {
                Drawable drawable = getResourcesManager().getDrawableByResName(resName);
                if (drawable == null) {
                    return;
                }
                ((ImageView) view).setImageDrawable(drawable);
            }
        }
    };

    private String attrType;

    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }

    public abstract void apply(View view, String resName);

    ResourcesManager getResourcesManager() {
        return null;
    }
}
