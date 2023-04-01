<template>
    <el-container>
        <el-main>
            <div style="display: flex;justify-content: center">
                <el-card style="width: 32%">
                    <template #header>
                        <span style="font-weight: bold">管理员后台登录</span>
                    </template>
                    <el-form label-position="right" label-width="72px">
                        <el-form-item label="密码">
                            <el-input v-model="passwordInput" show-password type="password"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="login">登录</el-button>
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
            passwordInput: "",
        }
    },
    methods: {
        login() {
            console.log(this.passwordInput);
        }
    }
}
</script>

<style scoped>

</style>