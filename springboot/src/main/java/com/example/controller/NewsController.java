package com.example.controller;
import com.example.common.Result;
import com.example.entity.News;
import com.example.service.NewsService;
import com.example.utils.HotScoreCalculator;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 新闻信息前端操作接口
 **/
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;
    HotScoreCalculator scoreCalculator = new HotScoreCalculator();
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody News news) {
        newsService.add(news);
        return Result.success();
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        newsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        newsService.deleteBatch(ids);
        return Result.success();
    }
    @GetMapping("/selectHot")
    public Result selectHot() {
        List<News> list = newsService.selectHotByReadAndCollect();
        return Result.success(list);
    }
    @GetMapping("/selectHotRecommend")
    public Result selectHotRecommend() {
        List<News> list = newsService.selectHot();
        return Result.success(list);
    }
    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody News news) {
        newsService.updateById(news);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        News news = newsService.selectById(id);
        return Result.success(news);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(News news) {
        List<News> list = newsService.selectAll(news);
        return Result.success(list);
    }
    @GetMapping("selectCaculate")
    public Result selectCaculate(){
        List<News> list = newsService.selectCaculate();
        return Result.success(list);
    }
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(News news,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<News> page = newsService.selectPage(news, pageNum, pageSize);
        return Result.success(page);
    }
    /**
     * 分页查询所有
     */
    @GetMapping("/selectPageForAll")
    public Result selectPageForAll(News news,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<News> page = newsService.selectPageForAll(news, pageNum, pageSize);
        return Result.success(page);
    }
    @PutMapping("/updateCount/{id}")
    public Result updateCount(@PathVariable Integer id) {
        newsService.updateCount(id);
        return Result.success();
    }

    @PutMapping("/updateUserCount/{id}")
    public Result updateUserCount(@PathVariable Integer id) {
        newsService.updateUserCount(id);
        return Result.success();
    }
    /**
     * 以下两个为用户和新闻的收藏关系,用于新闻的收藏和取消收藏
     * */
    @PutMapping("/updateCollectCount/{id}")
    public Result updateCollectCount(@PathVariable Integer id){
        newsService.updateCollectCount(id);
        return Result.success();
    }

    @PutMapping("/deleteCollectCount/{id}")
    public Result deleteCollectCount(@PathVariable Integer id){
        newsService.deleteCollectCount(id);
        return Result.success();
    }

    /**
     * 以下方法是用户收藏之后,新闻会被用户收藏
     * */
    @PutMapping("/addCollection/{id}")
    public void addCollection(@PathVariable Integer id){
        //获取到userId
        Integer userId = TokenUtils.getCurrentUser().getId();
        newsService.addCollection(id,userId);
    }

    @PutMapping("/deleteCollection/{id}")
    public void deleteCollection(@PathVariable Integer id){
        //获取到userId
        Integer userId = TokenUtils.getCurrentUser().getId();
        newsService.deleteCollection(id,userId);
    }
    @GetMapping("/selectHotNewsList")
    public Result selectHotNewsList(){
        List<News> list = newsService.selectHotNewsList();
        return Result.success(list);
    }
}