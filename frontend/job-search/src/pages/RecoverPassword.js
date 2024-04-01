import { TextField, Button, Typography, Link } from "@mui/material";
import useRecoverPassword from "../hooks/useRecoverPassword";
import CardContainer from "../components/CardContainer";

const RecoverPassword = () => {
  const { email, setEmail, message, handleSubmit, error } = useRecoverPassword();

  return (
    <CardContainer width="xs">
      <div>
        <h2>Recover Password</h2>
        <form
          onSubmit={handleSubmit}
          style={{ maxWidth: "300px", margin: "0 auto" }}
        >
          <TextField
            label="Email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            fullWidth
            margin="normal"
            required
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            style={{ marginTop: "10px" }}
          >
            Reset Password
          </Button>
        </form>
        
        {error && (
          <Typography variant="body1" style={{ marginTop: "10px", color: "red" }}>
            {error.message}
          </Typography>
        )}
        {!error && message && (
          <Typography variant="body1" style={{ marginTop: "10px" }}>
            {message}
          </Typography>
        )}
        <Typography variant="body2" style={{ marginTop: "10px" }}>
          Remember your password? <Link to="/login">Login</Link>
        </Typography>
      </div>
    </CardContainer>
  );
};

export default RecoverPassword;