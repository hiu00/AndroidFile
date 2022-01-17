package com.example.mymusic.domain.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 通用网络请求响应模型
 */

public class BaseResponse {

    /**
     * 状态码
     *
     * 只有发生了错误才会有值
     */
    private int status;

    /**
     * 出错的提示信息
     *
     * 发生了错误不一定有
     */
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("message", message)
                .toString();
    }
}
