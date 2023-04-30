<template>
  <el-container>
    <el-main>
      <div style="display: flex; justify-content: center">
        <el-card style="width: 40%; min-width: 500px">
          <template #header>
            <span style="font-weight: bold">注册新账号</span>
          </template>
          <el-form label-position="right" label-width="72px">
            <!--邮箱              -->
            <el-form-item label="邮箱">
              <el-input
                v-model.trim="email"
                placeholder="邮箱用于登录"
                type="email"
                @change="checkEmail()"
              />
              <div v-if="emailCheckResult.length > 0">
                <span style="font-size: small; color: red">
                  {{ emailCheckResult }}
                </span>
              </div>
            </el-form-item>
            <!--昵称              -->
            <el-form-item label="昵称">
              <el-input
                v-model.trim="nickname"
                placeholder="昵称将展示给其他用户"
                @change="checkNickname()"
              />
              <div v-if="isShowNicknameUsed">
                <span style="font-size: small; color: red"> 昵称已被使用 </span>
              </div>
            </el-form-item>
            <!--密码与确认密码-->
            <el-form-item label="密码">
              <el-input
                v-model.trim="password"
                show-password
                type="password"
                placeholder="登录使用的密码，至少8位"
              ></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input
                v-model.trim="confirmPassword"
                show-password
                type="password"
                placeholder="再次输入上面的密码"
                @change="checkPassword()"
              ></el-input>
              <div v-if="passwordCheckResult.length > 0">
                <span style="color: red; font-size: small">
                  两次密码不一致
                </span>
              </div>
            </el-form-item>
            <!--验证码              -->
            <el-form-item label="验证码">
              <el-input v-model.trim="code" placeholder="输入下方图片的验证码">
              </el-input>
              <el-tooltip content="点击更换" placement="left">
                <el-image
                  :src="codeImageUrl"
                  @click="doCodeImageUpdate()"
                  style="margin-top: 2px"
                />
              </el-tooltip>
            </el-form-item>
            <!--操作区              -->
            <el-form-item style="display: flex; justify-content: space-between">
              <el-button type="primary" @click="doRegister()"> 注册 </el-button>
              <el-button @click="toLogin()">
                已有账号?前往登录
                <el-icon><Position /> </el-icon>
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
import { baseUrl } from "@/api/requests";
import { AuthApi } from "@/api/auth";

export default {
  name: "Register",
  data() {
    return {
      //表单信息
      email: "",
      nickname: "",
      password: "",
      confirmPassword: "",
      code: "",
      codeImageUrl: "",

      //注册提示信息
      passwordCheckResult: "",
      emailCheckResult: "",
      isShowNicknameUsed: false,
    };
  },
  methods: {
    doRegister() {
      if (
        this.passwordCheckResult.length > 0 ||
        this.isShowNicknameUsed ||
        this.emailCheckResult.length > 0
      ) {
        this.$notify({
          titleInput: "请检查提示项",
          type: "warning",
        });
      }
      AuthApi.register(
        this.email,
        this.nickname,
        this.password,
        this.confirmPassword,
        this.code
      ).then((resp) => {
        if (resp.data.code == 0) {
          this.$notify({
            titleInput: "注册成功",
            message: "一封激活邮件将发送到您的邮箱，请点击其中链接进行激活",
            type: "success",
            duration: 100000,
          });
        } else {
          this.$notify({
            titleInput: "注册失败",
            message: resp.data.message,
            type: "error",
          });
          //如果是验证码不正确，那么要更新验证码以配合后端
          if (resp.data.code == 3) this.doCodeImageUpdate();
        }
      }); //doRegister
    },
    checkPassword() {
      if (this.password !== this.confirmPassword) {
        this.passwordCheckResult = "两次密码不一致";
      } else if (this.password.length < 8) {
        this.passwordCheckResult = "密码太短";
      } else {
        this.passwordCheckResult = "";
      }
    },
    checkEmail() {
      AuthApi.registerEmailCheck(this.email).then((resp) => {
        if (resp.data.code != 0) {
          this.emailCheckResult = resp.data.message;
        } else {
          this.emailCheckResult = "";
        }
      });
    },
    checkNickname() {
      AuthApi.registerNicknameCheck(this.nickname).then((resp) => {
        if (resp.data.code != 0) {
          this.isShowNicknameUsed = true;
        } else {
          this.isShowPasswordMismatch = false;
        }
      });
    },
    toLogin() {
      this.$router.replace("/login");
    },
    doCodeImageUpdate() {
      this.codeImageUrl =
        baseUrl + "/service-user/auth/registerCodeImage?magic=" + Date.now();
    },
  }, //methods
  mounted() {
    this.codeImageUrl = baseUrl + "/service-user/auth/registerCodeImage";
  },
};
</script>

<style scoped></style>
