package com.hunter.spittr.meta;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author zhang
 * @Date 2019/4/25 16:08
 * @Content
 */

public class Post {

    private int id;
    private String content;
    private int pid;
    private int uid;
    private String img_add;
    private int root;
    private int is_leaf;
    private Timestamp create_time;
    private Timestamp update_time;
    private int type;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg_add() {
        return img_add;
    }

    public void setImg_add(String img_add) {
        this.img_add = img_add;
    }

    public int getIs_leaf() {
        return is_leaf;
    }

    public void setIs_leaf(int is_leaf) {
        this.is_leaf = is_leaf;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }


}
