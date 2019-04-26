package com.hunter.spittr.service.impl;

import com.hunter.spittr.dao.PostDao;
import com.hunter.spittr.dao.SpitterDao;
import com.hunter.spittr.meta.Post;
import com.hunter.spittr.meta.PostPo;
import com.hunter.spittr.meta.PostVo;
import com.hunter.spittr.meta.Spitter;
import com.hunter.spittr.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author zhang
 * @Date 2019/4/25 16:11
 * @Content
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;
    @Autowired
    SpitterDao spitterDao;


    /**
     * 获取全部动态，这个代码是构造回复List
     * @param id
     * @return
     */

    public Map<PostPo,List<PostPo>> getAllPost(int id){
        Map<PostPo,List<PostPo>> map = new HashMap<PostPo, List<PostPo>>();
        List<Post> list = new ArrayList<Post>();
        list = postDao.getAllPostByPid(id);
        if(null == list || list.size() == 0)
            return null;


        for (Post post : list) {
            PostPo postPo = new PostPo();
            List<PostPo> postPoList = new ArrayList<PostPo>();
            postPo.setSpitter(spitterDao.getByUserId(post.getUid()));
            postPo.setPost(post);
            postPo.setReplayName(null);

            postPoList = getAllPost(postPoList,post);
            map.put(postPo,postPoList);


        }
//
        return map;
    }

    /**
     * 递归获取给定帖子的所有回复，并把回复按照顺寻放到List中去
     * @param plist
     * @param post
     * @return
     */
    public List<PostPo> getAllPost(List<PostPo> plist,Post post){
        //以该节点的id作为下个节点的pid
        List<Post> list = postDao.getAllPostByPidAndAsc(post.getId());

        for (Post p :list) {

            if(p.getIs_leaf() == 0){
                //如果不是叶子节点
                PostPo postVo = new PostPo();
                postVo.setSpitter(spitterDao.getByUserId(p.getUid()));
                postVo.setPost(p);
                Post parent1 = postDao.getPostById(p.getPid());
                if(parent1.getRoot() == 1)
                    postVo.setReplayName(null);
                else{

                    Spitter spitter = spitterDao.getByUserId(parent1.getUid());
                    postVo.setReplayName(spitter.getUsername());
                }
                plist.add(postVo);
                getAllPost(plist,p);


            }else {
                PostPo postPo = new PostPo();
                postPo.setSpitter(spitterDao.getByUserId(p.getUid()));
                postPo.setPost(p);
                Post parent = postDao.getPostById(p.getPid());
                if(parent.getRoot() == 1)
                    postPo.setReplayName(null);
                else{
                    Spitter spitter = spitterDao.getByUserId(parent.getUid());
                    postPo.setReplayName(spitter.getUsername());

                }
                plist.add(postPo);


                return plist;
            }
        }

        return plist;
    }

    /**
     * 这个代码是构造评论树，但是前端太难，故改成上面的代码逻辑
     */
//    public List<PostVo> getAllPost(int id){
//        Map<Post,List> map = new HashMap<Post, List>();
//        List<Post> list = new ArrayList<Post>();
//        list = postDao.getAllPost();
//        if(null == list || list.size() == 0)
//            return null;
//
//        List<PostVo> all = new ArrayList<PostVo>();
//        for (Post post : list) {
//            PostVo postVo = new PostVo();
//            List<PostVo> postVoList = new ArrayList<PostVo>();
//            postVo.setPost(post);
//            postVo.setList(getAllPost(postVoList,post));
//            all.add(postVo);
//
//        }
////
//        return all;
//    }
//
//    public List getAllPost(List<PostVo> plist,Post post){
//        //以该节点的id作为下个节点的pid
//        List<Post> list = postDao.getAllPostByPid(post.getId());
//
//        for (Post p :list) {
//
//            if(p.getIsLeaf() == 0){
//                //如果不是叶子节点
//                List postVoList = getAllPost(new ArrayList<PostVo>(),p);
//                PostVo postVo = new PostVo();
//                postVo.setPost(p);
//                postVo.setList(postVoList);
//                plist.add(postVo);
//
//            }else {
//                PostVo postVo = new PostVo();
//                postVo.setPost(p);
//                postVo.setList(new ArrayList<PostVo>());
//                plist.add(postVo);
//                return plist;
//            }
//        }
//
//        return plist;
//    }

}
