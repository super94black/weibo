package com.hunter.spittr.dao;

import com.hunter.spittr.meta.Zan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/28 15:17
 * @Content
 */
public interface ZanDao {

    @Select("select * from zan where post_id = #{post_id} and uid = #{uid} and type = 1")
    Zan getZanByPostIdAndUid(@Param("post_id") int post_id, @Param("uid") int uid);
    @Insert("INSERT INTO zan(post_id, uid, create_time, type) VALUES(#{post_id}, #{uid}, #{create_time},#{type})")
    void addZanByPostIdAndUid(Zan zan);

    @Select("SELECT count(*) FROM `zan` where post_id = #{post_id} and type = 1")
    int getZanCountByPostId(int post_id);
    @Update("update zan set type = 0 where post_id = #{post_id} and uid = #{uid}")
    void delZanByPostIdAndUid(@Param("post_id") int post_id, @Param("uid") int uid);

}
