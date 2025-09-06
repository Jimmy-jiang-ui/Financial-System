<template>
    <div class="container">
        <div class="login-box">
            <div class="welcome-text">欢迎使用</div>
            <el-form :model="form" :rules="rules" ref="formRef">
                <el-form-item prop="username">
                    <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="login-button" @click="login">登 录</el-button>
                </el-form-item>
                <div style="flex: 1">
                    <span style="color: #d45d5d; cursor: pointer" @click="$router.push('/login')">内部人员登录</span>
                </div>
                <div class="register-link">
                    还没有账号？请 <a href="/register">注册</a>
                </div>
            </el-form>
        </div>
    </div>
</template>
<script>
export default {
    name: "Login",
    data() {
        return {
            dialogVisible: true,
            form: { role: 'USER' },
            rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ]
            }
        }
    },
    methods: {
        login() {
            this.$refs['formRef'].validate((valid) => {
                if (valid) {
                    this.$request.post('/login', this.form).then(res => {
                        if (res.code === '200') {
                            localStorage.setItem("xm-user", JSON.stringify(res.data))
                            location.href = "/front/home"
                            this.$message.success('登录成功')
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            })
        }
    }
}
</script>

<style scoped>
.container {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #f8d7da, #ffffff); /* 使用白红渐变背景 */
    background-size: 200% 200%; /* 设置背景大小以便于动画效果 */
    animation: gradientAnimation 5s ease infinite; /* 添加渐变动画 */
}

@keyframes gradientAnimation {
    0% {
        background-position: 0% 50%;
    }
    50% {
        background-position: 100% 50%;
    }
    100% {
        background-position: 0% 50%;
    }
}

.login-box {
    width: 400px;
    padding: 40px;
    background-color: white;
    border-radius: 8px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    border: 1px solid #e0e0e0;
}

.welcome-text {
    text-align: center;
    font-size: 24px;
    margin-bottom: 30px;
    color: black; /* 深红色字体 */
    font-weight: bold;
}

.el-form-item {
    margin-bottom: 20px;
}

.login-button {
    width: 100%;
    background-color: #c82333; /* 使用淡红色背景 */
    border-color: #c82333; /* 边框颜色与背景一致 */
    color: white;
    font-size: 16px;
    transition: background-color 0.3s;
}

.login-button:hover {
    background-color: #a71c24; /* 悬停时变深 */
}

.register-link {
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
    color: #666;
}

a {
    color: #c82333; /* 链接颜色 */
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}
</style>