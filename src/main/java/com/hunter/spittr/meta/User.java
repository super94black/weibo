package com.hunter.spittr.meta;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class User {

    private long id;

    @Pattern(regexp = "^[a-zA-Z0-9_]{5,11}$", message = "{username.size}")
    private String username;

    @Size(min = 8, max = 16, message = "{password.size}")
    private String password;


    //用户头像及头像缩略图
    private String headIcon;
    private String thumbnail;

    @Pattern(regexp = "^[\u4e00-\u9fa5a-zA-Z0-9_]{2,12}$", message = "{nickname.size}")
    private String nickname;

    private Timestamp create_time;
    private int type;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
