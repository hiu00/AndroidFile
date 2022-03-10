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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mymusic.R;
import com.example.mymusic.adapter.PlayListAdapter;
import com.example.mymusic.domain.event.PlayListChangedEvent;
import com.example.mymusic.listener.ListManager;
import com.example.mymusic.manager.MusicPlayerManager;
import com.example.mymusic.service.MusicPlayerService;
import com.example.mymusic.util.EventBusUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

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
     * 删除所有按钮点击
     */
    @OnClick(R.id.ib_delete_all)
    public void onDeleteAllClick(){
        //关闭对话框
        dismiss();

        //删除全部音乐
        listManager.deleteAll();

        //发送音乐列表改变通知
        EventBusUtil.postPlayListChangedEvent();
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
        adapter = new PlayListAdapter(R.layout.item_play_list,listManager);

        //设置适配器
        rv.setAdapter(adapter);

        //设置数据
        adapter.replaceData(listManager.getDatum());
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //设置item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //关闭dialog
                dismiss();

                //播放点击的这首音乐
                listManager.play(listManager.getDatum().get(position));
            }
        });

        //item中子控件点击
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //关闭弹窗
                dismiss();

                //由于这里只有一个按钮点击
                //所以可以不判断
                if (R.id.iv_remove==view.getId()){
                    //删除按钮点击

                    //从列表管理器中删除
                    listManager.delete(position);

                    //发送音乐列表改变通知
                    EventBusUtil.postPlayListChangedEvent();
                }
            }
        });
    }
}
