//todo: add endpoints

const createUrl = (path) => {
  const port = "8080/";
  const base_url = "http://localhost:" + port;
  return base_url + "api/v1/" + path;
};

export const ENDPOINTS = {
  register: createUrl("auth/register"),
  recoverPassword: createUrl("recoverPassword/forgotPassword"),
  changePassword: createUrl("recoverPassword/changePassword"),
  activateNotification: createUrl("users/notification"),
  notification: createUrl("notifications/recipient"),
  notificationIsActive: createUrl("users/notificationStatus"),
  notificationRead: createUrl("notifications/markAsRead"),
  notificationTypes: createUrl("users/activatedNotificationTypes"),
  updateNotificationType: createUrl("users/activateNotificationType"),
  getSkills: createUrl("skills"),
  searchCandidates: createUrl("candidates/search"),
  getCandidatesByApplication: createUrl("candidates/vacancy"),
  submitCV: createUrl("cvs"),
  login: createUrl("auth/login"),
  getJobFamilies: createUrl("job-family"),
  users: createUrl("users"),
  vacancy: createUrl("vacancy"),
  searchVacancies: createUrl("vacancy/search"),
  getVacanciesByManager: createUrl("vacancy/my-vacancies"),
  userCv: createUrl("my-account/cv"),
  createUser: createUrl("users/create"),
  application: createUrl("application"),
  getUsers: createUrl("users/all"),
  getConversations: createUrl("conversation/user/all"),
  getCurrentConversationMessages: createUrl("conversation/messages"),
  getConversationIdByToken: createUrl("conversation/get-conversation-id-by-token"),
  sendMessage: createUrl("conversation/send-message"),
  invitation: createUrl("invitations"),
  candidateProfile: createUrl("profiles"),
  profile: createUrl("profiles/my-profile"),
  sendMessageNotification: createUrl("conversation/sendMessageNotification"),
  updateProfile: createUrl("profiles"),
};
