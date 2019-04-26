package com.hunter.spittr.meta;

import com.github.pagehelper.PageInfo;
import org.springframework.mail.MailParseException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2019/4/26 22:41
 * @Content
 */
public class PageVo <T>{

    private Map<PostPo, List<PostPo>> map;
    private PageInfo<T> pageInfo;

    public Map<PostPo, List<PostPo>> getMap() {
        return map;
    }

    public void setMap(Map<PostPo, List<PostPo>> map) {
        this.map = map;
    }

    public PageInfo<T> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<T> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
