<template>
    <div class="main-content">
        <div class="flex-container" style="display: flex; grid-gap: 20px;">
            <!-- 左侧热点部分开始-->
            <div style="flex: 0 0 25%;max-width: 30%">
                <div style="margin: 20px 0; background: #f8f8f8; padding: 15px; border-radius: 8px">
                    <div style="display: flex;  margin-bottom: 15px">
                        <div style="accent-color: black">新闻推荐</div>
                        <div style="flex: 1; text-align: right;">
                          <div style="flex: 1; text-align: right; ">
                            <span @click="loadRendom" style="font-size: 14px; cursor: pointer"><i class="el-icon-refresh"></i> 换一换</span>
                          </div>
                        </div>
                    </div>
                    <div>
                        <div @click="$router.push('/front/newsDetail?id=' + item.id)" v-for="(item, index) in HotRecommend" :key="item.id"
                             style="font-size: 14px; margin-bottom: 8px; cursor: pointer; color: #666" class="line1">
                            <strong style="color: red; margin-right: 8px" v-if="index < 3">{{ index + 1 }}</strong>
                            <strong style="color: #333; margin-right: 8px" v-else>{{ index + 1 }}</strong>
                            <span>{{ item.title }}</span>
                        </div>
                    </div>
                </div>
               <StockDisplay />
            </div>
            <!-- 左侧热点部分结束-->
            <!-- 中间内容部分开始-->
            <div style="flex: 0 0 50%;max-width: 50%;margin-top: 20px">
                <el-carousel height="300px">
                    <el-carousel-item v-for="item in bannerList" :key="item">
                        <a :href="'/front/newsDetail?id=' + item.newsId" target="_blank"><img :src="item.img" alt="" style="width: 100%"></a>
                    </el-carousel-item>
                </el-carousel>
              <hr class="custom-divider" style="margin-top: 26px" ></hr>
                <div>
                    <div style="margin: 20px 0; display: flex; flex-wrap: wrap; gap: 20px ">
                        <!-- 分类按钮样式调整 -->
                        <div @click="loadCategoryNews(null)" class="category-item" :class="{ 'category-item-active' : category === null }">全部</div>
                        <div @click="loadCategoryNews(item.name)" class="category-item" :class="{ 'category-item-active' : category === item.name }" v-for="item in categoryList"
                             :key="item.id">{{ item.name }}</div>
                    </div>
                    <div>
                        <div @click="$router.push('/front/newsDetail?id=' + item.id)" class="card" v-for="item in tableData" :key="item.id"
                             style="display: flex; cursor: pointer; gap: 12px; margin-bottom: 10px; padding: 12px; background: #f8f8f8; border-radius: 8px;overflow: hidden;text-overflow: ellipsis;">
                            <img :src="item.img" alt="" style="width: 120px; height: 80px; border-radius: 5px; display: block">
                            <div style="flex: 1">
                                <div style="font-size: 16px; margin-bottom: 8px; font-weight: 500" class="line1">{{ item.title }}</div>
                                <div style="color: #666; height: 36px; margin-bottom: 5px; font-size: 13px" class="line2">{{item.descr }}</div>
                                <div style="color: #666; font-size: 12px">
                                    <span style="margin-right: 15px"><i class="el-icon-time"></i> {{ item.time }}</span>
                                    <span><i class="el-icon-s-comment"></i> {{item.commentCount}}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="margin: 10px 0" v-if="total > pageSize">
                        <el-pagination
                                background
                                @current-change="handleCurrentChange"
                                :current-page="pageNum"
                                :page-sizes="[5, 10, 20]"
                                :page-size="pageSize"
                                layout="total, prev, pager, next"
                                :total="total">
                        </el-pagination>
                    </div>
                </div>
            </div>
            <!-- 中间内容部分结束-->
            <!-- 右侧部分开始-->
            <div style="flex: 0 0 25%;max-width: 30%;margin-top: 20px">
                <!-- 右侧热点榜样式调整 -->
                <div style="margin-bottom: 25px; background: #f8f8f8; padding: 15px; border-radius: 8px">
                    <div style="display: flex; align-items: center; margin-bottom: 15px">
                        <img src="@/assets/imgs/hot.png" alt="" style="width: 50px">
                    </div>
                    <div>
                        <div  @click="$router.push('/front/newsDetail?id=' + item.id)" v-for="(item, index) in hotNewsList" :key="item.id"
                              style="font-size: 14px; margin-bottom: 8px; cursor: pointer; color: #666" class="line1">
                            <strong style="color: red; margin-right: 8px" v-if="index < 3">{{ index + 1 }}</strong>
                            <strong style="color: #333; margin-right: 8px" v-else>{{ index + 1 }}</strong>
                            <span>{{ item.title }}</span>
                        </div>
                    </div>
                </div>
               <hr class="custom-divider" />
                <!-- 视频模块样式调整 -->
                <div style="margin: 20px 0; background: #f8f8f8; padding: 15px; border-radius: 8px">
                    <div style="font-size: 18px; margin-bottom: 15px; font-weight: 500">为您推荐</div>
                    <div style="margin-bottom: 10px;margin-top: 10px">
                        <video controls style="width: 300px; height: 180px; border-radius: 10px" :src="video.file"></video>
                    </div>
                    <div @click="loadVideo(item)" v-for="item in videoList" :key="item.id" class="video-item"
                         :class="{'video-item-active': video.id === item.id}" >
                        <img src="@/assets/imgs/play.gif" alt="" style="width: 15px; margin-right: 10px" v-if="video.id === item.id">
                        <span style="font-size: 13px">{{ item.name }}</span>
                    </div>
                </div>
            </div>
            <!-- 右侧部分结束-->
        </div>
    </div>
