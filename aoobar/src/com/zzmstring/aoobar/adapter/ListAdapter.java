package com.zzmstring.aoobar.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.zzmstring.aoobar.DB.DBHelper;
import com.zzmstring.aoobar.DB.SqlBrite;
import com.zzmstring.aoobar.R;
import com.zzmstring.aoobar.bean.MyMusicInfo;
import com.zzmstring.aoobar.music.MediaService;
import com.zzmstring.aoobar.utils.ExLog;

import rx.Observable;

/**
 * Created by ZGL on 2015/3/12.
 */
public class ListAdapter extends CursorAdapter {
    private SqlBrite db;
    private Cursor cursor;
    private Context context;
    Intent intent;
//    Observable<SqlBrite.Query> lists = db.createQuery("list", "SELECT * FROM list");
    public ListAdapter(Context context, Cursor c) {
        super(context, c);
        this.context=context;
        this.cursor=c;
        intent=new Intent(context, MediaService.class);
        changeCursor(c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        ViewHolder holder=new ViewHolder();
        View v=View.inflate(context, R.layout.item_music, null);
//        holder.cbconversationselected=(CheckBox) v.findViewById(R.id.cb_conversation_selected);
//        holder.ivphoto=(ImageView) v.findViewById(R.id.iv_conversation_list_photo);
//        holder.tvbody=(TextView) v.findViewById(R.id.tv_conversation_list_body);
//        holder.tvdate=(TextView) v.findViewById(R.id.tv_conversation_list_date);
//        holder.tvname=(TextView) v.findViewById(R.id.tv_conversation_list_name);
//
//        return v;
        holder.tvfile= (TextView) v.findViewById(R.id.tv_file);
        holder.tvpath= (TextView) v.findViewById(R.id.tv_path);
        holder.tvtime= (TextView) v.findViewById(R.id.tv_time);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder holder=(ViewHolder) view.getTag();
        String strFile=cursor.getString(cursor.getColumnIndex("file"));
        final String strPath=cursor.getString(cursor.getColumnIndex("path"));
        String strTime=cursor.getString(cursor.getColumnIndex("time"));
        ExLog.l("执行了>>"+ExLog.getCurrentMethodName()+"path>"+strPath+"file>"+strFile+"time>"+strTime);
        holder.tvpath.setText(strPath);
        holder.tvfile.setText(strFile);
        holder.tvtime.setText(strTime);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("path",strPath);
                context.startService(intent);
            }
        });
    }
    class ViewHolder{
        TextView tvpath;
        TextView tvfile;
        TextView tvtime;
    }

    @Override
    public void notifyDataSetChanged() {
//        this.cursor.requery();
        super.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return super.getCount();
    }


}
