package com.example.service;
import com.example.entity.Question;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface QuestionService {
    void add(Question question);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(Question question);
    Question selectById(Integer id);
    List<Question> selectAll(Question question);
    PageInfo<Question> selectPage(Question question, Integer pageNum, Integer pageSize);

    PageInfo<Question> selectFrontPage(Question question, Integer pageNum, Integer pageSize);

    List<Question> selectNoAnswer();

    void updateCount(Integer id);
}
