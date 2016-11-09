package com.example.administrator.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RadioGroup group;
    private ArrayList<Fragment>gragments=new ArrayList<>();
    private FragmentManager manager;

    private Fragment lastFragment;
private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragments();
        manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.add(R.id.fl,gragments.get(0));
        ft.addToBackStack("");
        ft.commit();
        lastFragment=gragments.get(0);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= (RadioButton) findViewById(checkedId);
                int tag=Integer.parseInt(rb.getTag().toString());
                FragmentTransaction ft = manager.beginTransaction();
                if (!gragments.get(tag).isAdded()) {
                    ft.add(R.id.fl, gragments.get(tag));
                } else {
                    ft.show(gragments.get(tag));
                }
                ft.hide(lastFragment);
                ft.addToBackStack("");
                ft.commit();
                lastFragment=gragments.get(tag);
            }
        });
    }

    private void initFragments() {
        gragments.add(new OneFragment());
        gragments.add(new TwoFragment());
        gragments.add(new ThreeFragment());
        gragments.add(new FourFragment());
    }

    private void initView() {
        group= (RadioGroup) findViewById(R.id.radioGroup1);
      //  lv= (ListView) findViewById(R.id.lv_fenlei);


    }
}
