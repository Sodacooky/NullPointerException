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
}
