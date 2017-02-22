package com.demo.changeskin.attr;

import android.view.View;

/**
 * Created by Administrator on 2017/2/21.
 */

public class SkinAttr {

    private String resName;
    private SkinAttrType attrType;

    public SkinAttr(String resName, SkinAttrType attrType) {
        this.resName = resName;
        this.attrType = attrType;
    }

    public void apply(View view) {
        attrType.apply(view, resName);
    }
}
