package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SetupOverActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	setContentView(R.layout.activity_setup_over);
		boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
		if (setup_over) {
			//密码输入成功,并且四个导航界面设置完成----->停留在设置完成功能列表界面
			setContentView(R.layout.activity_setup_over);
		}else {
			//密码输入成功,四个导航界面没有设置完成----->跳转到导航界面第1个
			Intent intent=new Intent(this, Setup1Activity.class);		
			startActivity(intent);
			//开启了一个新的界面以后,关闭功能列表界面
			finish();
		}
	}

}
