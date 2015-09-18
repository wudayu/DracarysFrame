package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Created by David on 8/25/15.
 *
 * BaseResult是由服务器返回的通用Json所对应的对象，其所包含的字段在每个服务器返回的字段中都包含，目前主要是返回的消息类型
 *
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResult {

    // resultCode 请求码
    public static final int RC_REQUEST_FINISHED = 200;      // 成功返回
    public static final int RC_SESSION_OUTOFDATE = 401;     // Token过期
    public static final int RC_VERSION_OUTOFDATE = 404;     // 版本需要更新
    public static final int RC_SERVER_CANT_HANDLE = 500;    // 服务端无法处理

    // messageType 返回的消息类型
    public static final int MESSAGE_TYPE_NORMAL = 1;        //普通消息类型
    public static final int MESSAGE_TYPE_SHOW = 2;          //需要使用Toast显示的消息类型
    public static final int MESSAGE_TYPE_NEED_CONFIRMED = 3;//需要点击确定才会消失的消息类型

    @JsonProperty(value = "resultCode")
    private int resultCode;

	@JsonProperty(value = "message")
	private String message;

    @JsonProperty(value = "messageType")
    private int messageType;

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageType() {
        return messageType;
    }

    public boolean isSuccessLogically() {
        return (getResultCode() % 1000) == RC_REQUEST_FINISHED;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }

}
