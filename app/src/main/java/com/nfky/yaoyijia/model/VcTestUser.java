package com.nfky.yaoyijia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.nfky.yaoyijia.constant.Constant;

/**
 * Created by David on 8/25/15.
 * <p/>
 * VcTestUser是UserModel的Object Json对象，测试用，可以删除
 */
@JsonIgnoreProperties(ignoreUnknown=Constant.jsonIgnoreUnknown)
public class VcTestUser {

	private String uuid;
    private String userName;
    private int age;
    private String createDate;
    private String modifyDate;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets modify date.
     *
     * @return the modify date
     */
    public String getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets modify date.
     *
     * @param modifyDate the modify date
     */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "VcTestUser{" +
                "uuid='" + uuid + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                '}';
    }

}
/*
    "uuid" : "19506268550842b6b05bb5e3c0f618ce",
    "createDate" : "2015-04-23 16:00:00",
    "modifyDate" : "2015-04-23 16:00:00",
    "userName" : "James Chow",
    "age" : 20
*/
