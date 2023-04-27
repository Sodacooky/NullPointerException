<template>
  <el-container>
    <el-main>
      <div style="display: flex; justify-content: center">
        <el-card style="width: 32%">
          <template #header>
            <span style="font-weight: bold">管理员后台登录</span>
          </template>
          <el-form label-position="right" label-width="72px">
            <el-form-item label="密码">
              <el-input
                v-model.trim="passwordInput"
                show-password
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="doLogin()">登录</el-button>
            </el-form-item>
          </el-form>
          <!--登录提示            -->
          <div style="font-size: small">密码储存在数据库中</div>
        </el-card>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { AuthApi } from "@/api/auth";

export default {
  name: "AdminLogin",
  data() {
    return {
      passwordInput: "",
    };
  },
  methods: {
    doLogin() {
      AuthApi.adminLogin(this.passwordInput).then((resp) => {
        if (resp.data.code === 0) {
          //登陆成功
          // - 储存token
          localStorage.setItem("adminToken", resp.data.data);
          // - 跳转回首页
          this.$notify({
            title: "登陆成功",
            type: "success",
          });
          this.$router.replace("/admin/");
        } else {
          //登录失败
          this.$notify({
            title: "登陆失败",
            message: resp.data.message,
            type: "error",
          });
        }
      });
    },
  },
};
</script>

<style scoped></style>
