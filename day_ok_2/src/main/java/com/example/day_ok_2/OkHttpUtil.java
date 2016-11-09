package com.example.day_ok_2;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/31.
 */
public class OkHttpUtil {
    private OkHttpClient client;
    private HttpUrl url;

    public OkHttpUtil(){
        client=new OkHttpClient();
    }
    private static  OkHttpUtil utils;
    public static OkHttpUtil getInstance() {
        if (utils == null) {
            synchronized (OkHttpUtil.class) {
                if (utils == null) {
                    utils = new OkHttpUtil();
                }
            }

        }
        return utils;
    }
    public <T extends Object>T get(String url,Class<T>clazz){
        Request request=new Request.Builder().url(url).build();
        try {
            Response res=client.newCall(request).execute();
            T zb=new Gson().fromJson(res.body().string(),clazz);
            return zb;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public interface GetTextCallback<T>{
        void getText(T result);
    }
    public <T extends Object>void getByEnqueue(final Activity activity,String url,final Class<T>clazz,
                                               final GetTextCallback callback){
        final Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final T zb=new Gson().fromJson(response.body().string(),clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(callback!=null){
                            callback.getText(zb);
                        }
                    }
                });
            }
        });
    }

    public <T extends  Object> void postByQueue(final Activity activity ,String url, Map<String ,String >map, final Class<T>clazz, final GetTextCallback<T>callback) {
        FormBody.Builder fb = new FormBody.Builder();
        Set<String> keys = map.keySet();
        for (String s : keys) {
            fb.add(s, map.get(s));
        }
                final Request post = new Request.Builder()
                .post(fb.build())
                .url(url)
                .build();
        client.newCall(post).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    final T t=new Gson().fromJson(response.body().string(),clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.getText(t);

                    }
                });
            }
        });

    }
    public <T extends Object>T post(String url,Map<String, String>map,Class<T>clazz){
        FormBody.Builder fb=new FormBody.Builder();
        Set<String>keys=map.keySet();
        for(String s :keys){
            fb.add(s,map.get(s));
        }
        Request post=new Request.Builder().post(fb.build()).url(url).build();

        try {
            Response res=client.newCall(post).execute();
                T t=new Gson().fromJson(res.body().string(),clazz);
            return t;
            } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


//  （火热的夏天 猪因为太热缺水想洗澡   所以就做梦天要下雨）==（朱梦雨）