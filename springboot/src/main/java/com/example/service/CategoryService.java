package com.example.service;

import com.example.entity.Category;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void add(Category category);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(Category category);
    Category selectByName(String name);
    Category selectById(Integer id);
    List<Category> selectAll(Category category);
    PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize);
}
