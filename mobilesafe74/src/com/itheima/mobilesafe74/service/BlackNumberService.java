package com.itheima.mobilesafe74.service;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.itheima.mobilesafe74.db.dao.BlackNumberDao;

import android.R.integer;
import android.R.string;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo.State;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.NoCopySpan.Concrete;

public class BlackNumberService extends Service {

	private BlackNumberDao mDao;
	private InnerSmsReceiver mInnerSmsReceiver;
	private TelephonyManager mTM;
	private MyPhoneStateListener mPhoneStateListener;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mDao = BlackNumberDao.getInstance(getApplicationContext());
		// 拦截短信
		IntentFilter intentFilter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		intentFilter.setPriority(1000);
		mInnerSmsReceiver = new InnerSmsReceiver();
		registerReceiver(mInnerSmsReceiver, intentFilter);
		// 监听电话的状态
		// 1,电话管理者对象

		mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// 2,监听电话状态
		mPhoneStateListener = new MyPhoneStateListener();
		mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:

				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:

				break;

			case TelephonyManager.CALL_STATE_RINGING:
				//挂断电话 	aidl文件中去了
//				mTM.endCall();
				endCall(incomingNumber);
				break;

			
			}
			super.onCallStateChanged(state, incomingNumber);
		}

	}

	class InnerSmsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 获取短信内容,获取发送短信电话号码,如果此电话号码在黑名单中,并且拦截模式也为1(短信)或者3(所有),拦截短信
			// 1,获取短信内容
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			// 2,循环遍历短信过程
			for (Object object : objects) {
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
				// 4,获取短信对象的基本信息
				String originatingAddress = sms.getOriginatingAddress();
				String messageBody = sms.getMessageBody();
				int mode = mDao.getMode(originatingAddress);
				if (mode == 1 || mode == 3) {
					// 拦截短信(android 4.4版本失效 短信数据库,删除)
					abortBroadcast();
				}
			}

		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void endCall(String phone) {
		// TODO Auto-generated method stub
int mode = mDao.getMode(phone);
		
		if(mode == 2 || mode == 3){
			//ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
			//ServiceManager此类android对开发者隐藏,所以不能去直接调用其方法,需要反射调用
			
			try {
				//1,获取ServiceManager字节码文件
				Class<?> clazz = Class.forName("android.os.ServiceManager");
				//2,获取方法
				Method method = clazz.getMethod("getService", string.class);
				//3,反射调用此方法
				IBinder iBinder = (IBinder)method.invoke(null, "TELEPHONY_SERVICE");
				//4,调用获取aidl文件对象方法
				ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
				//5,调用在aidl中隐藏的endCall方法
				iTelephony.endCall();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

}
