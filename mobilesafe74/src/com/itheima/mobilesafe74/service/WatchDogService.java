package com.itheima.mobilesafe74.service;

import com.itheima.mobilesafe74.db.dao.AppLockDao;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WatchDogService extends Service {
	private AppLockDao mDao;
	private boolean isWatch;

	@Override
	public void onCreate() {
		// 维护一个看门狗的死循环,让其时刻监测现在开启的应用,是否为程序锁中要去拦截的应用
		mDao = AppLockDao.getInstance(this);
		isWatch = true;
		watch();
		super.onCreate();
	}

	private void watch() {
		// 1,子线程中,开启一个可控死循环
		new Thread() {
			public void run() {
				
			};
		}.start();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
