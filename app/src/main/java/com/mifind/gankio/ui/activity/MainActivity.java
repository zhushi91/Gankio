package com.mifind.gankio.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mifind.gankio.GankApp;
import com.mifind.gankio.R;
import com.mifind.gankio.conf.Conf;
import com.mifind.gankio.http.ICallBack;
import com.mifind.gankio.http.RequestManager;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.fragment.MainFragment;
import com.mifind.gankio.utils.PreUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.xiaopan.android.widget.ToastUtils;

public class MainActivity extends BaseActivity implements OnNavigationItemSelectedListener {

    private int MAIN_INDEX;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    public void initParms(Bundle parms) {
        MAIN_INDEX = PreUtils.getInt(GankApp.getContext(), "MAIN_INDEX", 1);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void setListener() {
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void doBusiness(final Context mContext) {
        //获取首页列表
        requestMain();
    }

    private void requestMain() {
        RequestManager.getInstance().debug("request").get("all", Conf.RequestAll(50, MAIN_INDEX), true, new ICallBack<List<GankModel>>() {

            @Override
            public void onSuccess(List<GankModel> result) {
                setDefaultFragment(result);
            }

            @Override
            public void onFailure(String message) {
                Logger.i("onFailure :" + message);
                ToastUtils.toastS(mContext,message);
            }
        });
    }


    private void setDefaultFragment(List<GankModel> result) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment mainFragment = new MainFragment(mContext, result);
        transaction.replace(R.id.fl_main_layout, mainFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.action_theme) {

            return true;
        }
        if (id == R.id.action_about_app) {

            return true;
        }
        if (id == R.id.action_about_me) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", Conf.RequestBlog());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_all) {

        } else if (id == R.id.nav_gift) {
            Intent intent = new Intent(this,FuLiActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_android) {

        } else if (id == R.id.nav_ios) {

        } else if (id == R.id.nav_rest) {

        } else if (id == R.id.nav_html) {

        } else if (id == R.id.nav_expand) {

        } else if (id == R.id.nav_recommend) {

        }
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

}
