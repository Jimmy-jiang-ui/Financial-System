package com.example.utils;

import com.example.entity.News;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Component
public class HotScoreCalculator {
    private static final double ALPHA = 1.5;
    public double calculateHotScore(News news, LocalDateTime currentTime) {
        double views = Math.log10(news.getReadCount() + 1);
        long hoursDiff = Duration.between(LocalDateTime.parse(news.getTime()), currentTime).toHours();
        double timeDecay = Math.pow(hoursDiff + 1, ALPHA);
        return views / timeDecay;
    }
    public List<News> getTopHotNews(List<News> newsList, LocalDateTime currentTime, int topN) {
        List<News> hotNewsList = new ArrayList<>();
        for (News news : newsList) {
            long hoursDiff = Duration.between(LocalDateTime.parse(news.getTime()), currentTime).toHours();
            if (hoursDiff <= 24) {
                hotNewsList.add(news);
            }
        }
        hotNewsList.sort(Comparator.comparingDouble(news -> -calculateHotScore(news, currentTime)));
        return hotNewsList.subList(0, Math.min(topN, hotNewsList.size()));
    }
    public List<News> getTopHotNewsFromService(List<News> allNews) {
        LocalDateTime currentTime = LocalDateTime.now();
        return getTopHotNews(allNews, currentTime, 10); // 获取热度最高的10条新闻
    }
}