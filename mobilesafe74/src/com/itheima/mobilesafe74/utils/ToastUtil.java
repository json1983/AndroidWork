package com.itheima.mobilesafe74.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	public static void show(Context applicationContext, String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(applicationContext, msg, 0).show();
	}

}
