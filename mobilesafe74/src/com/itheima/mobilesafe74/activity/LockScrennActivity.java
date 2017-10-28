/**
 * 
 */
package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.receiver.MyDeviceAdminReceiver;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Administrator
 * 
 */
public class LockScrennActivity extends Activity {
	private ComponentName componentName;
	private DevicePolicyManager devicePolicyManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_lock_screnn);
		// 申请权限
		componentName = new ComponentName(getApplicationContext(),
				MyDeviceAdminReceiver.class);
		// 设备安全管理服务
		DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

		boolean isAdminActive = devicePolicyManager
				.isAdminActive(componentName);
		System.out.println("isAdminActive!!!!!!------->>>>>" + isAdminActive);
		// #*lockscrenn*# 锁屏 #*wipedate*#
		if (isAdminActive) {
			Intent intent = getIntent();
			String messageBody = intent.getStringExtra("messageBody");
			System.out.println("messageBody>>>>>>>>>" + messageBody);
			if (messageBody.equals("#*lockscrenn*#")) {
				// 一键锁屏
				devicePolicyManager.lockNow();
				//设置密码
				devicePolicyManager.resetPassword("122333", 0);
				//清空密码
				//devicePolicyManager.resetPassword("", 0);
				finish();
			} else if (messageBody.equals("#*wipedate*#")) {
				// 清除数据,恢复出厂设置
				devicePolicyManager.wipeData(0);
				//清除Sdcard上的数据
	            //devicePolicyManager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
				finish();
			}
		} else {
			// 开启设备管理器的activity
			Intent intent2 = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent2.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					componentName);
			intent2.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "设备管理器");
			startActivity(intent2);
			finish();

		}
	}

}
