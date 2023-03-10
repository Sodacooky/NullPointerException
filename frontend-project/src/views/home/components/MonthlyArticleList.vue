<template>
  <el-empty v-if="articlePreviewData.length<=0" description="暂时没有数据"/>

  <div class="list" v-for="item in articlePreviewData" :key="item.article_id">
    <ArticlePreviewCard :article_preview_data="item"/>
  </div>
</template>

<script>
import ArticlePreviewCard from "@/components/ArticlePreviewCard.vue";

export default {
  name: "MonthlyArticleList",
  components: {ArticlePreviewCard},
  data() {
    return {
      articlePreviewData: [],
    }
  },
  mounted() {
    function ArticlePreviewData(title, category, publish_time, article_id) {
      this.title = title;
      this.category = category;
      this.publish_time = publish_time;
      this.article_id = article_id;
      return this;
    }

    this.articlePreviewData.push(new ArticlePreviewData("1", "c1", "2023.03.10 16:59:00", "0"));
    this.articlePreviewData.push(new ArticlePreviewData("2", "c1", "2023.03.10 16:59:01", "1"));

  }
}
</script>

<style scoped>

</style>