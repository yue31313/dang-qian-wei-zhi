package com.liucanwen.baidulocation;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{
	public static String TAG = "LocTestDemo";

	private BroadcastReceiver broadcastReceiver;
	public static String LOCATION_BCR = "location_bcr";
	
	private Button locBtn;
	private TextView locInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();
		
		initializeViews();
		
		initializeListeners();
	}

	private void initialize()
	{
		registerBroadCastReceiver();
	}
	
	private void initializeViews()
	{
		locBtn = (Button) findViewById(R.id.location);
		locInfo = (TextView) findViewById(R.id.location_info);
	}
	
	private void initializeListeners()
	{
		locBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				locInfo.setText("定位中...");
				
				MyApplication.getInstance().requestLocationInfo();
			}
		});
	}

	/**
	 * 注册一个广播，监听定位结果
	 */
	private void registerBroadCastReceiver()
	{
		broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				String address = intent.getStringExtra("address");
				
				locInfo.setText(address);
			}
		};
		IntentFilter intentToReceiveFilter = new IntentFilter();
		intentToReceiveFilter.addAction(LOCATION_BCR);
		registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

}
