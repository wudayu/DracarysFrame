package com.nfky.yaoyijia.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.constant.ExtraNames;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.net.INetHandler;
import com.nfky.yaoyijia.net.RetrofitNetHandler;

/**
 * Created by David on 5/28/15.
 * <p/>
 * Description: TradingResultActivity是微信支付的回调类在回调之后所前往的页面
 */
public class TradingResultActivity extends BaseActivity {

	/**
	 * The Btn my order.
	 */
	Button btnMyOrder = null;
	/**
	 * The Btn purchase.
	 */
	Button btnPurchase = null;
	/**
	 * The Tv info.
	 */
	TextView tvInfo = null;
	/**
	 * The Net handler.
	 */
	INetHandler netHandler = RetrofitNetHandler.getInstance();

	private String payNum = null;
	private String payMode = null;

	@Override
	protected void initContainer() {
		setContentView(R.layout.activity_trading_result);
	}

	@Override
	protected void initComponents() {
		btnMyOrder = (Button) findViewById(R.id.btn_my_order);
		btnPurchase = (Button) findViewById(R.id.btn_purchase);
		tvInfo = (TextView) findViewById(R.id.tv_info);
	}

	@Override
	protected void initEvents() {
		btnMyOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent();
//			    intent.setClass(TradingResultActivity.this, MyOrderActivity.class);
//			    intent.putExtra(ExtraNames.ORDER_STATUS, OrderType.IS_PAY);
//			    intent.putExtra(ExtraNames.PAY_TO_ORDER, "");
//			    startActivity(intent);
			    finish();
			}
		});

		btnPurchase.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(TradingResultActivity.this, SaveMoneyActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			    startActivity(intent);
			}
		});
	}

	@Override
	protected void initData() {
		// 根据传入的参数做分支处理（支付成功或支付失败）
		boolean isPaySuccess = getIntent().getBooleanExtra(ExtraNames.IS_PAY_SUCCESSED, false);
		payNum = getIntent().getStringExtra(ExtraNames.PAY_NUM);
		payMode = getIntent().getStringExtra(ExtraNames.PAY_MODE);

		if (!isPaySuccess) {
			setViewsUnsuccess();
		}

		Utils.debug(Constant.TAG, "isPaySuccess = " + isPaySuccess + "; payNum = " + payNum + "; payMode = " + payMode);
	}

	@Override
	protected void afterAllSet() {
	}

	private void setViewsUnsuccess() {
		tvInfo.setTextColor(this.getResources().getColor(R.color.letter_grey_deep_9));
		tvInfo.setText(R.string.tv_order_pay_fail);
		Drawable drawable = getResources().getDrawable(R.drawable.pay_fail);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tvInfo.setCompoundDrawables(drawable, null, null, null);
	}

}
