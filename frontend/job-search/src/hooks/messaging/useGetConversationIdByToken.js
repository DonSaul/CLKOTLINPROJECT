import { useQuery } from "react-query";
import { ENDPOINTS } from "../../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../../helpers/constants";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const fetchConversationIdByToken = async (conversationToken) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);
  const response = await fetch(`${ENDPOINTS.getConversationIdByToken}?token=${conversationToken}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    const errorBody = await response.json();
    throw new Error(errorBody.message);
  }

  return response.json();
};

const useGetConversationIdByToken = (token, setSelectedConversationId) => {
  const navigate = useNavigate();
  return useQuery(["getConversationIdByToken", token], () => fetchConversationIdByToken(token), {
    enabled: !!token,
    retry: false,
    onSuccess: (data) => {
      setSelectedConversationId(data.conversationId);
      navigate('/messaging', { replace: true });
    },
    onError: (error) => {
      toast.error(error.message);
      navigate('/messaging', { replace: true });
    }
  });
};

export default useGetConversationIdByToken;
