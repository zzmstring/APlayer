package com.zzmstring.aoobar.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zzmstring.aoobar.DB.Dao;
import com.zzmstring.aoobar.R;
import com.zzmstring.aoobar.adapter.ListAdapter;
import com.zzmstring.aoobar.base.BaseFragment;

/**
 * Created by zzmstring on 2015/3/6.
 */
public class SimpleFragment extends BaseFragment implements View.OnClickListener {
    Context context;
    private Cursor cursor;
    private SQLiteDatabase database;
    private ListAdapter listAdapter;
    public SimpleFragment(){

    }
    public SimpleFragment(Context context,String title){
        this.context=context;
        this.title=title;
        database = Dao.getInstance(context).getConnection();
        this.cursor=database.rawQuery("select * from " + title, null);
    }
    @ViewInject(R.id.lv_main)
    ListView lv_main;
    @ViewInject(R.id.bt_addmp3)
    Button bt_addmp3;
    private String title;
    public void setTitle(String title){
        this.title=title;
    }
    @Override
    public String getTitle(){
        return this.title;
    }
    @Override
    public View initView(LayoutInflater inflater) {
        View view=inflater.inflate(R.layout.fragment_simple,null);
        ViewUtils.inject(this,view);
        return view;
    }
    @Override
    public void initListener() {
        bt_addmp3.setOnClickListener(this);
        listAdapter=new ListAdapter(context,cursor);
        lv_main.setAdapter(listAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_addmp3:
                addMp3();
                break;
        }
    }
    public void addMp3(){

    }
}
