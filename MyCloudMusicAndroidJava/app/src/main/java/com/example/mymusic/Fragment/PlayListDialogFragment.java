package com.example.mymusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.adapter.PlayListAdapter;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;

/**
 * 迷你控制器 播放列表(即更多按钮)
 */
public class PlayListDialogFragment extends BaseBottomSheetDialogFragment {
    /**
     * 循环模式
     */
    @BindView(R.id.tv_loop_model)
    TextView tv_loop_model;

    /**
     * 音乐数量
     */
    @BindView(R.id.tv_count)
    TextView tv_count;

    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;
    private ListManager listManager;
    private PlayListAdapter adapter;

    /**
     * 返回布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_play_list,null);
    }

    /**
     * 构造方法
     * @return
     */
    public static PlayListDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PlayListDialogFragment fragment = new PlayListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 显示对话框
     * @param fragmentManager
     */
    public static void show(FragmentManager fragmentManager){
        //创建fragment
        PlayListDialogFragment fragment = newInstance();

        fragment.show(fragmentManager,"song_play_list_dialog");
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        super.initViews();

        //固定尺寸
        rv.setHasFixedSize(true);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);

        //分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getMainActivity(), RecyclerView.VERTICAL);
        rv.addItemDecoration(decoration);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //创建列表管理器
        listManager = MusicPlayerService.getListManager(getMainActivity());

        //创建适配器
        adapter = new PlayListAdapter(R.layout.item_play_list);

        //设置适配器
        rv.setAdapter(adapter);

        //设置数据
        adapter.replaceData(listManager.getDatum());
    }
}
