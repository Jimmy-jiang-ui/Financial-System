package com.example.utils;
import com.example.controller.NewsController;
import com.example.entity.News;
import com.example.service.impl.NewsServiceImpl;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.List;


public class HotCaculator {
    @Resource
    private NewsServiceImpl newsService;
    //将数据传入之后计算得到一个加权值
    public List<News> caculatorWithData(){
        /**
         * 浏览readCount为1
         * 收藏collectCount为2
         * 时间差为被除数
         * */
        News news = new News();
        List<News> newsList = newsService.selectAll(news);
        return newsList;
    }
}
