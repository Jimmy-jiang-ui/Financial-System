package com.example.service;
import com.example.entity.Video;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface VideoService {
    void add(Video video);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(Video video);
    Video selectById(Integer id);
    List<Video> selectAll(Video video);
    PageInfo<Video> selectPage(Video video, Integer pageNum, Integer pageSize);
}
