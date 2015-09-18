package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Created by David on 8/25/15.
 *
 * 网络结果的对象的壳，data中对应的是数据，BaseResult对应的是返回消息
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VcObjectResult<T> extends BaseResult {

    @JsonProperty(value = "data")
    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "VcObjectResult{" + "object=" + object + '}';
    }

}
