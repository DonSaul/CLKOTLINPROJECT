import { useMutation } from "react-query";
import { ENDPOINTS } from "../../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../../helpers/constants";
import { toast } from "react-toastify";

const sendMessage = async (data) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

  const res = await fetch(ENDPOINTS.sendMessage, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });

  if (res.ok) {
    // If message sending is successful, send a notification
    await sendNotification(data.receiverUserName);
  }

  return res;
};

const sendNotification = async (receiverUserName) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);
  console.log(receiverUserName);
  const res = await fetch(
    `${ENDPOINTS.sendMessageNotification}/${receiverUserName}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );

  console.log(res);
  return res;
};

export const useSendMessage = () => {
  return useMutation(sendMessage, {
    onSuccess: (res) => {
      if (res.status === 403) {
        toast.error("You are not allowed to send messages");
      } else {
        console.log("Message sent successfully!");
        // Handle success as needed
      }
    },
    onError: (_err) => {
      toast.error("Error sending message");
      console.error("Error sending message:", _err);
    },
  });
};
