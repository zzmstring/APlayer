package com.zzmstring.aoobar.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.zzmstring.aoobar.R;
import com.zzmstring.aoobar.base.BaseFragment;

/**
 * Created by zzmstring on 2015/3/6.
 */
public class SimpleFragment extends BaseFragment {

    @Override
    public View initView(LayoutInflater inflater) {
        View view=inflater.inflate(R.layout.fragment_simple,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
