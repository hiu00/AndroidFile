package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mymusic.R;
import com.example.mymusic.util.Constant;

public class CommentActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }

    /**
     *  启动评论界面
     *  重构为方法的好初始化
     *  不查看代码就知道要传递哪些参数
     *
     * @param activity
     * @param sheetId
     */
    public static void start(Activity activity, String sheetId){
        //创建意图
        //意图：就是你要干什么
        Intent intent = new Intent(activity, CommentActivity.class);

        //传递歌单id
        intent.putExtra(Constant.SHEET_ID, sheetId);

        //启动界面
        activity.startActivity(intent);
    }
}