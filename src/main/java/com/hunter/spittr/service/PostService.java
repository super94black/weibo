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
    public PageVo<Post> getAllPost(int pageNum,int userId,int type);
    public boolean replayPost(Post post);
    public void deleteByPostId(int id);
    public void post(Integer uid,String imgAdd,String vodAdd,String content)throws Exception;
    public List<PostPo> getUncheckedPost();
    public void updateByPostId(int pid) throws Exception;
    public List<Post> getHotTopic() throws Exception;
}