</template>
<script>
import StockDisplay from './StockDisplay.vue';
export default {
    components: {
      StockDisplay
    },
    data() {
        return {
            bannerList: [],
            categoryList: [],
            category: null,
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            total: 0,
            hotNewsList: [],
            calculationList: [],
            video: {},
            videoList: [],
            HotRecommend: [],
        }
    },
    mounted() {
        this.loadBanner()
        this.loadCategory()
        this.load(1)
        this.loadHot()
        this.loadVideo(null)
        this.loadCaculation()
        this.loadHotRecommend()
    },
    // methods：本页面所有的点击事件或者其他函数定义区
    methods: {
        loadVideo(item) {
            this.$request.get('/video/selectPage', {
                params: {
                    pageNum: 1
                }
            }).then(res => {
                this.videoList = res.data?.list || []
                if (item) {
                    this.video = item
                } else {
                    this.video = this.videoList.length ? this.videoList[0] : {}
                }
            })
        },
        loadHot() {
            this.$request.get('/news/selectHot').then(res => [
                this.hotNewsList = res.data || []
            ])
        },
        loadRendom(){
          this.$request.get('/news/selectHotRecommend').then(res => [
            this.HotRecommend = res.data || []
          ])
        },
        //加载计算的算法
        loadCaculation() {
            this.$request.get('/news/selectCaculate').then(res=>[
                this.calculationList = res.data || []
            ])
        },
        loadBanner() {
            this.$request.get('/banner/selectAll').then(res => [
                this.bannerList = res.data || []
            ])
        },
        loadCategory() {
            this.$request.get('/category/selectAll').then(res => [
                this.categoryList = res.data || []
            ])
        },
        loadCategoryNews(name) {
            this.category = name
            this.load(1)
        },
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/news/selectPageForAll', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    category: this.category,
                }
            }).then(res => {
                if (res.code === '200') {
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        loadHotRecommend(){
          this.$request.get('/news/selectHotNewsList').then(res => [
            this.HotRecommend = res.data || []
          ])
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
    }
}
</script>

<style scoped>
.category-item {
    border: 1px solid gray;
    color: black;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 13px;
    transition: all 0.3s;
}
.category-item-active {
    background-color: darkred;
    color: white;
}
.line1:hover {
    color: red !important;
}
/* 调整视频条目样式 */
.video-item {
    margin-bottom: 8px;
    padding: 6px;
    border-radius: 4px;
    transition: all 0.3s;
}
.video-item:hover {
    background-color: #e8f4ff;
}
.video-item-active {
    color: #409EFF;
}
.custom-divider {
  width: 100%;          /* 占满父容器宽度 */
  height: 2px;          /* 线的高度 */
  background-color: #000; /* 黑色 */
  border: none;         /* 清除默认边框 */
}
.main-content{
  margin-top: 0px;
  margin-left: 20px;
}
</style>