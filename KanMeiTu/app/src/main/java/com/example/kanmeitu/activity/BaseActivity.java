package com.example.kanmeitu.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kanmeitu.util.PreferenceUtil;

/**
 *通用Activity
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 访问修饰符改为protected
     */
    protected PreferenceUtil sp;

    /**
     * 重写了setContentView方法
     * 因为在子类调用了setContentView设置布局
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        sp = PreferenceUtil.getInstance(this);
    }
}
