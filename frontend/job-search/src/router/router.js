import Login from "../pages/Login";
import Register from "../pages/Register";
import CV from "../pages/CV";
import Vacancies from "../pages/Vacancies";
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ButtonAppBar from "../components/Toolbar";


const RoutesConfig = () => {
    return (
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/cv" element={<CV />} />
        <Route path="/vacancies" element={<Vacancies />} />
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