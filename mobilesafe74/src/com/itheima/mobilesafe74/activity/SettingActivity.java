package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.service.AddressService;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.ServiceUtil;
import com.itheima.mobilesafe74.utils.SpUtil;
import com.itheima.mobilesafe74.view.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initUpdate();
		initAddress();
	}

	/**
	 * 是否显示电话号码归属地的方法
	 */
	private void initAddress() {
		final SettingItemView siv_address = (SettingItemView) findViewById(R.id.siv_address);
		// 对服务是否开的状态做显示
		boolean isRunning = ServiceUtil.isRunning(this,
				"com.itheima.mobilesafe74.service.AddressService");
		siv_address.setCheck(isRunning);

		//点击过程中,状态(是否开启电话号码归属地)的切换过程
		siv_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isCheck = siv_address.isCheck();
				siv_address.setCheck(!isCheck);
				if (!isCheck) {
					//开启服务,管理吐司
					Intent intent=new Intent(getApplicationContext(), AddressService.class);
					startService(intent);
				}else {
					//关闭服务,不需要显示吐司
					stopService(new Intent(getApplicationContext(),AddressService.class));
				}
			}
		});
	}

	/**
	 * 版本更新开关
	 */
	private void initUpdate() {
		final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);

		// 获取已有的开关状态,用作显示
		boolean open_update = SpUtil.getBoolean(this,
				ConstantValue.OPEN_UPDATE, false);
		// 是否选中,根据上一次存储的结果去做决定
		siv_update.setCheck(open_update);

		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 如果之前是选中的,点击过后,变成未选中
				// 如果之前是未选中的,点击过后,变成选中

				// 获取之前的选中状态
				boolean isCheck = siv_update.isCheck();
				// 将原有状态取反,等同上诉的两部操作
				siv_update.setCheck(!isCheck);
				// 将取反后的状态存储到相应sp中
				SpUtil.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_UPDATE, !isCheck);
			}
		});
	}

}
