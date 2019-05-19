package com.hunter.spittr.dao;

import com.hunter.spittr.meta.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2019/5/19 14:18
 * @Content
 */
public interface VideoDao {

    @Insert("INSERT INTO video(pid,name,create_time, " +
            " update_time, type) VALUES (#{pid}, #{name},#{create_time}," +
            "#{update_time}, #{type})")
    void addVideo(Video video);

    @Select("select * from video where pid = #{pid}")
    Video getVideoByPid(int pid);

}
