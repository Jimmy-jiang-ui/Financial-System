<template>
    <div class="main-container">
        <div>
            <div class="weather-container">
                <iframe scrolling="no" src="https://widget.tianqiapi.com/?style=tg&skin=pitaya" frameborder="0" width="470" height="40" allowtransparency="true"></iframe>
            </div>
            <!--头部-->
            <div class="front-header">
                <div class="front-header-left">
                    <img src="@/assets/imgs/logo1.png" alt="">
                    <div class="title">金融新闻网</div>
                </div>
                <div class="front-header-center">
                    <div style="display: flex; margin-left: 10px; ">
                        <div class="menu" :class="{ 'menu-active' : $route.path.includes(item.path) }"
                             v-for="item in menus" :key="item.path" @click="$router.push(item.path)">{{ item.text }}</div>
                    </div>
                </div>
                <div class="search-container" style="display: flex; align-items: center; margin-left: 0px;">
                    <el-input prefix-icon="el-icon-search" size="medium" placeholder="请输入新闻关键字搜索" style="width: 300px; margin-right: 10px"></el-input>
                    <el-button size="medium">搜索</el-button>
                </div>
                <div class="front-header-right" style="margin-left: 0px;">
                    <div v-if="!user.username">
                        <el-button @click="$router.push('/login')">登录</el-button>
                        <el-button @click="$router.push('/register')">注册</el-button>
                    </div>
                    <div v-else>
                        <el-dropdown>
                            <div class="front-header-dropdown">
                                <img :src="user.avatar" alt="">
                                <div style="margin-left: 10px">
                                    <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                                </div>
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item>
                                    <div @click="$router.push('/front/person')">个人信息</div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="$router.push('/front/userQuestion')">我的问题</div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="$router.push('/front/userNews')">我的新闻</div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="$router.push('/front/userAnswer')">我的回答</div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="logout">退出登录</div>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </div>
            </div>
            <!--主体-->
            <div class="main-body">
                <router-view ref="child" @update:user="updateUser" />
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "FrontLayout",
    data () {
        return {
            top: '',
            notice: [],
            user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
            menus: [
                { text: '新闻首页', path: '/front/home' },
                { text: '热门问答', path: '/front/question' },
                { text: '新闻视频', path: '/front/video' },
                { text: '意见反馈', path: '/front/feedback' },
                { text: '系统公告', path: '/front/notice' },
            ],
        }
    },
    mounted() {},
    methods: {
        updateUser() {
            this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
        },
        // 退出登录
        logout() {
            localStorage.removeItem("xm-user");
            this.$router.push("/login");
        },
    }
}
</script>

<style scoped>
/* 基础布局 */
.main-container {
    max-width: 1200px;
    margin-left: 150px ;
    padding: 0 20px;
}

/* 天气组件 */
.weather-container {
    margin-bottom: 15px;
}

/* 头部布局 */
.front-header {
    display: flex;
    align-items: center;
    height: 100px;
    padding: 0 15px;
    margin-top: 20px;
    gap: 10px; /* 元素间间隔 */
}

/* 左侧Logo区域 */
.front-header-left {
    display: flex;
    align-items: center;
    min-width: 270px; /* 增加左侧宽度 */
}
.front-header-left img {
    width: 100px;
    height: auto;
    margin-right: 10px;
}
.title {
    font-size: 25px; /* 适当缩小标题 */
    white-space: nowrap;
    font-weight: bold; /* 加粗标题 */
    color: darkred;
}

/* 中间导航菜单 */
.front-header-center {
    flex: 1;
    margin-left: 100px;
    font-size: 25px;
    min-width: 450px; /* 确保最小宽度 */
    transform: scale(1.10);
}
/*.front-header-center > div {*/
/*    display: flex;*/
/*    gap: 6px; !* 减小间距 *!*/
/*    transform: scale(0.96);*/
/*    min-width: 600px;*/
/*    transform-origin: left center;*/
/*}*/
.menu {
    padding: 0 8px;
    font-size: 13px;
    line-height: 36px;
    white-space: nowrap;
    //flex-shrink: 0; /* 禁止收缩 */
    border-radius: 4px;
    transition: all 0.2s;
    background-color: white; /* 白底 */
    color: black; /* 黑字 */
    font-weight: bold; /* 加粗字体 */
    margin-left: 10px;
}

/* 当前页面菜单项变成红色 */
.menu-active {
    color: white; /* 字体颜色为白色 */
    background: darkred; /* 背景颜色为红色 */
}
/* 右侧功能区域 */
.front-header-right {
    min-width: 150px; /* 缩小右侧宽度 */
    display: flex;
    justify-content: flex-end;
    gap: 8px;
}

/* 搜索框优化 */
.el-input {
    width: 250px !important;
    margin-right: 5px;
}
.el-button {
    padding: 7px 12px;
    font-size: 15px;
}

/* 用户信息 */
.front-header-dropdown {
    display: flex;
    align-items: center;
}
.front-header-dropdown img {
    width: 32px;
    height: 32px;
    border-radius: 50%;
}

/* 主体内容 */
.main-body {
    padding: 20px 0;
    max-width: 1200px;
    margin: 0 auto;
}

/* 菜单交互效果 */
.menu:hover:not(.menu-active) {
    color: #fff;
    background: red;
}

/* 响应式适配 */
@media (max-width: 1240px) {
    .front-header {

        gap: 15px;
        padding: 0 10px;
    }
    .front-header-center > div {
        gap: 4px;
        transform: scale(0.94);
    }
    .menu {
        font-size: 12.5px;
        padding: 0 6px;
    }
    .front-header-left {
        min-width: 160px;
    }
    .front-header-left img {
        width: 90px;
    }
    .title {
        font-size: 20px;
        color: red;
    }
}
</style>