import React, { useState } from 'react';
import CardContainer from '../components/CardContainer';
import { VacancyFilter } from '../components/VacancyFilter';
import VacancyTable from '../components/VacancyTable';

const Vacancies = () => {


  const [data, setData] = useState([]);


  return (
    <div >
      <VacancyFilter setData={setData}></VacancyFilter>

      <CardContainer>
        <VacancyTable dataFromQuery={data}></VacancyTable>
      </CardContainer>

    </div>
  );
};

export default Vacancies;