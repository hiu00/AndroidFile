package com.example.mymusic.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mymusic.R;
import com.example.mymusic.activity.SheetDetailActivity;
import com.example.mymusic.activity.WebViewActivity;
import com.example.mymusic.adapter.DiscoveryAdapter;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.Ad;
import com.example.mymusic.domain.BaseMultiItemEntity;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.Title;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.domain.response.ListResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.ImageUtil;
import com.example.mymusic.util.LogUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 首页-发现 界面
 */
public class DiscoveryFragment extends BaseCommonFragment implements OnBannerListener {

    private static final String TAG = "DiscoveryFragment";
    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;
    private GridLayoutManager layoutManager;

    /**
     * 适配器
     */
    private DiscoveryAdapter adapter;

    /**
     * 轮播图组件
     */
    private Banner banner;

    /**
     * 轮播图数据
     */
    private List<Ad> bannerData;

    /**
     * 构造方法
     * 固定写法
     *
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
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery, null);
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

        //请求轮播图数据
        fetchBannerData();

        orm.saveSong(new Song());
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //设置Item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //获取点击的数据
                Object data = adapter.getItem(position);

                //判断数据类型
                if (data instanceof Sheet){
                    Sheet sheet=((Sheet) data);

//                    //创建Intent
//                    Intent intent=new Intent(getMainActivity(), SheetDetailActivity.class);
//
//                    //传递id
//                    //这样详情界面才知道点击的是那个歌单
//                    intent.putExtra(Constant.ID,sheet.getId());
//
//                    //启动Intent里面的activity
//                    startActivity(intent);

                    //使用重构后的方法
                    startActivityExtraId(SheetDetailActivity.class,sheet.getId());
                }
            }
        });
    }

    private void fetchBannerData() {
        Api.getInstance()
                .ads()
                .subscribe(new HttpObserver<ListResponse<Ad>>() {
                    @Override
                    public void onSucceeded(ListResponse<Ad> data) {
                        showBanner(data.getData());
                    }
                });
    }

    /**
     * 显示轮播图数据
     *
     * @param data
     */
    private void showBanner(List<Ad> data) {
        LogUtil.d(TAG, "showBanner:" + data.size());

        this.bannerData=data;

        //设置到轮播图组件
        banner.setImages(data);

        //显示数据
        banner.start();

        //第一次也要滚动
        startBannerScroll();
    }

    /**
     * 第一次也要滚动
     */
    private void startBannerScroll() {
        banner.startAutoPlay();
    }

    /**
     * 生命周期方法
     * 当界面展示了
     */
    @Override
    public void onResume() {
        super.onResume();

        if(bannerData!=null){
            //有数据才开始滚动
            startBannerScroll();
        }
    }

    /**
     * 生命周期方法
     * 当界面看不见了
     *
     * 包括开启新界面，弹窗，后台
     */
    @Override
    public void onPause() {
        super.onPause();

        //结束轮播图滚动
        banner.stopAutoPlay();
    }

    //创建头部布局
    private View createHeaderView() {
        //把XML创建为View
        View view = getLayoutInflater().inflate(R.layout.header_discovery, (ViewGroup) rv.getParent(), false);

        //添加轮播图组件
        banner = view.findViewById(R.id.banner);

        //设置点击监听器
        banner.setOnBannerListener(this);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
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

    /**
     * 轮播图点击回调
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        //获取到点击的广告对象
        Ad ad = bannerData.get(position);

        //使用通用的WebView界面显示
        WebViewActivity.start(getMainActivity(),"活动详情",ad.getUri());
    }

    /**
     * Banner框架显示图片的实现类
     */
    public class GlideImageLoader extends ImageLoader{

        /**
         * 加载图片的方法
         * @param context
         * @param path
         * @param imageView
         */
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //将对象转为广告对象
            Ad banner=(Ad) path;

            //使用工具类方法显示图片
            ImageUtil.show(getMainActivity(),imageView,banner.getBanner());
        }
    }
}

