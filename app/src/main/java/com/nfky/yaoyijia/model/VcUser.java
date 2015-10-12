package com.nfky.yaoyijia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.nfky.yaoyijia.constant.Constant;

/**
 *
 * Created by David on 8/25/15.
 *
 * VcUser是UserModel的Object Json对象，并且加入了数据库与Jackson的处理注解
 * 其他model的格式需要按照此文件编写
 * 
 **/

@JsonIgnoreProperties(ignoreUnknown=Constant.jsonIgnoreUnknown)
@DatabaseTable(tableName="VcUser")
public class VcUser {

	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "user_name";
	public static final String SCREEN_NAME = "screen_name";
	public static final String M_PHONE = "m_phone";
	public static final String PHOTO_SRC = "photo_src";
	public static final String APPROVE_STATE = "approve_state";
	public static final String BROKER_TYPE = "broker_type";
	public static final String SUPERIOR_ID = "superior_id";


	@DatabaseField(id = true, columnName = USER_ID)
	String id;
	@DatabaseField(columnName = USER_NAME)
	String name;
	@DatabaseField(columnName = SCREEN_NAME)
	String screenName;
	@DatabaseField(columnName = M_PHONE)
	String mphone;
	@DatabaseField(columnName = PHOTO_SRC)
	String photosrc;
	@DatabaseField(columnName = APPROVE_STATE)
	String approveState; // Constant.APPROVE_STATE
	@DatabaseField(columnName = BROKER_TYPE)
	String brokerType;
	@DatabaseField(columnName = SUPERIOR_ID)
	String superiorId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getMphone() {
		return mphone;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	public String getPhotosrc() {
		return photosrc;
	}

	public void setPhotosrc(String photosrc) {
		this.photosrc = photosrc;
	}

	public String getApproveState() {
		return approveState;
	}

	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	public String getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(String brokerType) {
		this.brokerType = brokerType;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	@Override
	public String toString() {
		return "XcfcUser [id=" + id + ", name=" + name + ", screenName="
				+ screenName + ", mphone=" + mphone + ", photosrc=" + photosrc
				+ ", approveState=" + approveState + "]";
	}
}
