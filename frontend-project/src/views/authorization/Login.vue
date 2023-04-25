<template>
  <el-container>
    <el-main>
      <!--主题居中        -->
      <div style="display: flex; justify-content: center">
        <!--使用卡片存放          -->
        <el-card style="width: 40%; min-width: 500px">
          <!--顶部文字            -->
          <template #header>
            <span style="font-weight: bold">登录</span>
          </template>
          <!--登录填写内容表单            -->
          <el-form label-position="right" label-width="48px">
            <!--邮箱              -->
            <el-form-item label="邮箱">
              <el-input
                v-model="emailInput"
                placeholder="输入注册时填写的邮箱"
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
              <el-button type="primary" @click="doLogin()">登录</el-button>
              <el-button @click="toRegister()">
                注册新账号<el-icon><Position /> </el-icon>
              </el-button>
            </el-form-item>
          </el-form>
          <!--登录提示文案              -->
          <div style="font-size: small">
            <p style="font-style: italic; color: gray">登录后你可以：</p>
            <p>1. 发布问题和文章</p>
            <p>2. 对问题和文章发表回答回复等</p>
            <p>3. 订阅问题以及点赞认为有价值的内容</p>
            <p style="font-style: italic; color: gray">
              如果账号遗失或申述解封请发邮件到：
            </p>
            <p>xxxxxx@xxx.xx</p>
          </div>
        </el-card>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { AuthApi } from "@/api/auth";
import { ElNotification } from "element-plus";

export default {
  name: "Login",
  data() {
    return {
      emailInput: "",
      passwordInput: "",
    };
  },
  methods: {
    doLogin() {
      //检查输入框
      if (this.emailInput.length < 1 || this.passwordInput.length < 1) {
        ElNotification({
          title: "登陆失败",
          message: "邮箱与密码不能为空",
          type: "error",
        });
      } else {
        //执行登录
        AuthApi.login(this.emailInput, this.passwordInput).then((resp) => {
          if (resp.data.data != null) {
            //登陆成功
            // - 储存token
            localStorage.setItem("token", resp.data.data);
            // - 跳转回首页
            this.$notify({
              title: "登陆成功",
              message: "正在跳转到首页",
              type: "success",
            });
            this.$router.replace("/");
          } else {
            //登录失败
            this.$notify({
              title: "登陆失败",
              message: "请检查邮箱与密码",
              type: "error",
            });
          }
        });
      }
    },
    toRegister() {
      this.$router.replace("/register");
    },
  }, //methods
  mounted() {
    //检查当前是否已经登陆，如果已经登陆，跳回到首页
    if (localStorage.getItem("token") !== null) {
      AuthApi.hasLogin().then((resp) => {
        if (Boolean(resp.data.data) === true) {
          this.$router.replace("/");
        }
      });
    }
  }, //mounted
};
</script>

<style scoped></style>
