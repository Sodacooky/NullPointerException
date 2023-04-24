import requests from "@/api/requests";

export class ReadingApi {
  static getArticle(articleId) {
    return requests.get("/service-article/getArticle", {
      params: { articleId: articleId },
    });
  }

  static getArticleReply(articleId, page) {
    return requests.get("/service-article/getReplyOf", {
      params: { articleId: articleId, page: page },
    });
  }

  static getArticleApprovalAmount(articleId) {
    return requests.get("/service-article/getApprovalAmountOf", {
      params: { articleId: articleId },
    });
  }

  static getQuestionInfo(questionId) {
    return requests.get("/service-question/getQuestionInfo", {
      params: { questionId: questionId },
    });
  }

  static getQuestionText(questionId) {
    return requests.get("/service-question/getQuestionText", {
      params: { questionId: questionId },
    });
  }

  static getQuestionAnswerAmount(questionId) {
    return requests.get("/service-question/getAnswerAmountOf", {
      params: { questionId: questionId },
    });
  }

  static getQuestionSubscriptionAmount(questionId) {
    return requests.get("/service-question/getSubscriptionAmountOf", {
      params: { questionId: questionId },
    });
  }

  static getQuestionAnswerByTime(questionId, page, isAsc) {
    return requests.get("/service-question/getAnswerByTimeOf", {
      params: { questionId: questionId, page: page, isAsc: isAsc },
    });
  }

  static getQuestionAnswerByApproval(questionId, page, isAsc) {
    return requests.get("/service-question/getAnswerByApprovalOf", {
      params: { questionId: questionId, page: page, isAsc: isAsc },
    });
  }
}
