package com.example.service;

import com.example.entity.Feedback;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    void add(Feedback feedback);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(Feedback feedback);
    Feedback selectById(Integer id);
    List<Feedback> selectAll(Feedback feedback);
    PageInfo<Feedback> selectPage(Feedback feedback, Integer pageNum, Integer pageSize);
}
