package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;

import Beansss.TuijianBean;
import view.MyCustomRequest;
import view.UrlUtils;


/**
 * Created by Administrator on 2016/10/27.
 */
public class Tuijian_one extends Fragment{
    private GridView gridview;
    private MyAdapter adapter;
    private ImageLoader iloader;
    private RequestQueue queue;
    private ArrayList<TuijianBean.DataBean.WallpaperListInfoBean> list_tuijian=new ArrayList<TuijianBean.DataBean.WallpaperListInfoBean>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        queue= Volley.newRequestQueue(getActivity());
        initLoader();
        initData();



    }

    private void initLoader() {
        iloader=ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .diskCache(new LimitedAgeDiskCache(new File("/mnt/sdcard/ay1608"),24*3600))
                .memoryCache(new LruMemoryCache((int) (Runtime.getRuntime().maxMemory()/8)))
                .threadPoolSize(2)
                .build();
        iloader.init(config);
    }

    private void initView(View view) {
        gridview= (GridView) view.findViewById(R.id.tuijian_gridview);
    }

    private void initAdapter() {
        adapter=new MyAdapter();
        gridview.setAdapter(adapter);
    }

    private void initData() {
        MyCustomRequest<TuijianBean>mcr=new MyCustomRequest<TuijianBean>(UrlUtils.TZUIXINURL, TuijianBean.class,
                new Response.Listener<TuijianBean>() {
                    @Override
                    public void onResponse(TuijianBean response) {
                       list_tuijian.addAll( response.getData().getWallpaperListInfo());
                        initAdapter();
                    }
                },null);
    }


     class MyAdapter extends BaseAdapter{

         @Override
         public int getCount() {
             return 0;
         }

         @Override
         public Object getItem(int position) {
             return null;
         }

         @Override
         public long getItemId(int position) {
             return 0;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             return null;
         }
     }
}
