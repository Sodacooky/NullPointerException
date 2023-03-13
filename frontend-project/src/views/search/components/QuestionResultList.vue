<template>
  <el-select v-model="selectedResultOrder" style="margin-bottom: 8px" @change="onResultOrderChanged">
    <el-option label="按发布时间顺序" value="publish_time_asc"/>
    <el-option label="按发布时间倒序" value="publish_time_desc"/>
    <el-option label="按回答数量顺序" value="answer_amount_asc"/>
    <el-option label="按回答数量倒序" value="answer_amount_desc"/>
  </el-select>
  <div v-for="item in resultData" :key="item.id">
    <QuestionPreviewCard :question_preview_data="item"/>
  </div>
</template>

<script>
import QuestionPreviewCard from "@/components/QuestionPreviewCard.vue";

export default {
  name: "QuestionResultList",
  components: {QuestionPreviewCard},
  data() {
    return {
      resultData: [],
      selectedResultOrder: "answer_amount_desc",
    }
  },
  methods: {},
  mounted() {
    function QuestionPreviewData(title, short, category, publish_time, article_id) {
      this.title = title;
      this.short = short;
      this.category = category;
      this.publish_time = publish_time;
      this.article_id = article_id;
      return this;
    }

    this.resultData.push(new QuestionPreviewData("1", "short", "c1", "2023.03.10 16:59:00", "0"));
    this.resultData.push(new QuestionPreviewData("2", "ssss", "c1", "2023.03.10 16:59:01", "1"));

  }
}
</script>

<style scoped>

</style>