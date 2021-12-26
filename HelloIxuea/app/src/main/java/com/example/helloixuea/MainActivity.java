package com.example.helloixuea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import activity.ImageActivity;
import activity.ListActivity;
import activity.SecondActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//在这里    MainActivity就是一个Class 也就是一个类 和C#是一样的  都是一个类
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //这里创建OkHttpClient，建议一个应用只有一个
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //该界面对应的布局是activity_main
        //R表示一个类，layout是一个内部类，activity_main是一个int类型的常量
        //R类是动态生成的
        setContentView(R.layout.activity_main);

        //找到id为tv的TextView控件
       TextView tv= findViewById(R.id.tv);

        //然后就可以进行一些设置，不同的控件有不同的设置方法
        tv.setText("代码中设置的文字");

        //找按钮
        Button bt=findViewById(R.id.bt);

        //设置监听器
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //点击按钮后，他就会执行这个方法
                //基本上所有的Android事件都是这样一个使用方法
                //显示一个提示，就是Android特有的吐司 
                Toast.makeText(MainActivity.this, "你还真点击啊", Toast.LENGTH_SHORT).show();

                String name="爱学啊";
                Log.d(TAG,"按钮点击了 debug"+name);
                Log.e(TAG,"按钮点击了 error"+name);
            }
        });

//        findViewById(R.id.search_close_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Hello 爱学啊", Toast.LENGTH_SHORT).show();
//            }
//        });
        //找到按钮
        Button bt_open_scend = findViewById(R.id.bt_open_second);
        bt_open_scend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent可以翻译为意图
                // 意图是干什么，做什么的意思
                // 其实他就是个类
                // 这里我们启动SecondActivity
              Intent intent= new Intent(MainActivity.this, SecondActivity.class);

              //启动
                startActivity(intent);
            }
        });

       EditText et_username = findViewById(R.id.et_username);

       Button bt_login = findViewById(R.id.bt_login);
                                    //View.OnClickListener也是一个类 它不属于MainActivity
                                    //this 是就近原则this只会找View.Onclick







        /*
        这是一个接口
         */

//        public interface OnClickListener {
//        /**
//         * Called when a view has been clicked.
//         *
//         * @param v The view that was clicked.
//         */
//        void onClick(View v);
//         */
//接口就是 抽象类   只有方法没有实现  就是方法是空方法


       bt_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //获取输入的内容
               String inputText = et_username.getText().toString().trim();
               if (TextUtils.isEmpty(inputText)){
                   //如果为空，null，""都表示空
                   Toast.makeText(MainActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                   return;
               }

               //TODO 走到这里表示输入了用户名，可以调用登录接口
               Toast.makeText(MainActivity.this,"您输入的用户名是："+inputText,Toast.LENGTH_SHORT).show();;
           }
       });
    }
        //启动手动创建的Activity
    public void onOpenActivityClike(View view) {
       Intent intent = new Intent(this,IxueaActivity.class);
       startActivity(intent);
    }

    /**
     * 显示图片
     * @param view
     */
    public void showImage(View view) {
        Intent intent =new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    //显示列表
    public void onShowListClick(View view) {
        Intent intent =new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    /**
     * 请求网络数据
     * @param view
     */
    public void onRequestNetworkClick(View view) {
        //然后创建一个线程，因为我们这里学习的同步请求
        //因为Android不允许在主线程中请求网络
        //当然你也可以在AsyncTask这样的异步任务里面同步调用
        new Thread(){
            @Override
            public void run() {
                super.run();

                //创建一个Request，他里面包括的你要请求的网址等信息，
                // 同时这个类是构建者模式，我们在源码分析的时候会详解讲解
                Request request = new Request.Builder()
                        .url("http://www.ixuea.com/")
                        .build();


                try {
                    //然后调用newCall方法，传入刚刚创建的Request对象
                    // 然后调用execute方法来执行这个请求
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        //请求成功

                    //通过调用response的body上的string方法可以得到流的字符串
                    String result = response.body().string();

                    //将返回的字符串打印到日志
                    //这里不能直接将数据设置到界面上
                    //因为子线程中不能操作界面（Android规定）
                    //如果要更新，需要通过其他方式，这样就不讲解了
                    Log.d(TAG, result);
                } else {
                        //请求失败
                        Log.e(TAG, "请求失败！");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}