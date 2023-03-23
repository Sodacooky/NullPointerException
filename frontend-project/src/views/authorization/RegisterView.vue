<template>
  <el-container>
    <el-main>
      <div style="display: flex;justify-content: center">
        <el-card style="width: 32%">
          <template #header>
            <span style="font-weight: bold">注册新账号</span>
          </template>
          <el-form label-position="right" label-width="72px">
            <el-form-item label="邮箱">
              <el-input type="email" placeholder="邮箱用于登录和重置账号" v-model="emailInput"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input type="password" show-password v-model="passwordInput"></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input type="password" show-password v-model="confirmPasswordInput"></el-input>
            </el-form-item>
            <el-form-item style="display: flex;justify-content: space-between">
              <el-button style="flex:1" @click="doRegister">注册</el-button>
              <el-button type="primary" style="flex:1" @click="toLogin">已有账号，登录</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "RegisterView",
  data() {
    return {
      emailInput: "",
      passwordInput: "",
      confirmPasswordInput: "",
    }
  },
  methods: {
    doRegister() {
      console.log(this.emailInput + this.passwordInput + this.confirmPasswordInput);
    },
    toLogin() {
      this.$router.replace("/login");
    }
  }
}
</script>

<style scoped>

</style>