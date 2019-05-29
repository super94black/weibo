package com.hunter.spittr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hunter.spittr.dao.PostDao;
import com.hunter.spittr.dao.UserDao;
import com.hunter.spittr.dao.VideoDao;
import com.hunter.spittr.dao.ZanDao;
import com.hunter.spittr.meta.*;
import com.hunter.spittr.service.PostService;
import com.hunter.spittr.service.VideoService;
import com.hunter.spittr.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


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
    UserDao userDao;
    @Autowired
    ZanDao zanDao;
    @Autowired
    VideoService videoService;
    @Autowired
    VideoDao videoDao;
    @Autowired
    WordService wordService;



    /**
     * 获取全部动态，这个代码是构造回复List
     * @param
     * @return
     */

    public PageVo<Post>  getAllPost(int pageNum, int userId,int type,int postId){
        List<Post> list = new ArrayList<Post>();
        PageHelper.startPage(pageNum, 3);
        //startPage方法之后紧跟的查询 才是 分页查询
        if(userId != 0 && type == 1){
            //type == 1 userId != 0说明查询的是该用户所发送所有动态
            list = postDao.getAllPostByUid(userId);
        }else if(type == 0){
            //type == 0 说明无论用户登陆与否查询的是所有人的动态
            list = postDao.getAllPostByPid();
        }else if(type == 2){
            //type = 2 说明是通过PostID获取所有帖子
            list.add(postDao.getPostById(postId));
        }

        //使用pageInfo包装查询后的结果，只需将pageInfo交给页面即可
        PageInfo<Post> pageInfo = new PageInfo<Post>(list);

        PageVo<Post> pageVo = new PageVo<Post>();
        Map<PostPo, List<PostPo>> map = new HashMap<PostPo, List<PostPo>>();
        if(null == list || list.size() == 0){
            pageVo.setMap(map);
            pageVo.setPageInfo(pageInfo);
            return pageVo;
        }


        for (Post post : list) {
            PostPo postPo = new PostPo();
            List<PostPo> postPoList = new ArrayList<PostPo>();
            postPo.setUser(userDao.getByUserId(post.getUid()));
            postPo.setZanCount(zanDao.getZanCountByPostId(post.getId()));
            Zan zan = zanDao.getZanByPostIdAndUid(post.getId(),userId);

            if(null != zan)
                postPo.setIsZan(1);
            else
                postPo.setIsZan(0);
            postPo.setPost(post);
            postPo.setReplayName(null);
            Video video = videoDao.getVideoByPid(post.getId());
            if(null != video && !"".equals(video.getName())){
                postPo.setVideo(video);
            }
            postPoList = getAllPost(postPoList,post);
            map.put(postPo,postPoList);
        }

        pageVo.setMap(map);
        pageVo.setPageInfo(pageInfo);
        return pageVo;
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
                postVo.setUser(userDao.getByUserId(p.getUid()));
                postVo.setPost(p);
                Post parent1 = postDao.getPostById(p.getPid());
                if(parent1.getRoot() == 1)
                    postVo.setReplayName(null);
                else{

                    User spitter = userDao.getByUserId(parent1.getUid());
                    postVo.setReplayName(spitter.getNickname());
                }
                plist.add(postVo);
                getAllPost(plist,p);


            }else {
                PostPo postPo = new PostPo();
                postPo.setUser(userDao.getByUserId(p.getUid()));
                postPo.setPost(p);
                Post parent = postDao.getPostById(p.getPid());
                if(parent.getRoot() == 1)
                    postPo.setReplayName(null);
                else{
                    User spitter = userDao.getByUserId(parent.getUid());
                    postPo.setReplayName(spitter.getNickname());

                }
                plist.add(postPo);
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



    @Transactional
    public boolean replayPost(Post post){

        post.setCreate_time(new Timestamp(System.currentTimeMillis()));
        post.setIs_leaf(1);
        post.setType(1);
        post.setRoot(0);
        postDao.replayPost(post);

        Post p = postDao.getPostById(post.getPid());//此处的pid是postId
        if(post.getIs_leaf() == 1){
            postDao.updatePostLeaf(p.getId());
        }
        return true;
    }

    @Transactional
    public void deleteByPostId(int id){
        postDao.deleteByPostId(id);
    }

    @Transactional
    public void post(Integer uid,String imgAdd,String vodAdd,String content)throws Exception{
        Post post = new Post();
        if(wordService.isHaveNotAcceptWords(content) || null != imgAdd || null != vodAdd)
            post.setType(0);
        else
            post.setType(1);
        post.setUid(uid);
        post.setImg_add(imgAdd);
        post.setContent(content);
        post.setCreate_time(new Timestamp(System.currentTimeMillis()));
        post.setUpdate_time(new Timestamp(System.currentTimeMillis()));
        post.setPid(0);
        post.setIs_leaf(1);
        post.setRoot(1);
        postDao.post(post);
        System.out.println(post.getId());

        if(null != vodAdd && !"".equals(vodAdd)){
            Video video = new Video();
            video.setPid(post.getId());
            video.setName(vodAdd);
            video.setCreate_time(new Timestamp(System.currentTimeMillis()));
            video.setUpdate_time(new Timestamp(System.currentTimeMillis()));
            video.setType(1);
            videoService.addVideo(video);
        }
    }


    /**
     * 管理员获取所有没有审核的帖子
     * @return
     * @throws Exception
     */
    @Transactional
    public List<PostPo> getUncheckedPost(){
        List<PostPo> uncheckedList = new ArrayList<PostPo>();
        try {

            List<Post> list = postDao.getUncheckedPost();
            if(null != list && list.size() != 0){
                for (Post post:list) {
                    PostPo postPo = new PostPo();
                    postPo.setUser(userDao.getByUserId(post.getUid()));
                    postPo.setPost(post);
                    Video video = videoDao.getVideoByPid(post.getId());
                    if(null != video && !video.getName().isEmpty())
                        postPo.setVideo(video);
                    uncheckedList.add(postPo);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return uncheckedList;
    }
    @Transactional
    public void updateByPostId(int pid) throws Exception{
        postDao.updateByPostId(pid);
    }

    @Transactional
    public List<Post> getHotTopic() throws Exception{
        List<HotTopic> list = zanDao.getHotTopic();
        List<Post> postList = new ArrayList<Post>();
        for (HotTopic hotTopic : list) {
            postList.add(postDao.getPostById(hotTopic.getPostId()));
        }
        return postList;
    }


    @Transactional
    public PageVo<Post> getPostById(int pageNum,int postId)throws Exception{
        return getAllPost(pageNum,0,2,postId);
    }



}
