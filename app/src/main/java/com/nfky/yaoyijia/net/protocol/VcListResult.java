package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by David on 8/25/15.
 * <p/>
 * 多余的类，可以删除
 *
 * @param <T> the type parameter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VcListResult<T> extends BaseResult {

    @JsonProperty(value = "data")
    private ArrayList<T> list;

    /**
     * Gets list.
     *
     * @return the list
     */
    public ArrayList<T> getList() {
        return list;
    }

    /**
     * Sets list.
     *
     * @param list the list
     */
    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "VcListResult{" + "list=" + list + '}';
    }

}
