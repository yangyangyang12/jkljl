package com.example.administrator.myapplication;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import Beansss.HotSearchBean;

/**
 * Created by samsung on 2016/10/26.
 */
public class SearchListAdapter extends BaseAdapter {

    private ArrayList<HotSearchBean.DataBean> list;

    private LayoutInflater inflater;
    private ImageLoader loader;

    private DisplayImageOptions opts;
    public SearchListAdapter(ArrayList<HotSearchBean.DataBean> list, Context context, ImageLoader loader) {
        this.list = list;
        this.loader = loader;
        inflater = LayoutInflater.from(context);

        opts = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }




    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder ;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_lv_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HotSearchBean.DataBean  bean = list.get(position);
        holder.mTv.setText(bean.getKeyword());


        loader.displayImage(bean.getImgs().get(0),holder.mIv1,opts);
        loader.displayImage(bean.getImgs().get(1),holder.mIv2,opts);
        loader.displayImage(bean.getImgs().get(2),holder.mIv3,opts);

        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView mTv;
        public ImageView mIv1;
        public ImageView mIv2;
        public ImageView mIv3;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mTv = (TextView) rootView.findViewById(R.id.tv);
            this.mIv1 = (ImageView) rootView.findViewById(R.id.iv1);
            this.mIv2 = (ImageView) rootView.findViewById(R.id.iv2);
            this.mIv3 = (ImageView) rootView.findViewById(R.id.iv3);
        }

    }
}
