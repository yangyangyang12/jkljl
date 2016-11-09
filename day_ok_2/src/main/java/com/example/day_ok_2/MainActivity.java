package com.example.day_ok_2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<ZGCBean.ListBean>list=new ArrayList<>();
    private MyAdapter adapter;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        
        initAdapter();
    }

    private void initAdapter() {
        adapter=new MyAdapter();
        lv.setAdapter(adapter);
    }

    private void initView() {
        lv= (ListView) findViewById(R.id.lv);
    }

    public void click(View v){
        switch (v.getId()){
            case R.id.b1:
                list.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ZGCBean zb=OkHttpUtil.getInstance().get("http://lib.wap.zol.com.cn/ipj/docList/?v=6.0&class_id=0&page=4&vs=and412&retina=1",ZGCBean.class);
                        Log.i("cmd", "run:请求结果！！！！ "+zb.getList().toString());
                        list.addAll(zb.getList());
                        handler.sendEmptyMessage(0);
                    }
                }).start();
                break;
            case R.id.b2:
                list.clear();
                OkHttpUtil.getInstance().getByEnqueue(this,"http://lib.wap.zol.com.cn/ipj/docList/?v=6.0&class_id=0&page=4&vs=and412&retina=1",
                        ZGCBean.class, new OkHttpUtil.GetTextCallback<ZGCBean>() {
                            @Override
                            public void getText(ZGCBean result) {
                                list.addAll(result.getList());
                                adapter.notifyDataSetChanged();
                            }
                        });
                break;
            case R.id.b4:
                list.clear();
                HashMap<String,String> map1 = new HashMap<>();
                map1.put("v","6.0");
                map1.put("class_id","0");
                map1.put("page","4");
                map1.put("vs","and412");
                map1.put("retina","1");
                OkHttpUtil.getInstance().postByQueue(this, "http://lib.wap.zol.com.cn/ipj/docList/", map1, ZGCBean.class, new OkHttpUtil.GetTextCallback<ZGCBean>() {
                    @Override
                    public void getText(ZGCBean result) {
                        list.addAll(result.getList());
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case R.id.b3:
                list.clear();
                final HashMap<String,String>map=new HashMap<>();
                map.put("v","6.0");
                map.put("class_id","0");
                map.put("page","4");
                map.put("vs","and412");
                map.put("retina","1");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       ZGCBean zb=OkHttpUtil.getInstance().post("http://lib.wap.zol.com.cn/ipj/docList/",
                             map,ZGCBean.class  );
                        list.addAll(zb.getList());
                        handler.sendEmptyMessage(0);
                    }
                }).start();
                break;
        }
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item,parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(list.get(position).getStitle());
            holder.textView1.setText(list.get(position).getImgsrc2());
            return convertView;
        }
    }
    class ViewHolder{
        TextView textView;
        TextView textView1;
        public ViewHolder(View v){
            textView= (TextView) v.findViewById(R.id.textView);
            textView1= (TextView) v.findViewById(R.id.textView2);
        }
    }
}
