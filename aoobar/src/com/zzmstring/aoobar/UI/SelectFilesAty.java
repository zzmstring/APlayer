package com.zzmstring.aoobar.UI;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zzmstring.aoobar.R;
import com.zzmstring.aoobar.base.BaseActivity;
import com.zzmstring.aoobar.openfiledemo.CallbackBundle;
import com.zzmstring.aoobar.openfiledemo.OpenFileDialog;
import com.zzmstring.aoobar.utils.ExLog;
import com.zzmstring.aoobar.view.PagerSlidingTabStrip.select.MyFileSelectListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzmstring on 2015/3/9.
 */
public class SelectFilesAty extends BaseActivity implements CallbackBundle {
    @ViewInject(R.id.ll_main)
    LinearLayout ll_main;
    @ViewInject(R.id.ll_second)
    LinearLayout ll_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_selectfile);
        ViewUtils.inject(this);
        Map<String, Integer> images = new HashMap<String, Integer>();
        // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
        images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);	// 根目录图标
        images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);	//返回上一层的图标
        images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);	//文件夹图标
        images.put("wav", R.drawable.filedialog_wavfile);	//wav文件图标
        images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
        MyFileSelectListView myFileSelectListView=new MyFileSelectListView(this,0,this,".mp3;",images);
//        SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.filedialogitem, new String[]{"img", "name", "path"}, new int[]{R.id.filedialogitem_img, R.id.filedialogitem_name, R.id.filedialogitem_path});
//        this.setAdapter(adapter);
        ll_second.addView(myFileSelectListView,0);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void callback(Bundle bundle) {
        String filepath = bundle.getString("path");
//                            setTitle(filepath); // 把文件路径显示在标题上
        String filename=bundle.getString("name");
        ExLog.l("selected file is >>>>>" + filepath+"<filename>"+filename);

    }
}
