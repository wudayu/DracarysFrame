package com.nfky.yaoyijia.image.component;

import android.content.Context;
import android.net.Uri;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by David on 8/25/15.
 * <p/>
 * 带安全验证的图片下载器
 */
public class ImageDownloaderWithAccessToken extends BaseImageDownloader {

    /**
     * Instantiates a new Image downloader with access token.
     *
     * @param context the context
     */
    public ImageDownloaderWithAccessToken(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Image downloader with access token.
     *
     * @param context        the context
     * @param connectTimeout the connect timeout
     * @param readTimeout    the read timeout
     */
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
