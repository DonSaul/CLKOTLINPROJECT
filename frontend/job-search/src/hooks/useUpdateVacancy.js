import { useMutation } from "react-query";
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";
import { toast } from "react-toastify";

const updateVacancy = async (data) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);
  const res = await fetch(`${ENDPOINTS.vacancy}/${data.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });

  return res;
};

export const useUpdateVacancy = () => {
  return useMutation(updateVacancy, {
    onSuccess: (res) => {
      console.log("UPDATE RES:", res);

      if (res.status === 403) {
        toast.error("You are not allowed to do that");
      } else {
        toast.success("Vacancy updated successfully!");
      }
    },

    onMutate: async (data) => {
      console.log("onMutate data:", data);
    },
    onError: (_err, data, context) => {
      toast.error("Error saving vacancy!");
      console.log("Error on mutation", _err);
      console.log("Error data:", data);
    },
  });
};
