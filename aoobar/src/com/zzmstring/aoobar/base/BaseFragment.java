package com.zzmstring.aoobar.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public abstract class BaseFragment extends Fragment {


	protected View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=initView(inflater);
        initListener();
        initData();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	public abstract View initView(LayoutInflater inflater);

	public abstract void initListener();

	public abstract void initData();
    public abstract String getTitle();
}
