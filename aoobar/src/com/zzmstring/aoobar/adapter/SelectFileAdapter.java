package com.zzmstring.aoobar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzmstring.aoobar.R;

import java.util.List;
import java.util.Map;

/**
 * Created by zzmstring on 2015/3/23.
 */
public class SelectFileAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> list;
    public SelectFileAdapter(Context context,List<Map<String, Object>> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, Object> getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        /**
         * viewholder
         */
        View v =View.inflate(context,R.layout.filedialogitem,null);
        ImageView iv= (ImageView) v.findViewById(R.id.filedialogitem_img);
        TextView tv_name= (TextView) v.findViewById(R.id.filedialogitem_name);
        TextView tv_path= (TextView) v.findViewById(R.id.filedialogitem_path);
        CheckBox ch= (CheckBox) v.findViewById(R.id.ch_haha);

        return v;
    }

}
