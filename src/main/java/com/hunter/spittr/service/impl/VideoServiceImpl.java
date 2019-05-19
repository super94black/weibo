package com.hunter.spittr.service.impl;

import com.hunter.spittr.dao.VideoDao;
import com.hunter.spittr.meta.Video;
import com.hunter.spittr.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author zhang
 * @Date 2019/5/19 14:22
 * @Content
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Transactional
    public void addVideo(Video video){
        videoDao.addVideo(video);
    }
}
