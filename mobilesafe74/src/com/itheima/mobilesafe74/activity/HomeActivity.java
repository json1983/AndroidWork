package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private GridView gv_home;
	private String[] mTitleStrs;
	private int[] mDrawableIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initUI();
		// 初始化数据的方法
		initData();
	}

	private void initData() {
		// 准备数据(文字(9组),图片(9张))
		mTitleStrs = new String[] { "手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计",
				"手机杀毒", "缓存清理", "高级工具", "设置中心" };
		mDrawableIds = new int[] { R.drawable.home_safe,
				R.drawable.home_callmsgsafe, R.drawable.home_apps,
				R.drawable.home_taskmanager, R.drawable.home_netmanager,
				R.drawable.home_trojan, R.drawable.home_sysoptimize,
				R.drawable.home_tools, R.drawable.home_settings };
		gv_home.setAdapter(new MyAdapter());

	}

	private void initUI() {
		// TODO Auto-generated method stub
		gv_home = (GridView) findViewById(R.id.gv_home);

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTitleStrs.length;
		}

		@Override
		public Object getItem(int position) {
			// 条目的总数 文字组数 == 图片张数
			return mTitleStrs[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			View view = (View) View.inflate(getApplicationContext(),
					R.layout.gridview_item, null);
			TextView tv_title=(TextView)findViewById(R.id.tv_title);
			ImageView iv_icon=(ImageView)findViewById(R.id.iv_icon);
			tv_title.setText(mTitleStrs[position]);
			iv_icon.setBackgroundResource(mDrawableIds[position]);
			return view;
		}
	}
}
