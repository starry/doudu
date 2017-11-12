package com.rain.doudu.bean.http.jiangjianyu;

/**
 * Created by rain on 2017/4/27.
 */

public class Review {
    private String id;
    private String title;
    private String rating;
    private String content;
    private Integer Uid;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public Integer getUid() {
        return Uid;
    }

    public void setUid(Integer uid) {
        Uid = uid;
    }

    public Review() {
    }

    public Review(String id, String title, String rating, String content,  Integer uid) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.content = content;
        this.Uid = uid;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rating='" + rating + '\'' +
                ", content='" + content + '\'' +
                ", Uid=" + Uid +
                '}';
    }
}
