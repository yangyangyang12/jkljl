package view;

import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by samsung on 2016/10/12.
 */
public class MyListFragment extends ListFragment {

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter ;

    public static MyListFragment newInstance(String s) {

        Bundle args = new Bundle();

        MyListFragment fragment = new MyListFragment();
        args.putString("type",s);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initData();

        initAdapter();

        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initAdapter() {
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list);
    }


    private void initData() {
        list.clear();
        String s = getArguments().getString("type");
        for (int i = 0; i < 15; i++) {
            list.add(s+i);
        }
    }
}
