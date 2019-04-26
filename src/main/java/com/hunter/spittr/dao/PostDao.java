package com.hunter.spittr.dao;

import com.hunter.spittr.meta.Post;
import com.hunter.spittr.meta.Spitter;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/25 16:12
 * @Content
 */
public interface PostDao {

    @Select("SELECT * FROM post WHERE pid = 0")
    List<Post> getAllPost();


    @Select("SELECT * FROM post WHERE pid = #{pid} order by create_time desc")
    List<Post> getAllPostByPid(int pid);

    @Select("SELECT * FROM post WHERE id = #{pid}")
    Post getPostById(int pid);

    @Select("SELECT * FROM post WHERE pid = #{pid} order by create_time asc")
    List<Post> getAllPostByPidAndAsc(int pid);
}
