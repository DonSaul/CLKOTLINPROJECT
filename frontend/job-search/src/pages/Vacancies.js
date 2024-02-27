import React, { useState } from 'react';
import VacancyTable from '../components/VacancyTable';
import { VacancyFilter } from '../components/VacancyFilter';
import CardContainer from '../components/CardContainer';

const Vacancies = () => {


  const [data,setData]=useState([]);


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