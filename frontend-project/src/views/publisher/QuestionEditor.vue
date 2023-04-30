<template>
  <el-container>
    <el-main>
      <!--问题信息区        -->
      <el-card>
        <template #header>
          <span style="font-weight: bold; font-size: large">问题信息</span>
        </template>
        <el-input
          placeholder="用一句话概括你的问题"
          style="font-size: large"
          v-model="titleInput"
        >
          <template #prepend>问题标题</template>
        </el-input>
        <div
          class="title-tips"
          style="color: gray; font-size: small; margin-top: 8px"
        >
          <div>
            <el-button :icon="Search" @click="jumpSearch()"
              >点击以当前标题为关键词搜索问题
            </el-button>
          </div>
          <p>
            先搜索，再提问,这样能更快地帮你找到答案。即使没找到，在看了相关或类似的问题之后，你的提问会更准确。
          </p>
          <p>太多重复、相似的问题将会降低社区的整体质量~</p>
        </div>
        <el-input
          placeholder="是什么类型的问题呢"
          style="font-size: large; margin-top: 4px"
          v-model="categoryInput"
          @change="doCategorySuggest()"
        >
          <template #prepend>问题分类</template>
        </el-input>
        <div class="category-tips" style="font-size: small; color: gray">
          <p>推荐使用常见分类，便于检索和排行~~</p>
        </div>
        <div>
          推荐分类：
          <span v-if="categoriesSuggestion.length < 1">还没有推荐。。。</span>
          <span v-for="item in categoriesSuggestion" :key="item">
            <el-button style="margin: 4px" @click="categoryInput = item">
              {{ item }}
            </el-button>
          </span>
        </div>
      </el-card>

      <!--问题正文编辑区        -->
      <el-card :body-style="{ padding: 0 }" style="margin-top: 16px">
        <template #header>
          <span style="font-weight: bold; font-size: large">正文</span>
        </template>
        <mavon-editor
          ref="md"
          v-model="editorRawText"
          :toolbars="mavonToolbars"
          placeholder="写写你如何遇到问题、你尝试了什么、贴上相关的代码或者日志"
        />
      </el-card>

      <!--        确认发布区-->
      <el-card style="margin-top: 16px">
        <template #header>
          <span style="font-weight: bold; font-size: large">发布</span>
        </template>
        <!--两个同意          -->
        <div class="agreement-checkbox">
          <div>
            <el-checkbox
              label="我已知悉发布的内容不能违反国家法律与社区规章制度"
              v-model="isAgreeLaw"
              size="large"
            />
          </div>
          <div>
            <el-checkbox
              label="我保证发布的内容不存在非法抄袭等违反著作权行为"
              v-model="isAgreeCopyright"
              size="large"
            />
          </div>
          <div>
            <el-checkbox
              label="我保证发布的问题不存在重复、相似的问题，除非相似问题没有人回答过,并且符合网站内容"
              v-model="isAgreeNew"
              size="large"
            />
          </div>
        </div>
        <!--        发布按钮-->
        <div>
          <el-button
            v-if="isAgreeLaw && isAgreeCopyright && isAgreeNew"
            @click="doPublish()"
          >
            确认发布
          </el-button>
          <el-button v-else disabled>确认发布(需要勾选同意上述两项)</el-button>
        </div>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import { mavonToolbars } from "@/api/mavonSettings";
import { Search } from "@element-plus/icons-vue";
import { PublishingApi } from "@/api/publishing";

export default {
  name: "QuestionEditor",
  computed: {
    Search() {
      return Search;
    },
    mavonToolbars() {
      return mavonToolbars;
    },
  }, //computed
  data() {
    return {
      editorRawText: "",
      titleInput: "",
      categoryInput: "",
      categoriesSuggestion: [],
      isAgreeLaw: false,
      isAgreeCopyright: false,
      isAgreeNew: false,
    };
  }, //data
  methods: {
    //打开一个新标签页进行搜索
    jumpSearch() {
      let routeData = this.$router.resolve({
        path: "/search",
        query: { keyword: this.titleInput },
      });
      window.open(routeData.href, "_blank");
    },
    doCategorySuggest() {
      PublishingApi.getQuestionCategoriesSuggestion(this.categoryInput).then(
        (resp) => {
          if (resp.data.code === "0") {
            this.categoriesSuggestion = resp.data.data;
          } else {
            this.categoriesSuggestion = [];
          }
        }
      );
    },
    doPublish() {
      PublishingApi.publishQuestion(
        this.titleInput,
        this.categoryInput,
        this.editorRawText
      ).then((resp) => {
        //如果没有登录，那已经被拦截了
        if (resp.data.code === "0") {
          //发布成功
          //弹窗
          this.$notify({
            title: "发布成功",
            message: "正在跳转",
            type: "success",
          });
          //跳转
          this.$router.replace(`/question/${resp.data.data}`);
        } else {
          //发布失败
          this.$notify({
            title: "发布失败",
            message: resp.data.message,
            type: "error",
          });
        }
      });
    },
  }, //methods
};
</script>

<style scoped></style>
