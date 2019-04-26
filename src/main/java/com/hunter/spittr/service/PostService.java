package com.hunter.spittr.service;

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
    public Map<PostPo,List<PostPo>> getAllPost(int id);
}
