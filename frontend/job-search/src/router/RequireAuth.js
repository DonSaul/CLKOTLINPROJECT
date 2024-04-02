import { useAuth } from "../helpers/userContext";
import { useLocation } from "react-router-dom";
import { paths } from "./paths";
import { Navigate } from "react-router-dom";
import { Outlet } from "react-router-dom";

export function RequireAuth({ role }) {
  let auth = useAuth();
  let location = useLocation();

  if (!auth.isLoggedIn) {
    console.log("Not loggued in");
    return <Navigate to={paths.login} state={{ from: location }} />;
  }
  if (role && auth.getUserRole() !== role) {
    console.log("Not Authorized");
    return <Navigate to={paths.notAccess} />;
  }
  return <Outlet />;
}
