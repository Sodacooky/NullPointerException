import requests from "@/api/requests";

export class PublishingApi {
  static getArticleCategoriesSuggestion(input) {
    return requests.get("/service-article/getCategoriesSuggestion", {
      params: { input },
    });
  }

  static publishArticle(title, category, text) {
    return requests.post("/service-article/operation/publishArticle", {
      title,
      category,
      text,
    });
  }

  static publishArticleReply(articleId, text) {
    return requests.post("/service-article/operation/publishReply", {
      articleId,
      text,
    });
  }

  static getQuestionCategoriesSuggestion(input) {
    return requests.get("/service-question/getCategoriesSuggestion", {
      params: { input },
    });
  }

  static publishQuestion(title, category, text) {
    return requests.post("/service-question/operation/publishQuestion", {
      title,
      category,
      text,
    });
  }
}
