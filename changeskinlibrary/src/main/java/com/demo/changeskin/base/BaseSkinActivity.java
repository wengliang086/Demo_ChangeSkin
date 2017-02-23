package com.demo.changeskin.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.demo.changeskin.SkinManager;
import com.demo.changeskin.attr.SkinAttr;
import com.demo.changeskin.attr.SkinAttrSupport;
import com.demo.changeskin.attr.SkinView;
import com.demo.changeskin.callback.ISkinChangedListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/21.
 */

public class BaseSkinActivity extends AppCompatActivity implements ISkinChangedListener {

    static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap<>();
    private final Object[] mConstructorArgs = new Object[2];

    private static Method sCreateViewMethod;
    static final Class<?>[] sCreateViewSignature = new Class[]{View.class, String.class, Context.class, AttributeSet.class};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                //系统有没有使用setFactory
                //完成appCompat factory的工作
                AppCompatDelegate delegate = getDelegate();
                View view = null;
                try {
                    if (sCreateViewMethod == null) {
                        sCreateViewMethod = delegate.getClass().getMethod("createView", sCreateViewSignature);
                    }
                    view = (View) sCreateViewMethod.invoke(delegate, parent, name, context, attrs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (view == null) {
                    view = createViewFromTag(context, name, attrs);
                }
                if (view != null) {
                    List<SkinAttr> skinAttrList = SkinAttrSupport.getSkinAttrs(attrs, context);
                    injectSkin(view, skinAttrList);
                }
                return view;
            }
        });

        super.onCreate(savedInstanceState);
        SkinManager.getInstance().registerListener(this);
    }

    private void injectSkin(View view, List<SkinAttr> attrs) {
        if (attrs.size() > 0) {
            List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
            if (skinViews == null) {
                skinViews = new ArrayList<>();
                SkinManager.getInstance().addSkinView(this, skinViews);
            }
            skinViews.add(new SkinView(view, attrs));
        }
        //当前是否需要换肤，根据上次状态的存储
        if (SkinManager.getInstance().needChangeSkin()) {
            SkinManager.getInstance().apply(this);
        }
    }

    @Override
    public void onSkinChanged() {
        SkinManager.getInstance().apply(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unRegisterListener(this);
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                // try the android.widget prefix first...
                return createView(context, name, "android.widget.");
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }
}
