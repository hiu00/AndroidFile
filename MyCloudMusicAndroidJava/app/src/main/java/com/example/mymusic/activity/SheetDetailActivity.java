package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mymusic.R;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity {

    private static final String TAG = "SheetDetailActivity";
    /**
     * 歌单id
     */
    private String id;

    /**
     * 歌单
     */
    private Sheet data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_detail);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //获取传递的参数
        //id = getIntent().getStringExtra(Constant.ID);

        //使用重构后的方法
        id=extraId();

        //请求数据
        fetchData();
    }

    //请求数据
    private void fetchData() {
        Api.getInstance()
                .sheetDetail(id)
                .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Sheet> data) {
                        //显示数据
                        next(data.getData());
                    }
                });
    }

    /**
     * 显示数据
     * @param data
     */
    private void next(Sheet data) {
        this.data=data;

        LogUtil.d(TAG,"next"+data);
    }
}