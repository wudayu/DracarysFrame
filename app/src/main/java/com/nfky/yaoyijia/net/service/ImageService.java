package com.nfky.yaoyijia.net.service;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;

import com.nfky.yaoyijia.model.TypedImage;
import com.nfky.yaoyijia.net.INetHandler;
import com.nfky.yaoyijia.net.protocol.VcListResult;
import com.nfky.yaoyijia.net.protocol.VcObjectResult;

import java.util.Map;

/**
 * Created by David on 8/25/15.
 * <p/>
 * ImageService是用来向上传图片的Retrofit的Service
 * 包含了使用两种不同类型的格式上传的方式
 */
public interface ImageService {

    /*
     * 上传文件接口
     * 使用的是 Map
    @Multipart
    @POST("/api/upload/file")
    void uploadMultiplePic(@PartMap Map<String, TypedImage> imageResources, Callback<VcListResult<String>> cb);
     */

    /**
     * 上传文件接口
     * 使用的是 MultipartTypedOutput
     *
     * @param imageResources the image resources
     * @param cb             the cb
     */
    @POST("/api/upload/file")
    void uploadMultiplePic(@Body MultipartTypedOutput imageResources, Callback<VcListResult<String>> cb);

}
