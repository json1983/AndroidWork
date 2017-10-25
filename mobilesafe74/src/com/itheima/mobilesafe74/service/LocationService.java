/**
 * 
 */
package com.itheima.mobilesafe74.service;

import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;

/**
 * @author Administrator
 * 
 */
public class LocationService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 获取手机的经纬度坐标
		// 1,获取位置管理者对象
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 2,以最优的方式获取经纬度坐标()
		Criteria criteria = new Criteria();
		// 允许花费
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// 指定获取经纬度的精确度
		String bestProvider = lm.getBestProvider(criteria, true);
		// 3,在一定时间间隔,移动一定距离后获取经纬度坐标
		MyLocationListener myLocationListener = new MyLocationListener();
		lm.requestLocationUpdates(bestProvider, 0, 0, new MyLocationListener());
	}

	class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// 经度
			double longitude = location.getLongitude();
			// 纬度
			double latitude = location.getLatitude();
			// 4,发送短信(添加权限)
			SmsManager sms = SmsManager.getDefault();
			String phone = SpUtil.getString(getApplicationContext(),
					ConstantValue.CONTACT_PHONE, "");
			if (!TextUtils.isEmpty(phone)) {
				sms.sendTextMessage(phone, null, "longitude = " + longitude
						+ ",latitude = " + latitude, null, null);
			}

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
