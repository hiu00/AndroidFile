package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.adapter.DiscoveryAdapter;
import com.example.mymusic.domain.BaseMultiItemEntity;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.Title;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页-发现 界面
 */
public class DiscoveryFragment extends BaseCommonFragment{

    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;
    private GridLayoutManager layoutManager;
    private DiscoveryAdapter adapter;

    /**
     * 构造方法
     * 固定写法
     * @return
     */
    public static DiscoveryFragment newInstance() {

        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回布局文件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery,null);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //高度固定
        //可以提交性能
        rv.setHasFixedSize(true);

        //设置显示3列
        layoutManager = new GridLayoutManager(getMainActivity(), 3);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //创建适配器
        adapter = new DiscoveryAdapter();

        //设置适配器
        rv.setAdapter(adapter);

        //请求数据
        fetchData();
    }

    private void fetchData() {
        List<BaseMultiItemEntity> datum = new ArrayList<>();

        //添加标题
        datum.add(new Title("推荐歌单"));

        //添加歌单数据
        for (int i = 0; i < 9; i++) {
            datum.add(new Sheet());
        }
        //添加标题
        datum.add(new Title("推荐单曲"));

        //添加单曲数据
        for (int i = 0; i < 9; i++) {
            datum.add(new Song());
        }

        //将数据设置（替换）到适配器
        adapter.replaceData(datum);
    }
}
