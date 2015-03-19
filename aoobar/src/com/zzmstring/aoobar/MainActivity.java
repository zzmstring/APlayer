package com.zzmstring.aoobar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zzmstring.aoobar.DB.DBHelper;
import com.zzmstring.aoobar.DB.Dao;
import com.zzmstring.aoobar.DB.SqlBrite;
import com.zzmstring.aoobar.UI.SelectFilesAty;
import com.zzmstring.aoobar.adapter.FragmentAdapter;
import com.zzmstring.aoobar.adapter.ListAdapter;
import com.zzmstring.aoobar.base.BaseFragment;
import com.zzmstring.aoobar.bean.MusicInfo;
import com.zzmstring.aoobar.bean.MyMusicInfo;
import com.zzmstring.aoobar.fragment.SimpleFragment;
import com.zzmstring.aoobar.music.MediaBinder;
import com.zzmstring.aoobar.music.MediaService;
import com.zzmstring.aoobar.openfiledemo.CallbackBundle;
import com.zzmstring.aoobar.openfiledemo.OpenFileDialog;
import com.zzmstring.aoobar.support.hawk.Hawk;
import com.zzmstring.aoobar.utils.ExLog;
import com.zzmstring.aoobar.utils.FormatUtil;
import com.zzmstring.aoobar.utils.ListUtils;
import com.zzmstring.aoobar.view.PagerSlidingTabStrip.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends FragmentActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static final int SLIDING_MENU_SCAN = 0;// 侧滑->扫描歌曲
    public static final int SLIDING_MENU_ALL = 1;// 侧滑->全部歌曲
    public static final int SLIDING_MENU_FAVORITE = 2;// 侧滑->我的最爱
    public static final int SLIDING_MENU_FOLDER = 3;// 侧滑->文件夹
    public static final int SLIDING_MENU_EXIT = 4;// 侧滑->退出程序
    public static final int SLIDING_MENU_FOLDER_LIST = 5;// 侧滑->文件夹->文件夹列表

    public static final int DIALOG_DISMISS = 0;// 对话框消失
    public static final int DIALOG_SCAN = 1;// 扫描对话框
    public static final int DIALOG_MENU_REMOVE = 2;// 歌曲列表移除对话框
    public static final int DIALOG_MENU_DELETE = 3;// 歌曲列表提示删除对话框
    public static final int DIALOG_MENU_INFO = 4;// 歌曲详情对话框
    public static final int DIALOG_DELETE = 5;// 歌曲删除对话框

    public static final String PREFERENCES_NAME = "settings";// SharedPreferences名称
    public static final String PREFERENCES_MODE = "mode";// 存储播放模式
    public static final String PREFERENCES_SCAN = "scan";// 存储是否扫描过
    public static final String PREFERENCES_SKIN = "skin";// 存储背景图
    public static final String PREFERENCES_LYRIC = "lyric";// 存储歌词高亮颜色

    public static final String BROADCAST_ACTION_SCAN = "com.cwd.cmeplayer.action.scan";// 扫描广播标志
    public static final String BROADCAST_ACTION_MENU = "com.cwd.cmeplayer.action.menu";// 弹出菜单广播标志
    public static final String BROADCAST_ACTION_FAVORITE = "com.cwd.cmeplayer.action.favorite";// 喜爱广播标志
    public static final String BROADCAST_ACTION_EXIT = "com.cwd.cmeplayer.action.exit";// 退出程序广播标志
    public static final String BROADCAST_INTENT_PAGE = "com.cwd.cmeplayer.intent.page";// 页面状态
    public static final String BROADCAST_INTENT_POSITION = "com.cwd.cmeplayer.intent.position";// 歌曲索引
    public static final String BROADCASE_MP3PATH="com.zzmstring.aoobar.mp3path";
    private final String TITLE_ALL = "播放列表";
    private final String TITLE_FAVORITE = "我的最爱";
    private final String TITLE_FOLDER = "文件夹";
    private final String TITLE_NORMAL = "无音乐播放";
    private final String TIME_NORMAL = "00:00";

    private int skinId;// 背景图ID
    private int slidingPage = SLIDING_MENU_ALL;// 页面状态
    private int playerPage;// 发送给PlayerActivity的页面状态
    private int musicPosition;// 当前播放歌曲索引
    private int folderPosition;// 文件夹列表索引
    private int dialogMenuPosition;// 记住弹出歌曲列表菜单的歌曲索引

    private boolean canSkip = true;// 防止用户频繁点击造成多次解除服务绑定，true：允许解绑
    private boolean bindState = false;// 服务绑定状态

    private String mp3Current;// 歌曲当前时长
    private String mp3Duration;// 歌曲总时长
    private String dialogMenuPath;// 记住弹出歌曲列表菜单的歌曲路径

    private TextView mainTitle;// 列表标题
    private TextView mainSize;// 歌曲数量
    private TextView mainArtist;// 艺术家
    private TextView mainName;// 歌曲名称
    private TextView mainTime;// 歌曲时间
    private ImageView mainAlbum;// 专辑图片

    private ImageButton btnMenu;// 侧滑菜单按钮
    private ImageButton btnPrevious;// 上一首按钮
    private ImageButton btnPlay;// 播放和暂停按钮
    private ImageButton btnNext;// 下一首按钮

    private LinearLayout skin;// 背景图
    private LinearLayout viewBack;// 返回上一级
    private LinearLayout viewControl;// 底部播放控制视图

    private Intent playIntent;
    private MediaBinder binder;
    private MainReceiver receiver;

    private SharedPreferences preferences;
    private ServiceConnection serviceConnection;
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
    @ViewInject(R.id.sb_main)
    SeekBar seekBar;
    private boolean isMenuShow = false;
    private AlertDialog dialog;
    private boolean isOpen = false;
    private boolean isFirst = true;
    private List<String> chanelList;
    private List<BaseFragment> baseFragmentList;
    private FragmentAdapter fragmentAdapter;
    static private int openfileDialogId = 0;
    private SQLiteDatabase database;
    private SqlBrite db;
    public static final int REFORSELFILE = 101;
    private SparseArray<SimpleFragment> sparseArray;
    private HashMap<String, SimpleFragment> hashMap;

    @Override
    protected void onStart() {
        super.onStart();
        hashMap = new HashMap<>();
        chanelList = new ArrayList<String>();
        baseFragmentList = new ArrayList<BaseFragment>();
        database = Dao.getInstance(this).getConnection();
    }
    public Intent getPlayIntent(){
        return this.playIntent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hashMap = new HashMap<>();
        chanelList = new ArrayList<String>();
        baseFragmentList = new ArrayList<BaseFragment>();
        database = Dao.getInstance(this).getConnection();
        initView();
        initListener();
        initData();
    }

    protected void initView() {
        playIntent = new Intent(getApplicationContext(), MediaService.class);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initServiceConnection();
        db = SqlBrite.create(new DBHelper(this));
        Hawk.init(this, "heihei");
        isOpen = Hawk.get("isOpen", false);
        if (!isOpen) {
            String title = "defaule";
            db.insert("list", createList(title));
            database.execSQL("create table " + title + "(_id integer PRIMARY KEY AUTOINCREMENT, path char, "
                    + "file char, time integer)");
            Hawk.put("isOpen", true);
//            hashMap.put("")
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
                        SimpleFragment simpleFragment = new SimpleFragment(MainActivity.this, str);
                        baseFragmentList.add(simpleFragment);
                        hashMap.put(str, simpleFragment);
                    }
                    isFirst = false;
                } else {
                    cursor.moveToLast();
                    String str = cursor.getString(1);
                    SimpleFragment simpleFragment = new SimpleFragment(MainActivity.this, str);
                    baseFragmentList.add(simpleFragment);
                    hashMap.put(str, simpleFragment);
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
        seekBar.setOnSeekBarChangeListener(this);
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
                    database.execSQL("create table " + content + "(_id integer PRIMARY KEY AUTOINCREMENT, path char, "
                            + "file char, time integer)");
                    ExLog.l("创建了数据库");
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

    private void showMenu() {
        if (!isMenuShow) {
            ll_menu.setVisibility(View.VISIBLE);
            isMenuShow = true;
        } else {
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow = false;
        }
    }

    private void addMps() {
        if (isMshow()) {
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow = false;
        }
        String currenttabstring = tabs.getCurrent(view_pager.getCurrentItem());
        ExLog.l("当前的页面是>" + currenttabstring);
        Intent intent = new Intent(this, SelectFilesAty.class);
        startActivityForResult(intent, REFORSELFILE);

    }

    private void addList() {
        showAddTitle();
        if (isMshow()) {
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow = false;
        }
    }

    private void deleteList() {
        if (isMshow()) {
            ll_menu.setVisibility(View.INVISIBLE);
            isMenuShow = false;
        }
    }

    private boolean isMshow() {
        return ll_menu.getVisibility() == View.VISIBLE ? true : false;
    }

    @Override
    public void onUserInteraction() {
//        if(isMshow()){
//            ll_menu.setVisibility(View.INVISIBLE);
//            isMenuShow=false;
//        }
//        super.onUserInteraction();
    }

    private void insertMps(String table, List<MyMusicInfo> list) {
        if (!ListUtils.isEmpty(list)) {
            for (MyMusicInfo info : list) {
                ExLog.l("选中的文件是>>>" + info.file + "<>" + info.path);
                db.insert(table, createMusic(info));
            }
            SimpleFragment simpleFragment = hashMap.get(table);
            simpleFragment.initListener();
//            ListAdapter listAdapter=simpleFragment.getListAdapter();
//            boolean isnull=listAdapter==null;
//            ExLog.l("lsitadapter == null>>>"+isnull);
//            if(listAdapter!=null){
//                listAdapter.notifyDataSetChanged();
//                simpleFragment.getListView().invalidate();
//            }
        } else {

        }
    }

    private ContentValues createMusic(MyMusicInfo info) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", info.path);
        contentValues.put("file", info.file);
//        contentValues.put("time",info.);
        return contentValues;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REFORSELFILE:
                ArrayList<MyMusicInfo> list = data.getParcelableArrayListExtra("list");
                String currenttabstring = tabs.getCurrent(view_pager.getCurrentItem());
                insertMps(currenttabstring, list);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * 用于接收歌曲列表菜单及将歌曲标记为最爱的广播
     */
    private class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent != null) {
                final String action = intent.getAction();

                if (action.equals(BROADCAST_ACTION_EXIT)) {
//                    exitProgram();
                    return;
                }
                if(action.equals(BROADCASE_MP3PATH)){
                    String path=intent.getStringExtra("path");

                }
                // 没有传值的就是通过播放界面标记我的最爱的，所以默认赋值上次点击播放的页面，为0则默认为全部歌曲
//                slidingPage = intent.getIntExtra(BROADCAST_INTENT_PAGE,
//                        playerPage == 0 ? SLIDING_MENU_ALL : playerPage);
//                dialogMenuPosition = intent.getIntExtra(
//                        BROADCAST_INTENT_POSITION, 0);
//                MusicInfo info = null;


            }
        }
    }

    /*
     * 初始化服务绑定
	 */
    private void initServiceConnection() {
        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub
                binder = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // TODO Auto-generated method stub
                binder = (MediaBinder) service;
                if (binder != null) {
                    canSkip = true;// 重置
                    binder.setOnPlayStartListener(new MediaBinder.OnPlayStartListener() {

                        @Override
                        public void onStart(MyMusicInfo info) {
                            // TODO Auto-generated method stub
//                            playerPage = musicAdapter.getPage();
//                            mainArtist.setText(info.getArtist());
//                            mainName.setText(info.getName());
//                            mp3Duration = info.getTime();
                            if (mp3Current == null) {
                                mainTime.setText(TIME_NORMAL + " - "
                                        + mp3Duration);
                            } else {
                                mainTime.setText(mp3Current + " - "
                                        + mp3Duration);
                            }

                        }
                });
                binder.setOnPlayingListener(new MediaBinder.OnPlayingListener() {

                    @Override
                    public void onPlay(int currentPosition) {
                        // TODO Auto-generated method stub
                        mp3Current = FormatUtil.formatTime(currentPosition);
//                        mainTime.setText(mp3Current + " - " + mp3Duration);
                        seekBar.setProgress(currentPosition);
                    }
                });
                binder.setOnPlayPauseListener(new MediaBinder.OnPlayPauseListener() {

                    @Override
                    public void onPause() {
                        // TODO Auto-generated method stub
                        btnPlay.setImageResource(R.drawable.main_btn_play);
                    }
                });
                binder.setOnPlayCompletionListener(new MediaBinder.OnPlayCompleteListener() {

                    @Override
                    public void onPlayComplete() {
                        // TODO Auto-generated method stub
                        mp3Current = null;
                    }
                });
                binder.setOnPlayErrorListener(new MediaBinder.OnPlayErrorListener() {

                    @Override
                    public void onPlayError() {
                        // TODO Auto-generated method stub
                        dialogMenuPosition = musicPosition;
//                            removeList();// 文件已经不存在必须从列表移除
                    }
                });
//                    binder.setLyricView(null, true);// 无歌词视图
            }
        }
    };
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindState = bindService(playIntent, serviceConnection,
                Context.BIND_AUTO_CREATE);
        Intent intent = new Intent(MediaService.BROADCAST_ACTION_SERVICE);
        intent.putExtra(MediaService.INTENT_ACTIVITY,
                MediaService.ACTIVITY_MAIN);
        sendBroadcast(intent);
    }
}
