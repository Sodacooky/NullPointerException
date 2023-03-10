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
        <el-tabs v-model="activeSearchTypeName" class="demo-tabs" @tab-click="searchTypeChanged">
          <el-tab-pane label="问题" name="question">
            <router-view></router-view>
          </el-tab-pane>
          <el-tab-pane label="文章" name="article">
            <router-view></router-view>
          </el-tab-pane>
          <el-tab-pane label="用户" name="user">
            <router-view></router-view>
          </el-tab-pane>
        </el-tabs>
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
      activeSearchTypeName: "question",
      searchText: "", //输入框文本
      nowSearching: "", //当前搜索结果对应的搜索文本
    }
  },
  methods: {
    doSearch() {
      this.nowSearching = this.searchText;
      console.log(this.$route.path)
    },
    searchTypeChanged(dstTab) {
      if (String(dstTab.paneName).endsWith("question")) {
        this.$router.push({path: "/search/question", query: {searchText: this.nowSearching}})
      } else if (String(dstTab.paneName).endsWith("article")) {
        this.$router.push({path: "/search/article", query: {searchText: this.nowSearching}})
      } else if (String(dstTab.paneName).endsWith("user")) {
        this.$router.push({path: "/search/user", query: {searchText: this.nowSearching}})
      }
    }
  },
  created() {
    this.searchText = this.$route.query.searchText;
    this.nowSearching = this.searchText;

    if (this.searchText.length <= 0) console.log("Empty Search");
    else console.log("Searching: " + this.searchText);
  }

}
</script>

<style scoped>

</style>