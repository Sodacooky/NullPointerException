<template>
  <div class="article-preview-list">
    <div class="article-preview-card">
      <div class="title">我是一个标题</div>
      <div class="bottom-bar">
        <span style="float: left"><el-tag type="info">分类</el-tag></span>
        <span style="float: right;color: gray">2077.12.31 15:00</span>
      </div>
      <el-divider/>
    </div>

    <div class="article-preview-card">
      <div class="title">你就看我这个标题够不够长吧！《关于灵长类生物从事硅基计算设备运行维护开发工作的合法性研究》</div>
      <div class="bottom-bar">
        <span style="float: left"><el-tag type="info">分类</el-tag></span>
        <span style="float: right;color: gray">2077.12.31 15:00</span>
      </div>
      <el-divider/>
    </div>
  </div>


  <div style="width: 100%;text-align: center;margin-top: 16px">
    <el-button type="primary">加载更多</el-button>
  </div>
</template>

<script>
export default {
  name: "HomeArticleList"
}
</script>

<style scoped>
.article-preview-card {
  padding: 8px 8px 2px 8px;
}

.article-preview-card:hover {
  background-color: rgb(248, 248, 248);
}

.article-preview-card .title {
  font-weight: bold;
}

.article-preview-card .short {
  font-size: small;
  color: gray;
}

.article-preview-card .bottom-bar {
  margin-top: 2px;
  height: 10px;
}
</style>