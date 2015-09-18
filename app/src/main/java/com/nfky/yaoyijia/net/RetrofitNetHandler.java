package com.nfky.yaoyijia.net;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.mime.MultipartTypedOutput;

import android.app.Activity;
import android.util.Base64;

import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.model.TypedImage;
import com.nfky.yaoyijia.model.VcTestUser;
import com.nfky.yaoyijia.model.VcUser;
import com.nfky.yaoyijia.net.converter.JacksonConverter;
import com.nfky.yaoyijia.net.protocol.VcListResult;
import com.nfky.yaoyijia.net.protocol.VcObjectResult;
import com.nfky.yaoyijia.net.protocol.WeatherResult;
import com.nfky.yaoyijia.net.service.ImageService;
import com.nfky.yaoyijia.net.service.UserService;
import com.nfky.yaoyijia.net.service.WeatherService;

/**
 *
 * Created by David on 8/25/15.
 *
 * RetrofitNetHandler是INetHandler的Retrofit实现，目前本项目仅打算使用Retrofit作为其网络访问框架
 *
 **/

public class RetrofitNetHandler implements INetHandler {

	RestAdapter weatherAdapter = null;
	RestAdapter generalAdapter = null;

	/** Generate the Singleton */
	private static volatile RetrofitNetHandler instance;

	/**
	 * 初始化链接Adapter
	 */
	private RetrofitNetHandler() {
		weatherAdapter = new RestAdapter.Builder().setEndpoint(SERVER_URL_WEATHER).setConverter(new JacksonConverter(JacksonConverter.TEXT_HTML_VALUE)).build();
		generalAdapter = new RestAdapter.Builder().setEndpoint(SERVER_URL_FOR_RETROFIT).setConverter(new JacksonConverter()).build();
	}

	public static INetHandler getInstance() {
		if (instance == null) {
			synchronized (RetrofitNetHandler.class) {
				if (instance == null) {
					instance = new RetrofitNetHandler();
				}
			}
		}

		return instance;
	}

	/**
	 * 供Activity使用，用来打印RetrofitError
	 *
	 * @param activity 所调用的Activity
	 * @param error 需要打印的错误
	 */
	public static void toastNetworkError(Activity activity, RetrofitError error) {
		if (error.getKind() == RetrofitError.Kind.NETWORK) {
			Utils.toastMessage(activity, activity.getString(R.string.error_cannot_connect_server));
		} else if (error.getKind() == RetrofitError.Kind.CONVERSION) {
			Utils.toastMessage(activity, activity.getString(R.string.error_data_format_wrong));
		} else if (error.getKind() == RetrofitError.Kind.HTTP) {
			Utils.toastMessage(activity, activity.getString(R.string.error_server_got_problems));
		} else {
			Utils.toastMessage(activity, activity.getString(R.string.error_unexpected_error));
		}
	}

	@Override
	public void getForWeather(String code, Callback<WeatherResult> cb) {
		weatherAdapter.create(WeatherService.class).getWeather(code, cb);
	}

	@Override
	public void getForUserInfo(String userId, Callback<VcObjectResult<VcUser>> cb) {
		generalAdapter.create(UserService.class).getUser(userId, "0", cb);
	}

    @Override
    public void getForGetTestUser(String userId, Callback<VcObjectResult<VcTestUser>> cb) {
        generalAdapter.create(UserService.class).getTestUser(userId, cb);
    }

    @Override
    public void postForUploadSinglePic(String imagePath, Callback<VcListResult<String>> cb) {
        postForUploadMultiplePic(new String[]{imagePath}, cb);
    }

    /**
     * postForUploadMultiplePic 上传图片
	 * 可以使用Map的方式或者MultipartTypedOutput的方式来上传图片
	 * 这两者的不同在于：在Map方式中每个图片的FILE__KEY必须不同
     *
     * @param imagePaths 需要上传的图片路径
     * @param cb 图片上传的回调
     */
    @Override
    public void postForUploadMultiplePic(String[] imagePaths, Callback<VcListResult<String>> cb) {
        /* Map method
        Map<String, TypedImage> typedImages = new HashMap<String, TypedImage>();
        for (String imagePath : imagePaths) {
            typedImages.put(INetHandler.UPLOAD_PIC_FILE_KEY + System.currentTimeMillis(), new TypedImage(imagePath));
        }

        // new TypedImage(imagePath) ==also=can=be==> new TypedFile("image/jpg", new File(imagePath)) and the "image/jpg"can also be "audio/mp3" and so on
        */
        /* MultipartTypedOutput method */
        MultipartTypedOutput typedImages = new MultipartTypedOutput();
        for (String imagePath : imagePaths) {
            typedImages.addPart(INetHandler.UPLOAD_PIC_FILE_KEY, new TypedImage(imagePath));
        }
        /**/

        generalAdapter.create(ImageService.class).uploadMultiplePic(typedImages, cb);
    }


	/**
	 * Interceptor used to authorize requests.
	 * 可以使用AuthRequestInterceptor来在每个http request的头部加入Auth信息
	 */
	public class AuthRequestInterceptor implements RequestInterceptor {

	    @Override
	    public void intercept(RequestFacade requestFacade) {

	        // if (user != null) {
	            final String authorizationValue = encodeCredentialsForBasicAuthorization();
	            requestFacade.addHeader("Authorization", authorizationValue);
	        //}
	    }

	    private String encodeCredentialsForBasicAuthorization() {
	    	final String username = "deadmin";
	    	final String password = "deadmin";
	        final String userAndPassword = username + ":" + password;
	        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
	    }

	    /*
	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }
	    */
	}
}
