package com.example.service;
import com.example.entity.News;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface NewsService {
    void add(News news);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(News news);
    News selectById(Integer id);
    List<News> selectAll(News news);
    PageInfo<News> selectPage(News news, Integer pageNum, Integer pageSize);
    PageInfo<News> selectPageForAll(News news, Integer pageNum, Integer pageSize);
    List<News> selectHot();
    void updateCount(Integer id);
    List<News> selectCaculate();
    void updateUserCount(Integer id);

    void updateCollectCount(Integer id);

    void deleteCollectCount(Integer id);

    void addCollection(Integer id, Integer userId);

    void deleteCollection(Integer id, Integer userId);

    List<News> selectHotByReadAndCollect();

    List<News> selectHotNewsList();
}
