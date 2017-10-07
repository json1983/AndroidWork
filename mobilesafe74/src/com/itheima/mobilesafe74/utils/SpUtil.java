package com.itheima.mobilesafe74.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;

public class SpUtil {
	private static SharedPreferences sp;
	/**
	 * 读取boolean标示从sp中
	 * @param ctx	上下文环境
	 * @param key	存储节点名称
	 * @param defValue	没有此节点默认值
	 * @return		默认值或者此节点读取到的结果
	 */
	public static String getString(Context ctx,
			String key, String defValue) {
		if(sp==null){
			sp=ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}

}
