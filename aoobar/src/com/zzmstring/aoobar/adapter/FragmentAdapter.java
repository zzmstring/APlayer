package com.zzmstring.aoobar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zzmstring.aoobar.base.BaseFragment;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
	private List<BaseFragment> list;
    private String title;
    public void setPagerTitle(String title){
        this.title=title;
    }
	public FragmentAdapter(FragmentManager fm, List<BaseFragment> list) {
		super(fm);
		this.list=list;
	}
	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}
	@Override
	public int getCount() {
		return list.size();
	}

	public String getPageTitle(int i){
        return this.list.get(i).getTitle();
    }
	
}
