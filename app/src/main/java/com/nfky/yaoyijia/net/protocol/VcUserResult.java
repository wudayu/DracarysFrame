package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nfky.yaoyijia.model.VcUser;

/**
 * Created by David on 8/25/15.
 * <p/>
 * DafUserResult是继承于BaseResult的数据对象，其承载了一个DafUser对象
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VcUserResult extends BaseResult {

	private VcUser objValue;

	/**
	 * Gets obj value.
	 *
	 * @return the obj value
	 */
	public VcUser getObjValue() {
		return objValue;
	}

	/**
	 * Sets obj value.
	 *
	 * @param user the user
	 */
	public void setObjValue(VcUser user) {
		this.objValue = user;
	}

	@Override
	public String toString() {
		return "XcfcUserResult [" + super.toString() + ", user=" + objValue + "]";
	}
}
