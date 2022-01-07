package com.example.mymusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mymusic.Fragment.GuideFragment;
import com.example.mymusic.R;

/**
 * 引导界面适配器
 */
public class GuideAdapter extends FragmentPagerAdapter {
    public GuideAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * 返回当前位置的Fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(R.drawable.guide1);
    }

    /**
     * 返回有多少个
     * @return
     */
    @Override
    public int getCount() {
        return 10;
    }
}
