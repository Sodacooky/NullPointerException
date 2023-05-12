import requests, { baseUrl } from "@/api/requests";

export class AdminMiscApi {
  static removeAdv(advertisementId) {
    return requests.get("/service-miscellaneous/auth/admin/removeAdv", {
      params: {
        advertisementId,
      },
    });
  }

  static addAdv(url, image) {
    return requests.post("/service-miscellaneous/auth/admin/addAdv", {
      url,
      image,
    });
  }

  static getAdvImageUploadUrl() {
    return baseUrl + "/service-miscellaneous/auth/admin/uploadAdvImage";
  }

  static sendCustomNotice(goalUserId, title, text) {
    return requests.post("/service-user/auth/admin/sendCustomNotice", {
      goalUserId,
      title,
      text,
    });
  }
}
