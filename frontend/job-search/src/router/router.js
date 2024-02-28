import Login from "../pages/Login";
import Register from "../pages/Register";
import CV from "../pages/CV";
import Vacancies from "../pages/Vacancies";
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ButtonAppBar from "../components/Toolbar";
import { CreateVacancy } from "../pages/CreateVacancy";
import { CreateUser } from "../pages/CreateUser";

const RoutesConfig = () => {
    return (
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/cv" element={<CV />} />
        <Route path="/vacancies" element={<Vacancies />} />
        <Route path="/vacancies/new" element={<CreateVacancy />} />
        <Route path="/admin/users/new" element={<CreateUser />} />
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