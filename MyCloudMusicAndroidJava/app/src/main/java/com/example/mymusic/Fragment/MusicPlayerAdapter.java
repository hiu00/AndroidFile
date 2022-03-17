package com.example.mymusic.Fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mymusic.adapter.BaseFragmentPagerAdapter;
import com.example.mymusic.domain.Song;

public class MusicPlayerAdapter extends BaseFragmentPagerAdapter<Song> {
    /**
     * 构造方法
     *
     * @param context 上下文
     * @param fm      Fragment管理器
     */
    public MusicPlayerAdapter(Context context, @NonNull FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回当前位置fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MusicRecordFragment.newInstance(getData(position));
    }
}
