import React, { useState } from 'react';
import VacancyTable from '../components/VacancyTable';
import { VacancyFilter } from '../components/VacancyFilter';
import CardContainer from '../components/CardContainer';

//test
import { Delete, Edit, Visibility } from '@mui/icons-material';
import { useMemo } from 'react';
import { Button } from '@mui/material';
import { useAuth } from '../helpers/userContext';
import { ROLES } from '../helpers/constants';
import StandardTable from '../components/StandardTable';
import { useApplyVacancy } from '../hooks/useApplyVacancy';
const Vacancies = () => {

  const { getUserRole } = useAuth();
  const { mutate: applyToVacancy, isError, isSuccess } = useApplyVacancy();

  const [data, setData] = useState([]);


  const applyVacancy = (rowData) => {
    console.log("applyVacancy");
  };

  const visitAction = (row) => {
    console.log("visitAction");
  };

  const editAction = (row) => {
    console.log("editAction");
  };

  const deleteAction = (row) => {
    console.log("deleteAction");
  };

  const handleApply = (rowData) => {

    console.log('Applying to vacancy:', rowData);


    let applicationData =
    {
      vacancyId: rowData.id,



    }


    applyToVacancy(applicationData);



  };


  const actions = [
    { 
        label: "Apply", 
        action: () => applyVacancy(row), 
        icon: <Visibility />
    },
    { 
        label: "Visit", 
        action: () => visitAction(row), 
        icon: <Visibility />
    },
    { 
        label: "Edit", 
        action: () => editAction(row), 
        icon: <Edit />
    },
    { 
        label: "Delete", 
        action: () => deleteAction(row),
        icon: <Delete />
    }
];


  const columnsVacancies = useMemo(
    () => [
      {
        accessorKey: 'jobFamilyName',
        header: 'Category',
      },
      {
        accessorKey: 'name',
        header: 'Name',
      },
      {
        accessorKey: 'yearsOfExperience',
        header: 'Years of experience',

      },
      {
        accessorKey: 'salaryExpectation',
        header: 'Salary',
      },
      {
        accessorKey: 'vacancyId',
        header: 'ID',
        hidden: true,
      },
      {
        id: 'applyButton',
        header: 'Status',
        Cell: ({ row }) => (
          <Button variant="contained" color="primary" onClick={() => handleApply(row.original)} disabled={getUserRole() !== ROLES.CANDIDATE}>
            Apply
          </Button>
        ),
      },
    ],
    [],
  );


  return (
    <div >
      <VacancyFilter setData={setData}></VacancyFilter>

      <CardContainer width='xl'>
        <VacancyTable dataFromQuery={data}></VacancyTable>


        <StandardTable
          columns={columnsVacancies}
          data={data}
          actions={actions}


        > </StandardTable>
      </CardContainer>

    </div>
  );
};

export default Vacancies;