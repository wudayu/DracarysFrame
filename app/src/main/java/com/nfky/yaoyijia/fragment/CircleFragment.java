package com.nfky.yaoyijia.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.constant.WeatherCityCode;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.image.IImageHandler;
import com.nfky.yaoyijia.image.UILImageHandler;
import com.nfky.yaoyijia.net.INetHandler;
import com.nfky.yaoyijia.net.RetrofitNetHandler;
import com.nfky.yaoyijia.net.protocol.WeatherResult;
import com.nfky.yaoyijia.views.ProcessingDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import java.net.URISyntaxException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *
 * Created by David on 8/27/15.
 *
 * Description: 第二个界面的Fragment
 *
 **/

public class CircleFragment extends BaseFragment {

	private final UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.UM_DESCRIPTOR);

	INetHandler netHandler = null;
    IImageHandler imageHandler = null;

	TextView tvWeather = null;
	Button btnShare = null;
	Button btnScan = null;

	@Override
	protected View initContainer(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_circle, null);
	}

	@Override
	protected void initComponents(View fragView) {
		tvWeather = (TextView) fragView.findViewById(R.id.tv_weather);
		btnShare = (Button) fragView.findViewById(R.id.btn_share);
		btnScan = (Button) fragView.findViewById(R.id.btn_scan);

		netHandler = RetrofitNetHandler.getInstance();
        imageHandler = UILImageHandler.getInstance(this.getActivity());
	}

	@Override
	protected void initEvents() {
		tvWeather.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					Intent gaodeMapIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://showTraffic?sourceApplication=softname&poiid=BGVIS1&lat="+"39.98871"+"&lon="+"116.43234"+"&level=10&dev=0"));
					gaodeMapIntent.setPackage("com.autonavi.minimap");
					@SuppressWarnings("deprecation")
					Intent baiduMapIntent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=latlng:39.98871,116.43234|name:这是目的地&mode=driving#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
					if (gaodeMapIntent.resolveActivity(CircleFragment.this.getActivity().getPackageManager()) != null) {
						startActivity(gaodeMapIntent);
					} else if (baiduMapIntent.resolveActivity(CircleFragment.this.getActivity().getPackageManager()) != null) {
						startActivity(baiduMapIntent);
					} else {
						Utils.toastMessage(CircleFragment.this.getActivity(), "");
					}
				} catch (URISyntaxException e) {
					Utils.toastMessage(CircleFragment.this.getActivity(), "");
				}
			}
		});
		tvWeather.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				CircleFragment.this.showProcessingDialog(null, true, null);
				return false;
			}
		});
		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 设置分享内容
				mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
				// 设置分享图片, 参数2为图片的url地址
				mController.setShareMedia(new UMImage(getActivity(), "http://www.umeng.com/images/pic/banner_module_social.png"));
				// 设置分享图片，参数2为本地图片的资源引用
				// mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
				// 设置分享图片，参数2为本地图片的路径(绝对路径)
				//mController.setShareMedia(new UMImage(getActivity(),
				//                                BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

				// 设置分享音乐
				//UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
				//uMusic.setAuthor("GuGu");
				//uMusic.setTitle("天籁之音");
				// 设置音乐缩略图
				//uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
				//mController.setShareMedia(uMusic);

				// 设置分享视频
				//UMVideo umVideo = new UMVideo(
				//          "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
				// 设置视频缩略图
				//umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
				//umVideo.setTitle("友盟社会化分享!");
				//mController.setShareMedia(umVideo);

				mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
						SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT,
						SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.DOUBAN,
						SHARE_MEDIA.EVERNOTE, SHARE_MEDIA.YNOTE, SHARE_MEDIA.EMAIL);
				mController.openShare(getActivity(), false);
			}
		});
		btnScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				IntentIntegrator integrator = new IntentIntegrator(CircleFragment.this.getActivity());
				integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
				integrator.setPrompt("Scan a QRcode");
				integrator.setCameraId(0);  // Use a specific camera of the device
				integrator.setBeepEnabled(false);
				integrator.initiateScan();
			}
		});
	}

	@Override
	protected void initData() {
        Drawable testIcon = getResources().getDrawable(R.drawable.ic_launcher);
        testIcon.setBounds(0, 0, testIcon.getIntrinsicHeight(), testIcon.getIntrinsicWidth());
        tvWeather.setCompoundDrawables(testIcon, null, null, null);
        tvWeather.setCompoundDrawablePadding(10);
	}

	@Override
	protected void afterAllSet() {
        showProcessingDialog(null, true, null);
		netHandler.getForWeather(WeatherCityCode.findCityCodeByCityName("苏州"), new Callback<WeatherResult>() {
			@Override
			public void success(WeatherResult result, Response response) {
				tvWeather.setText("SUCCESS: " + result);
				dismissProcessingDialog();
			}
			@Override
			public void failure(RetrofitError error) {
				RetrofitNetHandler.toastNetworkError(CircleFragment.this.getActivity(), error);
				dismissProcessingDialog();
			}
		});
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Utils.toastMessage(CircleFragment.this.getActivity(), "Cancelled", Toast.LENGTH_LONG);
            } else {
                Log.d("MainActivity", "Scanned");
                Utils.toastMessage(CircleFragment.this.getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG);
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
