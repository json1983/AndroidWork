/**
 * 
 */
package com.itheima.mobilesafe74.receiver;

import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * sim卡变更,发送短信到绑定的安全号码
 * 
 * @author Administrator
 * 
 */
public class BootReceiver extends BroadcastReceiver {
	private static final String tag = "BootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.i(tag, "重启手机成功了,并且监听到了相应的广播......");
		// 1,获取开机后手机的sim卡的序列号
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNumber = tm.getSimSerialNumber();
		// 2,sp中存储的序列卡号
		String sim_number = SpUtil.getString(context, ConstantValue.SIM_NUMBER,
				"");
		if (!TextUtils.isEmpty(sim_number)) {
			// 3,比对不一致
			if (!sim_number.equals(simSerialNumber)) {
				// 4,发送短信给选中联系人号码
				String Phone = SpUtil.getString(context,
						ConstantValue.CONTACT_PHONE, "");
				if (!TextUtils.isEmpty(Phone)) {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(Phone, null, "sim change!!!",
							null, null);
				}

			}
		}

	}
}
