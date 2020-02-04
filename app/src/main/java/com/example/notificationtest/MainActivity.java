package com.example.notificationtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String channelId="1";//全局唯一
    private static final String channelName="First Notification";//渠道名称
    private static final String channelDescription="First Notification Channel";//设置描述
    /*importance:重要等级
    *IMPORTANCE_NONE = 0; 不提示，不展示
    *IMPORTANCE_MIN = 1; 不提示，在通知下拉栏会展示，但是是收起的
    *IMPORTANCE_LOW = 2; 会在状态栏中显示，但不会弹窗，通知下拉栏会展示
    *IMPORTANCE_DEFAULT = 3; 会在状态栏中显示，允许有声音提示，但不会弹窗，通知下拉栏会展示
    *IMPORTANCE_HIGH = 4; 会弹窗提示，允许有提示音
    *IMPORTANCE_MAX = 5; 会弹窗提示，允许有提示音，可以使用全屏*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice=findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    public static String createNotificationChannel(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //要显示横幅通知 ，NotificationChannel在创建的时候第三个参数还要设置成NotificationManager.IMPORTANCE_HIGH，不然也弹不出来
            NotificationChannel notificationChannel=new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription(channelDescription);
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
            return channelId;
        }else{
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:
                Intent intent=new Intent(this,NotificationAcvitity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(), Objects.requireNonNull(createNotificationChannel(this)));
                Notification notification=builder
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        /*.setAutoCancel(true)//通知图标消失
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))//通知铃声
                        .setLights(Color.BLUE,1000,1000)//呼吸灯*/
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText("This is content text This is content text This is content text This is content text This is content text This is content text This is content text"))//长文本
                        //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background)))//图片背景
                        //.setPriority(NotificationCompat.PRIORITY_MAX)//优先级未实现
                        //.setFullScreenIntent(pendingIntent,true)//全屏未实现
                        .build();
                NotificationManagerCompat.from(getApplicationContext()).notify(1,notification);
                break;
            default:
                break;
        }
    }
}
