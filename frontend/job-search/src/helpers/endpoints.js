//todo: add endpoints

const createUrl = (path) => {
  const port = "8080/";
  const base_url = "http://localhost:" + port;
  return base_url + "api/v1/" + path;
};

export const ENDPOINTS = {
  register: createUrl("register"),
  getSkills: createUrl("skills"),
  submitCV:createUrl("PUT-CV-ENDPOINT-HERE"),
  login: createUrl("login"),
  getJobFamilies: createUrl("job-family")
};
