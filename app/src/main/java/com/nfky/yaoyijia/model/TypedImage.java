package com.nfky.yaoyijia.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;

import retrofit.mime.TypedOutput;

/**
 * Created by David on 8/25/15.
 * <p/>
 * TypedImage是对图片进行对象化的类，若需要上传图片，则需要将图片变成次对象类型
 * 此文件请勿修改
 */
public class TypedImage extends FileSystemResource implements TypedOutput {

	/**
	 * Instantiates a new Typed image.
	 *
	 * @param file the file
	 */
	public TypedImage(File file) {
		super(file);
	}

	/**
	 * Instantiates a new Typed image.
	 *
	 * @param path the path
	 */
	public TypedImage(String path) {
		super(path);
	}

	@Override
	public String fileName() {
		return this.getFile().getName();
	}

	@Override
	public long length() {
		try {
			return this.contentLength();
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public String mimeType() {
		return MediaType.IMAGE_JPEG_VALUE;
	}

	@Override
	public void writeTo(OutputStream os) throws IOException {
		InputStream is = this.getInputStream();
		byte[] buffer = new byte[1024];
		int count;

		while ((count = is.read(buffer)) > 0) {
			os.write(buffer, 0, count);
		}

		is.close();
	}

}
