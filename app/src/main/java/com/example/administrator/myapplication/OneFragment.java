package com.example.administrator.myapplication;


import android.os.Bundle;



import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.ArrayList;

import view.MyListView;

import static android.support.design.widget.TabLayout.GRAVITY_CENTER;
import static android.support.design.widget.TabLayout.GRAVITY_FILL;

/**
 * Created by Administrator on 2016/10/25.
 */
public class OneFragment extends Fragment{
    private TabLayout layout;
    private ViewPager pager;
    private ArrayList<Fragment> list=new ArrayList<>();
    private  MyAdapter adapter;
    private String[]name={"最新","热门","随即"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.r_tuijian,container,false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();

        initAdapter();
        layout.setTabMode(TabLayout.MODE_FIXED);
        layout.setupWithViewPager(pager);

        layout.setTabGravity(GRAVITY_FILL);
        
    }

    private void initView(View view) {
        layout= (TabLayout) view.findViewById(R.id.tab_layout);
        pager= (ViewPager) view.findViewById(R.id.tuijian_pager);
    }

    private void initAdapter() {
        adapter=new MyAdapter(getFragmentManager());
        pager.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i <3 ; i++) {
           list.add(view.MyListFragment.newInstance("内容"+i+""));
        }
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
          return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name[position];
        }
    }


}
