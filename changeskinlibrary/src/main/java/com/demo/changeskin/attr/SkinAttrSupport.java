package com.demo.changeskin.attr;

import android.content.Context;
import android.util.AttributeSet;

import com.demo.changeskin.constant.SkinConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class SkinAttrSupport {

    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            SkinAttrType attrType = getSupportAttrType(attrName);
            if (attrType == null) {
                continue;
            }
            if (attrValue.startsWith("@")) {
                int id = -1;
                try {
                    id = Integer.parseInt(attrValue.substring(1));
                } catch (NumberFormatException e) {
                }
                if (id == -1) {
                    continue;
                }
                String entryName = context.getResources().getResourceEntryName(id);
                if (entryName.startsWith(SkinConfig.ATTR_PREFIX)) {
                    SkinAttr skinAttr = new SkinAttr(entryName, attrType);
                    skinAttrs.add(skinAttr);
                }
            }
        }
        return skinAttrs;
    }

    private static SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getAttrType().equals(attrName)) {
                return attrType;
            }
        }
        return null;
    }
}
