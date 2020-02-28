package com.hit_src.iot_terminal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hit_src.iot_terminal.R;
import com.hit_src.iot_terminal.ui.handler.OverviewStatusHandler;
import com.hit_src.iot_terminal.tools.MessageThread;

public class OverviewActivity extends AppCompatActivity {
    /**
     * OverviewActivity是“系统概述”界面的活动类
     */
    public static OverviewStatusHandler handler;
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        handler=new OverviewStatusHandler(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        MessageThread.sendMessage(handler,OverviewStatusHandler.SENSOR_UPDATE);
        MessageThread.sendMessage(handler,OverviewStatusHandler.INTERNET_UPDATE);
    }
}