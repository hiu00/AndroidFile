package com.example.mymusic.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.mymusic.R;

/**
 * 通知工具类
 */
public class NotificationUtil {
    /**
     * 通知渠道
     */
    private static final String IMPORTANCE_LOW_CHANNEL_ID = "IMPORTANCE_LOW_CHANNEL_ID";

    /**
     * 通知管理器实例
     */
    private static NotificationManager notificationManager;

    public static Notification getServiceForeground(Context context) {

        //获取通知管理器
        getNotificationManager(context);
        //因为这个API是8.0及以上版本才有
        //所以要这样判断版本
        //不然低版本会奔溃
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            //创建渠道
            //可以多次创建
            //但Id一样只会创建一个
            NotificationChannel channel = new NotificationChannel(IMPORTANCE_LOW_CHANNEL_ID,"重要通知", NotificationManager.IMPORTANCE_HIGH);

            //创建一个渠道
            notificationManager.createNotificationChannel(channel);
        }

        //创建一个通知
        //内容随便写
        Notification notification = new NotificationCompat.Builder(context, IMPORTANCE_LOW_CHANNEL_ID)
                //通知标题
                .setContentTitle("我们是爱学啊")

                //通知内容
                .setContentText("人生苦短，我们只做好课！")

                //通知小图标
                .setSmallIcon(R.mipmap.ic_launcher)

                //通知大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))

                //创建一个通知
                .build();

        //返回
        return notification;
    }

    /**
     * 获取通知管理器
     */
    private static void getNotificationManager(Context context) {
        if (notificationManager==null){
            notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * 显示通知
     * @param id
     * @param notification
     */
    public static void showNotification(int id, Notification notification) {
        notificationManager.notify(id,notification);
    }
}
