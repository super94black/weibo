package com.hunter.spittr.meta;

/**
 * @Author zhang
 * @Date 2019/4/26 14:23
 * @Content
 */
public class PostPo {

    private Post post;
    private String replayName;
    private Spitter spitter;

    public Spitter getSpitter() {
        return spitter;
    }

    public void setSpitter(Spitter spitter) {
        this.spitter = spitter;
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
