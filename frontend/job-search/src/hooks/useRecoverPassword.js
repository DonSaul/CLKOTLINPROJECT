import { useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { ENDPOINTS } from "../helpers/endpoints";

function useRecoverPassword() {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

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
        toast.success("Password recovery email sent successfully.");
      } else {
        const errorData = await response.json();
        throw new Error(
          errorData.message || "Failed to send reset password email."
        );
      }
    } catch (error) {
      setLoading(false);
      setError(error); // Set the error state
      setMessage(
        `Failed to send reset password email. ${error.message || "Please try again later."}`
      );
      toast.error(message); // Display the concatenated error message
    }
  };

  return { email, setEmail, message, loading, handleSubmit, error };
}
export default useRecoverPassword;