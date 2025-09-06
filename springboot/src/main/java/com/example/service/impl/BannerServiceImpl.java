package com.example.service.impl;
import com.example.entity.Banner;
import com.example.mapper.BannerMapper;
import com.example.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
@Service
public class BannerServiceImpl implements BannerService {
    @Resource
    private BannerMapper bannerMapper;

    /**
     * 新增
     */
    public void add(Banner banner) {
        bannerMapper.insert(banner);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        bannerMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            bannerMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Banner banner) {
        bannerMapper.updateById(banner);
    }

    /**
     * 根据ID查询
     */
    public Banner selectById(Integer id) {
        return bannerMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Banner> selectAll(Banner banner) {
        return bannerMapper.selectAll(banner);
    }

    /**
     * 分页查询
     */
    public PageInfo<Banner> selectPage(Banner banner, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Banner> list = bannerMapper.selectAll(banner);
        return PageInfo.of(list);
    }
}
