package com.demo.changeskin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.demo.changeskin.attr.SkinView;
import com.demo.changeskin.callback.ISkinChangedListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/9.
 */

public class SkinManager {

    private static SkinManager sInstance;
    private Context mContext;
    private ResourcesManager resourcesManager;

    private List<ISkinChangedListener> mSkinChangedListeners = new ArrayList<>();
    //存放多个activity需要更换皮肤
    private Map<ISkinChangedListener, List<SkinView>> mSkinViewMaps = new HashMap<>();

    public static SkinManager getInstance() {
        if (sInstance == null) {
            synchronized (SkinManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    private SkinManager() {
    }

    public ResourcesManager getResourcesManager() {
        return resourcesManager;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
//        loadPlagin(context);
    }

    private void loadPlagin(String skinPath, String skinPkgName, String suffix) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPathMethod.invoke(assetManager, skinPath);

        Resources superResources = mContext.getResources();
        Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());
        resourcesManager = new ResourcesManager(resources, skinPkgName);
    }

    public List<SkinView> getSkinViews(ISkinChangedListener listener) {
        return mSkinViewMaps.get(listener);
    }

    public void addSkinView(ISkinChangedListener listener, List<SkinView> views) {
        mSkinViewMaps.put(listener, views);
    }

    public void registerListener(ISkinChangedListener listener) {
        mSkinChangedListeners.add(listener);
    }

    public void unRegisterListener(ISkinChangedListener listener) {
        mSkinChangedListeners.remove(listener);
        mSkinViewMaps.remove(listener);
    }

    public void apply(ISkinChangedListener listener) {

    }
}
