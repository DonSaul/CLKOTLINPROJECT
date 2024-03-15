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
  getSkills: createUrl("skills"),
  register:createUrl("auth/register"),
  searchCandidates:createUrl("candidates/search"),
  getSkills:createUrl("skills"),
  submitCV:createUrl("cvs"),
  login:createUrl("auth/login"),
  getJobFamilies:createUrl("job-family"),
  users:createUrl("users"),
  vacancy:createUrl("vacancy"),
  searchVacancies:createUrl("vacancy/search"),
  userCv:createUrl("my-account/cv"),
  createUser:createUrl("users/create"),
  application:createUrl("application"),
  getUsers:createUrl("users/all"),
  getConversations:createUrl("conversation/user/all"),
  getCurrentConversationMessages:createUrl("conversation/messages"),
  sendMessage:createUrl("conversation/send-message"),
};
