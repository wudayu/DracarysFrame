package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by David on 8/25/15.
 *
 * 多余的类，可以删除
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VcCollectionResult<T> extends BaseResult {

    @JsonProperty(value = "data")
    private Collection<T> collection;

    public Collection<T> getCollection() {
        return collection;
    }

    public void setCollection(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "VcCollectionResult{" + "collection=" + collection + '}';
    }
    
}
