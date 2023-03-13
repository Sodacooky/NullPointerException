<template>
  <el-select v-model="selectedResultOrder" style="margin-bottom: 8px" @change="onResultOrderChanged">
    <el-option label="按发布时间顺序" value="publish_time_asc"/>
    <el-option label="按发布时间倒序" value="publish_time_desc"/>
    <el-option label="按点赞数量顺序" value="approval_asc"/>
    <el-option label="按点赞数量倒序" value="approval_desc"/>
  </el-select>
  <div v-for="item in resultData" :key="item.id">
    <ArticlePreviewCard :article_preview_data="item"/>
  </div>
</template>

<script>
import ArticlePreviewCard from "@/components/ArticlePreviewCard.vue";

export default {
  name: "ArticleResultList",
  components: {ArticlePreviewCard},
  data() {
    return {
      resultData: [],
      selectedResultOrder: "approval_desc"
    }
  },
  methods: {
    onResultOrderChanged() {

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

    this.resultData.push(new ArticlePreviewData("1", "c1", "2023.03.10 16:59:00", "0"));
    this.resultData.push(new ArticlePreviewData("2", "c1", "2023.03.10 16:59:01", "1"));

  }
}
</script>

<style scoped>

</style>