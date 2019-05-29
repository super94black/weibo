package com.hunter.spittr.dao;

import com.hunter.spittr.meta.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/25 16:12
 * @Content
 */
public interface PostDao {

    @Select("SELECT * FROM post WHERE uid = #{uid} and pid = 0 and type = 1 order by create_time desc")
    List<Post> getAllPostByUid(int uid);



    @Select("SELECT * FROM post WHERE pid = 0 and type = 0 order by create_time desc")
    List<Post> getUncheckedPost();


    @Select("SELECT * FROM post WHERE pid = 0 and type = 1 order by create_time desc")
    List<Post> getAllPostByPid();

    @Select("SELECT * FROM post WHERE id = #{pid} and type = 1")
    Post getPostById(int pid);

    @Select("SELECT * FROM post WHERE pid = #{pid} and type = 1 order by create_time asc")
    List<Post> getAllPostByPidAndAsc(int pid);

    @Insert("INSERT INTO post(img_add, content, pid, uid, root, is_leaf, create_time, " +
            "update_time, type) VALUES (#{img_add}, #{content}, #{pid}, #{uid}, #{root}, #{is_leaf}, #{create_time}," +
            "#{update_time}, #{type})")
    void replayPost(Post post);

    @Insert("INSERT INTO post(img_add, content, pid, uid, root, is_leaf, create_time, " +
            "update_time, type) VALUES (#{img_add}, #{content}, #{pid}, #{uid}, #{root}, #{is_leaf}, #{create_time}," +
            "#{update_time}, #{type})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int post(Post post);

    @Update("update post set is_leaf = 0 where id = #{id}")
    void updatePostLeaf(int id);

    @Update("update post set type = 0 where id = #{id}")
    void deleteByPostId(int id);

    @Update("update post set type = 1 where id = #{pid}")
    void updateByPostId(int pid);



}
