package com.example.mymusic.api;

import com.example.mymusic.domain.Session;
import com.example.mymusic.domain.Sheet;
import com.example.mymusic.domain.SheetDetailWrapper;
import com.example.mymusic.domain.SheetListWrapper;
import com.example.mymusic.domain.User;
import com.example.mymusic.domain.response.DetailResponse;
import com.example.mymusic.domain.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 网络接口配置
 * <p>
 * 之所以调用接口还能返回数据
 * 是因为Retrofit框架内部处理了
 */
public interface Service {

    /**
     * 歌单列表
     *
     * @return
     */
    @GET("v1/sheets")
    Observable<ListResponse<Sheet>> sheets();


    /**
     * 歌单详情
     *
     * @param id
     * @return
     */
    @GET("v1/sheets/{id}")
    Observable<DetailResponse<Sheet>> sheetDetail(@Path("id") String id);

    /**
     * 登录接口
     * @param data
     * @return
     */
    @POST("v1/sessions")
    Observable<DetailResponse<Session>> login(@Body User data);
}
