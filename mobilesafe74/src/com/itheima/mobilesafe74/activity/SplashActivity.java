package com.itheima.mobilesafe74.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.StreamUtil;
import com.itheima.mobilesafe74.utils.ToastUtil;

import android.net.ParseException;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private final class HandlerE extends Handler {
	}

	/**
	 * 更新新版本的状态码
	 */
	protected static final int UPDATE_VERSION = 100;
	/**
	 * 进入应用程序主界面状态码
	 */
	protected static final int ENTER_HOME = 101;

	/**
	 * url地址出错状态码
	 */
	protected static final int URL_ERROR = 102;
	protected static final int IO_ERROR = 103;
	protected static final int JSON_ERROR = 104;
	private TextView tv_version_name;
	private int mLocalVersionCode;
	protected String mVersionDes;
	protected String mDownloadUrl;
	protected static final String tag = "SplashActivity";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_VERSION:
				// 弹出对话框,提示用户更新
				showUpdateDialog();
			case ENTER_HOME:
				// 进入应用程序主界面,activity跳转过程
				enterHome();
				break;

			case URL_ERROR:
				ToastUtil.show(getApplicationContext(), "url异常");
				enterHome();
				break;

			case IO_ERROR:
				ToastUtil.show(getApplicationContext(), "读取异常");
				enterHome();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json解析异常");
				enterHome();
				break;

			default:
				enterHome();
				break;
			}
		}

		private void enterHome() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(this,HomeActivity.class);
			startActivity(intent);
			//在开启一个新的界面后,将导航界面关闭(导航界面只可见一次)
			finish();

		}

		private void showUpdateDialog() {
			// TODO Auto-generated method stub

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// 初始化UI
		initUI();
		// 初始化数据
		initData();

	}

	private void initData() {
		// TODO Auto-generated method stub
		// 1,应用版本名称
		tv_version_name.setText("懒人手机卫士_版本名称:" + getVersionName()
				+ "\n\nby:王超 TEL:13766242758");
		tv_version_name.setText("版本名称:" + getVersionName());
		// 检测(本地版本号和服务器版本号比对)是否有更新,如果有更新,提示用户下载(member)
		// 2,获取本地版本号
		mLocalVersionCode = getVersionCode();
		// 3,获取服务器版本号(客户端发请求,服务端给响应,(json,xml))
		// http://www.oxxx.com/update74.json?key=value 返回200 请求成功,流的方式将数据读取下来
		// json中内容包含:
		/*
		 * 更新版本的版本名称 新版本的描述信息 服务器版本号 新版本apk下载地址
		 */
		checkVersion();

	}

	private void checkVersion() {
		new Thread() {
			public void run() {

				// 发送请求获取数据,参数则为请求json的链接地址
				// http://192.168.1.100:8080/update74.json 测试阶段不是最优
				// 仅限于模拟器访问电脑tomcat
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				// 1,封装url地址

				try {
					URL url = new URL("http://10.0.2.2:8080/update74.json");
					// 2,开启一个链接
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					// 3,设置常见请求参数(请求头)
					// 请求超时
					connection.setConnectTimeout(2000);
					// 读取超时
					connection.setReadTimeout(2000);
					// 默认就是get请求方式
					connection.setRequestMethod("GET");
					// 4,获取请求成功响应码

					if (connection.getResponseCode() == 200) {
						// //5,以流的形式,将数据获取下来
						InputStream is = connection.getInputStream();
						// 6,将流转换成字符串(工具类封装)
						String json = StreamUtil.streamToString(is);

						Log.i(tag, json);
						// 7,json解析
						JSONObject jsonObject = new JSONObject();
						String versionName = jsonObject
								.getString("versionName");
						String mVersionDes = jsonObject.getString("versionDes");
						String versionCode = jsonObject
								.getString("versionCode");
						String mDownloadUrl = jsonObject
								.getString("downloadUrl");
						// 日志打印
						Log.i(tag, versionName);
						Log.i(tag, mVersionDes);
						Log.i(tag, versionCode);
						Log.i(tag, mDownloadUrl);
						// 8,比对版本号(服务器版本号>本地版本号,提示用户更新)
						if (mLocalVersionCode < Integer.parseInt(versionCode)) {
							// 提示用户更新,弹出对话框(UI),消息机制
							msg.what = UPDATE_VERSION;

						} else {
							// 进入应用程序主界面
							msg.what = ENTER_HOME;
						}

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// 指定睡眠时间,请求网络的时长超过4秒则不做处理
					// 请求网络的时长小于4秒,强制让其睡眠满4秒钟
					long endTime = System.currentTimeMillis();
					if (endTime - startTime < 4000) {
						Thread.sleep(4000 - (endTime - startTime));
					}
					mHandler.sendMessage(msg);
				}

			};
		}.start();

	}

	/**
	 * 返回版本号
	 * 
	 * @return 非0 则代表获取成功
	 */
	private int getVersionCode() {
		// 1,包管理者对象packageManager
		PackageManager pm = getPackageManager();
		// 2,从包的管理者对象中,获取指定包名的基本信息(版本名称,版本号),传0代表获取基本信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取版本名称:清单文件中
	 * 
	 * @return应用版本名称 返回null代表异常
	 */
	private String getVersionName() {
		// TODO Auto-generated method stub
		// 1,包管理者对象packageManager
		PackageManager pm = getPackageManager();
		// 2,从包的管理者对象中,获取指定包名的基本信息(版本名称,版本号),传0代表获取基本信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 3,获取版本名称
			return packageInfo.versionName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private void initUI() {
		// TODO Auto-generated method stub
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);
	}

}
