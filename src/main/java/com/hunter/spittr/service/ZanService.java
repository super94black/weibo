package com.hunter.spittr.service;

import com.hunter.spittr.meta.HotTopic;

import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/28 15:36
 * @Content
 */
public interface ZanService {

    public void addZanByPidAndUid(int postId, int uid);
    public void delZanByPostIdAndUid(int postId, int uid);
    public List<HotTopic> getHotTopic() throws Exception;

}
