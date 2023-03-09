<template>
  <div class="question-preview-list" v-for="item in questionList" :key="item.question_id">
    <QuestionPreviewCard
        :title="item.title" :short="item.short" :category="item.category"
        :publish_time="item.publish_time" :question_id="item.question_id"
    />
  </div>
  <div style="width: 100%;text-align: center;margin-top: 16px">
    <el-button type="primary">加载更多</el-button>
  </div>


</template>

<script>
import QuestionPreviewCard from "@/components/QuestionPreviewCard.vue";

export default {
  name: "HomeQuestionList",
  components: {QuestionPreviewCard},
  data() {
    return {
      questionList: []
    }
  },
  methods: {},
  mounted() {
    function QuestionPreview(title, short, category, publish_time, question_id) {
      this.title = title;
      this.short = short;
      this.category = category;
      this.publish_time = publish_time;
      this.question_id = question_id;
      return this;
    }

    this.questionList.push(new QuestionPreview("1", "1", "11", "1", "1"));
    this.questionList.push(new QuestionPreview("2", "1", "11", "1", "2"));
  }
}
</script>

<style scoped>
</style>