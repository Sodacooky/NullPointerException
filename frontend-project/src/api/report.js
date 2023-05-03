import requests from "@/api/requests";

export class ReportApi {
  /**
   * 条目是否已经举报了
   * @param goalId 目标id
   * @param goalType 目标类型，可以是REPORT_ANSWER, REPORT_ARTICLE, REPORT_ARTICLE_REPLY, REPORT_QUESTION, REPORT_USER
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  static isReported(goalId, goalType) {
    return requests.get("/service-miscellaneous/auth/report/isReported", {
      params: { goalId, goalType },
    });
  }

  static reportQuestion(questionId, comment) {
    return requests.get("/service-miscellaneous/auth/report/question", {
      params: { questionId, comment },
    });
  }

  static reportAnswer(answerId, comment) {
    return requests.get("/service-miscellaneous/auth/report/answer", {
      params: { answerId, comment },
    });
  }

  static reportArticle(articleId, comment) {
    return requests.get("/service-miscellaneous/auth/report/article", {
      params: { articleId, comment },
    });
  }

  static reportArticleReply(articleReplyId, comment) {
    return requests.get("/service-miscellaneous/auth/report/articleReply", {
      params: { articleReplyId, comment },
    });
  }

  static reportUser(userId, comment) {
    return requests.get("/service-miscellaneous/auth/report/user", {
      params: { userId, comment },
    });
  }
}
