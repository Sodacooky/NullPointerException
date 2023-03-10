<template>
  <el-empty v-if="questionPreviewData.length<=0" description="暂时没有数据"/>

  <div class="list" v-for="item in questionPreviewData" :key="item.article_id">
    <QuestionPreviewCard :question_preview_data="item"/>
  </div>
</template>

<script>
import QuestionPreviewCard from "@/components/QuestionPreviewCard.vue";

export default {
  name: "WeeklyQuestionList",
  components: {QuestionPreviewCard},
  data() {
    return {
      questionPreviewData: [],
    }
  },
  mounted() {
    function QuestionPreviewData(title, short, category, publish_time, article_id) {
      this.title = title;
      this.short = short;
      this.category = category;
      this.publish_time = publish_time;
      this.article_id = article_id;
      return this;
    }

    this.questionPreviewData.push(new QuestionPreviewData("1", "short", "c1", "2023.03.10 16:59:00", "0"));
    this.questionPreviewData.push(new QuestionPreviewData("2", "ssss", "c1", "2023.03.10 16:59:01", "1"));

  }
}
</script>

<style scoped>

</style>