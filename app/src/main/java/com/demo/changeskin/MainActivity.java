package com.demo.changeskin;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private String mSkinPluginPath = Environment.getExternalStorageDirectory() + File.separator + "Skin_Plugin.apk";
    private String[] mDatas = new String[]{"Activity", "Service", "Activity", "Service", "Activity", "Service", "Activity", "Service"};

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
        ListView listView = (ListView) findViewById(R.id.id_listview);
        listView.setAdapter(new ArrayAdapter<String>(this, -1, mDatas) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.id_tv_title);
                tv.setText(getItem(position));
                return convertView;
            }
        });
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.id_left_menu_container);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.id_left_menu_container, new MenuLeftFragment());
            ft.commit();
        }
    }

//    public void loadPlagin() {
//        try {
//            AssetManager assetManager = AssetManager.class.newInstance();
//            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
//            addAssetPathMethod.invoke(assetManager, mSkinPluginPath);
//
//            Resources superResources = getResources();
//            Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());
//            ResourcesManager resourcesManager = new ResourcesManager(resources, mSkinPluginPackage);
//            Drawable drawable = resourcesManager.getDrawableByResName("skin_background");
//            if (drawable != null) {
//                mImageView.setImageDrawable(drawable);
//            }
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.id_action_plugin_change:
                Toast.makeText(MainActivity.this, "id_action_plugin_change", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_action_default:
                Toast.makeText(MainActivity.this, "id_action_default", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_action_test_res:
                Toast.makeText(MainActivity.this, "id_action_test_res", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
