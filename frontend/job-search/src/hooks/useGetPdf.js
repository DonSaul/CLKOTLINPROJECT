import { useQuery } from "react-query";
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";

const fetchUserPdf = async (id, token) => {
  const response = await fetch(`${ENDPOINTS.getUserPdf}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to fetch user PDF");
  }

  const pdfBlob = await response.blob();
  return pdfBlob;
};

export const useGetUserPdf = (id) => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);

  const { data, ...queryInfo } = useQuery(["getUserPdf", id], () => fetchUserPdf(id, token), {
    enabled: !!token,
  });

  return { pdf: data, ...queryInfo };
};
