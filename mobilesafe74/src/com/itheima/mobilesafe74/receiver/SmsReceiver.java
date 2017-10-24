/**
 * 
 */
package com.itheima.mobilesafe74.receiver;

import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;



/**
 * 播放报警音乐
 * @author Administrator
 *
 */
public class SmsReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {
		//1,判断是否开启了防盗保护
		boolean open_security = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY, false);
		if (open_security) {
			//2,获取短信内容
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			//3,循环遍历短信过程
			for (Object object:objects) {
				//4,获取短信对象
				SmsMessage sms = SmsMessage.createFromPdu((byte[])object)
			}
		}
	}

}
