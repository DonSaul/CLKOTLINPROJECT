import { useState } from "react";
import { ENDPOINTS } from "../helpers/endpoints";

function useRecoverPassword() {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      const response = await fetch(ENDPOINTS.recoverPassword, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email }),
      });

      if (response.ok) {
        setLoading(false);
        setMessage(
          "An email with instructions for resetting your password has been sent."
        );
      } else {
        const errorData = await response.json();
        throw new Error(
          errorData.message || "Failed to send reset password email."
        );
      }
    } catch (error) {
      setLoading(false);
      setMessage(
        "Failed to send reset password email. Please try again later."
      );
    }
  };

  return { email, setEmail, message, loading, handleSubmit };
}
export default useRecoverPassword;
