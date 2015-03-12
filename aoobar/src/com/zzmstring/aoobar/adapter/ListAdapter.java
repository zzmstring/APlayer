package com.zzmstring.aoobar.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.zzmstring.aoobar.DB.DBHelper;
import com.zzmstring.aoobar.DB.SqlBrite;
import com.zzmstring.aoobar.R;
import com.zzmstring.aoobar.utils.ExLog;

import rx.Observable;

/**
 * Created by ZGL on 2015/3/12.
 */
public class ListAdapter extends CursorAdapter {
    private SqlBrite db;


    Observable<SqlBrite.Query> lists = db.createQuery("list", "SELECT * FROM list");
    public ListAdapter(Context context, Cursor c) {
        super(context, c);

    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view=View.inflate(context, R.layout.item_music,null);
        TextView file= (TextView) view.findViewById(R.id.tv_file);
        TextView path= (TextView) view.findViewById(R.id.tv_path);
        TextView time= (TextView) view.findViewById(R.id.tv_time);
        String strFile=cursor.getString(cursor.getColumnIndex("file"));
        String strPath=cursor.getString(cursor.getColumnIndex("path"));
        String strTime=cursor.getString(cursor.getColumnIndex("time"));
        ExLog.l("执行了>>"+ExLog.getCurrentMethodName()+"path>"+strPath+"file>"+strFile+"time>"+strTime);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
