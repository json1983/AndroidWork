/**
 * 
 */
package com.itheima.mobilesafe74.service;

import com.itheima.mobilesafe74.receiver.MyDeviceAdminReceiver;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Administrator
 *远程锁屏
 */
public class LockScrennService extends Service {
	private ComponentName componentName;
	private DevicePolicyManager devicePolicyManager;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//申请权限
		componentName=new ComponentName(getApplicationContext(), MyDeviceAdminReceiver.class);
		//设备安全管理服务
		DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		
		boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);
		System.out.println("isAdminActive!!!!!!------->>>>>"+isAdminActive);
		if (isAdminActive) {
			devicePolicyManager.lockNow();//一键锁屏
			stopSelf();
		}else {
			//开启设备管理器的activity
			
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"设备管理器");
			startActivity(intent);
			
		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
