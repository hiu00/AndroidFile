package com.example.mymusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.mymusic.R;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.Song;
import com.example.mymusic.domain.event.CollectSongClickEvent;
import com.example.mymusic.util.Constant;
import com.example.mymusic.util.ImageUtil;
import com.example.mymusic.util.PreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 歌曲-更多对话框
 */
public class SongMoreDialogFragment extends BaseBottomSheetDialogFragment{

    /**
     * 封面图
     */
    @BindView(R.id.iv_banner)
    ImageView iv_banner;

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView tv_title;

    /**
     * 歌手名称
     */
    @BindView(R.id.tv_info)
    TextView tv_info;

    /**
     * 评论数
     */
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;

    /**
     * 歌手名称
     */
    @BindView(R.id.tv_singer_name)
    TextView tv_singer_name;

    /**
     * 从歌单中删除音乐容器
     */
    @BindView(R.id.ll_delete_song_in_sheet)
    View ll_delete_song_in_sheet;

    private Sheet sheet;

    private Song song;

    /**
     * 返回view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_song_more,null);
    }

    /**
     * 构造方法
     * @param sheet
     * @param song
     * @return
     */
    public static SongMoreDialogFragment newInstance(Sheet sheet, Song song) {
        //创建bundle
        Bundle args = new Bundle();

        //创建fragment
        SongMoreDialogFragment fragment = new SongMoreDialogFragment();

        //添加参数
        args.putSerializable(Constant.SHEET,sheet);
        args.putSerializable(Constant.SONG,song);

        //设置参数
        fragment.setArguments(args);

        //返回fragment
        return fragment;
    }

    /**
     * 显示对话框
     *
     * @param fragmentManager
     * @param sheet
     * @param song
     */
    public static void show(FragmentManager fragmentManager, Sheet sheet, Song song) {
        //创建fragment
        SongMoreDialogFragment fragment = newInstance(sheet, song);

        //显示
        fragment.show(fragmentManager, "song_more_dialog");
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //获取传递的对象
        sheet = (Sheet) getArguments().getSerializable(Constant.SHEET);
        song = (Song) getArguments().getSerializable(Constant.SONG);

        //封面
        ImageUtil.show(getMainActivity(), iv_banner, sheet.getBanner());

        //标题
        tv_title.setText(sheet.getTitle());

        //歌手
        tv_info.setText(song.getSinger().getNickname());

        //评论数
        tv_comment_count.setText(getResources()
                .getString(R.string.comment_count, sheet.getComments_count()));

        //歌手
        tv_singer_name.setText(getResources()
                .getString(R.string.singer_name, song.getSinger().getNickname()));

        //只有我的歌单才显示删除音乐
        if (PreferenceUtil
                .getInstance(getMainActivity())
                .getUserId()
                .equals(sheet.getUser().getId())) {
            ll_delete_song_in_sheet.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 收藏歌曲到歌单按钮点击
     */
    @OnClick(R.id.ll_collect_song)
    public void onCollectionSongClick(){
        //关闭对话框
        dismiss();

        //使用监听器回调事件
        //发布活动信息
        EventBus.getDefault().post(new CollectSongClickEvent(song));
    }
}
