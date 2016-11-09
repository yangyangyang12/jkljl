package com.example.administrator.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import Beansss.Bean;
import Tasksss.ImageTask;
import Tasksss.WNTextTask;

/**
 * Created by Administrator on 2016/10/25.
 */
public class TwoFragment extends Fragment {
    private ListView lv;
    private ArrayList<Bean.DataBean>list=new ArrayList<Bean.DataBean>();
    private Myadapter adapter;
    private View aaView;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.r_fenlei,container,false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();

    }

    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv_fenlei);
    }

    private void initAdapter() {
        adapter=new Myadapter();
        lv.setAdapter(adapter);

    }

    private void initData() {
       // Log.i("cmd", "initData: !!!!!!!!!!!!!!!!!!!!!");
        new WNTextTask<Bean>(new WNTextTask.TextCallback<Bean>() {
            @Override
            public void getText(Bean result) {
               // Log.i("cmd", "getText: "+result.getData());
                list.addAll(result.getData());

                initAdapter();
                adapter.notifyDataSetChanged();
            }
        },getActivity(),Bean.class).execute("http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=category");

        }

        class  Myadapter extends BaseAdapter{
            private LruCache<String,Bitmap> lruCache;
            private HashMap<String,SoftReference<Bitmap>> map;
                    public Myadapter(){
                        map=new HashMap<String,SoftReference<Bitmap>>();
                        lruCache=new LruCache<String,Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){
                            @Override
                            protected int sizeOf(String key, Bitmap value) {
                                return value.getByteCount();
                            }

                            @Override
                            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                                super.entryRemoved(evicted, key, oldValue, newValue);
                                if(evicted){
                                    map.put(key,new SoftReference<Bitmap>(oldValue));
                                }
                            }
                        };


                    }
            @Override
            public int getCount() {
                return list.size();
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
                final ViewHolder holder;
                if(convertView==null){
                    convertView=LayoutInflater.from(getActivity()).inflate(R.layout.aaview,parent,false);
                    holder=new ViewHolder();
                    holder.iv_aa1= (ImageView) convertView.findViewById(R.id.iv_aa1);
                    holder.tv_aa1= (TextView) convertView.findViewById(R.id.tv_aa1);
                    holder.tv_aa2= (TextView) convertView.findViewById(R.id.tv_aa2);
                    convertView.setTag(holder);
                }else{
                   holder= (ViewHolder) convertView.getTag();
                }

                holder.tv_aa1.setText(list.get(position).getPicCategoryName());
                holder.tv_aa2.setText(list.get(position).getDescWords());


                holder.iv_aa1.setImageResource(R.mipmap.ic_launcher);
                holder.iv_aa1.setTag(list.get(position).getCategoryPic());

                Bitmap bitmap = getBitmap(list.get(position).getCategoryPic());
                if(bitmap != null){
                    holder.iv_aa1.setImageBitmap(bitmap);
                }else {
                    new ImageTask(new ImageTask.ImageCallback() {
                        @Override
                        public void getImage(Bitmap bitmap, String url) {
                        holder.iv_aa1.setImageBitmap(bitmap);
                            lruCache.put(url,bitmap);
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"zmy");
                            if(!file.exists()){
                                file.mkdirs();
                            }
                            String name = url.replace("/","+");
                            try {
                                FileOutputStream fos = new FileOutputStream(new File(file,name));
                                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).execute(list.get(position).getCategoryPic());
                }
                return convertView;
            }


    private Bitmap getBitmap(String url){
        Bitmap mapresult = null;

        mapresult = lruCache.get(url);

        if(mapresult == null){
            SoftReference<Bitmap> softReference = map.get(url);
            if(softReference != null){
                mapresult = softReference.get();
            }

            if(mapresult == null){
                String filename = url.replaceAll("/", "+");
                mapresult = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "ay1608" + File.separator + filename);

            }
        }


        return mapresult;
    }
        class ViewHolder{
            ImageView iv_aa1;
            TextView  tv_aa1,tv_aa2;

        }
}
}
