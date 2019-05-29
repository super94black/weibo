package com.hunter.spittr.service.impl;

import com.hunter.spittr.service.WordService;
import com.hunter.spittr.util.BadWordUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author zhang
 * @Date 2019/5/29 20:10
 * @Content 敏感词汇业务层
 */
@Service
public class WordServiceImpl implements WordService {


    /**
     * 检测是否有敏感词汇
     * @param content
     * @return
     */
    public boolean isHaveNotAcceptWords(String content){
//        List<Word> list = getWordList();
//        if(null == list || 0 == list.size())
//            return false;
//        for (Word word :list) {
//            if(content.indexOf(word.getWord()) != -1)
//                return true;
//        }
//        return false;

        return BadWordUtil2.isContaintBadWord(content,2);

    }

//    public List<String> spiltStrign(String content){
//        List<String> list = new ArrayList<String>();
//        for (int i = 0; i < content.length(); i++){
//            for (int j = i+1; j < content.length(); j++){
//                list.add(content.indexOf())
//            }
//        }
//    }
}
