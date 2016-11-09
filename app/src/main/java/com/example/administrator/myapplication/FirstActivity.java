package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by Administrator on 2016/10/25.
 */
public class FirstActivity extends AppCompatActivity{
    private Handler handler=new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first);

    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(FirstActivity.this,MainActivity.class);
            startActivity(intent);
            FirstActivity.this.finish();
        }
    },2000);


    }
}
