package com.nfky.yaoyijia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.nfky.yaoyijia.constant.Constant;

/**
 *
 * Created by David on 8/25/15.
 *
 * VcTestUser是UserModel的Object Json对象，测试用，可以删除
 * 
 **/

@JsonIgnoreProperties(ignoreUnknown=Constant.jsonIgnoreUnknown)
public class VcTestUser {

	private String uuid;
    private String userName;
    private int age;
    private String createDate;
    private String modifyDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

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
