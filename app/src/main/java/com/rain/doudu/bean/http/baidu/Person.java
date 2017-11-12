package com.rain.doudu.bean.http.baidu;

import java.io.Serializable;

/**
 * Created by rain on 2017/4/29.
 */

public class Person implements Serializable {
    //当前登录用户的数字ID
    private int userid;
    //当前登录用户的用户名，值可能为空。
    private String username;
    //用户真实姓名，可能为空。
    private String realname;
    //当前登录用户的头像
    private String portrait;
    //自我简介，可能为空。
    private String userdetail;
    //生日，以yyyy-mm-dd格式显示。
    private String brithday;
    //婚姻状况
    private String marriage;
    //性别。"1"表示男，"0"表示女。
    private String sex;
    //血型
    private String blood;
    //体型
    private String figure;
    //星座
    private String constellation;
    //学历
    private String education;
    //当前职业
    private String trade;
    //职位
    private String job;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getUserdetail() {
        return userdetail;
    }

    public void setUserdetail(String userdetail) {
        this.userdetail = userdetail;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", portrait='" + portrait + '\'' +
                ", userdetail='" + userdetail + '\'' +
                ", brithday='" + brithday + '\'' +
                ", marriage='" + marriage + '\'' +
                ", sex='" + sex + '\'' +
                ", blood='" + blood + '\'' +
                ", figure='" + figure + '\'' +
                ", constellation='" + constellation + '\'' +
                ", education='" + education + '\'' +
                ", trade='" + trade + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
