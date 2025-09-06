package com.example.service;

import com.example.entity.Comment;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CommentService {
    void add(Comment comment);
    void deleteDeep(Integer id);
    void deepDelete(Integer pid);
    void deleteBatch(List<Integer> ids);
    void updateById(Comment comment);
    void deleteById(Integer id);
    Comment selectById(Integer id);
    List<Comment> selectAll(Comment comment);
    PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize);
    PageInfo<Comment> selectTree(Integer fid, String module, Integer pageNum, Integer pageSize);
    Integer selectCount(Integer fid, String module);

}
