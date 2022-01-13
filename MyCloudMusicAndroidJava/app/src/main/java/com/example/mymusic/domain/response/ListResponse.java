package com.example.mymusic.domain.response;

import java.util.List;

/**
 * 解析列表网络请求
 */
public class ListResponse<T> extends BaseResponse{

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
