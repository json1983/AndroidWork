package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AToolActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atool);
		//电话归属地查询方法
				initPhoneAddress();
	}

	private void initPhoneAddress() {
		TextView tv_query_phone_address = (TextView) findViewById(R.id.tv_query_phone_address);
		tv_query_phone_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(), QueryAddressActivity.class);
				startActivity(intent);
				
			}
		});
	}
}
