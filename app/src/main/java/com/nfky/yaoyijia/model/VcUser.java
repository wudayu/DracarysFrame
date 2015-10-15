package com.nfky.yaoyijia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.nfky.yaoyijia.constant.Constant;

/**
 * Created by David on 8/25/15.
 * <p/>
 * VcUser是UserModel的Object Json对象，并且加入了数据库与Jackson的处理注解
 * 其他model的格式需要按照此文件编写
 */
@JsonIgnoreProperties(ignoreUnknown=Constant.jsonIgnoreUnknown)
@DatabaseTable(tableName="VcUser")
public class VcUser {

	/**
	 * The constant USER_ID.
	 */
	public static final String USER_ID = "user_id";
	/**
	 * The constant USER_NAME.
	 */
	public static final String USER_NAME = "user_name";
	/**
	 * The constant USER_PASS.
	 */
	public static final String USER_PASS = "user_pass";
	/**
	 * The constant SCREEN_NAME.
	 */
	public static final String SCREEN_NAME = "screen_name";
	/**
	 * The constant M_PHONE.
	 */
	public static final String M_PHONE = "m_phone";
	/**
	 * The constant PHOTO_SRC.
	 */
	public static final String PHOTO_SRC = "photo_src";
	/**
	 * The constant APPROVE_STATE.
	 */
	public static final String APPROVE_STATE = "approve_state";
	/**
	 * The constant BROKER_TYPE.
	 */
	public static final String BROKER_TYPE = "broker_type";
	/**
	 * The constant SUPERIOR_ID.
	 */
	public static final String SUPERIOR_ID = "superior_id";


	/**
	 * The Id.
	 */
	@DatabaseField(id = true, columnName = USER_ID)
	String id;
	/**
	 * The Name.
	 */
	@DatabaseField(columnName = USER_NAME)
	String name;
	/**
	 * The Password.
	 */
	@DatabaseField(columnName = USER_PASS)
    String password;
	/**
	 * The Screen name.
	 */
	@DatabaseField(columnName = SCREEN_NAME)
	String screenName;
	/**
	 * The Mphone.
	 */
	@DatabaseField(columnName = M_PHONE)
	String mphone;
	/**
	 * The Photosrc.
	 */
	@DatabaseField(columnName = PHOTO_SRC)
	String photosrc;
	/**
	 * The Approve state.
	 */
	@DatabaseField(columnName = APPROVE_STATE)
	String approveState; // Constant.APPROVE_STATE
	/**
	 * The Broker type.
	 */
	@DatabaseField(columnName = BROKER_TYPE)
	String brokerType;
	/**
	 * The Superior id.
	 */
	@DatabaseField(columnName = SUPERIOR_ID)
	String superiorId;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
        return password;
    }

	/**
	 * Sets password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
        this.password = password;
    }

	/**
	 * Gets screen name.
	 *
	 * @return the screen name
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * Sets screen name.
	 *
	 * @param screenName the screen name
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * Gets mphone.
	 *
	 * @return the mphone
	 */
	public String getMphone() {
		return mphone;
	}

	/**
	 * Sets mphone.
	 *
	 * @param mphone the mphone
	 */
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	/**
	 * Gets photosrc.
	 *
	 * @return the photosrc
	 */
	public String getPhotosrc() {
		return photosrc;
	}

	/**
	 * Sets photosrc.
	 *
	 * @param photosrc the photosrc
	 */
	public void setPhotosrc(String photosrc) {
		this.photosrc = photosrc;
	}

	/**
	 * Gets approve state.
	 *
	 * @return the approve state
	 */
	public String getApproveState() {
		return approveState;
	}

	/**
	 * Sets approve state.
	 *
	 * @param approveState the approve state
	 */
	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	/**
	 * Gets broker type.
	 *
	 * @return the broker type
	 */
	public String getBrokerType() {
		return brokerType;
	}

	/**
	 * Sets broker type.
	 *
	 * @param brokerType the broker type
	 */
	public void setBrokerType(String brokerType) {
		this.brokerType = brokerType;
	}

	/**
	 * Gets superior id.
	 *
	 * @return the superior id
	 */
	public String getSuperiorId() {
		return superiorId;
	}

	/**
	 * Sets superior id.
	 *
	 * @param superiorId the superior id
	 */
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
