<template>
  <el-container>
    <el-main>
      <!--主题居中        -->
      <div style="display: flex; justify-content: center">
        <!--使用卡片存放          -->
        <el-card style="width: 32%">
          <!--顶部文字            -->
          <template #header>
            <span style="font-weight: bold">登录</span>
          </template>
          <!--登录填写内容表单            -->
          <el-form label-position="right" label-width="72px">
            <!--邮箱              -->
            <el-form-item label="邮箱">
              <el-input
                v-model="emailInput"
                placeholder="输入注册时的邮箱"
                type="email"
              ></el-input>
            </el-form-item>
            <!--密码              -->
            <el-form-item label="密码">
              <el-input
                v-model="passwordInput"
                show-password
                type="password"
              ></el-input>
            </el-form-item>
            <!--登录按钮以及跳转注册按钮              -->
            <el-form-item>
              <el-button type="primary" @click="login">登录</el-button>
              <el-button @click="toRegister">注册新账号</el-button>
            </el-form-item>
          </el-form>
          <!--登录提示文案              -->
          <div style="font-size: small">
            <p style="font-style: italic; color: gray">登录后你可以：</p>
            <p>1. 发布问题和文章</p>
            <p>2. 对问题和文章发表回答回复等</p>
            <p>3. 订阅问题以及点赞认为有价值的内容</p>
          </div>
        </el-card>
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "LoginView",
  data() {
    return {
      emailInput: "",
      passwordInput: "",
    };
  },
  methods: {
    login() {
      console.log(this.emailInput + this.passwordInput);
    },
    toRegister() {
      this.$router.replace("/register");
    },
  }, //methods
  mounted() {
    //检查当前是否已经登陆，如果已经登陆，跳回到首页
    if (localStorage.getItem("token") !== null) {
    }
  }, //mounted
};
</script>

<style scoped></style>
