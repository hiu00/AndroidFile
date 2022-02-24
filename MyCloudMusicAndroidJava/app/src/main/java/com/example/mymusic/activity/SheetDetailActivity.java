package com.example.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.adapter.SongAdapter;
import com.example.mymusic.api.Api;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.listener.HttpObserver;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.ImageUtil;
import com.example.mymusic.util.LogUtil;

import org.apache.commons.lang3.StringUtils;

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

        if (StringUtils.isBlank(data.getBanner())) {
            //如果图片为空

            //就用默认图片
            iv_banner.setImageResource(R.drawable.placeholder);
        } else {
            //有图片

            ImageUtil.show(getMainActivity(), iv_banner, data.getBanner());
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


    }

}