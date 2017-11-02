/**
 * 
 */
package com.itheima.mobilesafe74.service;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Administrator
 * 
 */
public class AddressService extends Service {
	public static final String tag = "AddressService";
	private TelephonyManager mTM;
	private MyPhoneStateListener mPhoneStateListener;
	private View mViewToast;
	private WindowManager mWM;
	private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
	private TextView tv_toast;
	protected int mScreenHeight;
	protected int mScreenWidth;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 第一次开启服务以后,就需要去管理吐司的显示
		// 电话状态的监听(服务开启的时候,需要去做监听,关闭的时候电话状态就不需要监听)
		// 1,电话管理者对象
		mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// 2,监听电话状态
		mPhoneStateListener = new MyPhoneStateListener();
		mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		// 获取窗体对象
		mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
	}

	class MyPhoneStateListener extends PhoneStateListener {
		// 3,手动重写,电话状态发生改变会触发的方法
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				//空闲状态,没有任何活动(移除吐司)
				Log.i(tag, "挂断电话,空闲了.......................");
				//挂断电话的时候窗体需要移除吐司
				if (mWM!=null && mViewToast!=null) {
					mWM.removeView(mViewToast)
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				//摘机状态，至少有个电话活动。该活动或是拨打（dialing）或是通话
				
			break;
			case TelephonyManager.CALL_STATE_RINGING:
				//响铃(展示吐司)
				Log.i(tag, "响铃了.......................");
				showToast(incomingNumber);
				break;

			}
			
			super.onCallStateChanged(state, incomingNumber);
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void showToast(String incomingNumber) {
		final WindowManager.LayoutParams params = mParams;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		// | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE 默认能够被触摸
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		// 在响铃的时候显示吐司,和电话类型一致
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.setTitle("Toast");
		// 指定吐司的所在位置(将吐司指定在左上角)
		params.gravity = Gravity.LEFT + Gravity.TOP;
		// 吐司显示效果(吐司布局文件),xml-->view(吐司),将吐司挂在到windowManager窗体上
		mViewToast = View.inflate(getApplicationContext(), R.layout.toast_view,
				null);
		tv_toast = (TextView) mViewToast.findViewById(R.id.tv_toast);
		mViewToast.setOnTouchListener(new OnTouchListener() {
			private int startX;
			private int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				             
				case MotionEvent.ACTION_DOWN:  //按下
					startX=(int)event.getRawX();
					startY=(int)event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE: //移动
					int moveX = (int) event.getRawX();
					int moveY = (int) event.getRawY();
					
					int disX = moveX-startX;
					int disY = moveY-startY;
					params.x=disX+params.x;
					params.y=disY+params.y;
					//容错处理
					if(params.x<0){
						params.x = 0;
					}
					
					if(params.y<0){
						params.y=0;
					}
					
					if(params.x>mScreenWidth-mViewToast.getWidth()){
						params.x = mScreenWidth-mViewToast.getWidth();
					}
					
					if(params.y>mScreenHeight-mViewToast.getHeight()-22){
						params.y = mScreenHeight-mViewToast.getHeight()-22;
					}
					//告知窗体吐司需要按照手势的移动,去做位置的更新
					mWM.updateViewLayout(mViewToast, params);
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();

					break;
				case MotionEvent.ACTION_UP: //抬起
					SpUtil.putInt(getApplicationContext(),ConstantValue.LOCATION_X, params.x);
					SpUtil.putInt(getApplicationContext(),ConstantValue.LOCATION_Y, params.y);

					break;
				
				}
				return true;
			}
		});
		
	}
}
