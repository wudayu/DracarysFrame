package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nfky.yaoyijia.model.VcUser;

/**
 *
 * Created by David on 8/25/15.
 *
 * DafUserResult是继承于BaseResult的数据对象，其承载了一个DafUser对象
 *
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class VcUserResult extends BaseResult {

	private VcUser objValue;

	public VcUser getObjValue() {
		return objValue;
	}

	public void setObjValue(VcUser user) {
		this.objValue = user;
	}

	@Override
	public String toString() {
		return "XcfcUserResult [" + super.toString() + ", user=" + objValue + "]";
	}
}
