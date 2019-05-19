package com.hunter.spittr.service.impl;

import com.hunter.spittr.dao.ZanDao;
import com.hunter.spittr.meta.HotTopic;
import com.hunter.spittr.meta.Zan;
import com.hunter.spittr.service.ZanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/28 15:22
 * @Content
 */
@Service
public class ZanServiceImpl implements ZanService {

    @Autowired
    ZanDao zanDao;

    @Transactional
    public void addZanByPidAndUid(int postId, int uid){
        Zan zan = new Zan();
        zan.setPost_id(postId);
        zan.setUid(uid);
        zan.setCreate_time(new Timestamp(System.currentTimeMillis()));
        zan.setType(1);
        zanDao.addZanByPostIdAndUid(zan);
    }

    @Transactional
    public void delZanByPostIdAndUid(int postId, int uid){
        zanDao.delZanByPostIdAndUid(postId,uid);
    }

    @Transactional
    public List<HotTopic> getHotTopic() throws Exception{
        return zanDao.getHotTopic();
    }
}
