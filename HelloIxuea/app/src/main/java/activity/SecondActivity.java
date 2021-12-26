package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helloixuea.R;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
      Button bt_close= findViewById(R.id.bt_close);
      bt_close.setOnClickListener(this);//让当前类实现监听器,前面使用的是匿名监听器
    }

    @Override
    public void onClick(View v) {
        finish();//在哪个界面调用就会关闭哪个界面
    }
}