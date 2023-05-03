import requests from "@/api/requests";

export class PublishingApi {
  static getArticleCategoriesSuggestion(input) {
    return requests.get("/service-article/public/getCategoriesSuggestion", {
      params: { input },
    });
  }

  static publishArticle(title, category, text) {
    return requests.post("/service-article/auth/publishArticle", {
      title,
      category,
      text,
    });
  }

  static publishArticleReply(articleId, text) {
    return requests.post("/service-article/auth/publishReply", {
      articleId,
      text,
    });
  }

  static getQuestionCategoriesSuggestion(input) {
    return requests.get("/service-question/public/getCategoriesSuggestion", {
      params: { input },
    });
  }

  static publishQuestion(title, category, text) {
    return requests.post("/service-question/auth/publishQuestion", {
      title,
      category,
      text,
    });
  }

  static publishQuestionAnswer(questionId, text) {
    return requests.post("/service-question/auth/publishAnswer", {
      questionId,
      text,
    });
  }
}
