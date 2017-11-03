/**
 * 
 */
package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * @author Administrator
 * 
 */
public class ToastLocationActivity extends Activity {
	private Button bt_bottom;
	private Button bt_top;
	private ImageView iv_drag;
	private WindowManager mWM;
	private int mScreenHeight;
	private int mScreenWidth;
	private long[] mHits = new long[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toast_location);
		initUI();
	}

	private void initUI() {
		iv_drag = (ImageView) findViewById(R.id.iv_drag);
		bt_top = (Button) findViewById(R.id.bt_top);
		bt_bottom = (Button) findViewById(R.id.bt_bottom);
		mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
		mScreenWidth = mWM.getDefaultDisplay().getWidth();
		mScreenHeight = mWM.getDefaultDisplay().getHeight();
		int locationX = SpUtil.getInt(getApplicationContext(),
				ConstantValue.LOCATION_X, 0);
		int locationY = SpUtil.getInt(getApplicationContext(),
				ConstantValue.LOCATION_Y, 0);

		// 左上角坐标作用在iv_drag上
		// iv_drag在相对布局中,所以其所在位置的规则需要由相对布局提供

		// 指定宽高都为WRAP_CONTENT
		LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		//将左上角的坐标作用在iv_drag对应规则参数上
		layoutParams.leftMargin=locationX;
		layoutParams.topMargin=locationY;
		//将以上规则作用在iv_drag上
		iv_drag.setLayoutParams(layoutParams);
		if (locationY>mScreenHeight/2) {
			bt_bottom.setVisibility(View.INVISIBLE);
			bt_top.setVisibility(View.VISIBLE);
		}else {
			bt_bottom.setVisibility(View.VISIBLE);
			bt_top.setVisibility(View.INVISIBLE);
		}
		iv_drag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
				
				
			}
		});
	}

}
