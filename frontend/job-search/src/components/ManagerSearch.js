import { Autocomplete, Button, Grid, TextField, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import useJobFamily from '../hooks/useJobFamily';
import useSearchCandidates from '../hooks/useSearchCandidates';
import CardContainer from './CardContainer';
import WorkIcon from '@mui/icons-material/Work';
import EmojiPeopleIcon from '@mui/icons-material/EmojiPeople';
import DescriptionIcon from '@mui/icons-material/Description';
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import BusinessIcon from '@mui/icons-material/Business';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import { EmojiEventsTwoTone } from '@mui/icons-material';

export const CandidatesFilter = ({ setData }) => {
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

  const handleApplyFilter = async () => {
    try {
      const fetchedData = await refetch(salary, jobFamily, yearsOfExperience);
      setData(fetchedData.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleResetFilter = () => {
    setYearsOfExperience('');
    setJobFamily('');
    setSalary('');
  };

  // Ejecutar la búsqueda al cargar la página
  useEffect(() => {
    handleApplyFilter();
  }, []);

  // Ejecutar la búsqueda cada vez que cambie algún filtro
  useEffect(() => {
    handleApplyFilter();
  }, [yearsOfExperience, jobFamily, salary]);

  return (
    <div>
      <CardContainer>
        <Typography variant="h6" align="center" gutterBottom>
          <EmojiPeopleIcon fontSize="large" /> Advanced Candidate Search <WorkIcon fontSize="large" />
        </Typography>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={3}>
            <TextField
              label={<><EmojiEventsTwoTone /> Years of Experience</>}
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
              renderInput={(params) => <TextField {...params} label={<><BusinessCenterIcon /> Job Family</>} margin="normal" />}
            />
          </Grid>
          <Grid item xs={3}>
            <TextField
              label={<><MonetizationOnIcon /> Salary</>}
              type="number"
              value={salary}
              onChange={handleSalaryChange}
              fullWidth
              margin="normal"
            />
          </Grid>
          <Grid item xs={3}>
            <Button onClick={handleApplyFilter} variant="contained" color="primary" style={{ marginRight: '8px' }}>
              Apply Filter
            </Button>
            <Button onClick={handleResetFilter} variant="contained" style={{ backgroundColor: '#CCCCCC', color: 'black' }}>
              Reset
            </Button>
          </Grid>
        </Grid>
      </CardContainer>
    </div>
  );
};

export default CandidatesFilter;
