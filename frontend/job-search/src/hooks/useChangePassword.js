import { useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { ENDPOINTS } from "../helpers/endpoints";

const useChangePassword = () => {
  const [newPassword, setNewPassword] = useState("");
  const [message, setMessage] = useState("");

  const resetPassword = async (token) => {
    try {
      const response = await fetch(
        `${ENDPOINTS.changePassword}?token=${token}&newPassword=${newPassword}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        setMessage("Password reset successfully.");
        toast.success("Password reset successfully.");
      } else {
        const errorMessage = await response.text();
        setMessage(`Failed to reset password: ${errorMessage}`);
        toast.error(`Failed to reset password: ${errorMessage}`);
      }
    } catch (error) {
      setMessage("An error occurred while resetting password.");
      toast.error("An error occurred while resetting password.");
    }
  };

  return { newPassword, setNewPassword, message, resetPassword };
};

export default useChangePassword;