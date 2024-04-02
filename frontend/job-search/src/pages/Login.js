import { useState } from "react";
import { TextField } from "@mui/material";
import { useEffect } from "react";
import Button from "@mui/material/Button";
import CardContainer from "../components/CardContainer";
import TextFieldPassword from "../components/TextFieldPassword";
import { Typography } from "@mui/material";
import { Link } from "react-router-dom";
import { useLogin } from "../hooks/useLogin";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const location = useLocation();
  let state = location.state;

  let fromPathname = state && state.from && state.from.pathname;

  let from = fromPathname || "/";

  const { mutate, isError, isSuccess } = useLogin();

  useEffect(() => {
    if (isSuccess) {
      navigate(from, { replace: true });
      //navigate('/vacancies');
    }
  }, [isSuccess, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    let formData = {
      username,
      password,
    };

    try {
      await mutate(formData);
    } catch (error) {}
  };

  return (
    <div>
      <CardContainer width="xs">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            fullWidth
            margin="normal"
            required
          />
          <TextFieldPassword
            password={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            //onClick={handleSubmit}
            //fullWidth
          >
            Login
          </Button>
        </form>

        <Typography variant="body2" sx={{ marginTop: 2 }}>
          Don't have an account?<Link to="/register"> Register </Link>
        </Typography>

        <Typography variant="body2">
          Forgot your password? <Link to="/forgot-password">Click here</Link>
        </Typography>
      </CardContainer>
    </div>
  );
};

export default Login;
