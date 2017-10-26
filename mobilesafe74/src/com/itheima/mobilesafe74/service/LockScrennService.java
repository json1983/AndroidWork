/**
 * 
 */
package com.itheima.mobilesafe74.service;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Administrator
 *远程锁屏
 */
public class LockScrennService extends Service {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		DevicePolicyManager   dmp = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
	       dmp.lockNow();//一键锁屏
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
