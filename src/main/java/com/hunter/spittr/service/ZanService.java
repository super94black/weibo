package com.hunter.spittr.service;

/**
 * @Author zhang
 * @Date 2019/4/28 15:36
 * @Content
 */
public interface ZanService {

    public void addZanByPidAndUid(int postId, int uid);
    public void delZanByPostIdAndUid(int postId, int uid);

}
