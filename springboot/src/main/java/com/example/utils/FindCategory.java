package com.example.utils;
import com.example.entity.News;
import com.example.service.CategoryService;
import com.example.service.NewsService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
/**
 * 工具类的目的是找到对应的种类
 * */
@Component
public class FindCategory {
    @Resource
    NewsService newsService;
    @Resource
    CategoryService categoryService;
    //找到根据新闻的种类找到对应的id
    public Integer findCategory(Integer id){
        News news = newsService.selectById(id);
        if (news == null) {
            // 处理新闻不存在的情况
            System.out.println("新闻不存在，id: " + id);
            return null;
        }
        String newsCategory = news.getCategory();
        if (newsCategory == null) {
            // 处理新闻分类为空的情况
            System.out.println("新闻分类为空，id: " + id);
            return null;
        }
        com.example.entity.Category category = categoryService.selectByName(newsCategory);
        if (category == null) {
            // 处理分类不存在的情况
            System.out.println("分类不存在，分类名称: " + newsCategory);
            return null;
        }
        return category.getId();
    }
}