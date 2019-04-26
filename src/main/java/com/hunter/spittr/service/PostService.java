package com.hunter.spittr.service;

import com.hunter.spittr.meta.PageVo;
import com.hunter.spittr.meta.Post;
import com.hunter.spittr.meta.PostPo;
import com.hunter.spittr.meta.PostVo;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2019/4/25 20:58
 * @Content
 */
public interface PostService {
    public PageVo<Post> getAllPost(int pageNum,int id);
    public boolean replayPost(Post post);
}
