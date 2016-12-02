package com.mangues.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import mangues.com.rxandroiddemo.R;

public class RemoteViewActivity extends AppCompatActivity {
    private Notification mNotification;
    private NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view);

        starRemoveViewtNotification();


    }




    private void startNotification(){
        Notification mNotification = new Notification.Builder(this)
                .setContentTitle("这是标题 ")
                .setContentText("这是内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        //4.获取NotificationManager
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,mNotification);

    }


    private void starRemoveViewtNotification(){

        RemoteViews mRemoteViews=new RemoteViews("mangues.com.rxandroiddemo", R.layout.delete_btn);


        //2.构建一个打开Activity的PendingIntent
        Intent intent=new Intent(RemoteViewActivity.this,RemoteViewActivity.class);
        PendingIntent mPendingIntent=PendingIntent.getActivity(RemoteViewActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //3.创建一个Notification
        mNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(mPendingIntent)
                .setContent(mRemoteViews)
                .build();


        //4.获取NotificationManager
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,mNotification);

    }
}
