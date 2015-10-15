package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by David on 8/25/15.
 * <p/>
 * BaseResult是由服务器返回的通用Json所对应的对象，其所包含的字段在每个服务器返回的字段中都包含，目前主要是返回的消息类型
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResult {

    /**
     * The constant RC_REQUEST_FINISHED. resultCode 请求码
     */
    public static final int RC_REQUEST_FINISHED = 200;      // 成功返回
    /**
     * The constant RC_SESSION_OUTOFDATE.
     */
    public static final int RC_SESSION_OUTOFDATE = 401;     // Token过期
    /**
     * The constant RC_VERSION_OUTOFDATE.
     */
    public static final int RC_VERSION_OUTOFDATE = 404;     // 版本需要更新
    /**
     * The constant RC_SERVER_CANT_HANDLE.
     */
    public static final int RC_SERVER_CANT_HANDLE = 500;    // 服务端无法处理

    /**
     * The constant MESSAGE_TYPE_NORMAL. messageType 返回的消息类型
     */
    public static final int MESSAGE_TYPE_NORMAL = 1;        //普通消息类型
    /**
     * The constant MESSAGE_TYPE_SHOW.
     */
    public static final int MESSAGE_TYPE_SHOW = 2;          //需要使用Toast显示的消息类型
    /**
     * The constant MESSAGE_TYPE_NEED_CONFIRMED.
     */
    public static final int MESSAGE_TYPE_NEED_CONFIRMED = 3;//需要点击确定才会消失的消息类型

    @JsonProperty(value = "resultCode")
    private int resultCode;

	@JsonProperty(value = "message")
	private String message;

    @JsonProperty(value = "messageType")
    private int messageType;

    /**
     * Gets result code.
     *
     * @return the result code
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets message type.
     *
     * @return the message type
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * Is success logically boolean.
     *
     * @return the boolean
     */
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
