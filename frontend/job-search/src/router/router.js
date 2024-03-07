import Login from "../pages/Login";
import Register from "../pages/Register";
import CV from "../pages/CV";
import Vacancies from "../pages/Vacancies";
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ButtonAppBar from "../components/Toolbar";
import { CreateVacancy } from "../pages/CreateVacancy";
import { CreateUser } from "../pages/CreateUser";
import Home from "../pages/Home";
import Profile from "../pages/Profile";
import Messaging from "../pages/Messaging";
import Notifications from "../pages/Notifications";
import NotFound from "../pages/NotFound";
import { paths } from "./paths";
import { RequireAuth } from "./RequireAuth";
import { ROLES } from "../helpers/constants";
import NotAccess from "../pages/NotAccess";
const RoutesConfig = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path={paths.login} element={<Login />} />
      <Route path={paths.register} element={<Register />} />

      <Route element={<RequireAuth />}>
        <Route path={paths.notifications} element={<Notifications />} />
        <Route path={paths.messaging} element={<Messaging />} />
        <Route path={paths.profile} element={<Profile />} />
        <Route path={paths.vacancies} element={<Vacancies />} />
      </Route>

      <Route element={<RequireAuth role={ROLES.CANDIDATE} />}>
        <Route path={paths.cv} element={<CV />} />

      </Route>
      <Route element={<RequireAuth role={ROLES.MANAGER} />}>
        <Route path={paths.createVacancy} element={<CreateVacancy />} />

      </Route>
      <Route element={<RequireAuth role={ROLES.ADMIN} />}>
        <Route path={paths.createUser} element={<CreateUser />} />

      </Route>

      <Route path={paths.notAccess} element={<NotAccess />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};

const RouterWrapper = () => {
  return <Router>

    <ButtonAppBar></ButtonAppBar>

    {RoutesConfig()}
  </Router>;
};

export default RouterWrapper;