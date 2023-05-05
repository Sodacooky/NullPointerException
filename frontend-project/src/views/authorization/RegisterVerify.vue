<template>
  <el-main style="display: flex; justify-content: center">
    <div style="width: 50%; min-width: 500px">
      <el-card>
        <div v-if="magic === null" style="align-items: center; display: flex">
          <Close style="width: 32px; height: 32px; color: red" />
          <span> 你不是从邮箱中点的链接吧？ </span>
        </div>
        <div
          v-else-if="isSuccess === false"
          style="align-items: center; display: flex"
        >
          <Close style="width: 32px; height: 32px; color: red" />
          <span> 验证失败，但你可以在15天后用同样的数据重新注册~ </span>
        </div>
        <div
          v-else-if="isSuccess === true"
          style="align-items: center; display: flex"
        >
          <Check style="width: 32px; height: 32px; color: green" />
          <span> 验证完成了，你可以登陆了~ </span>
        </div>
        <div v-else>
          <el-icon>
            <Loading />
          </el-icon>
        </div>
      </el-card>
    </div>
  </el-main>
</template>

<script>
import { AuthApi } from "@/api/auth";

export default {
  name: "RegisterVerify",
  data() {
    return {
      magic: null,
      isSuccess: null,
    };
  }, //data
  mounted() {
    //判断是否有magic
    if (this.$route.query.magic) {
      this.magic = this.$route.query.magic;
    }
    //有magic，进行请求
    if (this.magic !== null) {
      AuthApi.registerVerify(this.magic).then((resp) => {
        this.isSuccess = resp.data.code === "0";
      });
    }
  }, //mounted
};
</script>

<style scoped></style>