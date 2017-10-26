/**
 * 
 */
package com.itheima.mobilesafe74.receiver;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.service.LocationService;
import com.itheima.mobilesafe74.service.LockScrennService;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;
import com.itheima.mobilesafe74.utils.ToastUtil;


import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
				SmsMessage sms = SmsMessage.createFromPdu((byte[])object);
				//5,获取短信对象的基本信息
				String originatingAddress = sms.getOriginatingAddress();
				String messageBody = sms.getMessageBody();
				System.out.println("发送人:"+originatingAddress+"内容:"+messageBody);
				//6,判断是否包含播放音乐的关键字
				String phone = SpUtil.getString(context, ConstantValue.CONTACT_PHONE, "");
				if (messageBody.contains("#*alarm*#") && originatingAddress.contains(phone)) {
					//7,播放音乐(准备音乐,MediaPlayer)
					MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
					mediaPlayer.setLooping(true);
					mediaPlayer.start();
				}
				if(messageBody.contains("#*location*#") && originatingAddress.contains(phone)){
					//8,开启获取位置服务
					context.startService(new Intent(context,LocationService.class));
				}
				//远程锁屏
				if(messageBody.contains("#*lockscrenn*#")){
					//9,开启远程锁屏
					context.startService(new Intent(context,LockScrennService.class));
					 
				       
				}
				if(messageBody.contains("#*wipedate*#") && originatingAddress.contains(phone)){
					
				}
			}
		}
	}

}
