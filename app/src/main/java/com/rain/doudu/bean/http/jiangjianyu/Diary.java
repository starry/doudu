package com.rain.doudu.bean.http.jiangjianyu;

/**
 * Created by rain on 2017/3/20.
 */

public class Diary {
    private String title;
    private String content;
    private String dtime;
    private Integer uid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Diary() {
    }

    @Override
    public String toString() {
        return "Diary{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dtime='" + dtime + '\'' +
                ", uid=" + uid +
                '}';
    }

    public Diary(String title, String content, String dtime, Integer uid) {
        this.title = title;
        this.content = content;
        this.dtime = dtime;
        this.uid = uid;
    }
}
