package com.zzmstring.aoobar;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zzmstring.aoobar.DB.SqlBrite;
import com.zzmstring.aoobar.adapter.FragmentAdapter;
import com.zzmstring.aoobar.base.BaseFragment;
import com.zzmstring.aoobar.fragment.SimpleFragment;
import com.zzmstring.aoobar.support.hawk.Hawk;
import com.zzmstring.aoobar.utils.ListUtils;
import com.zzmstring.aoobar.view.PagerSlidingTabStrip.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @ViewInject(R.id.iv_addfragment)
    ImageView iv_addfragment;
    @ViewInject(R.id.view_pager)
    ViewPager view_pager;
    @ViewInject(R.id.activity_main_tv_artist)
    TextView activity_main_tv_artist;
    @ViewInject(R.id.activity_main_tv_name)
    TextView activity_main_tv_name;
    @ViewInject(R.id.activity_main_tv_time)
    TextView activity_main_tv_time;
    @ViewInject(R.id.activity_main_ib_previous)
    ImageButton activity_main_ib_previous;
    @ViewInject(R.id.activity_main_ib_play)
    ImageButton activity_main_ib_play;
    @ViewInject(R.id.activity_main_ib_next)
    ImageButton activity_main_ib_next;
    private AlertDialog dialog;
    private boolean isOpen=false;
    private List<String> chanelList;
    private List<BaseFragment> baseFragmentList;
    private FragmentAdapter fragmentAdapter;
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
        chanelList=new ArrayList<>();
        baseFragmentList=new ArrayList<BaseFragment>();
        Hawk.init(this,"heihei");
        isOpen=Hawk.get("isOpen",false);
        if(!isOpen){
            String title="defaule";
            SimpleFragment simpleFragment=new SimpleFragment();
            simpleFragment.setTitle(title);
            baseFragmentList.add(simpleFragment);
            chanelList.add(title);
            Hawk.put("list",chanelList);
            Hawk.put("isOpen",true);
        }else {
            List<String> tempList=Hawk.get("list");
            if(!ListUtils.isEmpty(tempList)){
                for(String s:tempList){
                    chanelList.add(s);
                    SimpleFragment simpleFragment=new SimpleFragment();
                    simpleFragment.setTitle(s);
                    baseFragmentList.add(simpleFragment);
                }
            }

        }
    }
    protected void initData(){
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),baseFragmentList);
        view_pager.setAdapter(fragmentAdapter);
        tabs.setViewPager(view_pager);

    }
    protected void initListener(){
        iv_addfragment.setOnClickListener(this);
        activity_main_ib_previous.setOnClickListener(this);
        activity_main_ib_play.setOnClickListener(this);
        activity_main_ib_next.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_addfragment:
                showAddTitle();
                break;
            case R.id.activity_main_ib_next:
                break;
            case R.id.activity_main_ib_play:
                break;
            case R.id.activity_main_ib_previous:
                break;

        }
    }
    private void showAddTitle(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v2 = View.inflate(this, R.layout.pupop_edit, null);
        Button ok = (Button) v2.findViewById(R.id.bt_ok);
        Button cancel = (Button) v2.findViewById(R.id.bt_cancel);
        final EditText et_edit=(EditText)v2.findViewById(R.id.et_edit);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=et_edit.getText().toString().trim();
                if(!TextUtils.isEmpty(content)){

                    SimpleFragment simpleFragment=new SimpleFragment();
                    simpleFragment.setTitle(content);
                    baseFragmentList.add(simpleFragment);
                    tabs.notifyDataSetChanged();
                    fragmentAdapter.notifyDataSetChanged();
                    if(!chanelList.contains(content)){
                        chanelList.add(content);
                        Hawk.put("list",chanelList);
                    }
//                    tabs.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.setView(v2, 0, 0, 0, 0);
        dialog.show();
    }
}
