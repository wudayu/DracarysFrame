package com.nfky.yaoyijia.image.component;

import android.content.Context;
import android.net.Uri;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by David on 8/25/15.
 *
 * 带安全验证的图片下载器
 */

public class ImageDownloaderWithAccessToken extends BaseImageDownloader {

    public ImageDownloaderWithAccessToken(Context context) {
        super(context);
    }

    public ImageDownloaderWithAccessToken(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    /**
     * 为图片下载添加AccessToken
     */
    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
        // 添加的AccessToken
        // conn.setRequestProperty("AccessToken", DirectoryConfiguration.getAccessToken(context));
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        return conn;
    }

}
