package com.example.service;
import com.example.entity.Banner;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BannerService {
    void add(Banner banner);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(Banner banner);
    Banner selectById(Integer id);
    List<Banner> selectAll(Banner banner);
    PageInfo<Banner> selectPage(Banner banner, Integer pageNum, Integer pageSize);
}
