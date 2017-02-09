package com.demo.changeskin;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.changeskin.skin.ResourcesManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private TextView mTextView;
    private ImageView mImageView;
    private Button startTestFactoryBtn;

    private String mSkinPluginPath = Environment.getExternalStorageDirectory() + File.separator + "Skin_Plugin.apk";
    private String mSkinPluginPackage = "com.demo.skinplugin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvents();
    }

    private void initEvents() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rigthScale = 0.8f + scale * 0.2f;
                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;
                    mMenu.setScaleX(leftScale);
                    mMenu.setScaleY(leftScale);
                    mMenu.setAlpha(0.6f + 0.4f * (1 - scale));

                    mContent.setTranslationX(mContent.getMeasuredWidth() * (1 - scale));
                    mContent.setPivotX(0);
                    mContent.setPivotY(mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    mContent.setScaleX(rigthScale);
                    mContent.setScaleY(rigthScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        startTestFactoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestFactoryActivity.class));
            }
        });
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mTextView = (TextView) findViewById(R.id.id_text_view);
        mImageView = (ImageView) findViewById(R.id.id_imageView);
        startTestFactoryBtn = (Button) findViewById(R.id.id_start_test_factory);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.id_left_menu_container);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.id_left_menu_container, new MenuLeftFragment());
            ft.commit();
        }
    }

    public void resetDefault() {
        mImageView.setImageResource(R.mipmap.ic_launcher);
    }

    public void loadPlagin() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, mSkinPluginPath);

            Resources superResources = getResources();
            Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());
            ResourcesManager resourcesManager = new ResourcesManager(resources, mSkinPluginPackage);
            Drawable drawable = resourcesManager.getDrawableByResName("skin_background");
            if (drawable != null) {
                mImageView.setImageDrawable(drawable);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
