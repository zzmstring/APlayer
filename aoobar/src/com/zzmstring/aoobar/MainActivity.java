package com.zzmstring.aoobar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zzmstring.aoobar.view.PagerSlidingTabStrip.PagerSlidingTabStrip;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @ViewInject(R.id.tv_addfragment)
    TextView tv_addfragment;
    @ViewInject(R.id.view_pager)
    ViewPager view_pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
	}
    protected void initView(){
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
    }
    protected void initData(){

    }
    protected void initListener(){
        tv_addfragment.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_addfragment:
                break;
        }
    }
}
