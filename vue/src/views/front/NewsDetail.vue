<template>
  <div style="width: 100%; margin-top: 10px;margin-left: 100px ">
    <div class="card" style="margin-bottom: 10px">
      <div style="font-size: 24px; font-weight: bold; text-align: center; margin-bottom: 10px">{{ news.title }} </div>
      <div style="text-align: center; color: #888; margin-bottom: 20px">
        <span>发布时间：{{ news.time }}</span>
        <span style="margin-left: 20px">浏览量：{{ news.readCount }}</span>
        <button :class="{ clicked: isCollected }" @click="toggleCollect">{{ collectText }}</button>
      </div>
      <div class="w-e-text">
        <div v-html="news.content" class="mytext"></div>
      </div>
    </div>
    <div class="card">
      <Comment :fid="id" module="news" />
    </div>
  </div>
</template>

<script>
import E from 'wangeditor'
import Comment from "@/components/Comment";
export default {
  name: "NewsDetail",
  //引用的时候需要在这里配置才能够生效
  components: {Comment},
  data() {
    return {
      id: this.$route.query.id,
      news: {},
      isCollected: false,
      collectText: '+收藏'
    }
  },
  created() {
    this.$request.put('/news/updateCount/' + this.id).then(() => {
      this.load()
    })
    this.$request.put('/news/updateUserCount/' + this.id).then(() => {
      this.load()
    })
  },
  methods: {
    load() {
      this.$request.get('/news/selectById/' + this.id).then(res => {
        this.news = res.data || {}
      })
    },
    checkCollectStatus() {
      this.$request.get('/user/selectCollectByUserId' + this.id).then(res => {
        const flag = res.data.data; // 假设返回结果结构为 { data: 1/0 }
        if (flag === 1) {
          this.isCollected = true;
          this.collectText = '已收藏';
        } else {
          this.isCollected = false;
          this.collectText = '+收藏';
        }
      }).catch(error => {
        console.error('检查收藏状态失败:', error);
      });
    },
    toggleCollect() {
      if (this.isCollected) {
        // 取消收藏逻辑，调用后端取消收藏接口
        this.$request.put('/news/deleteCollection/'+this.id).then(() => {
          this.isCollected = false;
          this.collectText = '+收藏';
        }).catch(error => {
          console.error('取消收藏失败:', error);
        });
        this.$request.put('/news/deleteCollectCount/'+this.id).then(() => {
        }).catch(error => {
        });
      }
      else {
        // 收藏逻辑，调用后端收藏接口
        this.$request.put('/news/updateCollectCount/'+this.id).then(() => {
          this.isCollected = true;
          this.collectText = '已收藏';
        }).catch(error => {
          console.error('收藏失败:', error);
        });
        this.$request.put('/news/addCollection/'+this.id).then(() => {
        }).catch(error => {
        });
      }
    }
  }
}
</script>

<style scoped>
button {
  margin-left: 20px;
  padding: 2px 3px; /* 增加内边距使按钮变大 */
  font-size: 15px; /* 增大字体大小 */
  border: 2px solid red; /* 红框 */
  background-color: red; /* 红色背景 */
  color: white; /* 白色字 */
  border-radius: 8px; /* 添加圆角 */
  cursor: pointer; /* 鼠标悬停时显示指针 */
  transition: all 0.3s ease; /* 添加过渡效果 */
}

button.clicked {
  background-color: white; /* 点击后白色背景 */
  color: black; /* 点击后黑色字 */
  border: 2px solid black; /* 转换为黑框 */
}

</style>