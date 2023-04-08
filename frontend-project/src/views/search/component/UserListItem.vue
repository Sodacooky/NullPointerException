<template>
  <el-col :span="12">
    <div
      class="item-card"
      style="padding: 4px 4px 4px 4px; display: flex"
      @click="$router.push(`/profile?userId=${item.id}`)"
    >
      <!--头像        -->
      <div class="avatar">
        <!--.../service-user/avatar/xxx.jpg          -->
        <el-image
          :src="getUserAvatarUrl(item.avatar)"
          fit="fill"
          style="height: 96px; width: 96px"
        />
      </div>
      <!--右侧信息        -->
      <div class="info" style="margin-left: 8px">
        <!--用户名与ID-->
        <div class="base-info">
          <span class="nickname" style="font-weight: bold">
            {{ item.nickname }}
          </span>
          <span class="user-id" style="font-size: small; color: gray">
            ID: {{ item.id }}
          </span>
        </div>
        <!--自我介绍          -->
        <div class="description">
          <span style="font-size: small; color: gray"> 简介：</span>
          {{ item.description }}
        </div>
        <!--注册时间          -->
        <div class="reg-time">
          <span style="font-size: small; color: gray"> 注册时间：</span>
          {{ item.registerTime }}
        </div>
      </div>
    </div>
  </el-col>
</template>

<script>
import { baseUrl } from "@/api/requests";
import { getUserAvatarUrl } from "@/api/user";

export default {
  name: "UserListItem",
  methods: {
    getUserAvatarUrl,
    baseUrl() {
      return baseUrl;
    },
  },
  props: ["item"],
};
</script>

<style scoped>
.item-card:hover {
  background: rgb(248, 248, 248);
}
</style>
