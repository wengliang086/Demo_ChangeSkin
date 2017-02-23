package com.demo.changeskin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.demo.changeskin.attr.SkinView;
import com.demo.changeskin.callback.ISkinChangedListener;
import com.demo.changeskin.callback.ISkinChangingCallback;
import com.demo.changeskin.utils.PrefUtils;

import java.io.File;
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

    /**
     * 换肤资源后缀
     */
    private String mSuffix = "";
    private String mCurPluginPath;
    private String mCurPluginPkg;
    private PrefUtils mPrefUtils;

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
        mPrefUtils = new PrefUtils(context);
        try {
            String pluginPkg = mPrefUtils.getPluginPackageName();
            String pluginPath = mPrefUtils.getPluginPath();
            String suffix = mPrefUtils.getSuffix();
            if (new File(pluginPath).exists()) {
                loadPlagin(pluginPath, pluginPkg, suffix);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mPrefUtils.clear();
        }
    }

    private void loadPlagin(String skinPath, String skinPkgName, String suffix) throws Exception {
        if (skinPath.equals(mCurPluginPath) && skinPkgName.equals(mCurPluginPkg)) {
            return;
        }
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPathMethod.invoke(assetManager, skinPath);

        Resources superResources = mContext.getResources();
        Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());
        resourcesManager = new ResourcesManager(resources, skinPkgName);

        mCurPluginPath = skinPath;
        mCurPluginPkg = skinPkgName;
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
        List<SkinView> skinViews = getSkinViews(listener);
        if (skinViews != null) {
            for (SkinView skinView : skinViews) {
                skinView.apply();
            }
        }
    }

    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, ISkinChangingCallback skinChangingCallback) {
        if (skinChangingCallback == null) {
            skinChangingCallback = ISkinChangingCallback.DEFAULT_SKIN_CHANGING_CALLBACK;
        }
        final ISkinChangingCallback callback = skinChangingCallback;
        callback.onStart();
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    loadPlagin(skinPluginPath, skinPluginPkg, "");
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
                return 1;
            }

            @Override
            protected void onPostExecute(Integer code) {
                try {
                    if (code == 0) {
                        callback.onError(null);
                        return;
                    }
                    //执行真正的换肤操作
                    notifyChangeListeners();
                    updatePluginInfo(skinPluginPath, skinPluginPkg, "");
                    callback.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
            }
        }.execute();
    }

    private void updatePluginInfo(String skinPluginPath, String skinPluginPkg, String suffix) {
        mPrefUtils.putPluginPath(skinPluginPath);
        mPrefUtils.putPluginPackageName(skinPluginPkg);
        mPrefUtils.putSuffix(suffix);
        mCurPluginPkg = skinPluginPkg;
        mCurPluginPath = skinPluginPath;
        mSuffix = suffix;
    }

    private void notifyChangeListeners() {
        for (ISkinChangedListener listener : mSkinChangedListeners) {
            listener.onSkinChanged();
        }
    }

    public boolean needChangeSkin() {
        return !TextUtils.isEmpty(mCurPluginPath);
    }
}
