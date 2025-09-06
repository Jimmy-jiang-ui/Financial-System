<template>
    <div class="main-content">
        <div class="search" style="display: flex ; margin-left: 750px;width: 60%">
            <el-input size="medium" placeholder="请输入关键字查询" style="width: 350px" v-model="name"></el-input>
            <el-button size="medium" type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button size="medium" type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="video-container">
            <el-row :gutter="10">
                <el-col :span="12" v-for="(item, index) in tableData" :key="item.id">
                    <h3>【金融视频例子】:{{item.name}}</h3>
                    <div style="display: flex; width: 1000px; height: 400px">
                        <video :src="item.file" controls style="width: 600px; height: 400px;"></video>
                        <div style=" margin-top: 30px; margin-left: 50px;background: #f8f8f8; padding: 15px; border-radius: 8px">
                            <div style="font-size: 18px; margin-bottom: 15px;">为您推荐</div>
                            <div style="margin-bottom: 10px">
                                <video controls style="width: 100%; height: 180px; border-radius: 6px" :src="video.file"></video>
                            </div>
                          <div v-for="(item, index) in videoList" :key="item.id" class="video-item"
                               @click="loadVideo(item)"
                               :class="{'video-item-active': video.id === item.id}"
                               v-if="index < 3">
                            <img src="@/assets/imgs/play.gif" alt="" style="width: 15px; margin-right: 10px" v-if="video.id === item.id">
                            <span style="font-size: 13px">{{ item.name }}</span>
                          </div>
                        </div>
                    </div>
                </el-col>
            </el-row>
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
</template>
<script>
export default {
    name: "Video",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 1,  // 每页显示的个数
            total: 0,
            name: null,
            fromVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            rules: {
            },
            video: {},
            videoList: [],
        }
    },
    created() {
        this.load(1)
        this.loadVideo(null)
    },
    methods: {
        getNextVideoName(currentIndex) {
            const nextIndex = currentIndex - 1;
            return this.tableData[nextIndex].name;
        },
        handleAdd() {   // 新增数据
            this.form = {}  // 新增数据的时候清空数据
            this.fromVisible = true   // 打开弹窗
        },
        handleEdit(row) {   // 编辑数据
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
            this.fromVisible = true   // 打开弹窗
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: this.form.id ? '/video/update' : '/video/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.load(1)
                            this.fromVisible = false
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
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
        del(id) {   // 单个删除
            this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/video/delete/' + id).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load(1)
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => {
            })
        },
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/video/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
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
        reset() {
            this.name = null
            this.load(1)
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        handleFileSuccess(response, file, fileList) {
            this.form.file = response.data
        },
    },

}
</script>
<style scoped>

.video-container {
    padding: 20px;
    border: 2px solid #ccc;
    border-radius: 10px;
    transition: box-shadow 0.3s;
    width: 1200px;
    height: 550px;
    margin-left: 100px;
    margin-top:30px;
}

.video-container:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
/* 调整视频条目样式 */
.video-item {
    /*margin-bottom: 8px;*/
    padding: 6px;
    border-radius: 4px;
    transition: all 0.3s;
}
.video-item:hover {
    background-color: #e8f4ff;
}
.video-item-active {
    color: #409EFF;
    border-radius: 10px; /* 添加圆弧形状 */
    background-color: #e8f4ff; /* 保持背景颜色 */

}
</style>