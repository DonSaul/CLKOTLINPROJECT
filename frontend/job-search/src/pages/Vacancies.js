import React, { useState } from "react";
import CardContainer from "../components/CardContainer";
import { VacancyFilter } from "../components/VacancyFilter";
import VacancyTable from "../components/VacancyTable";
import { CircularProgress } from "@mui/material";
import LoadingSpinner from "../components/LoadingSpinner";

const Vacancies = () => {
  const [data, setData] = useState([]);
  const [isLoadingVacancies, setIsLoadingVacancies] = useState(false);

  return (
    <div>
      <VacancyFilter
        setData={setData}
        setIsLoadingVacancies={setIsLoadingVacancies}
      ></VacancyFilter>

      <CardContainer width="xl">
        {isLoadingVacancies ? (
          <LoadingSpinner></LoadingSpinner>
        ) : (
          <>
            {data ? (
              <VacancyTable dataFromQuery={data}></VacancyTable>
            ) : (
              <p>No vacancies Found!</p>
            )}
          </>
        )}
      </CardContainer>
    </div>
  );
};

export default Vacancies;
