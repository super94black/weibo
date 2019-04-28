package com.hunter.spittr.meta;

/**
 * @Author zhang
 * @Date 2019/4/26 14:23
 * @Content
 */
public class PostPo {

    private Post post;
    private String replayName;
    private User user;
    private int zanCount;
    private int isZan;

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }

    public int getIsZan() {
        return isZan;
    }

    public void setIsZan(int isZan) {
        this.isZan = isZan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getReplayName() {
        return replayName;
    }

    public void setReplayName(String replayName) {
        this.replayName = replayName;
    }
}
