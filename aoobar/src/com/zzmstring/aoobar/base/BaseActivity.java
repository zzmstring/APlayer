package com.zzmstring.aoobar.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public  class BaseActivity extends FragmentActivity implements View.OnClickListener{
    /**
     * 记录所有活动的Activity
     */

    private static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    /**
     * 记录处于前台的Activity
     */
    private static BaseActivity mForegroundActivity = null;
    //顶部导航栏
    protected RelativeLayout title_bar;
    //导航栏中的文字
    protected TextView title_bar_text;
    //导航栏中左侧按钮
    protected TextView title_bar_left;
    //导航栏从右侧按钮
    protected TextView title_bar_right;
    //导航栏右侧第二个按钮
    protected TextView title_bar_right_second;
    //标题栏左侧图标
    Drawable drawableLeft;
    //标题栏右侧图标
    Drawable drawableRight;
    //标题栏右侧第二个图标
    Drawable drawableThird;

    InputMethodManager manager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initListener();
		initData();
	}
	
	public  void initView(){

    }

	public  void initListener(){

    }

	public  void initData(){

    }
    public static List<BaseActivity> getmActivities() {
        return mActivities;
    }
    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        mActivities.clear();
    }
    /**
     * 关闭所有Activity，除了参数传递的Activity
     */
    public static void finishAll(BaseActivity except) {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            if (activity != except) activity.finish();
        }
    }
    /**
     * 关闭所有Activity, 除了第一个
     */
    public static void finishAllButMain() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (int i = 0; i < copy.size(); i++) {
            String localClassName = copy.get(i).getLocalClassName();
            if (localClassName.equals("MainActivity")) {
                continue;
            } else {
                copy.get(i).finish();
            }
        }
    }
    /**
     * 是否有启动的Activity
     */
    public static boolean hasActivity() {
        return mActivities.size() > 0;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }
    /**
     * 获取当前处于栈顶的activity，无论其是否处于前台
     */
    public static BaseActivity getCurrentActivity() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        if (copy.size() > 0) {
            return copy.get(copy.size() - 1);
        }
        return null;
    }
    //收起键盘
    private void softInput() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
//                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
    }



    /**
     * 初始化Actionbar, 左右无按钮
     *
     * @param str
     */
//    protected void initActionBar(String str) {
//        //顶部导航栏
//        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
//        //导航栏中的文字
//        title_bar_text = (TextView) findViewById(R.id.title_bar_text);
//        //导航栏中左侧按钮
//        title_bar_left = (TextView) findViewById(R.id.title_bar_left);
//        //导航栏从右侧按钮
//        title_bar_right = (TextView) findViewById(R.id.title_bar_right);
//
//        //初始化标题文字
//        if (!TextUtils.isEmpty(str)) {
//            Animation animation = AnimationUtils.loadAnimation(UIUtils.getContext(), R.anim.text_fade_in);
//            title_bar_text.setAnimation(animation);
//            title_bar_text.setText(str);
//        }
//        title_bar_left.setVisibility(View.GONE);
//        title_bar_right.setVisibility(View.GONE);
//    }

    /**
     * 初始化Actionbar, 左右有按钮
     *
     * @param str        中间文字
     * @param leftResID  左侧tv的图片资源id, 为0则不显示这个按钮
     * @param rightResID 右侧tv的图片资源id, 为0则不现实这个按钮
     */
//    protected void initActionBar(String str, int leftResID, int rightResID) {
//        //顶部导航栏
//        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
//        //导航栏中的文字
//        title_bar_text = (TextView) findViewById(R.id.title_bar_text);
//        //导航栏中左侧按钮
//        title_bar_left = (TextView) findViewById(R.id.title_bar_left);
//        //导航栏从右侧按钮
//        title_bar_right = (TextView) findViewById(R.id.title_bar_right);
//
//        //初始化标题文字
//        if (!TextUtils.isEmpty(str)) {
//            title_bar_text.setText(str);
//        }
//        if (leftResID != 0) {
//            title_bar_left.setVisibility(View.VISIBLE);
//            drawableLeft = getResources().getDrawable(leftResID);
//            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
//            title_bar_left.setCompoundDrawables(null, drawableLeft, null, null);
//        } else {
//            title_bar_left.setVisibility(View.GONE);
//        }
//
//        if (rightResID != 0) {
//            title_bar_right.setVisibility(View.VISIBLE);
//            drawableRight = getResources().getDrawable(rightResID);
//            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
//            title_bar_right.setCompoundDrawables(null, drawableRight, null, null);
//        } else {
//            title_bar_right.setVisibility(View.GONE);
//        }
//
//        title_bar_left.setOnClickListener(this);
//        title_bar_right.setOnClickListener(this);
//    }

    /**
     * 初始化顶栏右边第二个按钮
     *
     * @param resId
     */
