package com.example.mymusic.domain;

import static com.example.mymusic.util.Constant.TYPE_TITLE;

import com.example.mymusic.adapter.DiscoveryAdapter;

/**
 * 标题
 */
public class Title extends BaseMultiItemEntity{

    private String title;

    /**
     * 构造方法
     * @param title
     */
    public Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 返回Item类型
     *
     * 对应现在使用的BaseRecyclerViewAdapterHelper框架
     * 如果要显示多类型这就是固定写法
     *
     * @return
     */
    @Override
    public int getItemType() {
        return TYPE_TITLE;
    }
}
