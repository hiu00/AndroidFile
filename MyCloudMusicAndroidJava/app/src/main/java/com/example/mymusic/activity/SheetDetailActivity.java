package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mymusic.R;
import com.example.mymusic.adapter.SongAdapter;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.LogUtil;

import butterknife.BindView;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    private static final String TAG = "SheetDetailActivity";
    /**
     * 歌单id
     */
    private String id;

    /**
     * 歌单
     */
    private Sheet data;
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_detail);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //尺寸固定
        rv.setHasFixedSize(true);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //获取传递的参数
        //id = getIntent().getStringExtra(Constant.ID);

        //使用重构后的方法
        id=extraId();

        //创建适配器
        adapter = new SongAdapter(R.layout.item_song_detail);

        //设置适配器
        rv.setAdapter(adapter);

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

        if (data.getSongs()!=null && data.getSongs().size()>0){
            //有音乐才设置

            //设置数据
            adapter.replaceData(data.getSongs());
        }

    }

}