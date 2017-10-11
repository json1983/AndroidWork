package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;
import com.itheima.mobilesafe74.utils.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
		// 九宫格控件设置数据适配器(等同ListView数据适配器)
		gv_home.setAdapter(new MyAdapter());
		// 注册九宫格单个条目点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			// 点中列表条目索引position
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					// 开启对话框
					showDialog();
					break;
				case 8:
					System.out.println("position=====" + position);
					break;

				}

			}

		});

	}

	protected void showDialog() {
		// TODO Auto-generated method stub
		// 判断本地是否有存储密码(sp 字符串)
		String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
		if (TextUtils.isEmpty(psd)) {
			// 1,初始设置密码对话框
			showSetPsdDialog();
		} else {
			// 2,确认密码对话框
			showConfirmPsdDialog();
		}

	}
	/**
	 * 确认密码对话框
	 */
	private void showConfirmPsdDialog() {
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(getApplicationContext(), R.layout.dialog_confirm_psd, null);
		dialog.setView(view);
		dialog.show();
		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText et_confirm_psd=(EditText)findViewById(R.id.et_confirm_psd);
				String confirmPsd =et_confirm_psd.getText().toString();
				if(!TextUtils.isEmpty(confirmPsd)){
					//不为空,去XML文件中比对是否一致
					String psd=SpUtil.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");
					if (psd.equals(confirmPsd)) {
						//一致，//进入应用手机防盗模块,开启一个新的activity
						Intent intent=new Intent(getApplicationContext(), TestActivity.class);
						startActivity(intent);
						dialog.dismiss();
						
					}else {
						//不一致
						ToastUtil.show(getApplicationContext(), "确认密码错误");
						
					}
					
				}else {
					//为空提示用户密码输入有为空的情况
					ToastUtil.show(getApplicationContext(), "请输入密码");
				}
				
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});
	}
	
	/**
	 * 设置密码对话框
	 */
	private void showSetPsdDialog() {
		// 因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
		// view是由自己编写的xml转换成的view对象xml----->view
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(getApplicationContext(),
				R.layout.dialog_set_psd, null);
		// 让对话框显示一个自己定义的对话框界面效果
		dialog.setView(view);
		dialog.show();
		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		// 点击事件
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et_set_psd = (EditText) view
						.findViewById(R.id.et_set_psd);
				EditText et_confirm_psd = (EditText) view
						.findViewById(R.id.et_confirm_psd);
				String psd = et_set_psd.getText().toString();
				String confirmPsd = et_confirm_psd.getText().toString();
				// 两次密码都不能为空
				if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)) {
					if (psd.equals(confirmPsd)) {
						// 两次密码一致
						System.out.println(psd + "++++++" + confirmPsd
								+ "===========");
						// 进入应用手机防盗模块,开启一个新的activity
						Intent intent = new Intent(getApplicationContext(),
								TestActivity.class);
						startActivity(intent);
						dialog.dismiss();
						// 保存配置
						SpUtil.putString(getApplicationContext(),
								ConstantValue.MOBILE_SAFE_PSD, psd);
					} else {
						// 两次密码不一致
						ToastUtil.show(getApplicationContext(), "确认密码错误");
					}

				} else {
					// 两次密码都有一个为空
					ToastUtil.show(HomeActivity.this, "请输入密码");
				}

			}

		}

		);
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});

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

			View view = View.inflate(getApplicationContext(),
					R.layout.gridview_item, null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

			tv_title.setText(mTitleStrs[position]);
			iv_icon.setBackgroundResource(mDrawableIds[position]);

			return view;
		}
	}
}
