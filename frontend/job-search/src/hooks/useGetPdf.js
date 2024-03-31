import { useQuery } from "react-query";
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";

const fetchUserPdf = async (id, token) => {
  const url = id ? `${ENDPOINTS.getUserPdf}/${id}` : ENDPOINTS.getUserPdf;
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const response = await fetch(url, { headers });

  if (!response.ok) {
    throw new Error(`Failed to fetch user PDF: ${response.statusText}`);
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
