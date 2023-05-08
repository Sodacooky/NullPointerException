import requests from "@/api/requests";

export class ReadingApi {
  static getArticle(articleId) {
    return requests.get("/service-article/public/getArticle", {
      params: { articleId: articleId },
    });
  }

  static getArticleReply(articleId, page) {
    return requests.get("/service-article/public/getReplyOf", {
      params: { articleId: articleId, page: page },
    });
  }

  static getArticleApprovalAmount(articleId) {
    return requests.get("/service-article/public/getApprovalAmountOf", {
      params: { articleId: articleId },
    });
  }

  static doArticleApprove(articleId) {
    return requests.get("/service-article/auth/approve", {
      params: { articleId },
    });
  }

  static doArticleUnApprove(articleId) {
    return requests.get("/service-article/auth/unApprove", {
      params: { articleId },
    });
  }

  static getArticleIsApproved(articleId) {
    return requests.get("/service-article/auth/isApproved", {
      params: { articleId },
    });
  }

  static getQuestionInfo(questionId) {
    return requests.get("/service-question/public/getQuestionInfo", {
      params: { questionId: questionId },
    });
  }

  static getQuestionText(questionId) {
    return requests.get("/service-question/public/getQuestionText", {
      params: { questionId: questionId },
    });
  }

  static getQuestionAnswerAmount(questionId) {
    return requests.get("/service-question/public/getAnswerAmountOf", {
      params: { questionId },
    });
  }

  static getQuestionSubscriptionAmount(questionId) {
    return requests.get("/service-question/public/getSubscriptionAmountOf", {
      params: { questionId: questionId },
    });
  }

  static doQuestionSubscribe(questionId) {
    return requests.get("/service-question/auth/subscription", {
      params: { questionId },
    });
  }

  static doQuestionUnSubscribe(questionId) {
    return requests.get("/service-question/auth/unSubscription", {
      params: { questionId },
    });
  }

  static getQuestionIsSubscribed(questionId) {
    return requests.get("/service-question/auth/isSubscribed", {
      params: { questionId },
    });
  }

  static getQuestionAnswerByTime(questionId, page, isAsc) {
    return requests.get("/service-question/public/getAnswerByTimeOf", {
      params: { questionId: questionId, page: page, isAsc: isAsc },
    });
  }

  static getQuestionAnswerByApproval(questionId, page, isAsc) {
    return requests.get("/service-question/public/getAnswerByApprovalOf", {
      params: { questionId: questionId, page: page, isAsc: isAsc },
    });
  }

  static getOneQuestionAnswer(answerId) {
    return requests.get("/service-question/public/getAnswer", {
      params: { answerId },
    });
  }

  static getOneArticleReply(replyId) {
    return requests.get("/service-article/public/getReply", {
      params: { replyId },
    });
  }

  static doAnswerApprove(answerId) {
    return requests.get("/service-question/auth/approve", {
      params: { answerId },
    });
  }

  static doAnswerUnApprove(answerId) {
    return requests.get("/service-question/auth/unApprove", {
      params: { answerId },
    });
  }

  static getAnswerIsApproved(answerId) {
    return requests.get("/service-question/auth/isApproved", {
      params: { answerId },
    });
  }
}
