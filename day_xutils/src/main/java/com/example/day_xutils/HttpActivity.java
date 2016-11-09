package com.example.day_xutils;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/2.
 */
@ContentView(value = R.layout.item)
public class HttpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    x.view().inject(this);

    }
    @Event(value = {R.id.b_get,R.id.b_post,R.id.b_bean,R.id.b_file},type = View.OnClickListener.class )
    private void click(View v){
        switch(v.getId()){
            case R.id.b_get:
                RequestParams params=new RequestParams("");
                x.http().get(params, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }
                });
                break;
            case R.id.b_post:
                RequestParams post=new RequestParams("");
                post.addBodyParameter("","");
                x.http().post(post, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.b_file:
                RequestParams file=new RequestParams("");
                file.setMultipart(true);
                file.addBodyParameter("","");
                file.addBodyParameter("","");
                x.http().post(file, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
        }
    }
}
