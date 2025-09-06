package com.example.service;
import com.example.entity.Answer;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface AnswerService {
    void add(Answer answer);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(Answer answer);
    List<Answer> selectAll(Answer answer);
    Answer selectById(Integer id);
    PageInfo<Answer> selectPage(Answer answer, Integer pageNum, Integer pageSize);

    PageInfo<Answer> selectFrontPage(Answer answer, Integer pageNum, Integer pageSize);
}
