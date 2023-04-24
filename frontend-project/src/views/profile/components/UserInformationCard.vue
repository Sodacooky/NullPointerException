<template>
  <el-card :body-style="{ padding: '16px' }">
    <el-row>
      <!--          头像-->
      <el-col :span="3">
        <el-image fit="fill" :src="UserApi.getUserAvatarUrl(userInfo.avatar)" />
      </el-col>
      <!--          详细信息-->
      <el-col :span="20">
        <div class="user-info" style="padding-left: 16px">
          <!--            大大的昵称和小小的ID-->
          <div class="nickname-id">
            <span style="font-weight: bold; font-size: larger">
              {{ userInfo.nickname }}
            </span>
            <span style="color: gray; font-size: small">
              ID: {{ userInfo.id }}
            </span>
          </div>
          <!--            普通的自我介绍-->
          <div class="description">
            {{ userInfo.description }}
          </div>
          <!--            注册时间-->
          <div class="register-time" style="color: gray">
            <div>注册时间：{{ userInfo.registerTime }}</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import { UserApi } from "@/api/user";

export default {
  name: "UserInformationCard",
  computed: {
    UserApi() {
      return UserApi;
    },
  },
  props: ["userInfo"],
};
</script>

<style scoped></style>
