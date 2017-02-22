package com.demo.changeskin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class SkinView {

    private View view;
    private List<SkinAttr> attrs;

    public SkinView(View view, List<SkinAttr> attrs) {
        this.view = view;
        this.attrs = attrs;
    }

    public void apply() {
        if (view != null) {
            for (SkinAttr attr : attrs) {
                attr.apply(view);
            }
        }
    }
}
