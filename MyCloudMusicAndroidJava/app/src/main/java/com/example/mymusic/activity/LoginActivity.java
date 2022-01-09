package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.mymusic.R;

import butterknife.BindView;

public class LoginActivity extends BaseCommonActivity {

    /**
     * Toolbar
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //初始化toolbar
        setSupportActionBar(toolbar);
    }
}