package com.nfky.yaoyijia.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.constant.ImageLoaderHelper;
import com.nfky.yaoyijia.constant.ReqCode;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.image.IImageHandler;
import com.nfky.yaoyijia.image.UILImageHandler;
import com.nfky.yaoyijia.model.VcUser;
import com.nfky.yaoyijia.net.INetHandler;
import com.nfky.yaoyijia.net.RetrofitNetHandler;
import com.nfky.yaoyijia.net.protocol.VcListResult;
import com.nfky.yaoyijia.views.ProcessingDialog;

/**
 *
 * Created by David on 8/25/15.
 *
 * 我的Fragment，主要展示了上传图片的一整套功能
 *
 **/

public class MineFragment extends BaseFragment {

	public static final String CURR_USER_ID = "4fef1bb5822e47ca9453443f7fa4820c";

    // 图片容器ImageView
	ImageView ivAvatar = null;

    // Handler声明
	IImageHandler imageHandler = null;
	INetHandler netHandler = null;

    // 直接拍照的照片的放置路径
	String takePicturePath = null;
    // 裁减后的照片的放置路径
	String cuttedImagePath = null;
    // 压缩后的图片路径
	List<String> filePathes = new ArrayList<>();
    // 上传完成后的图片uuid
	String uploadedUUid = null;
    // 当前处理到的图片下标
	int currPicIndex = -1;

	VcUser currUser = null;

    /** 初始化界面容器 */
	@Override
	protected View initContainer(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mine, null);
	}

    /** 初始化控件与Handler */
	@Override
	protected void initComponents(View fragView) {
		ivAvatar = (ImageView) fragView.findViewById(R.id.iv_avatar);

		imageHandler = UILImageHandler.getInstance(getActivity());
		netHandler = RetrofitNetHandler.getInstance();
	}

    /** 初始化事件 */
	@Override
	protected void initEvents() {
		ivAvatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				takePicturePath = imageHandler.getNewTmpImagePath();
				imageHandler.selectGetImageWay(MineFragment.this.getActivity(), ivAvatar, takePicturePath);
			}
		});
	}

    /** 初始化数据 */
	@Override
	protected void initData() {
	}

    /** 在一切初始化结束后的程序入口 */
	@Override
	protected void afterAllSet() {
        /*
		processingDialog = new ProcessingDialog(this.getActivity(), true, null);
		processingDialog.show();

		netHandler.getForUserInfo(CURR_USER_ID, new Callback<VcObjectResult<VcUser>>() {
			@Override
			public void success(VcObjectResult<VcUser> result, Response response) {
				Utils.debug("result = " + result);
				imageHandler.loadHeaderImage(result.isSuccess() ? result.getObject().getPhotosrc() : "", ivHeader);
				dismissProcessingDialog();
			}
			@Override
			public void failure(RetrofitError error) {
				RetrofitNetHandler.toastNetworkError(MineFragment.this.getActivity(), error);
				dismissProcessingDialog();
			}
		});
		*/
	}

    /**
     * 根据选择照片结束后的Uri来获取图片相关信息，并准备进行剪裁压缩
     *
     * @param uri 选择照片或者拍照的图片信息
     */
	public void cutTheImage(Uri uri) {
		cuttedImagePath = imageHandler.getNewTmpImagePath();
		imageHandler.cutTheImageHead(this.getActivity(), uri, cuttedImagePath);
	}

    /**
     * 根据从图片拍照选取到图片上传前这个过程中所涉及到的不同界面返回回来的数据来进行不同的操作
     */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (resultCode == Activity.RESULT_OK) {
				switch (requestCode) {
				// 如果是直接从相册获取
				case ReqCode.ALBUM:
					cutTheImage(data.getData());
					break;
				// 如果是调用相机拍照时
				case ReqCode.CAMERA:
					cutTheImage(Uri.fromFile(new File(takePicturePath)));
					break;
				// 取得裁剪后的图片
				case ReqCode.CUTTED:
					setPicToView(cuttedImagePath);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 将图片设置到界面上
     *
     * @param cuttedImagePath 裁减完的图片路径
     */
	private void setPicToView(String cuttedImagePath) {
		String compressedPath = imageHandler.compressImage(cuttedImagePath);
		imageHandler.loadHeaderImage(ImageLoaderHelper.URI_PREFIX_FILE + compressedPath, ivAvatar);
		++currPicIndex;
		filePathes.add(compressedPath);

		uploadPic(compressedPath);
	}

    /**
     * 调用已经编写好的Retrofit接口上传图片
     *
     * @param filePath 图片路径
     */
	void uploadPic(String filePath) {
		showProcessingDialog("Uploading", false, null);
        /*
		netHandler.postForUploadPic(CURR_USER_ID, filePath, new Callback<VcObjectResult<String>>() {
			@Override
			public void success(VcObjectResult<String> result, Response response) {
				uploadedUUid = result.getObject();
				dismissProcessingDialog();
			}
			@Override
			public void failure(RetrofitError error) {
				RetrofitNetHandler.toastNetworkError(MineFragment.this.getActivity(), error);
                Utils.debug("error = " + error);
				dismissProcessingDialog();
			}
		});
		*/
        /*
        netHandler.postForUploadTestPic(filePath, new Callback<VcListResult<String>>() {
            @Override
            public void success(VcListResult<String> result, Response response) {
                // uploadedUUid = result.getObject();
                Utils.debug("uploadedUUids = " + result.getList());
                Utils.debug("UUids.size = " + result.getList().size());
                dismissProcessingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                RetrofitNetHandler.toastNetworkError(MineFragment.this.getActivity(), error);
                Utils.debug("error = " + error);
                dismissProcessingDialog();
            }
        });
        */
        netHandler.postForUploadSinglePic(filePath, new Callback<VcListResult<String>>() {
            @Override
            public void success(VcListResult<String> result, Response response) {
                // uploadedUUid = result.getObject();
                Utils.debug("uploadedUUids = " + result.getList());
                Utils.debug("UUids.size = " + result.getList().size());
                dismissProcessingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                RetrofitNetHandler.toastNetworkError(MineFragment.this.getActivity(), error);
                Utils.debug("error = " + error);
                dismissProcessingDialog();
            }
        });
	}

}
