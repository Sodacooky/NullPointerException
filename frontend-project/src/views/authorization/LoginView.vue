<template>
  <el-container>
    <el-main>
      <div style="display: flex;justify-content: center">
        <el-card style="width: 32%">
          <template #header>
            <span style="font-weight: bold">登录</span>
          </template>
          <el-form label-position="right" label-width="72px">
            <el-form-item label="邮箱">
              <el-input type="email" placeholder="输入注册时的邮箱" v-model="emailInput"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input type="password" show-password v-model="passwordInput"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="login">登录</el-button>
              <el-button @click="toRegister">注册新账号</el-button>
            </el-form-item>
          </el-form>
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
    }
  },
  methods: {
    login() {
      console.log(this.emailInput + this.passwordInput);
    },
    toRegister() {
      this.$router.replace("/register");
    }
  }
}
</script>

<style scoped>

</style>