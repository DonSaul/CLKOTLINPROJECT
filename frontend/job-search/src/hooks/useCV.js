import { useMutation } from "react-query";
import { useQuery } from "react-query";
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";
import { toast } from "react-toastify";

const addCV = async (data) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

  const res = await fetch(ENDPOINTS.submitCV, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });

  return res;
};

export const useCV = () => {
  return useMutation(addCV, {
    onSuccess: (res) => {
      console.log("onSuccess res:", res);

      if (res.status === 403) {
        toast.error("You are not allowed to do that");
      } else if (res.status === 400) {
        toast.error("Invalid CV Data!");
      } else {
        toast.success("CV created successfully!");
      }
    },

    onMutate: async (data) => {
      console.log("onMutate data:", data);
    },
    onError: (_err, data, context) => {
      toast.error("Error saving CV!");
      console.log("Error on mutation", _err);
      console.log("Error data:", data);
    },
  });
};

//get cv of user
const fetchCV = async (id) => {
  const response = await fetch(`${ENDPOINTS.submitCV}/${id}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${AUTH_TOKEN_NAME}`,
    },
  });
  if (!response.ok) {
    throw new Error("Failed to fetch cv");
  }
  return response.json();
};

const fetchCVByUser = async () => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

  const requestData = {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  };
  //console.log('Request Data:', requestData);

  const response = await fetch(`${ENDPOINTS.userCv}`, requestData);

  //console.log('Request Data:', requestData);
  if (!response.ok) {
    throw new Error("Failed to fetch cv");
  }
  return response.json();
};

export const useGetCurrentUserCv = () => {
  return useQuery("cv-user", fetchCVByUser, {
    refetchOnWindowFocus: false,
    retry: false,
  });
};

const updateCV = async (data) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

  const res = await fetch(`${ENDPOINTS.submitCV}/${data.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });

  return res;
};

export const useUpdateCV = () => {
  return useMutation(updateCV, {
    onSuccess: (res) => {
      console.log("onSuccess res:", res);

      if (res.status === 403) {
        toast.error("You are not allowed to do that");
      } else if (res.status === 400) {
        toast.error("Invalid CV Data!");
      } else {
        toast.success("CV updated successfully!");
      }
    },

    onMutate: async (data) => {
      console.log("onMutate data:", data);
    },
    onError: (_err, data, context) => {
      toast.error("Error updating CV!");
      console.log("Error on mutation", _err);
      console.log("Error data:", data);
    },
  });
};
