package com.example.kanmeitu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kanmeitu.MainActivity;
import com.example.kanmeitu.R;
import com.example.kanmeitu.util.Constant;
import com.example.kanmeitu.util.PreferenceUtil;
import com.example.kanmeitu.util.RegexUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_email;
    private EditText et_password;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //找控件
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);

        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void login() {
        //获取输入的邮箱
      String email= et_email.getText().toString().trim();

      //判断邮箱格式是否正确
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, R.string.email_hint, Toast.LENGTH_SHORT).show();
            return;
        }
        //通过正则表达式判断邮箱格式是否正确
        //关于正则表达式的内容请参考我们的其他课程，因为他的内容都够讲一本书了
        if (!RegexUtil.isEmail(email)){
            Toast.makeText(this, R.string.error_email_format, Toast.LENGTH_SHORT).show();
            return;
        }

        //获取输入的密码
       String password= et_password.getText().toString().trim();

        //判断密码长度
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.password, Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length()<6 || password.length()>15){
            Toast.makeText(this, R.string.error_passord_format, Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO 在这里就调用调用服务端的登录接口
        // 我们这里就简单实现，将密码和用户名都写到本地了
        if (!Constant.EMAIL.equals(email)||!Constant.PASSWORD.equals(password)){
            Toast.makeText(this, "邮箱或密码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }else
            {
                //登录完成后保存一个标致，下次就不用在登录了
                sp.setLogin(true);
                sp.setEmail(email);

                Intent intent =new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        //关闭当前界面
        finish();
    }
}