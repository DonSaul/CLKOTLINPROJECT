import React, { useState } from 'react';
import CardContainer from './CardContainer';
import { TextField } from '@mui/material';
import { Button } from '@mui/material';
import { Grid } from '@mui/material';
import useSearchCandidates from '../hooks/useSearchCandidates';
import useJobFamily from '../hooks/useJobFamily';
import { Autocomplete } from '@mui/material';
import { useEffect } from 'react';
import JobFamilyAutocomplete from './JobFamilyAutocomplete';

export const CandidatesFilter = ({ onFilterChange, setData }) => {
  const [yearsOfExperience, setYearsOfExperience] = useState('');
  const [jobFamily, setJobFamily] = useState('');
  const [salary, setSalary] = useState('');


  const { dataVacancies, refetch } = useSearchCandidates(salary, jobFamily, yearsOfExperience);

  const { data: jobFamilies } = useJobFamily();

  const handleYearsOfExperienceChange = (event) => {
    setYearsOfExperience(event.target.value);
  };
  const handleJobFamilyChange = (newValue) => {
    setJobFamily(newValue ? newValue.id : '');
  };
  const handleSalaryChange = (event) => {
    setSalary(event.target.value);
  };

  //only for test
  useEffect(() => {
    const fetchData = async () => {
      try {
        const initialData = await refetch('', '', '');
        console.log("Initial data fetched,n o params:", initialData);
        setData(initialData.data);
      } catch (error) {
        console.error("Error fetching initial data:", error);
      }
    };

    fetchData();
  }, []);



  const handleApplyFilter = async () => {
    try {
      const fetchedData = await refetch(salary, jobFamily, yearsOfExperience);
      console.log("fetched:", fetchedData);
      setData(fetchedData.data);
    } catch (error) {

      console.error("Error fetching data:", error);
    }
  };

  return (
    <div>
      <CardContainer>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={3}>
            <TextField
              label="Years of Experience"
              type="number"
              value={yearsOfExperience}
              onChange={handleYearsOfExperienceChange}
              fullWidth
              margin="normal"

            />
          </Grid>

          <Grid item xs={3}>
            <Autocomplete
              options={jobFamilies || []}
              getOptionLabel={(option) => option.name || ''}
              value={jobFamilies?.find((job) => job.id === jobFamily) || null}
              isOptionEqualToValue={(option, value) => option.id === value?.id}
              onChange={(e, newValue) => handleJobFamilyChange(newValue)}
              renderInput={(params) => <TextField {...params} label={`Select Job Family`} margin="normal" />}

            />




          </Grid>
          {/* 
                    <JobFamilyAutocomplete
                            onChange={(e, newValue) => handleJobFamilyChange(newValue)}
                            value={jobFamily}
                        />*/}

          <Grid item xs={3}>
            <TextField
              label="Salary"
              type="number"
              value={salary}
              onChange={handleSalaryChange}
              fullWidth
              margin="normal"
            />
          </Grid>

          <Grid item xs={3}>
            <Button onClick={handleApplyFilter} variant="contained" color="primary">
              Apply Filter
            </Button>
          </Grid>
        </Grid>
      </CardContainer>






    </div>
  );
};

export default CandidatesFilter;