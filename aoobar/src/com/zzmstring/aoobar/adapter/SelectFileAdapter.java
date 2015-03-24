package com.zzmstring.aoobar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzmstring.aoobar.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzmstring on 2015/3/23.
 */
public class SelectFileAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> list;
    Map<Integer, Boolean> isCheckMap ;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer,Boolean> isSelected;

    public SelectFileAdapter(Context context,List<Map<String, Object>> list){
        this.context=context;
        this.list=list;
        isCheckMap=  new HashMap<Integer, Boolean>();
        isSelected = new HashMap<Integer, Boolean>();
        initDate();
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
        View v;
        ViewHolder viewHolder = null;
        if(view==null)
        {
            viewHolder = new ViewHolder();
            v = View.inflate(context,R.layout.filedialogitem,null);
            viewHolder.iv = (ImageView)v.findViewById(R.id.filedialogitem_img);
            viewHolder.tv_name = (TextView)v.findViewById(R.id.filedialogitem_name);
            viewHolder.tv_path = (TextView)v.findViewById(R.id.filedialogitem_path);
            viewHolder.ch=(CheckBox) v.findViewById(R.id.ch_haha);
            v.setTag(viewHolder);
        }
        else
        {
            v = view;
            viewHolder = (ViewHolder)view.getTag();
        }
        Map<String, Object> map=list.get(i);
        viewHolder.iv.setImageResource((Integer) map.get("img"));
        viewHolder.tv_name.setText((CharSequence) map.get("name"));
        viewHolder.tv_path .setText((CharSequence) map.get("path"));
        if(isCheckMap!=null && isCheckMap.containsKey(i))
        {
            viewHolder.ch.setChecked(isCheckMap.get(i));
        }
        else
        {
            viewHolder.ch.setChecked(false);
        }

        viewHolder.ch.setChecked(getIsSelected().get(i));
        viewHolder.ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int radiaoId = Integer.parseInt(compoundButton.getTag().toString());
                if(b)
                {
                    //将选中的放入hashmap中
                    isCheckMap.put(radiaoId, b);
                }
                else
                {
                    //取消选中的则剔除
                    isCheckMap.remove(radiaoId);
                }
            }
        });
        return v;
    }
    static  class  ViewHolder{
        ImageView iv;
        TextView tv_name;
        TextView tv_path;
        CheckBox ch;
    }
    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        SelectFileAdapter.isSelected = isSelected;
    }
    // 初始化isSelected的数据
    private void initDate(){
        for(int i=0; i<list.size();i++) {
            getIsSelected().put(i,false);
        }
    }
}
