package com.hunter.spittr.meta;

import java.sql.Timestamp;

/**
 * @Author zhang
 * @Date 2019/4/27 19:25
 * @Content
 */
public class Zan {

    private int id;
    private int post_id;
    private int uid;
    private Timestamp create_time;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
