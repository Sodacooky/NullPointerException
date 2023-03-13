<template>
  <el-container>
    <el-main>
      <!--  搜索框-->
      <div class="search-input-area" style="display: flex;justify-content: center;">
        <el-input v-model="searchText"
                  @keydown.enter="doSearch"
                  placeholder="搜索问题、文章和用户"
                  style="font-size: large;margin-bottom: 16px">
          <template #prepend>
            <el-icon>
              <Search/>
            </el-icon>
          </template>
          <template #append>
            <el-button @click="doSearch">搜索</el-button>
          </template>
        </el-input>
      </div>
      <!--  结果列表-->
      <div class="search-result-area" v-if="nowSearching.length>0">
        <el-menu :default-active="activeSearchTypeRoutePath" :router="true" mode="horizontal">
          <el-menu-item index="/search/question">问题</el-menu-item>
          <el-menu-item index="/search/article">文章</el-menu-item>
          <el-menu-item index="/search/user">用户</el-menu-item>
        </el-menu>
        <div style="margin-top: 8px">
          <router-view></router-view>
        </div>
      </div>
      <!--      如果没有搜索-->
      <el-empty description="未输入搜索内容" v-if="nowSearching.length<=0"/>
    </el-main>
  </el-container>


</template>

<script>
export default {
  name: "SearchIndex",
  data() {
    return {
      activeSearchTypeRoutePath: "/search/question",
      searchText: "", //输入框文本
      nowSearching: "", //当前搜索结果对应的搜索文本
    }
  },
  methods: {
    doSearch() {
      this.nowSearching = this.searchText;
    },
  },
  mounted() {
    //将其他页面过来时携带的查询参数的“指针”复制
    this.searchText = this.$route.query.searchText === undefined ? "" : this.$route.query.searchText;
    console.log("searchText: " + this.searchText);
    //当前页面展示的结果是谁的
    this.nowSearching = this.searchText;
    console.log("nowSearching: " + this.nowSearching);
    //修正原地刷新可能会遇到标签页不正确的问题
    this.activeSearchTypeRoutePath = this.$route.path;
  },

}
</script>

<style scoped>

</style>