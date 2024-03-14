import React, { useState } from 'react';
import { useSearchCandidates } from '../hooks/useSearchCandidates';

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
    <div>
      <h1>Manager Search</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Years of Experience:
          <input type="number" name="yearsOfExperience" onChange={handleInputChange} />
        </label>
        <label>
          Job Family:
          <input type="text" name="jobFamily" onChange={handleInputChange} />
        </label>
        <label>
          Salary Expectation:
          <input type="number" name="salaryExpectation" onChange={handleInputChange} />
        </label>
        <button type="submit">Search</button>
      </form>
      {candidates && candidates.map(candidate => (
        <div key={candidate.id}>
          <h2>{candidate.name}</h2>
          <p>{candidate.yearsOfExperience} years of experience</p>
          <p>Job Family: {candidate.jobFamily}</p>
          <p>Salary Expectation: {candidate.salaryExpectation}</p>
        </div>
      ))}
    </div>
  );
};

export default ManagerSearch;
