package com.example.service;
import com.example.entity.Account;
import com.example.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface UserService {
    void add(User user);
    void deleteById(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateById(User user);
    User selectById(Integer id);
    List<User> selectAll(User user);
    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);
    Account login(Account account);
    void register(Account account);
    void updatePassword(Account account);

    Integer selectCollectByUserId(Integer id, Integer userId);
}
