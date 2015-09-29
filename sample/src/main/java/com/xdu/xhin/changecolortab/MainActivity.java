package com.xdu.xhin.changecolortab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.xdu.xhin.library.view.ChangeColorTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhinliang on 15-9-28.
 * xhinliang@gmail.com
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Fragment> mTabFragments = new ArrayList<>();
    private ChangeColorTab changeColorTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    //FindView
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        changeColorTab = (ChangeColorTab) findViewById(R.id.change_color_tab);
    }

    //初始化所有事件
    private void initEvent() {
        changeColorTab.setViewpager(mViewPager);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    //初始化四个Fragment
    private void initData() {
        String[] mTitles = {"First Fragment !", "Second Fragment !", "Third Fragment !", "Fourth Fragment !"};
        for (String title : mTitles) {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE, title);
            tabFragment.setArguments(bundle);
            mTabFragments.add(tabFragment);
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabFragments.get(position);
            }
        });
    }




}