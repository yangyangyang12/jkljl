package com.example.administrator.myapplication;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;



import java.io.File;
import java.util.ArrayList;

import Beansss.HotSearchBean;

import Beansss.SearchMoreBean;
import view.MyCustomRequest;
import view.UrlUtils;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ThreeFragment extends Fragment{


    private LinearLayout layout_three;
    private ViewPager pager;
    private RadioGroup radioGroup;
    private Button button;
    private RequestQueue queue;
    private ImageLoader loader;
    private Context context;
    //准备一个用于存储所有热门搜索数据的集合
    private ArrayList<HotSearchBean.DataBean> hotList = new ArrayList<>();

    //用于存储查看更多下方列表中的数据源
    private ArrayList<HotSearchBean.DataBean> searchList = new ArrayList<>();
    private ViewPager viewpager;
    private SearchListAdapter adapter;
    private ListView lv;

    private ArrayList<View> pagerDatas = new ArrayList<>();
    private ArrayList<SearchMoreBean.DataBean.TopicBean> moreList = new ArrayList<>();
    private SearchMorePagerAdapter searchMorePagerAdapter;

    private Handler handler = new Handler();
    private Runnable r;
    private boolean flag;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search,container,false);

    }
    private void initView(View view) {
        button = (Button)view.findViewById(R.id.button);
        layout_three = (LinearLayout) view.findViewById(R.id.hotsearch_layout);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        lv = (ListView) view.findViewById(R.id.lv_sousuo);
       // scrollView = (ScrollView) inflate.findViewById(R.id.);
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context=getActivity();
        initView(view);
        queue= Volley.newRequestQueue(context);
        initImageLoader();
        //加载热门搜索部分的数据
        initHotSearchData();
        
        //获取查看更多下方的列表中的数据
        initSearchListData();
        
        initListView();
        r = new Runnable() {
            @Override
            public void run() {
                viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
                handler.postDelayed(r,2000);
            }
        };
        initPagerData();
        initViewPager();
         viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                 radioGroup.check(position%pagerDatas.size());
             }

             @Override
             public void onPageScrollStateChanged(int state) {
                 switch(state){
                     case ViewPager.SCROLL_STATE_DRAGGING:
                         handler.removeCallbacks(r);
                         flag = true;
                         break;
                     case ViewPager.SCROLL_STATE_IDLE:
                         if (flag) {
                             handler.postDelayed(r,2000);
                             flag = false;
                         }
                         break;
                 }
             }
         });

    }

    private void initPagerData() {
        MyCustomRequest<SearchMoreBean> mcr = new MyCustomRequest<SearchMoreBean>(
                UrlUtils.SEARCHMOREURL,
                SearchMoreBean.class,
                new Response.Listener<SearchMoreBean>() {
                    @Override
                    public void onResponse(SearchMoreBean response) {
                        moreList.addAll(response.getData().getTopic());

                        int num = 0;
                        if (moreList.size()%2==0) {
                            num = moreList.size()/2;
                        } else {
                            num = moreList.size()/2+1;
                        }
                        for (int i = 0; i < num; i++) {
                            View v = LayoutInflater.from(context).inflate(R.layout.viewpager_item,null);
                            ImageView left = (ImageView)v.findViewById(R.id.pager_iv1);
                            ImageView right = (ImageView)v.findViewById(R.id.pager_iv2);

                            DisplayImageOptions opts = new DisplayImageOptions.Builder()
                                    .cacheOnDisk(true)
                                    .cacheInMemory(true)
                                    .showImageOnFail(R.mipmap.ic_launcher)
                                    .displayer(new RoundedBitmapDisplayer(30)).build();

                            loader.displayImage(moreList.get(i*2).getCover_path(),left,opts);
                            try {
                                loader.displayImage(moreList.get(i*2+1).getCover_path(),right,opts);
                            } catch (Exception e) {
                                right.setVisibility(View.GONE);
                            }
                            pagerDatas.add(v);
                        }
                        searchMorePagerAdapter.notifyDataSetChanged();

                        initRadioButtons(num);

                        handler.postDelayed(r,2000);
                    }
                },null);
        queue.add(mcr);
    }

    private void initSearchListData() {
        MyCustomRequest<HotSearchBean> mcr = new MyCustomRequest<HotSearchBean>(UrlUtils.SEARCHLISTURL,
                HotSearchBean.class,
                new Response.Listener<HotSearchBean>() {

                    @Override
                    public void onResponse(HotSearchBean response) {
                        searchList.addAll(response.getData());
                        adapter.notifyDataSetChanged();
                    }
                },null);
        queue.add(mcr);
    }

    private void initImageLoader() {

       loader=ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new LimitedAgeDiskCache(new File("/mnt/sdcard/ay1608"),24*3600))
                .memoryCache(new LruMemoryCache((int) (Runtime.getRuntime().maxMemory()/8)))
                .threadPoolSize(2)
                .build();
       loader.init(config);
    }
    private void initHotSearchData() {
        MyCustomRequest<HotSearchBean> mcr = new MyCustomRequest<HotSearchBean>(UrlUtils.HOTSEARCHURL,
                HotSearchBean.class,
                new Response.Listener<HotSearchBean>() {
                    @Override
                    public void onResponse(HotSearchBean response) {
                        hotList.addAll(response.getData());
                        //根据hotList的个数初始化横向滚动条中TextView的个数
                        initHorizontal();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(mcr);
    }
    private void initListView() {
        initListViewAdapter();
    }

    private void initListViewAdapter() {
        adapter = new SearchListAdapter(searchList,context,loader);

        lv.setAdapter(adapter);
        //以下代码的作用为解决也页面初始显示时ViewPager显示在屏幕以外的bug
        viewpager.setFocusable(true);
        lv.setFocusable(false);
    }
    private void initRadioButtons(int num) {
        for (int i = 0; i < num; i++) {
            RadioButton rb = new RadioButton(context);
            rb.setId(i);
            rb.setButtonDrawable(R.drawable.dian);

            radioGroup.addView(rb);
        }
        radioGroup.check(0);
    }

    private void initViewPager() {
        viewpager.setBackgroundColor(Color.BLUE);
        searchMorePagerAdapter = new SearchMorePagerAdapter(pagerDatas);

        viewpager.setAdapter(searchMorePagerAdapter);
    }

    private void initHorizontal() {
        for (int i = 0; i < hotList.size(); i++) {
            View hotView = LayoutInflater.from(context).inflate(R.layout.hotsearch_item,null);
            ImageView hotIv = (ImageView) hotView.findViewById(R.id.hot_iv);
            TextView hotTv = (TextView)hotView.findViewById(R.id.hot_tv);

            hotTv.setText(hotList.get(i).getKeyword());

            DisplayImageOptions opts = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .displayer(new RoundedBitmapDisplayer(30))
                    .build();
            //通过ImageLoader框架加载图片的显示
            loader.displayImage(hotList.get(i).getImgs().get(0),hotIv,opts);

            layout_three.addView(hotView);

        }


    }

}
