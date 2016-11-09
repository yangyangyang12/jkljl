package com.example.day_okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button b1;
    private Button b2;
    private OkHttpClient client;
    private Button b3;
    private Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        client=new OkHttpClient();
    }

    private void initView() {
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        b4=(Button)findViewById(R.id.b4);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b1:
                Request get=new Request.Builder()
                        .get()
                        .url("http://lib.wap.zol.com.cn/ipj/docList/?v=6.0&class_id=0&page=4&vs=and412&retina=1")
                        .build();
                final Call getCall=client.newCall(get);
                getCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("cmd", "onResponse: thread  "+Thread.currentThread().getName());
                        Log.i("cmd", "onResponse: "+response.body().string());
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("cmd", "run:子线程？？？？？ "+Thread.currentThread().getName());
                            }
                        });
                    }
                });
                break;
            case R.id.b2:
                RequestBody body= new FormBody.Builder()
                        .add("platform","2")
                        .add("gifttype","1")
                        .add("compare","60841c5b7c69a1bbb3f06536ed685a48")
                        .build();
                Request post= new Request.Builder()
                        .post(body)
                        .url("http://zhushou.72g.com/app/gift/gift_list/")
                        .build();
                final Call postCall=client.newCall(post);
                postCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("cmd", "onResponse: post result  "+response.body().string());
                    }
                });
                break;
            case R.id.b4:
                Request fileDown=new Request.Builder()
                        .url("http://i3.72g.com/upload/201506/201506041344181170.jpg")
                        .build();
                client.newCall(fileDown).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        InputStream is=response.body().byteStream();
                        FileOutputStream fos=new FileOutputStream("/mnt/sdcard/aydown.png");
                        byte []b=new byte[1024];
                        int num=-1;
                        while((num=is.read(b))!=-1){
                            fos.write(b,0,num);
                            fos.flush();
                        }
                        is.close();
                        fos.close();
                        Log.i("cmd", "onResponse:下载OK!!!!! ");
                    }
                });
                break;
            case R.id.b3:
                RequestBody b=new MultipartBody.Builder()
                        .addFormDataPart("filename","aypost"+ System.currentTimeMillis()+".png",RequestBody.create(MediaType.parse("application/octet-stream"),new File("/mnt/sdcard/aydown.png")))
                        .build();
                Request filePost=new Request.Builder()
                        .post(b)
                        .url("http://10.11.56.14:8080/upload/UploadServlet")
                        .build();
                client.newCall(filePost).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("cmd", "onResponse: 上传成功！！！！");
                    }

                });
                break;
        }
    }


}
