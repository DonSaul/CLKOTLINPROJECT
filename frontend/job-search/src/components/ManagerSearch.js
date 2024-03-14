import React, { useState } from 'react';
import { useSearchCandidates } from '../hooks/useSearchCandidates';
import { TextField, Button, Container, Typography } from '@material-ui/core';

const ManagerSearch = () => {
  const [filters, setFilters] = useState({
    yearsOfExperience: null,
    jobFamily: null,
    salaryExpectation: null
  });

  const { data: candidates, isLoading, error } = useSearchCandidates(filters);

  const handleInputChange = (event) => {
    setFilters({
      ...filters,
      [event.target.name]: event.target.value
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
  };

  if (isLoading) return 'Loading...';
  if (error) return 'An error has occurred: ' + error.message;

  return (
    <Container>
      <Typography variant="h4">Manager Search</Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Years of Experience"
          type="number"
          name="yearsOfExperience"
          onChange={handleInputChange}
        />
        <TextField
          label="Job Family"
          type="text"
          name="jobFamily"
          onChange={handleInputChange}
        />
        <TextField
          label="Salary Expectation"
          type="number"
          name="salaryExpectation"
          onChange={handleInputChange}
        />
        <Button type="submit" variant="contained" color="primary">
          Search
        </Button>
      </form>
      {candidates && candidates.map(candidate => (
        <div key={candidate.id}>
          <Typography variant="h6">{candidate.name}</Typography>
          <Typography>{candidate.yearsOfExperience} years of experience</Typography>
          <Typography>Job Family: {candidate.jobFamily}</Typography>
          <Typography>Salary Expectation: {candidate.salaryExpectation}</Typography>
        </div>
      ))}
    </Container>
  );
};

export default ManagerSearch;
