package com.example.mymusic.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mymusic.R;
import com.example.mymusic.adapter.SongAdapter;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.ImageUtil;
import com.example.mymusic.util.LogUtil;
import com.example.mymusic.util.ResourceUtil;
import com.example.mymusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import retrofit2.Response;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity implements View.OnClickListener {

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
    private View ll_header;
    private ImageView iv_banner;
    private TextView tv_title;
    private ImageView iv_avatar;
    private TextView tv_nickname;
    private View ll_comment_container;
    private TextView tv_comment_count;
    private Button bt_collection;
    private View ll_play_all_container;
    private TextView tv_count;

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

    /**
     * 创建头部
     * @return
     */
    private View createHeaderView(){
        //从XML创建View
        View view = getLayoutInflater().inflate(R.layout.header_sheet_detail, (ViewGroup) rv.getParent(), false);

        //头部容器
        ll_header = view.findViewById(R.id.ll_header);

        //封面图
        iv_banner = view.findViewById(R.id.iv_banner);

        //标题
        tv_title = view.findViewById(R.id.tv_title);

        //歌单创建者头像
        iv_avatar = view.findViewById(R.id.iv_avatar);

        //歌单创建者昵称
        tv_nickname = view.findViewById(R.id.tv_nickname);

        //评论容器
        ll_comment_container = view.findViewById(R.id.ll_comment_container);

        //评论数
        tv_comment_count = view.findViewById(R.id.tv_comment_count);

        //收藏按钮
        bt_collection = view.findViewById(R.id.bt_collection);

        //播放全部容器
        ll_play_all_container = view.findViewById(R.id.ll_play_all_container);

        //歌曲数
        tv_count = view.findViewById(R.id.tv_count);

        //返回View
        return view;
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

        //添加头部
        adapter.addHeaderView(createHeaderView());

        //设置适配器
        rv.setAdapter(adapter);

        //请求数据
        fetchData();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //收藏按钮单击事件
        bt_collection.setOnClickListener(this);
    }

    /**
     * 按钮点击回调方法
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_collection:
                //收藏歌单按钮点击了
                processCollectionClick();
                break;
        }
    }

    /**
     * 处理收藏和取消收藏逻辑
     */
    private void processCollectionClick() {
        LogUtil.d(TAG,"processCollectionClick"+ data.isCollection());

        if (data.isCollection()) {
            //已经收藏了

            //取消收藏
            Api.getInstance()
                    .deleteCollect(data.getId())
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> d) {
                            //弹出提示
                            ToastUtil.errorShortToast(R.string.cancel_success);

                            //重新加载数据
                            //目的是显示新的收藏状态
                            //fetchData();

                            //取消收藏成功
                            data.setCollection_id(null);

                            //收藏数-1
                            data.setCollections_count(data.getCollections_count() - 1);

                            //刷新状态
                            showCollectionStatus();
                        }
                    });
        } else {
            //没有收藏

            //收藏
            Api.getInstance()
                    .collect(data.getId())
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> d) {
                            //弹出提示
                            ToastUtil.successShortToast(R.string.collection_success);

                            //重新加载数据
                            //目的是显示新的收藏状态
                            //fetchData();

                            //收藏状态变更后
                            //可以重新调用歌单详情界面接口
                            //获取收藏状态
                            //但对于收藏来说
                            //收藏数可能没那么重要
                            //所以不用及时刷新
                            data.setCollection_id(1);

                            //收藏数+1
                            data.setCollections_count(data.getComments_count()+1);

                            //刷新状态
                            showCollectionStatus();
                        }
                    });
        }
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

//        显示封面
//        if (StringUtils.isBlank(data.getBanner())) {
//            //如果图片为空
//
//            //就用默认图片
//            iv_banner.setImageResource(R.drawable.placeholder);
//        } else {
//            //有图片
//
//            ImageUtil.show(getMainActivity(), iv_banner, data.getBanner());
//        }

        //使用Palette获取封面颜色
        if (StringUtils.isBlank(data.getBanner())) {
            //图片为空

            //使用默认图片
            iv_banner.setImageResource(R.drawable.placeholder);
        } else {
            //有图片
            Glide.with(this)
                    .asBitmap()
                    .load(ResourceUtil.resourceUri(data.getBanner()))

                    //加载图片到自定义目标
                    .into(new CustomTarget<Bitmap>() {

                        /**
                         * 资源加载完成调用
                         * @param resource
                         * @param transition
                         */
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                            //显示封面
                            iv_banner.setImageBitmap(resource);

                            Palette.from(resource)
                                    .generate(new Palette.PaletteAsyncListener() {
                                        /**
                                         * 颜色计算完成了
                                         * @param palette
                                         */
                                        @Override
                                        public void onGenerated(@Nullable Palette palette) {
                                            //获取 有活力 的颜色
                                            Palette.Swatch swatch = palette.getVibrantSwatch();

                                            //可能没有值所以要判断
                                            if (swatch != null) {
                                                //获取颜色的rgb
                                                int rgb = swatch.getRgb();

                                                //设置标题颜色
                                                toolbar.setBackgroundColor(rgb);

                                                //设置头部容器颜色
                                                ll_header.setBackgroundColor(rgb);

                                                //这些api只有高版本才有
                                                //所以说要判断
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    //设置状态栏颜色
                                                    Window window = getWindow();

                                                    window.setStatusBarColor(rgb);

                                                    //设置导航栏颜色
                                                    window.setNavigationBarColor(rgb);
                                                }
                                            }
                                        }
                                    });

                            }

                        /**
                         * 加载任务取消了
                         * 可以在这里释放我们定义的一些资源
                         *
                         * @param placeholder
                         */
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }

        //显示标题
        tv_title.setText(data.getTitle());

        //头像
        ImageUtil.showAvatar(getMainActivity(), iv_avatar, data.getUser().getAvatar());

        //昵称
        tv_nickname.setText(data.getUser().getNickname());

        //评论数
        tv_comment_count.setText(String.valueOf(data.getComments_count()));

        //音乐数
        tv_count.setText(getResources().getString(R.string.music_count, data.getSongs_count()));

        //显示收藏状态
        showCollectionStatus();
    }

    /**
     * 显示收藏状态
     */
    @SuppressLint({"ResourceType", "StringFormatInvalid"})
    private void showCollectionStatus() {
        if (data.isCollection()){
            //收藏了

            //将按钮文字改为取消
            bt_collection.setText(getResources().getString(R.string.cancel_collection_all,data.getCollections_count()));

            //弱化取消收藏按钮
            //因为我们的本质是想让用户收藏歌单
            //所以去掉背景
            bt_collection.setBackground(null);

            //设置文字颜色为灰色
            bt_collection.setTextColor(getResources().getColor(R.color.light_grey));
        } else {
            //没有收藏

            //将按钮文字改为收藏
            bt_collection.setText(getResources().getString(R.string.collection_all,data.getCollections_count()));

            //设置按钮颜色为主色调
            bt_collection.setBackgroundResource(R.drawable.selector_color_primary);

            //将文字颜色设置为白色
            bt_collection.setTextColor(getResources().getColorStateList(R.drawable.selector_text_color_primary));
        }
    }


}