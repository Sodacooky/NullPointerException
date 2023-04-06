import requests, { baseUrl } from "@/api/requests";

export function getUserInfo(userId) {
  return requests.get("/service-user/getUserInfo", {
    params: { userId: userId },
  });
}

export function getUserAvatarUrl(avatarFilename) {
  if (avatarFilename === undefined || avatarFilename === "default") {
    return "/default.jpg";
  } else {
    return baseUrl + "/service-user/avatar/" + avatarFilename;
  }
}
