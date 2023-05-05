import requests from "@/api/requests";

export class AdminContentManageApi {
  static modifyQuestionInfo(id, title, category, text) {
    return requests.post("/service-question/auth/admin/modifyQuestionInfo", {
      id,
      title,
      category,
      text,
    });
  }

  static modifyQuestionAnswer(id, text) {
    return requests.post("/service-question/auth/admin/modifyAnswer", {
      id,
      text,
    });
  }

  static removeQuestion(questionId) {
    return requests.get("/service-question/auth/admin/removeQuestion", {
      params: { questionId },
    });
  }

  static removeQuestionAnswer(answerId) {
    return requests.get("/service-question/auth/admin/removeAnswer", {
      params: { answerId },
    });
  }

  static modifyArticle(id, title, category, text) {
    return requests.post("/service-article/auth/admin/modifyArticle", {
      id,
      title,
      category,
      text,
    });
  }

  static modifyArticleReply(id, text) {
    return requests.post("/service-article/auth/admin/modifyReply", {
      id,
      text,
    });
  }

  static removeArticle(articleId) {
    return requests.get("/service-article/auth/admin/removeArticle", {
      params: { articleId },
    });
  }

  static removeArticleReply(replyId) {
    return requests.get("/service-article/auth/admin/removeReply", {
      params: { replyId },
    });
  }

  static modifyUser(id, nickname, description, avatar) {
    return requests.post("/service-user/auth/admin/modify", {
      id,
      nickname,
      description,
      avatar,
    });
  }

  static banUser(userId) {
    return requests.get("/service-user/auth/admin/ban", {
      params: { userId },
    });
  }

  static unbanUser(userId) {
    return requests.get("/service-user/auth/admin/unban", {
      params: { userId },
    });
  }
}
