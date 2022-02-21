package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mymusic.R;
import com.example.mymusic.adapter.DiscoveryAdapter;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.BaseMultiItemEntity;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.Title;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.domain.response.ListResponse;
import com.example.mymusic.listener.HttpObserver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

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

        //设置列宽度
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                //在这里
                //获取模型上面的宽度
                return adapter.getItem(position).getSpanSize();
            }
        });

        //设置列宽度
        //lambda写法
//        adapter.setSpanSizeLookup((gridLayoutManager, position) -> {
//            //在这里
//            //获取模型上面的宽度
//            return adapter.getItem(position).getSpanSize();
//        });

        //添加头部
        adapter.addHeaderView(createHeaderView());

        //设置适配器
        rv.setAdapter(adapter);

        //请求数据
        fetchData();
    }

    //创建头部布局
    private View createHeaderView() {
        //把XML创建为View
        View view = getLayoutInflater().inflate(R.layout.header_discovery, (ViewGroup) rv.getParent(), false);

        //找到日期文本控件
        TextView tv_day = view.findViewById(R.id.tv_day);

        //设置日期
        //获取日历对象
        Calendar calendar = Calendar.getInstance();

        //获取当前月的天
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_day.setText(String.valueOf(day));
        //返回控件
        return view;
    }

    private void fetchData() {
        List<BaseMultiItemEntity> datum = new ArrayList<>();

        //添加标题
        datum.add(new Title("推荐歌单"));

        //歌单Api
        Observable<ListResponse<Sheet>> sheets = Api.getInstance().sheets();
        //单曲API
        Observable<ListResponse<Song>> songs = Api.getInstance().songs();

        //请求歌单数据
        sheets.subscribe(new HttpObserver<ListResponse<Sheet>>() {
            @Override
            public void onSucceeded(ListResponse<Sheet> data) {
                //添加歌单数据
                datum.addAll(data.getData());
                Log.i("loveYan", "我是数据大小 : " + data.getData().size());
                //请求单曲
                songs.subscribe(new HttpObserver<ListResponse<Song>>() {
                    @Override
                    public void onSucceeded(ListResponse<Song> data) {

                        //添加标题
                        datum.add(new Title("推荐单曲"));

                        //添加单曲
                        datum.addAll(data.getData());

                        //设置数据到适配器
                        adapter.replaceData(datum);
                    }
                });
    }
});
    }
}

