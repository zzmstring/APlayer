package com.zzmstring.aoobar.utils;

import android.util.Log;

public class ExLog {

	static final String TAG = ExLog.class.getSimpleName();

	/**
	 * 输出调试信息(System.out)，并在调试输入信息中附带当前代码在哪个类哪一行的额外数据。
	 * @param message 调试信息
	 */
	public static void l(String message){
		StackTraceElement ele = Thread.currentThread().getStackTrace()[3];
		int line = ele.getLineNumber();
		String clazz = ele.getClassName();
		System.out.println(":::: @"+clazz+" -> "+line+" :::: "+message);
	}

	/**
	 * 取得当前代码所在的方法名
	 * @return 当前方法名
	 */
	public static String getCurrentMethodName(){
		// 0 getThreadStackTrce
		// 1 getStackTrace
		// 2 * this method: getCurrentMethodName
		// 3 your method
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}
	
	/**
	 * 输出方法调用链
	 * @param object 对象
	 */
	public static void logCurrentMethodChain(Object object){
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		long time = System.currentTimeMillis();
		Log.d(TAG, String.format("###### Object(%s) Method Chain ###### @Time( %d )", object.getClass().getSimpleName(), time));
		for(StackTraceElement e : es){
			String msg = String.format("### Method Chain ### Caller:%s  ->：%s", e.getClassName(),e.getMethodName());
			Log.d(TAG, msg);
		}
	}
	
	/**
	 * 输出当前方法调用
	 * @param object 对象
	 */
	public static void logCurrentMethod(Object object){
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		long time = System.currentTimeMillis();
		String msg = String.format("###### Calling Method ###### Object(%s) -> %s @Time( %d )", object.getClass().getSimpleName(),methodName, time);
		Log.d(TAG, msg);
	}
	
}