package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.News;
import com.example.mapper.NewsMapper;
import com.example.service.CategoryService;
import com.example.service.CommentService;
import com.example.service.NewsService;
import com.example.utils.FindCategory;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService{
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private CommentService commentService;
    @Resource
    private CategoryService categoryService;
    /**
     * 新增
     */
    public void add(News news) {
        news.setTime(DateUtil.now());
        //从Token工具当中解析出来当前用户，因为Token当中包含了用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        //判断是否是管理员，也就是只有管理员才能加入
        if (currentUser.getRole().equals(RoleEnum.ADMIN.name())) {
            news.setStatus("通过");
            news.setType("common");
        }else{
            news.setStatus("待审核");
            news.setType("local");
            news.setUserId(currentUser.getId());
        }
        newsMapper.insert(news);
    }
    /**
     * 删除
     */
    public void deleteById(Integer id) {
        newsMapper.deleteById(id);
    }
    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            newsMapper.deleteById(id);
        }
    }
    public List<News> selectHot() {
        List<News> newsList = newsMapper.selectAll(null);
        newsList = newsList.stream().sorted((n1,n2) -> n2.getReadCount() - n1.getReadCount()).limit(20).collect(Collectors.toList());
        Collections.shuffle(newsList);  // 打乱数据
        newsList = newsList.stream().limit(8).collect(Collectors.toList());
        return newsList;
    }

    public List<News> selectHotNewsList(){
        List<News> newsList = newsMapper.selectHotNewsList();
        return newsList;
    }
    /**
     * 修改
     */
    public void updateById(News news) {
        newsMapper.updateById(news);
    }
    /**
     * 根据ID查询
     */
    public News selectById(Integer id) {
        return newsMapper.selectById(id);
    }
    /**
     * 查询所有
     */
    public List<News> selectAll(News news) {
        return newsMapper.selectAll(news);
    }

    /**
     * 分页查询
     */
    public PageInfo<News> selectPage(News news, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            news.setType("local");
            news.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<News> list = newsMapper.selectAll(news);
        for(News n : list){
            Integer count = commentService.selectCount(n.getId(),"news");
            n.setCommentCount(count);
        }
        return PageInfo.of(list);
    }
    public PageInfo<News> selectPageForAll(News news, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<News> list = newsMapper.selectAll(news);
        for(News n : list){
            Integer count = commentService.selectCount(n.getId(),"news");
            n.setCommentCount(count);
        }
        return PageInfo.of(list);
    }
    public void updateCount(Integer id) {
        newsMapper.updateCount(id);
    }

    @Override
    public List<News> selectCaculate() {
        List<News> newsList = newsMapper.selectAll(null);
        return newsList;
    }

    @Override
    public void updateUserCount(Integer id) {
        Integer userId = TokenUtils.getCurrentUser().getId();
        News news = selectById(id);
        String newsCategory = news.getCategory();
        System.out.println(newsCategory);
        //传入阅读数据
        newsMapper.updateUserCount(id,userId,categoryService.selectByName(newsCategory).getId());
    }

    @Override
    public void updateCollectCount(Integer id) {
        newsMapper.updateCollectCount(id);
    }

    @Override
    public void deleteCollectCount(Integer id) {
        newsMapper.deleteCollectCount(id);
    }

    @Override
    public void addCollection(Integer id, Integer userId) {
        newsMapper.addCollection(id,userId);
    }

    @Override
    public void deleteCollection(Integer id, Integer userId) {
        newsMapper.deleteCollection(id,userId);
    }
    /**
     热点推荐环节
     */
    public List<News> selectHotByReadAndCollect(){
        List<News> newsList = newsMapper.getNewsWithCollectCount();
        // 计算每条新闻的综合得分并排序
        Collections.sort(newsList, new Comparator<News>() {
            @Override
            public int compare(News news1, News news2) {
                // 假设 time 是日期时间字符串，格式为 "yyyy-MM-dd HH:mm:ss"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long time1 = 0;
                long time2 = 0;
                try {
                    time1 = sdf.parse(news1.getTime()).getTime();
                    time2 = sdf.parse(news2.getTime()).getTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int readCount1 = news1.getReadCount();
                int readCount2 = news2.getReadCount();
                int collectCount1 = news1.getCollectCount();
                int collectCount2 = news2.getCollectCount();
                // 计算综合得分
                double score1 = (readCount1 * 1 + collectCount1 * 2) / (double) time1;
                double score2 = (readCount2 * 1 + collectCount2 * 2) / (double) time2;
                // 降序排序
                return Double.compare(score2, score1);
            }
        });
        // 截取前八条新闻
        int size = newsList.size();
        int endIndex = Math.min(8, size);
        return newsList.subList(0, endIndex);
    }
}
