package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		//九宫格控件设置数据适配器(等同ListView数据适配器)
		gv_home.setAdapter(new MyAdapter());
		//注册九宫格单个条目点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener(){
			//点中列表条目索引position
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					//开启对话框
					showDialog();
					break;
				case 8:
					System.out.println("position====="+position);
					break;

				
				}
				
			}

			
			
		} );

	}

	protected void showDialog() {
		// TODO Auto-generated method stub
		//判断本地是否有存储密码(sp	字符串)
		String psd=SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
		if(TextUtils.isEmpty(psd)){
			//1,初始设置密码对话框
			showSetPsdDialog();
		}else{
			//2,确认密码对话框
			showConfirmPsdDialog();
		}
		
	}

	private void showConfirmPsdDialog() {
		// TODO Auto-generated method stub
		
	}

	private void showSetPsdDialog() {
		// TODO Auto-generated method stub
		
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
		
			View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			
			tv_title.setText(mTitleStrs[position]);
			iv_icon.setBackgroundResource(mDrawableIds[position]);
			
			return view;
		}
	}
}
