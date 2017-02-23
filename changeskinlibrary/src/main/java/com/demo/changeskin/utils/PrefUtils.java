package com.demo.changeskin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.changeskin.constant.SkinConfig;

/**
 * Created by Administrator on 2017/2/9.
 */

public class PrefUtils {

    private Context mContext;
    private SharedPreferences sp;

    public PrefUtils(Context context) {
        mContext = context;
        sp = mContext.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getPluginPath() {
        return sp.getString(SkinConfig.KEY_PLUGIN_PATH, "");
    }

    public String getSuffix() {
        return sp.getString(SkinConfig.KEY_PLUGIN_SUFFIX, "");
    }

    public String getPluginPackageName() {
        return sp.getString(SkinConfig.KEY_PLUGIN_PKG, "");
    }

    public void putPluginPath(String path) {
        sp.edit().putString(SkinConfig.KEY_PLUGIN_PATH, path).apply();
    }

    public void putSuffix(String suffix) {
        sp.edit().putString(SkinConfig.KEY_PLUGIN_SUFFIX, suffix).apply();
    }

    public void putPluginPackageName(String packageName) {
        sp.edit().putString(SkinConfig.KEY_PLUGIN_PKG, packageName).apply();
    }

    public void clear() {
        sp.edit().clear().apply();
    }
}