//    protected void initActionBarThirdIcon(int resId) {
//        if (resId != 0) {
//            title_bar_right_second = (TextView) findViewById(R.id.title_bar_right_second);
//            title_bar_right_second.setVisibility(View.VISIBLE);
//            drawableThird = getResources().getDrawable(resId);
//            drawableThird.setBounds(0, 0, drawableThird.getMinimumWidth(), drawableThird.getMinimumHeight());
//            title_bar_right_second.setCompoundDrawables(null, drawableThird, null, null);
//            title_bar_right_second.setOnClickListener(this);
//        }
//    }

    /**
     * 初始化带文字的顶栏
     *
     * @param title      顶栏标题
     * @param textLeft   顶栏左边文字
     * @param textRight  顶栏右边文字
     * @param leftResID  顶栏左边TextView的DrawableLeft
     * @param rightResID 顶栏右边TextView的DrawableLeft
     */
//    protected void initActionBarWithText(String title, String textLeft, String textRight, int leftResID, int rightResID) {
//        //顶部导航栏
//        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
//        //导航栏中的文字
//        title_bar_text = (TextView) findViewById(R.id.title_bar_text);
//        //导航栏中左侧按钮
//        title_bar_left = (TextView) findViewById(R.id.title_bar_left);
//        //导航栏从右侧按钮
//        title_bar_right = (TextView) findViewById(R.id.title_bar_right);
//        //初始化标题文字
//        if (!TextUtils.isEmpty(title)) {
//            title_bar_text.setText(title);
//        }
//        //初始化标题栏左侧的文字和图标
//        if (!TextUtils.isEmpty(textLeft)) {
//            title_bar_left.setVisibility(View.VISIBLE);
//            title_bar_left.setText(textLeft);
//            title_bar_left.setTextColor(Color.WHITE);
//            //title_bar_left.setBackgroundResource(R.drawable.rounded_action_bar_textview);
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) title_bar_left.getLayoutParams();
//            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
//            layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
//            layoutParams.leftMargin = UIUtils.dip2px(5);
//            title_bar_left.setPadding(UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5));
//            title_bar_left.setLayoutParams(layoutParams);
//        }
//
//        if (!TextUtils.isEmpty(textRight)) {
//            title_bar_right.setVisibility(View.VISIBLE);
//            title_bar_right.setText(textRight);
//            title_bar_right.setTextColor(Color.WHITE);
//            //title_bar_right.setBackgroundResource(R.drawable.rounded_action_bar_textview);
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) title_bar_right.getLayoutParams();
//            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
//            layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
//            layoutParams.rightMargin = UIUtils.dip2px(5);
//            title_bar_right.setPadding(UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5));
//            title_bar_right.setLayoutParams(layoutParams);
//        }
//
//        //判断是否给了drawableLeft的图片id
//        if (leftResID != 0) {
//            title_bar_left.setVisibility(View.VISIBLE);
//            drawableLeft = getResources().getDrawable(leftResID);
//            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
//            title_bar_left.setCompoundDrawables(null, drawableLeft, null, null);
//        }
//        //判断是否给了drawableLeft的图片id
//        if (rightResID != 0) {
//            title_bar_right.setVisibility(View.VISIBLE);
//            drawableRight = getResources().getDrawable(rightResID);
//            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
//            title_bar_right.setCompoundDrawables(null, drawableRight, null, null);
//        }
//
//        if (TextUtils.isEmpty(textLeft) && leftResID == 0) {
//            title_bar_left.setVisibility(View.GONE);
//        }
//
//        if (TextUtils.isEmpty(textRight) && rightResID == 0) {
//            title_bar_right.setVisibility(View.GONE);
//        }
//
//        title_bar_left.setOnClickListener(this);
//        title_bar_right.setOnClickListener(this);
//    }

    /**
     * 推出应用
     */
    public void exitApp() {
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onClick(View v) {

    }



}
