package com.example.day_xutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
@ContentView(value = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
@ViewInject(value = R.id.b1)
    Button b1;
    @ViewInject(value = R.id.iv)
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        x.view().inject(this);
        b1.setText("点你妹");
    }
    //监听事件
    @Event(value = R.id.b1,type = View.OnClickListener.class)
    private void click(View v){
        Toast.makeText(MainActivity.this,"!!!!!!!!!!!!!!!",Toast.LENGTH_SHORT).show();
    }
}
