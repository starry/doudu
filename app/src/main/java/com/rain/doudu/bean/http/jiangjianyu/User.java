package com.rain.doudu.bean.http.jiangjianyu;

import java.io.Serializable;

/**
 * Created by rain on 2017/3/11.
 */

public class User implements Serializable{
    public static final long serialVersionUID = 7060254125600463481L;
    private Integer myId;
    private String myBirth;
    private String myName;
    private String myPassword;
    private String myCity;
    private String myDesc;


    public Integer getMyId() {
        return myId;
    }

    public void setMyId(Integer myId) {
        this.myId = myId;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public String getMyBirth() {
        return myBirth;
    }

    public void setMyBirth(String myBirth) {
        this.myBirth = myBirth;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getMyCity() {
        return myCity;
    }

    public void setMyCity(String myCity) {
        this.myCity = myCity;
    }

    public String getMyDesc() {
        return myDesc;
    }

    public void setMyDesc(String myDesc) {
        this.myDesc = myDesc;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "myId=" + myId +
                ", myBirth='" + myBirth + '\'' +
                ", myName='" + myName + '\'' +
                ", myPassword='" + myPassword + '\'' +
                ", myCity='" + myCity + '\'' +
                ", myDesc='" + myDesc + '\'' +
                '}';
    }

    public User(Integer myId, String myBirth, String myName, String myPassword, String myCity, String myDesc) {
        this.myId = myId;
        this.myBirth = myBirth;
        this.myName = myName;
        this.myPassword = myPassword;
        this.myCity = myCity;
        this.myDesc = myDesc;
    }
}
