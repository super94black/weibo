package com.hunter.spittr.meta;

import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/25 18:27
 * @Content 动态包装类
 */
public class PostVo {

    private Post post;
    private List list;
    private String replayName;



    public String getReplayName() {
        return replayName;
    }

    public void setReplayName(String replayName) {
        this.replayName = replayName;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
