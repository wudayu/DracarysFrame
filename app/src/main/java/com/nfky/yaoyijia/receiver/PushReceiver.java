package com.nfky.yaoyijia.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.service.PushService;

/**
 *
 * Created by David on 8/25/15.
 *
 * PushReceiver是用来唤醒PushService的广播。它们是一对，通过向服务器拉取推送来进行推送显示的，PushService的开关是Constant.needPush
 * 暂不推荐使用，请使用XgCustomPushReceiver
 *
 **/

public class PushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Constant.needPush) {
			Intent pushServiceIntent = new Intent(context, PushService.class);
			context.startService(pushServiceIntent);
		}
	}

}
