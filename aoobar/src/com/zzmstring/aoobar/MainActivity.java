package com.zzmstring.aoobar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zzmstring.aoobar.DB.DBHelper;
import com.zzmstring.aoobar.DB.Dao;
import com.zzmstring.aoobar.DB.SqlBrite;
import com.zzmstring.aoobar.adapter.FragmentAdapter;
import com.zzmstring.aoobar.base.BaseFragment;
import com.zzmstring.aoobar.fragment.SimpleFragment;
import com.zzmstring.aoobar.openfiledemo.CallbackBundle;
import com.zzmstring.aoobar.openfiledemo.OpenFileDialog;
import com.zzmstring.aoobar.support.hawk.Hawk;
import com.zzmstring.aoobar.utils.ExLog;
import com.zzmstring.aoobar.utils.ListUtils;
import com.zzmstring.aoobar.view.PagerSlidingTabStrip.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

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
    @ViewInject(R.id.iv_more)
    ImageView mMore;
    @ViewInject(R.id.tv_addlist)
    TextView tv_addlist;
    @ViewInject(R.id.tv_deletelist)
    TextView tv_deletelist;
    @ViewInject(R.id.tv_addmps)
    TextView tv_addmps;
    @ViewInject(R.id.ll_menu)
    LinearLayout ll_menu;
    private boolean isMenuShow=false;
    private AlertDialog dialog;
    private boolean isOpen = false;
    private boolean isFirst = true;
    private List<String> chanelList;
    private List<BaseFragment> baseFragmentList;
    private FragmentAdapter fragmentAdapter;
    static private int openfileDialogId = 0;
    private SQLiteDatabase database;
    private SqlBrite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    protected void initView() {
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        chanelList = new ArrayList<String>();
        baseFragmentList = new ArrayList<BaseFragment>();
        database = Dao.getInstance(this).getConnection();
        db = SqlBrite.create(new DBHelper(this));
        Hawk.init(this, "heihei");
        isOpen = Hawk.get("isOpen", false);
        if (!isOpen) {
            String title = "defaule";
            db.insert("list", createList(title));
            database.execSQL("create table " + title + "(_id integer PRIMARY KEY AUTOINCREMENT, "
                    + "music char)");
            Hawk.put("isOpen", true);
        } else {
//            List<String> tempList=Hawk.get("list");
//            if(!ListUtils.isEmpty(tempList)){
//                for(String s:tempList){
//                    chanelList.add(s);
//                    SimpleFragment simpleFragment=new SimpleFragment();
//                    simpleFragment.setTitle(s);
//                    baseFragmentList.add(simpleFragment);
//                }
//            }

        }
    }

    protected void initData() {
        Observable<SqlBrite.Query> lists = db.createQuery("list", "SELECT * FROM list");
        lists.subscribe(new Action1<SqlBrite.Query>() {
            @Override
            public void call(SqlBrite.Query query) {
                Cursor cursor = query.run();
                int count = cursor.getColumnCount();
                int rawCount = cursor.getCount();
                ExLog.l("rawCount >>" + rawCount);
                if (isFirst) {
                    while (cursor.moveToNext()) {
                        String str = cursor.getString(1);
                        SimpleFragment simpleFragment = new SimpleFragment();
                        simpleFragment.setTitle(str);
                        baseFragmentList.add(simpleFragment);
                    }
                    isFirst = false;
                } else {
                    cursor.moveToLast();
                    String str = cursor.getString(1);
                    SimpleFragment simpleFragment = new SimpleFragment();
                    simpleFragment.setTitle(str);
                    baseFragmentList.add(simpleFragment);
                }
            }
        });
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), baseFragmentList);
        view_pager.setAdapter(fragmentAdapter);
        tabs.setViewPager(view_pager);

    }

    protected void initListener() {
        iv_addfragment.setOnClickListener(this);
        activity_main_ib_previous.setOnClickListener(this);
        activity_main_ib_play.setOnClickListener(this);
        activity_main_ib_next.setOnClickListener(this);
        mMore.setOnClickListener(this);
        tv_addlist.setOnClickListener(this);
        tv_addmps.setOnClickListener(this);
        tv_deletelist.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_addfragment:
//                showAddTitle();
                break;
            case R.id.activity_main_ib_next:
                break;
            case R.id.activity_main_ib_play:
                break;
            case R.id.activity_main_ib_previous:
                break;
            case R.id.iv_more:
                showMenu();
                break;
            case R.id.tv_addmps:
                addMps();
                break;
            case R.id.tv_addlist:
                addList();
                break;
            case R.id.tv_deletelist:
                deleteList();
                break;

        }
    }

    private void showAddTitle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v2 = View.inflate(this, R.layout.pupop_edit, null);
        Button ok = (Button) v2.findViewById(R.id.bt_ok);
        Button cancel = (Button) v2.findViewById(R.id.bt_cancel);
        final EditText et_edit = (EditText) v2.findViewById(R.id.et_edit);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_edit.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
//                    SqlBrite db=SqlBrite.create(database);
                    db.insert("list", createList(content));
                    tabs.notifyDataSetChanged();
                    fragmentAdapter.notifyDataSetChanged();
//                    SimpleFragment simpleFragment=new SimpleFragment();
//                    simpleFragment.setTitle(content);
//                    baseFragmentList.add(simpleFragment);
//                    tabs.notifyDataSetChanged();
//                    fragmentAdapter.notifyDataSetChanged();
//                    if(!chanelList.contains(content)){
//                        chanelList.add(content);
//                        Hawk.put("list",chanelList);
//                    }
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

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == openfileDialogId) {
            Map<String, Integer> images = new HashMap<String, Integer>();
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);    // 根目录图标
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);    //文件夹图标
            images.put("mp3", R.drawable.filedialog_wavfile);    //wav文件图标
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
//                            setTitle(filepath); // 把文件路径显示在标题上
                            ExLog.l("selected file is >>>>>" + filepath);
                        }
                    },
                    ".mp3;",
                    images);
            return dialog;
        }
        return null;
    }

    private ContentValues createList(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        return contentValues;
    }
    private void showDialog() {
        Map<String, Integer> images = new HashMap<String, Integer>();
        // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
        images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);    // 根目录图标
        images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标
        images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);    //文件夹图标
        images.put("mp3", R.drawable.filedialog_wavfile);    //wav文件图标
        images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
        Dialog dialog = OpenFileDialog.createDialog(0, this, "打开文件", new CallbackBundle() {
                    @Override
                    public void callback(Bundle bundle) {
                        String filepath = bundle.getString("path");
//                            setTitle(filepath); // 把文件路径显示在标题上
                        ExLog.l("selected file is >>>>>" + filepath);
                    }
                },
                ".mp3;",
                images);
        dialog.show();
        }
    private void showMenu(){
        if(!isMenuShow){
            ll_menu.setVisibility(View.VISIBLE);
            isMenuShow=true;
        }else {
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow=false;
        }
    }
    private void addMps(){
        if(isMshow()){
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow=false;
        }
    }
    private void addList(){
        showAddTitle();
        if(isMshow()){
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow=false;
        }
    }
    private void deleteList(){
        if(isMshow()){
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow=false;
        }
    }
    private boolean isMshow(){
        return ll_menu.getVisibility()==View.VISIBLE?true:false;
    }

    @Override
    public void onUserInteraction() {
//        if(isMshow()){
//            ll_menu.setVisibility(View.INVISIBLE);
//            isMenuShow=false;
//        }
//        super.onUserInteraction();
    }
}
