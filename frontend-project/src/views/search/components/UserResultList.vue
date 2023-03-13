<template>
  <el-select v-model="selectedResultOrder" style="margin-bottom: 8px" @change="onResultOrderChanged">
    <el-option label="按注册时间顺序" value="register_time_asc"/>
    <el-option label="按注册时间倒序" value="register_time_desc"/>
  </el-select>
  <div v-for="item in resultData" :key="item.id">
    <UserPreviewCard :user_preview_data="item"/>
  </div>
</template>

<script>
import UserPreviewCard from "@/components/UserPreviewCard.vue";

export default {
  name: "UserResultList",
  components: {UserPreviewCard},
  data() {
    return {
      resultData: [],
      selectedResultOrder: "register_time_desc",
    }
  },
  mounted() {
    this.doSearch();
  },
  methods: {
    doSearch() {
      const searchText = this.$route.query.searchText;
      console.log("user result list got search text: " + searchText);

      //do api
      //simulation vvv
      function UserPreviewData(id, nickname, description, register_time, avatar_url) {
        this.id = id;
        this.nickname = nickname;
        this.description = description;
        this.avatar_url = avatar_url;
        this.register_time = register_time;
        return this;
      }

      this.resultData.push(new UserPreviewData(1, "小明", "简介文本", "2022.11.4 00:00:00", "https://s3.bmp.ovh/imgs/2022/12/14/18f7342584baa5a4.png"));
      this.resultData.push(new UserPreviewData(2, "小张", "简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本简介文本介文本简介文本介文本简介文本介文本简介文本介文本简介文本介文本简介文本", "2022.11.4 00:00:10", "https://s3.bmp.ovh/imgs/2022/12/14/18f7342584baa5a4.png"));
    },
    onResultOrderChanged(val) {
      console.log("result order changed to: " + this.selectedResultOrder + ", " + val);
    }

  }

}
</script>

<style scoped>


</style>