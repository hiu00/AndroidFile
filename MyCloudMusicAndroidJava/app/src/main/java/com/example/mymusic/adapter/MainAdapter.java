package com.example.mymusic.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mymusic.Fragment.DiscoveryFragment;
import com.example.mymusic.Fragment.FeedFragment;
import com.example.mymusic.Fragment.MeFragment;
import com.example.mymusic.Fragment.VideoFragment;

/**
 * 主界面ViewPager的Adapter
 */
public class MainAdapter extends BaseFragmentPagerAdapter<Integer>{


    /**
     * 构造方法
     *
     * @param context 上下文
     * @param fm      Fragment管理器
     */
    public MainAdapter(Context context, @NonNull FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回当前位置Fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MeFragment.newInstance();
        } else if (position == 1) {
            return DiscoveryFragment.newInstance();
        } else if (position == 2) {
            return FeedFragment.newInstance();
        } else {
            return VideoFragment.newInstance();
        }
    }
}
