package com.example.mymusic.activity;

import androidx.appcompat.widget.Toolbar;

import com.example.mymusic.R;

import butterknife.BindView;

/**
 * 通用标题界面
 */
public class BaseTitleActivity extends BaseCommonActivity{
    /**
     * Toolbar
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();

        //初始化toolbar
        setSupportActionBar(toolbar);
    }
}
