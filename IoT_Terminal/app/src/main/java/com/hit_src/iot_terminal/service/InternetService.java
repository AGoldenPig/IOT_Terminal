package com.hit_src.iot_terminal.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.hit_src.iot_terminal.GlobalVar;
import com.hit_src.iot_terminal.protocol.Modbus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class InternetService extends Service {
    private Thread mainThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                Socket socket = new Socket();
                String hostname = null;
                int port = -1;
                hostname = SettingsService.getInstance().getUpperServerAddr();
                port = SettingsService.getInstance().getUpperServerModbusPort();
                if (hostname == null || port == -1) {
                    GlobalVar.log("网络连接设置错误！");
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                try {
                    socket.connect(new InetSocketAddress(hostname, port), 5000);
                } catch (IOException e) {//无法上联到服务器
                    GlobalVar.internetConnectionStatus.set(false);
                    try {
                        sleep(3000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    continue;
                }
                GlobalVar.internetConnectionStatus.set(true);
                try {
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    while (true) {
                        byte[] cmd = new byte[8];
                        inputStream.read(cmd);
                        byte[] rep = Modbus.exec(cmd);
                        if (rep == null) {
                            break;
                        }
                        outputStream.write(rep);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        GlobalVar.internetConnectionStatus.set(false);
        mainThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mainThread.interrupt();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
